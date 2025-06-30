package com.projeto.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "anexos")
public class Anexo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome do arquivo é obrigatório")
    @Column(nullable = false)
    private String nomeArquivo;
    
    @NotBlank(message = "Caminho do arquivo é obrigatório")
    @Column(nullable = false)
    private String caminhoArquivo;
    
    @Column(name = "tipo_arquivo")
    private String tipoArquivo;
    
    @Column(name = "tamanho_arquivo")
    private Long tamanhoArquivo;
    
    @Column(name = "descricao")
    private String descricao;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coleta_id", nullable = false)
    private Coleta coleta;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @Column(name = "data_upload")
    private LocalDateTime dataUpload;
    
    @Column(name = "tipo_anexo")
    @Enumerated(EnumType.STRING)
    private TipoAnexo tipoAnexo;
    
    public enum TipoAnexo {
        DOCUMENTO("Documento"),
        IMAGEM("Imagem"),
        COMPROVANTE("Comprovante"),
        OUTRO("Outro");
        
        private final String descricao;
        
        TipoAnexo(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    // Construtores
    public Anexo() {
        this.dataUpload = LocalDateTime.now();
    }
    
    public Anexo(String nomeArquivo, String caminhoArquivo, String tipoArquivo, Long tamanhoArquivo, Usuario usuario) {
        this();
        this.nomeArquivo = nomeArquivo;
        this.caminhoArquivo = caminhoArquivo;
        this.tipoArquivo = tipoArquivo;
        this.tamanhoArquivo = tamanhoArquivo;
        this.usuario = usuario;
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
    
    public LocalDateTime getDataUpload() {
        return dataUpload;
    }
    
    public void setDataUpload(LocalDateTime dataUpload) {
        this.dataUpload = dataUpload;
    }
    
    public TipoAnexo getTipoAnexo() {
        return tipoAnexo;
    }
    
    public void setTipoAnexo(TipoAnexo tipoAnexo) {
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