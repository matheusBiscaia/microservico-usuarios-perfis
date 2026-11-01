package br.ucsal.usuarioperfis.dto;

import br.ucsal.usuarioperfis.model.StatusUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record AtualizarUsuarioRequest(
        @NotBlank(message = "Nome e obrigatorio")
        String nome,

        @Email(message = "Email invalido")
        @NotBlank(message = "Email e obrigatorio")
        String email,

        @NotBlank(message = "Telefone e obrigatorio")
        String telefone,

        StatusUsuario status,
        UUID escolaId,
        UUID cursoId,
        UUID matrizId
) {
}
