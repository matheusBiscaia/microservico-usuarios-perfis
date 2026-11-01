package br.ucsal.usuarioperfis.dto;

import br.ucsal.usuarioperfis.model.Perfil;
import br.ucsal.usuarioperfis.model.StatusUsuario;
import java.util.UUID;

public record LoginResponse(
        UUID id,
        String nome,
        String email,
        Perfil perfil,
        StatusUsuario status,
        String mensagem
) {
}
