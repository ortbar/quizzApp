package com.quizzapp.Controller;


import com.quizzapp.DTO.UserDTO;
import com.quizzapp.DTO.UserProfileUpdateDTO;
import com.quizzapp.Models.UserEntity;
import com.quizzapp.Repository.UserRepository;
import com.quizzapp.service.UserService;
import com.quizzapp.util.JwtUtils;
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
public class editUserController {


    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;


    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getCurrentUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        UserDTO userDTO = userService.findByUsername(username);
        return ResponseEntity.ok(userDTO);
    }

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







