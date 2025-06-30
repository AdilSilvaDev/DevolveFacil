package com.projeto.Controller;

import com.projeto.DTO.LoginDTO;
import com.projeto.DTO.UsuarioDTO;
import com.projeto.Entity.Usuario;
import com.projeto.Repository.UsuarioRepository;
import com.projeto.Service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@Valid @RequestBody LoginDTO loginDTO, HttpSession session) {
        Usuario usuario = authenticationService.authenticate(loginDTO.getEmail(), loginDTO.getSenha());
        session.setAttribute("usuario", usuario);
        return ResponseEntity.ok(new UsuarioDTO(usuario));
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO created = authenticationService.register(usuarioDTO);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO> getMe() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioRepository.findByEmail(auth.getName()).orElse(null);
        if (usuario != null) {
            return ResponseEntity.ok(new UsuarioDTO(usuario));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }
} 