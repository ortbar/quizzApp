package com.quizzapp.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(nullable = false)
    @NotBlank(message = "La contraseña no puede estar vacía")
//    @Pattern(
//            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,100}$",
//            message = "La contraseña debe contener al menos una letra, un número y un carácter especial"
//    )
    private String password;

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

    /* RElacion user-roles */

    @ManyToMany(fetch=FetchType.EAGER, targetEntity = RoleEntity.class,cascade = CascadeType.PERSIST)
    @JoinTable(name="user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

    /* RElacion user-games */

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GameEntity> games;

    /*
    ¿Dónde se configuran las validaciones en Spring Boot?
    en tres lugares:

    1️⃣  En las Entidades (@Entity) 🔥 (Lo que ya hicimos)
    Aquí se colocan validaciones de los datos en sí,
    asegurando que cumplan ciertos requisitos antes de ser procesados.

        - Se aplican automáticamente cada vez que se recibe un objeto Usuario en una petición.
        - Si un dato no cumple las reglas, Spring Boot devuelve un error 400 (Bad Request) sin que tengas que hacer más código.

    2️⃣ En los Controladores (@RestController) con @Valid
    Cuando recibes datos desde una API (ejemplo: JSON de un usuario nuevo),
    debes indicar que quieres validar el objeto.

        - @Valid → Activa las validaciones que pusimos en la entidad Usuario.
        - Si el JSON enviado por el usuario no cumple las validaciones,
         Spring rechaza la petición automáticamente y devuelve un error 400 con los mensajes de validación.

    3️⃣ ? Los @Service pueden hacer validaciones más complejas, como verificar si un email ya existe en la BD.
    */

}
