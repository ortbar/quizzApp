package com.quizzapp.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthCreateUserRequest(
        @NotBlank
        @Size(min=6, message = "El username debe tener al menos 6 caracteres")
        String username,
        @NotBlank
        String password
//        @Valid AuthCreateRoleRequest roleRequest
){


}
