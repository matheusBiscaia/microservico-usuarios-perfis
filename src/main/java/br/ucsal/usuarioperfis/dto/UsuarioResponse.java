package br.ucsal.usuarioperfis.dto;

import br.ucsal.usuarioperfis.model.Perfil;
import br.ucsal.usuarioperfis.model.StatusUsuario;
import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String nome,
        String email,
        String telefone,
        Perfil perfil,
        StatusUsuario status,
        UUID escolaId,
        UUID cursoId,
        UUID matrizId,
        LocalDateTime dataCadastro
) {
}
