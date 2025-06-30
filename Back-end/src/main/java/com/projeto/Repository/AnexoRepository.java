package com.projeto.Repository;

import com.projeto.Entity.Anexo;
import com.projeto.Entity.Coleta;
import com.projeto.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnexoRepository extends JpaRepository<Anexo, Long> {
    
    List<Anexo> findByColeta(Coleta coleta);
    
    List<Anexo> findByColetaOrderByDataUploadDesc(Coleta coleta);
    
    List<Anexo> findByUsuario(Usuario usuario);
    
    List<Anexo> findByTipoAnexo(Anexo.TipoAnexo tipoAnexo);
    
    List<Anexo> findByColetaAndTipoAnexo(Coleta coleta, Anexo.TipoAnexo tipoAnexo);
    
    @Query("SELECT a FROM Anexo a WHERE a.coleta = :coleta AND a.tipoArquivo LIKE 'image/%'")
    List<Anexo> findImagensByColeta(@Param("coleta") Coleta coleta);
    
    @Query("SELECT a FROM Anexo a WHERE a.dataUpload BETWEEN :dataInicio AND :dataFim")
    List<Anexo> findByPeriodo(@Param("dataInicio") LocalDateTime dataInicio, 
                              @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM Anexo a WHERE a.nomeArquivo LIKE %:nome%")
    List<Anexo> findByNomeArquivoContaining(@Param("nome") String nome);
    
    @Query("SELECT a FROM Anexo a WHERE a.coleta = :coleta AND a.tipoArquivo = :tipoArquivo")
    List<Anexo> findByColetaAndTipoArquivo(@Param("coleta") Coleta coleta, 
                                           @Param("tipoArquivo") String tipoArquivo);
    
    @Query("SELECT COUNT(a) FROM Anexo a WHERE a.coleta = :coleta")
    long countByColeta(@Param("coleta") Coleta coleta);
    
    @Query("SELECT COUNT(a) FROM Anexo a WHERE a.coleta = :coleta AND a.tipoArquivo LIKE 'image/%'")
    long countImagensByColeta(@Param("coleta") Coleta coleta);
    
    @Query("SELECT SUM(a.tamanhoArquivo) FROM Anexo a WHERE a.coleta = :coleta")
    Long sumTamanhoByColeta(@Param("coleta") Coleta coleta);
} 