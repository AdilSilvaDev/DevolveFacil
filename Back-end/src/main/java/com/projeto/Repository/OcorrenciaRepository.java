package com.projeto.Repository;

import com.projeto.Entity.Coleta;
import com.projeto.Entity.Ocorrencia;
import com.projeto.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
    
    List<Ocorrencia> findByColeta(Coleta coleta);
    
    List<Ocorrencia> findByColetaOrderByDataOcorrenciaDesc(Coleta coleta);
    
    List<Ocorrencia> findByUsuario(Usuario usuario);
    
    List<Ocorrencia> findByTipo(Ocorrencia.TipoOcorrencia tipo);
    
    List<Ocorrencia> findByAprovada(boolean aprovada);
    
    List<Ocorrencia> findByColetaAndAprovada(Coleta coleta, boolean aprovada);
    
    List<Ocorrencia> findByAprovador(Usuario aprovador);
    
    @Query("SELECT o FROM Ocorrencia o WHERE o.coleta = :coleta AND o.tipo = :tipo")
    List<Ocorrencia> findByColetaAndTipo(@Param("coleta") Coleta coleta, 
                                         @Param("tipo") Ocorrencia.TipoOcorrencia tipo);
    
    @Query("SELECT o FROM Ocorrencia o WHERE o.dataOcorrencia BETWEEN :dataInicio AND :dataFim")
    List<Ocorrencia> findByPeriodo(@Param("dataInicio") LocalDateTime dataInicio, 
                                   @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT o FROM Ocorrencia o WHERE o.coleta = :coleta AND o.precisaAprovacao() = true AND o.aprovada = false")
    List<Ocorrencia> findPendentesAprovacaoByColeta(@Param("coleta") Coleta coleta);
    
    @Query("SELECT o FROM Ocorrencia o WHERE o.precisaAprovacao() = true AND o.aprovada = false")
    List<Ocorrencia> findTodasPendentesAprovacao();
    
    @Query("SELECT COUNT(o) FROM Ocorrencia o WHERE o.coleta = :coleta AND o.tipo = :tipo")
    long countByColetaAndTipo(@Param("coleta") Coleta coleta, 
                              @Param("tipo") Ocorrencia.TipoOcorrencia tipo);
    
    @Query("SELECT COUNT(o) FROM Ocorrencia o WHERE o.coleta = :coleta AND o.aprovada = false")
    long countPendentesByColeta(@Param("coleta") Coleta coleta);
} 