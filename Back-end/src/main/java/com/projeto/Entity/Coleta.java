package com.projeto.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "coletas")
public class Coleta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Código da coleta é obrigatório")
    @Column(unique = true, nullable = false)
    private String codigo;
    
    @NotBlank(message = "Descrição é obrigatória")
    @Column(columnDefinition = "TEXT")
    private String descricao;
    
    @Column(name = "endereco_coleta")
    private String enderecoColeta;
    
    @Column(name = "endereco_entrega")
    private String enderecoEntrega;
    
    @Column(name = "data_prevista_coleta")
    private LocalDateTime dataPrevistaColeta;
    
    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;
    
    @Column(name = "data_fim")
    private LocalDateTime dataFim;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusColeta status = StatusColeta.SOLICITADA;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitante_id", nullable = false)
    private Usuario solicitante;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transportador_id")
    private Usuario transportador;
    
    @Column(name = "arquivo_anexo")
    private String arquivoAnexo;
    
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    @OneToMany(mappedBy = "coleta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ocorrencia> ocorrencias = new ArrayList<>();
    
    @OneToMany(mappedBy = "coleta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Anexo> anexos = new ArrayList<>();
    
    public enum StatusColeta {
        SOLICITADA("Coleta solicitada"),
        COLETADA("Material coletado"),
        ENTREGUE("Material entregue na fábrica"),
        CANCELADA("Coleta cancelada"),
        SINISTRO("Sinistro");
        
        private final String descricao;
        
        StatusColeta(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    // Construtores
    public Coleta() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
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
    
    public StatusColeta getStatus() {
        return status;
    }
    
    public void setStatus(StatusColeta status) {
        this.status = status;
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    public Usuario getSolicitante() {
        return solicitante;
    }
    
    public void setSolicitante(Usuario solicitante) {
        this.solicitante = solicitante;
    }
    
    public Usuario getTransportador() {
        return transportador;
    }
    
    public void setTransportador(Usuario transportador) {
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
    
    public List<Ocorrencia> getOcorrencias() {
        return ocorrencias;
    }
    
    public void setOcorrencias(List<Ocorrencia> ocorrencias) {
        this.ocorrencias = ocorrencias;
    }
    
    public List<Anexo> getAnexos() {
        return anexos;
    }
    
    public void setAnexos(List<Anexo> anexos) {
        this.anexos = anexos;
    }
    
    // Métodos auxiliares
    public void adicionarOcorrencia(Ocorrencia ocorrencia) {
        ocorrencias.add(ocorrencia);
        ocorrencia.setColeta(this);
    }
    
    public void removerOcorrencia(Ocorrencia ocorrencia) {
        ocorrencias.remove(ocorrencia);
        ocorrencia.setColeta(null);
    }
    
    public void adicionarAnexo(Anexo anexo) {
        anexos.add(anexo);
        anexo.setColeta(this);
    }
    
    public void removerAnexo(Anexo anexo) {
        anexos.remove(anexo);
        anexo.setColeta(null);
    }
    
    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
} 