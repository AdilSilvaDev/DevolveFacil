package com.projeto.DTO;

import com.projeto.Entity.Ocorrencia;
import com.projeto.Entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class OcorrenciaDTO {
    
    private Long id;
    
    @NotNull(message = "Tipo da ocorrência é obrigatório")
    private Ocorrencia.TipoOcorrencia tipo;
    
    @NotBlank(message = "Comentário é obrigatório")
    private String comentario;
    
    private Long coletaId;
    
    private UsuarioDTO usuario;
    
    private LocalDateTime dataOcorrencia;
    
    private boolean aprovada;
    
    private LocalDateTime dataAprovacao;
    
    private UsuarioDTO aprovador;
    
    private String comentarioAprovacao;
    
    // Construtores
    public OcorrenciaDTO() {}
    
    public OcorrenciaDTO(Ocorrencia ocorrencia) {
        this.id = ocorrencia.getId();
        this.tipo = ocorrencia.getTipo();
        this.comentario = ocorrencia.getComentario();
        this.dataOcorrencia = ocorrencia.getDataOcorrencia();
        this.aprovada = ocorrencia.isAprovada();
        this.dataAprovacao = ocorrencia.getDataAprovacao();
        this.comentarioAprovacao = ocorrencia.getComentarioAprovacao();
        
        if (ocorrencia.getColeta() != null) {
            this.coletaId = ocorrencia.getColeta().getId();
        }
        
        if (ocorrencia.getUsuario() != null) {
            this.usuario = new UsuarioDTO(ocorrencia.getUsuario());
        }
        
        if (ocorrencia.getAprovador() != null) {
            this.aprovador = new UsuarioDTO(ocorrencia.getAprovador());
        }
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Ocorrencia.TipoOcorrencia getTipo() {
        return tipo;
    }
    
    public void setTipo(Ocorrencia.TipoOcorrencia tipo) {
        this.tipo = tipo;
    }
    
    public String getComentario() {
        return comentario;
    }
    
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public Long getColetaId() {
        return coletaId;
    }
    
    public void setColetaId(Long coletaId) {
        this.coletaId = coletaId;
    }
    
    public UsuarioDTO getUsuario() {
        return usuario;
    }
    
    public void setUsuario(UsuarioDTO usuario) {
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
    }
    
    public LocalDateTime getDataAprovacao() {
        return dataAprovacao;
    }
    
    public void setDataAprovacao(LocalDateTime dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }
    
    public UsuarioDTO getAprovador() {
        return aprovador;
    }
    
    public void setAprovador(UsuarioDTO aprovador) {
        this.aprovador = aprovador;
    }
    
    public String getComentarioAprovacao() {
        return comentarioAprovacao;
    }
    
    public void setComentarioAprovacao(String comentarioAprovacao) {
        this.comentarioAprovacao = comentarioAprovacao;
    }
} 