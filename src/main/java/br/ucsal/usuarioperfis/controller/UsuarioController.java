package br.ucsal.usuarioperfis.controller;

import br.ucsal.usuarioperfis.dto.AtualizarUsuarioRequest;
import br.ucsal.usuarioperfis.dto.CriarUsuarioRequest;
import br.ucsal.usuarioperfis.dto.UsuarioResponse;
import br.ucsal.usuarioperfis.model.Perfil;
import br.ucsal.usuarioperfis.service.UsuarioService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> criar(@Valid @RequestBody CriarUsuarioRequest request) {
        UsuarioResponse response = service.criar(request);
        return ResponseEntity.created(URI.create("/usuarios/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar(@RequestParam(required = false) Perfil perfil) {
        return ResponseEntity.ok(service.listar(perfil));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody AtualizarUsuarioRequest request
    ) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @PutMapping("/{id}/inativar-aluno")
    public ResponseEntity<UsuarioResponse> inativarAluno(@PathVariable UUID id) {
        return ResponseEntity.ok(service.inativarAluno(id));
    }
}
