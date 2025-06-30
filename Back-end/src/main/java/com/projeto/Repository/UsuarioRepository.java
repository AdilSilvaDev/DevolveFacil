package com.projeto.Repository;

import com.projeto.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    List<Usuario> findByTipo(Usuario.TipoUsuario tipo);
    
    List<Usuario> findByAtivo(boolean ativo);
    
    @Query("SELECT u FROM Usuario u WHERE u.tipo = :tipo AND u.ativo = true")
    List<Usuario> findAtivosByTipo(@Param("tipo") Usuario.TipoUsuario tipo);
    
    @Query("SELECT u FROM Usuario u WHERE u.email LIKE %:email%")
    List<Usuario> findByEmailContaining(@Param("email") String email);
    
    @Query("SELECT u FROM Usuario u WHERE u.nome LIKE %:nome%")
    List<Usuario> findByNomeContaining(@Param("nome") String nome);
} 