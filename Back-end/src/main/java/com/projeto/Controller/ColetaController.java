package com.projeto.Controller;

import com.projeto.DTO.ColetaDTO;
import com.projeto.DTO.OcorrenciaDTO;
import com.projeto.Entity.Coleta;
import com.projeto.Entity.Usuario;
import com.projeto.Service.AuthenticationService;
import com.projeto.Service.ColetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coletas")
public class ColetaController {

    @Autowired
    private ColetaService coletaService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<ColetaDTO> criarColeta(@RequestBody ColetaDTO coletaDTO, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario solicitante = (Usuario) authenticationService.loadUserByUsername(userDetails.getUsername());
        ColetaDTO created = coletaService.criarColeta(coletaDTO, solicitante);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<ColetaDTO>> listarMinhasColetas(@AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = (Usuario) authenticationService.loadUserByUsername(userDetails.getUsername());
        List<ColetaDTO> coletas = coletaService.buscarPorUsuario(usuario);
        return ResponseEntity.ok(coletas);
    }

    @GetMapping("/todas")
    public ResponseEntity<List<ColetaDTO>> listarTodas() {
        List<ColetaDTO> coletas = coletaService.buscarTodas();
        return ResponseEntity.ok(coletas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColetaDTO> buscarPorId(@PathVariable Long id) {
        ColetaDTO coleta = coletaService.buscarPorId(id);
        return ResponseEntity.ok(coleta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColetaDTO> atualizarColeta(@PathVariable Long id, @RequestBody ColetaDTO coletaDTO) {
        ColetaDTO updated = coletaService.atualizarColeta(id, coletaDTO);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ColetaDTO> atualizarStatus(@PathVariable Long id, @RequestParam Coleta.StatusColeta status, @RequestParam String comentario, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = (Usuario) authenticationService.loadUserByUsername(userDetails.getUsername());
        ColetaDTO updated = coletaService.atualizarStatus(id, status, comentario, usuario);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarColeta(@PathVariable Long id) {
        coletaService.deletarColeta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/ocorrencias")
    public ResponseEntity<List<OcorrenciaDTO>> listarOcorrencias(@PathVariable Long id) {
        List<OcorrenciaDTO> ocorrencias = coletaService.buscarOcorrencias(id);
        return ResponseEntity.ok(ocorrencias);
    }
} 