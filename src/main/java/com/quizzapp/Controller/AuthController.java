package com.quizzapp.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.quizzapp.DTO.AuthCreateUserRequest;
import com.quizzapp.DTO.AuthLoginRequest;
import com.quizzapp.DTO.AuthResponse;
import com.quizzapp.service.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Operaciones de autenticación y registro")
public class AuthController {


    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Operation(summary = "Registrar un nuevo usuario", description = "Crea un usuario y devuelve el token de autenticación.")
    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest authCreateUserRequest) {
        return new ResponseEntity<>(this.userDetailService.createUser(authCreateUserRequest),HttpStatus.CREATED);
    }


    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y devuelve el token de autenticación.")
    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest) {
        return new ResponseEntity<>(this.userDetailService.loginUser(userRequest), HttpStatus.OK);
    }



}
