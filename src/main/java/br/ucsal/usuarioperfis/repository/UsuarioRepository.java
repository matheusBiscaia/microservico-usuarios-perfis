package br.ucsal.usuarioperfis.repository;

import br.ucsal.usuarioperfis.model.Perfil;
import br.ucsal.usuarioperfis.model.Usuario;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByPerfil(Perfil perfil);
}
