package com.projeto.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "ocorrencias")
public class Ocorrencia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoOcorrencia tipo;
    
    @NotBlank(message = "Comentário é obrigatório")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String comentario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coleta_id", nullable = false)
    private Coleta coleta;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @Column(name = "data_ocorrencia")
    private LocalDateTime dataOcorrencia;
    
    @Column(name = "aprovada")
    private boolean aprovada = false;
    
    @Column(name = "data_aprovacao")
    private LocalDateTime dataAprovacao;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aprovador_id")
    private Usuario aprovador;
    
    @Column(name = "comentario_aprovacao")
    private String comentarioAprovacao;
    
    public enum TipoOcorrencia {
        SOLICITADA("Coleta solicitada"),
        COLETADA("Material coletado"),
        ENTREGUE("Material entregue na fábrica"),
        CANCELADA("Coleta cancelada"),
        SINISTRO("Sinistro");
        
        private final String descricao;
        
        TipoOcorrencia(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    // Construtores
    public Ocorrencia() {
        this.dataOcorrencia = LocalDateTime.now();
    }
    
    public Ocorrencia(TipoOcorrencia tipo, String comentario, Usuario usuario) {
        this();
        this.tipo = tipo;
        this.comentario = comentario;
        this.usuario = usuario;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public TipoOcorrencia getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoOcorrencia tipo) {
        this.tipo = tipo;
    }
    
    public String getComentario() {
        return comentario;
    }
    
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public Coleta getColeta() {
        return coleta;
    }
    
    public void setColeta(Coleta coleta) {
        this.coleta = coleta;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public LocalDateTime getDataOcorrencia() {
        return dataOcorrencia;
    }
    
    public void setDataOcorrencia(LocalDateTime dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }
    
    public boolean isAprovada() {
        return aprovada;
    }
    
    public void setAprovada(boolean aprovada) {
        this.aprovada = aprovada;
        if (aprovada) {
            this.dataAprovacao = LocalDateTime.now();
        } else {
            this.dataAprovacao = null;
        }
    }
    
    public LocalDateTime getDataAprovacao() {
        return dataAprovacao;
    }
    
    public void setDataAprovacao(LocalDateTime dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }
    
    public Usuario getAprovador() {
        return aprovador;
    }
    
    public void setAprovador(Usuario aprovador) {
        this.aprovador = aprovador;
    }
    
    public String getComentarioAprovacao() {
        return comentarioAprovacao;
    }
    
    public void setComentarioAprovacao(String comentarioAprovacao) {
        this.comentarioAprovacao = comentarioAprovacao;
    }
    
    // Métodos auxiliares
    public boolean precisaAprovacao() {
        return tipo == TipoOcorrencia.SOLICITADA || 
               tipo == TipoOcorrencia.CANCELADA || 
               tipo == TipoOcorrencia.SINISTRO;
    }
    
    public void aprovar(Usuario aprovador, String comentario) {
        this.aprovada = true;
        this.aprovador = aprovador;
        this.comentarioAprovacao = comentario;
        this.dataAprovacao = LocalDateTime.now();
    }
} 