package com.projeto.DTO;

import com.projeto.Entity.Coleta;
import com.projeto.Entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ColetaDTO {
    
    private Long id;
    
    @NotBlank(message = "Código da coleta é obrigatório")
    private String codigo;
    
    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;
    
    private String enderecoColeta;
    
    private String enderecoEntrega;
    
    private LocalDateTime dataPrevistaColeta;
    
    private LocalDateTime dataInicio;
    
    private LocalDateTime dataFim;
    
    private Coleta.StatusColeta status;
    
    private UsuarioDTO solicitante;
    
    private UsuarioDTO transportador;
    
    private String arquivoAnexo;
    
    private LocalDateTime dataCriacao;
    
    private LocalDateTime dataAtualizacao;
    
    private List<OcorrenciaDTO> ocorrencias;
    
    private List<AnexoDTO> anexos;
    
    // Construtores
    public ColetaDTO() {}
    
    public ColetaDTO(Coleta coleta) {
        this.id = coleta.getId();
        this.codigo = coleta.getCodigo();
        this.descricao = coleta.getDescricao();
        this.enderecoColeta = coleta.getEnderecoColeta();
        this.enderecoEntrega = coleta.getEnderecoEntrega();
        this.dataPrevistaColeta = coleta.getDataPrevistaColeta();
        this.dataInicio = coleta.getDataInicio();
        this.dataFim = coleta.getDataFim();
        this.status = coleta.getStatus();
        this.arquivoAnexo = coleta.getArquivoAnexo();
        this.dataCriacao = coleta.getDataCriacao();
        this.dataAtualizacao = coleta.getDataAtualizacao();
        
        if (coleta.getSolicitante() != null) {
            this.solicitante = new UsuarioDTO(coleta.getSolicitante());
        }
        
        if (coleta.getTransportador() != null) {
            this.transportador = new UsuarioDTO(coleta.getTransportador());
        }
        
        if (coleta.getOcorrencias() != null) {
            this.ocorrencias = coleta.getOcorrencias().stream()
                    .map(OcorrenciaDTO::new)
                    .collect(Collectors.toList());
        }
        
        if (coleta.getAnexos() != null) {
            this.anexos = coleta.getAnexos().stream()
                    .map(AnexoDTO::new)
                    .collect(Collectors.toList());
        }
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getEnderecoColeta() {
        return enderecoColeta;
    }
    
    public void setEnderecoColeta(String enderecoColeta) {
        this.enderecoColeta = enderecoColeta;
    }
    
    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }
    
    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }
    
    public LocalDateTime getDataPrevistaColeta() {
        return dataPrevistaColeta;
    }
    
    public void setDataPrevistaColeta(LocalDateTime dataPrevistaColeta) {
        this.dataPrevistaColeta = dataPrevistaColeta;
    }
    
    public LocalDateTime getDataInicio() {
        return dataInicio;
    }
    
    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }
    
    public LocalDateTime getDataFim() {
        return dataFim;
    }
    
    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }
    
    public Coleta.StatusColeta getStatus() {
        return status;
    }
    
    public void setStatus(Coleta.StatusColeta status) {
        this.status = status;
    }
    
    public UsuarioDTO getSolicitante() {
        return solicitante;
    }
    
    public void setSolicitante(UsuarioDTO solicitante) {
        this.solicitante = solicitante;
    }
    
    public UsuarioDTO getTransportador() {
        return transportador;
    }
    
    public void setTransportador(UsuarioDTO transportador) {
        this.transportador = transportador;
    }
    
    public String getArquivoAnexo() {
        return arquivoAnexo;
    }
    
    public void setArquivoAnexo(String arquivoAnexo) {
        this.arquivoAnexo = arquivoAnexo;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
    
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
    
    public List<OcorrenciaDTO> getOcorrencias() {
        return ocorrencias;
    }
    
    public void setOcorrencias(List<OcorrenciaDTO> ocorrencias) {
        this.ocorrencias = ocorrencias;
    }
    
    public List<AnexoDTO> getAnexos() {
        return anexos;
    }
    
    public void setAnexos(List<AnexoDTO> anexos) {
        this.anexos = anexos;
    }
} 