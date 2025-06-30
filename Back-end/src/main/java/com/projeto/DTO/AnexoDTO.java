package com.projeto.DTO;

import com.projeto.Entity.Anexo;
import com.projeto.Entity.Usuario;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class AnexoDTO {
    
    private Long id;
    
    @NotBlank(message = "Nome do arquivo é obrigatório")
    private String nomeArquivo;
    
    private String caminhoArquivo;
    
    private String tipoArquivo;
    
    private Long tamanhoArquivo;
    
    private String descricao;
    
    private Long coletaId;
    
    private UsuarioDTO usuario;
    
    private LocalDateTime dataUpload;
    
    private Anexo.TipoAnexo tipoAnexo;
    
    // Construtores
    public AnexoDTO() {}
    
    public AnexoDTO(Anexo anexo) {
        this.id = anexo.getId();
        this.nomeArquivo = anexo.getNomeArquivo();
        this.caminhoArquivo = anexo.getCaminhoArquivo();
        this.tipoArquivo = anexo.getTipoArquivo();
        this.tamanhoArquivo = anexo.getTamanhoArquivo();
        this.descricao = anexo.getDescricao();
        this.dataUpload = anexo.getDataUpload();
        this.tipoAnexo = anexo.getTipoAnexo();
        
        if (anexo.getColeta() != null) {
            this.coletaId = anexo.getColeta().getId();
        }
        
        if (anexo.getUsuario() != null) {
            this.usuario = new UsuarioDTO(anexo.getUsuario());
        }
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNomeArquivo() {
        return nomeArquivo;
    }
    
    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
    
    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }
    
    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }
    
    public String getTipoArquivo() {
        return tipoArquivo;
    }
    
    public void setTipoArquivo(String tipoArquivo) {
        this.tipoArquivo = tipoArquivo;
    }
    
    public Long getTamanhoArquivo() {
        return tamanhoArquivo;
    }
    
    public void setTamanhoArquivo(Long tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
    
    public LocalDateTime getDataUpload() {
        return dataUpload;
    }
    
    public void setDataUpload(LocalDateTime dataUpload) {
        this.dataUpload = dataUpload;
    }
    
    public Anexo.TipoAnexo getTipoAnexo() {
        return tipoAnexo;
    }
    
    public void setTipoAnexo(Anexo.TipoAnexo tipoAnexo) {
        this.tipoAnexo = tipoAnexo;
    }
    
    // Métodos auxiliares
    public boolean isImagem() {
        return tipoArquivo != null && tipoArquivo.toLowerCase().startsWith("image/");
    }
    
    public String getExtensao() {
        if (nomeArquivo != null && nomeArquivo.contains(".")) {
            return nomeArquivo.substring(nomeArquivo.lastIndexOf(".") + 1);
        }
        return "";
    }
    
    public String getTamanhoFormatado() {
        if (tamanhoArquivo == null) return "0 B";
        
        long bytes = tamanhoArquivo;
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        if (bytes < 1024 * 1024 * 1024) return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
        return String.format("%.1f GB", bytes / (1024.0 * 1024.0 * 1024.0));
    }
} 