package com.projeto.Repository;

import com.projeto.Entity.Coleta;
import com.projeto.Entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ColetaRepository extends JpaRepository<Coleta, Long> {
    
    Optional<Coleta> findByCodigo(String codigo);
    
    boolean existsByCodigo(String codigo);
    
    List<Coleta> findBySolicitante(Usuario solicitante);
    
    List<Coleta> findByTransportador(Usuario transportador);
    
    List<Coleta> findByStatus(Coleta.StatusColeta status);
    
    List<Coleta> findBySolicitanteAndStatus(Usuario solicitante, Coleta.StatusColeta status);
    
    List<Coleta> findByTransportadorAndStatus(Usuario transportador, Coleta.StatusColeta status);
    
    @Query("SELECT c FROM Coleta c WHERE c.dataCriacao BETWEEN :dataInicio AND :dataFim")
    List<Coleta> findByPeriodo(@Param("dataInicio") LocalDateTime dataInicio, 
                               @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT c FROM Coleta c WHERE c.solicitante = :usuario OR c.transportador = :usuario")
    List<Coleta> findByUsuario(@Param("usuario") Usuario usuario);
    
    @Query("SELECT c FROM Coleta c WHERE c.solicitante = :usuario OR c.transportador = :usuario ORDER BY c.dataAtualizacao DESC")
    Page<Coleta> findByUsuarioPaginado(@Param("usuario") Usuario usuario, Pageable pageable);
    
    @Query("SELECT c FROM Coleta c WHERE c.codigo LIKE %:codigo% OR c.descricao LIKE %:descricao%")
    List<Coleta> findByCodigoOrDescricaoContaining(@Param("codigo") String codigo, 
                                                   @Param("descricao") String descricao);
    
    @Query("SELECT c FROM Coleta c WHERE c.dataPrevistaColeta BETWEEN :dataInicio AND :dataFim")
    List<Coleta> findByDataPrevistaBetween(@Param("dataInicio") LocalDateTime dataInicio, 
                                          @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT COUNT(c) FROM Coleta c WHERE c.status = :status")
    long countByStatus(@Param("status") Coleta.StatusColeta status);
    
    @Query("SELECT COUNT(c) FROM Coleta c WHERE c.solicitante = :usuario AND c.status = :status")
    long countBySolicitanteAndStatus(@Param("usuario") Usuario usuario, 
                                    @Param("status") Coleta.StatusColeta status);
    
    @Query("SELECT COUNT(c) FROM Coleta c WHERE c.transportador = :usuario AND c.status = :status")
    long countByTransportadorAndStatus(@Param("usuario") Usuario usuario, 
                                      @Param("status") Coleta.StatusColeta status);
} 