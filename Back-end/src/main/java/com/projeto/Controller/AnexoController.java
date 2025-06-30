package com.projeto.Controller;

import com.projeto.DTO.AnexoDTO;
import com.projeto.Entity.Anexo;
import com.projeto.Entity.Coleta;
import com.projeto.Entity.Usuario;
import com.projeto.Repository.AnexoRepository;
import com.projeto.Repository.ColetaRepository;
import com.projeto.Service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/anexos")
public class AnexoController {

    private static final String UPLOAD_DIR = "uploads";

    @Autowired
    private AnexoRepository anexoRepository;

    @Autowired
    private ColetaRepository coletaRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/upload/{coletaId}")
    public ResponseEntity<AnexoDTO> uploadAnexo(@PathVariable Long coletaId,
                                                @RequestParam("file") MultipartFile file,
                                                @RequestParam(value = "descricao", required = false) String descricao,
                                                @RequestParam(value = "tipoAnexo", required = false) Anexo.TipoAnexo tipoAnexo,
                                                @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        Coleta coleta = coletaRepository.findById(coletaId)
                .orElseThrow(() -> new RuntimeException("Coleta não encontrada"));
        Usuario usuario = (Usuario) authenticationService.loadUserByUsername(userDetails.getUsername());

        // Cria diretório se não existir
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String filename = System.currentTimeMillis() + "_" + originalFilename;
        Path filePath = Paths.get(UPLOAD_DIR, filename);
        Files.copy(file.getInputStream(), filePath);

        Anexo anexo = new Anexo();
        anexo.setNomeArquivo(originalFilename);
        anexo.setCaminhoArquivo(filePath.toString());
        anexo.setTipoArquivo(file.getContentType());
        anexo.setTamanhoArquivo(file.getSize());
        anexo.setDescricao(descricao);
        anexo.setColeta(coleta);
        anexo.setUsuario(usuario);
        anexo.setDataUpload(LocalDateTime.now());
        anexo.setTipoAnexo(tipoAnexo);

        Anexo saved = anexoRepository.save(anexo);
        return ResponseEntity.ok(new AnexoDTO(saved));
    }

    @GetMapping("/coleta/{coletaId}")
    public ResponseEntity<List<AnexoDTO>> listarAnexosPorColeta(@PathVariable Long coletaId) {
        Coleta coleta = coletaRepository.findById(coletaId)
                .orElseThrow(() -> new RuntimeException("Coleta não encontrada"));
        List<AnexoDTO> anexos = anexoRepository.findByColetaOrderByDataUploadDesc(coleta)
                .stream().map(AnexoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(anexos);
    }

    @GetMapping("/download/{anexoId}")
    public ResponseEntity<Resource> downloadAnexo(@PathVariable Long anexoId) throws IOException {
        Anexo anexo = anexoRepository.findById(anexoId)
                .orElseThrow(() -> new RuntimeException("Anexo não encontrado"));
        File file = new File(anexo.getCaminhoArquivo());
        if (!file.exists()) {
            throw new RuntimeException("Arquivo não encontrado no servidor");
        }
        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + anexo.getNomeArquivo() + "\"")
                .contentType(MediaType.parseMediaType(anexo.getTipoArquivo()))
                .body(resource);
    }

    @DeleteMapping("/{anexoId}")
    public ResponseEntity<Void> removerAnexo(@PathVariable Long anexoId) {
        Anexo anexo = anexoRepository.findById(anexoId)
                .orElseThrow(() -> new RuntimeException("Anexo não encontrado"));
        File file = new File(anexo.getCaminhoArquivo());
        if (file.exists()) file.delete();
        anexoRepository.delete(anexo);
        return ResponseEntity.noContent().build();
    }
} 