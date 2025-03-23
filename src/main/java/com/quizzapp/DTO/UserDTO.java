package com.quizzapp.DTO;


import com.quizzapp.Models.RoleEntity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
//    @NotBlank(message = "El email no puede estar vacío")
//    @Email(message = "Debe ser un email válido")
//    @Size(max = 80, message = "El email no puede tener más de 80 caracteres")
//    @Pattern(
//            regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$",
//            message = "Solo se permiten correos de Gmail (@gmail.com)"
//    )
    private String email;

//    @NotBlank(message = "El username no puede estar vacío")
//    @Size(min = 5, max = 10, message = "El username debe tener entre 5 y 10 caracteres")
//    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "El username solo puede contener letras, números y guiones bajos")
//    @Column(nullable = false, unique = true, length = 30)
    private String username;

//    @Column(nullable = false)
//    @NotBlank(message = "La contraseña no puede estar vacía")
//    @Pattern(
//            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,100}$",
//            message = "La contraseña debe contener al menos una letra, un número y un carácter especial"
//    )
    private String password;

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
