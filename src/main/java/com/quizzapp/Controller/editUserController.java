package com.quizzapp.Controller;

import com.quizzapp.DTO.UserDTO;
import com.quizzapp.DTO.UserProfileUpdateDTO;
import com.quizzapp.Models.UserEntity;
import com.quizzapp.Repository.UserRepository;
import com.quizzapp.service.UserService;
import com.quizzapp.util.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "api/user")
@Tag(name = "User Profile", description = "Operaciones sobre el perfil del usuario autenticado")
@SecurityRequirement(name = "bearerAuth")
public class editUserController {


    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;


    @Operation(summary = "Obtener perfil propio", description = "Devuelve el perfil del usuario autenticado.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getCurrentUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        UserDTO userDTO = userService.findByUsername(username);
        return ResponseEntity.ok(userDTO);
    }

    @Operation(summary = "Editar perfil propio", description = "Permite al usuario autenticado editar su perfil. Para cambiar la contraseña, debe enviar la contraseña actual, la nueva y repetir la nueva.", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/edit-profile")
    public ResponseEntity<UserDTO> updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                                 @RequestBody @Valid UserProfileUpdateDTO updateDTO) {
        String username = userDetails.getUsername();
        UserDTO updatedUser = userService.updateOwnProfile(username, updateDTO);
        var token = jwtUtils.createTokenFromUsername(updatedUser.getUsername());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(updatedUser);
    }

}







