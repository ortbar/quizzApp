package com.quizzapp.DTO;


import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileUpdateDTO {




    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Elija un email válido")
    private String email;

    @NotBlank(message = "El username no puede estar vacío")
    @Size(min = 5, max = 10, message = "El username debe tener entre 5 y 10 caracteres")
    private String username;

   
    private String currentPassword;

    @Size(min = 5, max = 10, message = "La nueva contraseña debe tener entre 5 y 10 caracteres")
    private String newPassword;

    private String repeatNewPassword;

    @AssertTrue(message = "El password actual no puede estar vacío si quieres cambiar la contraseña")
    public boolean isCurrentPasswordRequired() {
        if ((newPassword != null && !newPassword.isBlank()) || (repeatNewPassword != null && !repeatNewPassword.isBlank())) {
            return currentPassword != null && !currentPassword.isBlank();
        }
        return true;
    }

    // El campo password y token ya no se usan para el cambio de contraseña
}
