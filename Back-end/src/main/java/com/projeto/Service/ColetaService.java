package com.projeto.Service;

import com.projeto.DTO.ColetaDTO;
import com.projeto.DTO.OcorrenciaDTO;
import com.projeto.Entity.Coleta;
import com.projeto.Entity.Ocorrencia;
import com.projeto.Entity.Usuario;
import com.projeto.Repository.ColetaRepository;
import com.projeto.Repository.OcorrenciaRepository;
import com.projeto.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ColetaService {
    
    @Autowired
    private ColetaRepository coletaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;
    
    public ColetaDTO criarColeta(ColetaDTO coletaDTO, Usuario solicitante) {
        // Gerar código único para a coleta
        String codigo = gerarCodigoUnico();
        
        Coleta coleta = new Coleta();
        coleta.setCodigo(codigo);
        coleta.setDescricao(coletaDTO.getDescricao());
        coleta.setEnderecoColeta(coletaDTO.getEnderecoColeta());
        coleta.setEnderecoEntrega(coletaDTO.getEnderecoEntrega());
        coleta.setDataPrevistaColeta(coletaDTO.getDataPrevistaColeta());
        coleta.setSolicitante(solicitante);
        coleta.setStatus(Coleta.StatusColeta.SOLICITADA);
        coleta.setArquivoAnexo(coletaDTO.getArquivoAnexo());
        
        Coleta savedColeta = coletaRepository.save(coleta);
        
        // Criar primeira ocorrência
        Ocorrencia ocorrencia = new Ocorrencia(
                Ocorrencia.TipoOcorrencia.SOLICITADA,
                "Coleta solicitada pelo solicitante",
                solicitante
        );
        ocorrencia.setColeta(savedColeta);
        ocorrenciaRepository.save(ocorrencia);
        
        return new ColetaDTO(savedColeta);
    }
    
    public ColetaDTO atualizarColeta(Long id, ColetaDTO coletaDTO) {
        Coleta coleta = coletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coleta não encontrada"));
        
        coleta.setDescricao(coletaDTO.getDescricao());
        coleta.setEnderecoColeta(coletaDTO.getEnderecoColeta());
        coleta.setEnderecoEntrega(coletaDTO.getEnderecoEntrega());
        coleta.setDataPrevistaColeta(coletaDTO.getDataPrevistaColeta());
        coleta.setArquivoAnexo(coletaDTO.getArquivoAnexo());
        
        Coleta updatedColeta = coletaRepository.save(coleta);
        return new ColetaDTO(updatedColeta);
    }
    
    public ColetaDTO atribuirTransportador(Long coletaId, Long transportadorId) {
        Coleta coleta = coletaRepository.findById(coletaId)
                .orElseThrow(() -> new RuntimeException("Coleta não encontrada"));
        
        Usuario transportador = usuarioRepository.findById(transportadorId)
                .orElseThrow(() -> new RuntimeException("Transportador não encontrado"));
        
        if (transportador.getTipo() != Usuario.TipoUsuario.TRANSPORTADOR) {
            throw new RuntimeException("Usuário não é um transportador");
        }
        
        coleta.setTransportador(transportador);
        coleta.setDataInicio(LocalDateTime.now());
        
        Coleta updatedColeta = coletaRepository.save(coleta);
        return new ColetaDTO(updatedColeta);
    }
    
    public ColetaDTO atualizarStatus(Long coletaId, Coleta.StatusColeta novoStatus, String comentario, Usuario usuario) {
        Coleta coleta = coletaRepository.findById(coletaId)
                .orElseThrow(() -> new RuntimeException("Coleta não encontrada"));
        
        // Verificar se o usuário tem permissão para atualizar o status
        if (!podeAtualizarStatus(coleta, usuario, novoStatus)) {
            throw new RuntimeException("Usuário não tem permissão para atualizar o status");
        }
        
        coleta.setStatus(novoStatus);
        
        // Definir data de fim se for finalizada
        if (novoStatus == Coleta.StatusColeta.ENTREGUE || novoStatus == Coleta.StatusColeta.CANCELADA) {
            coleta.setDataFim(LocalDateTime.now());
        }
        
        Coleta updatedColeta = coletaRepository.save(coleta);
        
        // Criar ocorrência
        Ocorrencia ocorrencia = new Ocorrencia(
                mapearStatusParaTipoOcorrencia(novoStatus),
                comentario,
                usuario
        );
        ocorrencia.setColeta(updatedColeta);
        ocorrenciaRepository.save(ocorrencia);
        
        return new ColetaDTO(updatedColeta);
    }
    
    public ColetaDTO buscarPorId(Long id) {
        Coleta coleta = coletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coleta não encontrada"));
        return new ColetaDTO(coleta);
    }
    
    public List<ColetaDTO> buscarPorUsuario(Usuario usuario) {
        List<Coleta> coletas = coletaRepository.findByUsuario(usuario);
        return coletas.stream()
                .map(ColetaDTO::new)
                .collect(Collectors.toList());
    }
    
    public Page<ColetaDTO> buscarPorUsuarioPaginado(Usuario usuario, Pageable pageable) {
        Page<Coleta> coletas = coletaRepository.findByUsuarioPaginado(usuario, pageable);
        return coletas.map(ColetaDTO::new);
    }
    
    public List<ColetaDTO> buscarPorStatus(Coleta.StatusColeta status) {
        List<Coleta> coletas = coletaRepository.findByStatus(status);
        return coletas.stream()
                .map(ColetaDTO::new)
                .collect(Collectors.toList());
    }
    
    public List<ColetaDTO> buscarTodas() {
        List<Coleta> coletas = coletaRepository.findAll();
        return coletas.stream()
                .map(ColetaDTO::new)
                .collect(Collectors.toList());
    }
    
    public void deletarColeta(Long id) {
        Coleta coleta = coletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coleta não encontrada"));
        
        // Verificar se pode ser deletada
        if (coleta.getStatus() != Coleta.StatusColeta.SOLICITADA) {
            throw new RuntimeException("Coleta não pode ser deletada pois já foi iniciada");
        }
        
        coletaRepository.delete(coleta);
    }
    
    public List<OcorrenciaDTO> buscarOcorrencias(Long coletaId) {
        Coleta coleta = coletaRepository.findById(coletaId)
                .orElseThrow(() -> new RuntimeException("Coleta não encontrada"));
        
        List<Ocorrencia> ocorrencias = ocorrenciaRepository.findByColetaOrderByDataOcorrenciaDesc(coleta);
        return ocorrencias.stream()
                .map(OcorrenciaDTO::new)
                .collect(Collectors.toList());
    }
    
    public OcorrenciaDTO aprovarOcorrencia(Long ocorrenciaId, String comentarioAprovacao, Usuario aprovador) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(ocorrenciaId)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));
        
        if (ocorrencia.isAprovada()) {
            throw new RuntimeException("Ocorrência já foi aprovada");
        }
        
        // Verificar se o aprovador tem permissão
        if (!podeAprovarOcorrencia(ocorrencia, aprovador)) {
            throw new RuntimeException("Usuário não tem permissão para aprovar esta ocorrência");
        }
        
        ocorrencia.aprovar(aprovador, comentarioAprovacao);
        Ocorrencia savedOcorrencia = ocorrenciaRepository.save(ocorrencia);
        
        return new OcorrenciaDTO(savedOcorrencia);
    }
    
    public List<OcorrenciaDTO> buscarOcorrenciasPendentes() {
        List<Ocorrencia> ocorrencias = ocorrenciaRepository.findTodasPendentesAprovacao();
        return ocorrencias.stream()
                .map(OcorrenciaDTO::new)
                .collect(Collectors.toList());
    }
    
    // Métodos auxiliares
    private String gerarCodigoUnico() {
        String codigo;
        do {
            codigo = "COL" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (coletaRepository.existsByCodigo(codigo));
        return codigo;
    }
    
    private boolean podeAtualizarStatus(Coleta coleta, Usuario usuario, Coleta.StatusColeta novoStatus) {
        // Solicitante pode atualizar qualquer status
        if (coleta.getSolicitante().getId().equals(usuario.getId())) {
            return true;
        }
        
        // Transportador só pode atualizar status específicos
        if (coleta.getTransportador() != null && coleta.getTransportador().getId().equals(usuario.getId())) {
            return novoStatus == Coleta.StatusColeta.COLETADA || 
                   novoStatus == Coleta.StatusColeta.ENTREGUE ||
                   novoStatus == Coleta.StatusColeta.SINISTRO;
        }
        
        return false;
    }
    
    private Ocorrencia.TipoOcorrencia mapearStatusParaTipoOcorrencia(Coleta.StatusColeta status) {
        switch (status) {
            case SOLICITADA:
                return Ocorrencia.TipoOcorrencia.SOLICITADA;
            case COLETADA:
                return Ocorrencia.TipoOcorrencia.COLETADA;
            case ENTREGUE:
                return Ocorrencia.TipoOcorrencia.ENTREGUE;
            case CANCELADA:
                return Ocorrencia.TipoOcorrencia.CANCELADA;
            case SINISTRO:
                return Ocorrencia.TipoOcorrencia.SINISTRO;
            default:
                throw new RuntimeException("Status não mapeado");
        }
    }
    
    private boolean podeAprovarOcorrencia(Ocorrencia ocorrencia, Usuario aprovador) {
        // Solicitante pode aprovar ocorrências de cancelamento e sinistro
        if (ocorrencia.getColeta().getSolicitante().getId().equals(aprovador.getId())) {
            return ocorrencia.getTipo() == Ocorrencia.TipoOcorrencia.CANCELADA ||
                   ocorrencia.getTipo() == Ocorrencia.TipoOcorrencia.SINISTRO;
        }
        
        // Transportador pode aprovar ocorrências de solicitação
        if (ocorrencia.getColeta().getTransportador() != null && 
            ocorrencia.getColeta().getTransportador().getId().equals(aprovador.getId())) {
            return ocorrencia.getTipo() == Ocorrencia.TipoOcorrencia.SOLICITADA;
        }
        
        return false;
    }
} 