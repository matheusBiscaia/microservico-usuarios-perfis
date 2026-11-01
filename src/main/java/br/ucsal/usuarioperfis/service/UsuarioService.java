package br.ucsal.usuarioperfis.service;

import br.ucsal.usuarioperfis.dto.AtualizarUsuarioRequest;
import br.ucsal.usuarioperfis.dto.CriarUsuarioRequest;
import br.ucsal.usuarioperfis.dto.LoginRequest;
import br.ucsal.usuarioperfis.dto.LoginResponse;
import br.ucsal.usuarioperfis.dto.UsuarioResponse;
import br.ucsal.usuarioperfis.exception.RegraNegocioException;
import br.ucsal.usuarioperfis.model.Perfil;
import br.ucsal.usuarioperfis.model.StatusUsuario;
import br.ucsal.usuarioperfis.model.Usuario;
import br.ucsal.usuarioperfis.repository.UsuarioRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public UsuarioResponse criar(CriarUsuarioRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new RegraNegocioException("Email ja cadastrado");
        }

        validarVinculosPorPerfil(request.perfil(), request.escolaId(), request.cursoId(), request.matrizId());

        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(request.senha());
        usuario.setTelefone(request.telefone());
        usuario.setPerfil(request.perfil());
        usuario.setStatus(request.status() == null ? StatusUsuario.ATIVO : request.status());
        usuario.setEscolaId(request.escolaId());
        usuario.setCursoId(request.cursoId());
        usuario.setMatrizId(request.matrizId());

        return mapear(repository.save(usuario));
    }

    public List<UsuarioResponse> listar(Perfil perfil) {
        List<Usuario> usuarios = perfil == null ? repository.findAll() : repository.findByPerfil(perfil);
        return usuarios.stream().map(this::mapear).toList();
    }

    public UsuarioResponse buscarPorId(UUID id) {
        return mapear(obterUsuario(id));
    }

    public UsuarioResponse atualizar(UUID id, AtualizarUsuarioRequest request) {
        Usuario usuario = obterUsuario(id);

        repository.findByEmail(request.email())
                .filter(usuarioEncontrado -> !usuarioEncontrado.getId().equals(id))
                .ifPresent(usuarioEncontrado -> {
                    throw new RegraNegocioException("Email ja cadastrado");
                });

        validarVinculosPorPerfil(usuario.getPerfil(), request.escolaId(), request.cursoId(), request.matrizId());

        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setTelefone(request.telefone());
        usuario.setStatus(request.status() == null ? usuario.getStatus() : request.status());
        usuario.setEscolaId(request.escolaId());
        usuario.setCursoId(request.cursoId());
        usuario.setMatrizId(request.matrizId());

        return mapear(repository.save(usuario));
    }

    public UsuarioResponse inativarAluno(UUID id) {
        Usuario usuario = obterUsuario(id);

        if (usuario.getPerfil() != Perfil.ALUNO) {
            throw new RegraNegocioException("Somente usuarios com perfil ALUNO podem ser inativados por este endpoint");
        }

        usuario.setStatus(StatusUsuario.INATIVO);
        return mapear(repository.save(usuario));
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = repository.findByEmail(request.email())
                .orElseThrow(() -> new RegraNegocioException("Credenciais invalidas"));

        if (!usuario.getSenha().equals(request.senha())) {
            throw new RegraNegocioException("Credenciais invalidas");
        }

        if (usuario.getStatus() == StatusUsuario.INATIVO) {
            throw new RegraNegocioException("Usuario inativo");
        }

        return new LoginResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil(),
                usuario.getStatus(),
                "Login realizado com sucesso"
        );
    }

    private Usuario obterUsuario(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado"));
    }

    private void validarVinculosPorPerfil(Perfil perfil, UUID escolaId, UUID cursoId, UUID matrizId) {
        if (perfil == Perfil.PROFESSOR && escolaId == null) {
            throw new RegraNegocioException("Professor deve estar vinculado a uma escola");
        }

        if (perfil == Perfil.ALUNO && (cursoId == null || matrizId == null)) {
            throw new RegraNegocioException("Aluno deve estar vinculado a um curso e a uma matriz");
        }
    }

    private UsuarioResponse mapear(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getPerfil(),
                usuario.getStatus(),
                usuario.getEscolaId(),
                usuario.getCursoId(),
                usuario.getMatrizId(),
                usuario.getDataCadastro()
        );
    }
}
