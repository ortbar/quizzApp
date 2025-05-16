package com.quizzapp.DTO;


import com.quizzapp.Models.RoleEntity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;

//    @Column(nullable = false, unique = true, length = 80)

//    @Email(message = "Debe ser un email válido")

//    @Pattern(
//            regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$",
//            message = "Solo se permiten correos de Gmail (@gmail.com)"
//    )
    @NotBlank(message = "El email no puede estar vacío")
    @Size(max = 80, message = "El email no puede tener más de 80 caracteres")
    @Email(message = "Elija un email válido")
    private String email;


//
//    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "El username solo puede contener letras, números y guiones bajos")
//    @Column(nullable = false, unique = true, length = 30)
    @Size(min = 5, max = 10, message = "El username debe tener entre 5 y 10 caracteres")
    @NotBlank(message = "El username no puede estar vacío")
    private String username;

//    @Column(nullable = false)

//    @Pattern(
//            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,100}$",
//            message = "La contraseña debe contener al menos una letra, un número y un carácter especial"
//    )

    @Size(min = 5, max = 10, message = "El password debe tener entre 5 y 10 caracteres")
    private String password;

    @NotEmpty(message = "El usuario debe tener al menos un rol.")
    private Set<String> roles = new HashSet<>();

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "acconunt_No_expired")
    private boolean accountNotExpired;

    @Column(name = "account_No_locked")
    private boolean accountNotLocked;

    @Column(name = "credential_No_expired")
    private boolean credentialNotExpired;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
