package br.ucsal.usuarioperfis.dto;

import br.ucsal.usuarioperfis.model.Perfil;
import br.ucsal.usuarioperfis.model.StatusUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record CriarUsuarioRequest(
        @NotBlank(message = "Nome e obrigatorio")
        String nome,

        @Email(message = "Email invalido")
        @NotBlank(message = "Email e obrigatorio")
        String email,

        @Size(min = 6, message = "Senha deve ter no minimo 6 caracteres")
        @NotBlank(message = "Senha e obrigatoria")
        String senha,

        @NotBlank(message = "Telefone e obrigatorio")
        String telefone,

        @NotNull(message = "Perfil e obrigatorio")
        Perfil perfil,

        StatusUsuario status,
        UUID escolaId,
        UUID cursoId,
        UUID matrizId
) {
}
