package com.quizzapp.Controller.adminController;


import com.quizzapp.DTO.UserDTO;
import com.quizzapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "admin/users")
@Tag(name = "Admin Users", description = "Operaciones administrativas sobre usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private UserService userService;



    @Operation(summary = "Listar todos los usuarios", description = "Devuelve una lista de todos los usuarios registrados.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/findAll")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Buscar usuario por ID", description = "Devuelve los datos de un usuario específico por su ID.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/findUser/{id}")
    public ResponseEntity<UserDTO>getUser(@PathVariable Long id) {
        UserDTO user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Crear usuario", description = "Crea un nuevo usuario desde el panel de administración.", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/createUser")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO savedUser = userService.saveUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente.", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserDTO> updateUser( @PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        UserDTO user = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su ID.", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("deleteUser/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }




}
