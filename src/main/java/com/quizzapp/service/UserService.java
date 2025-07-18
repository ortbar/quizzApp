package com.quizzapp.service;

import com.quizzapp.DTO.UserProfileUpdateDTO;
import com.quizzapp.Models.Erole;
import com.quizzapp.Models.RoleEntity;
import com.quizzapp.DTO.UserDTO;
import com.quizzapp.Models.UserEntity;
import com.quizzapp.Repository.RoleRepository;
import com.quizzapp.Repository.UserRepository;
import com.quizzapp.exceptions.EmailAlreadyExistsException;
import com.quizzapp.exceptions.UserNotFoundException;
import com.quizzapp.exceptions.UsernameInUseException;
import com.quizzapp.exceptions.IncorrectPasswordException;
import com.quizzapp.util.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;



    public UserDTO findById(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            UserEntity userEntity = userOptional.get();

            // Convertir UserEntity a UserDTO usando Builder
            UserDTO userDTO = UserDTO.builder()
                    .id(userEntity.getId())
                    .email(userEntity.getEmail())
                    .username(userEntity.getUsername())
                    .roles(userEntity.getRoles().stream()
                            .map(role->role.getRoleEnum().name())
                            .collect(Collectors.toSet()))
                    .isEnabled(userEntity.isEnabled())
                    .accountNotExpired(userEntity.isAccountNotExpired())
                    .accountNotLocked(userEntity.isAccountNotLocked())
                    .credentialNotExpired(userEntity.isCredentialNotExpired())
                    .createdAt(userEntity.getCreatedAt())
                    .build();

            return userDTO;
        } else {
            throw new UserNotFoundException("Usuario con ID " + id + " no encontrado");
        }
    }



    public List<UserDTO> findAll() {
        List<UserEntity> userEntities = userRepository.findAll();

        // Convertir cada UserEntity a UserDTO usando Builder
        return userEntities.stream()
                .map(userEntity -> UserDTO.builder()
                        .id(userEntity.getId())
                        .email(userEntity.getEmail())
                        .username(userEntity.getUsername())
                        .roles(userEntity.getRoles().stream() // Convertir roles a nombres
                                .map(role -> role.getRoleEnum().name())
                                .collect(Collectors.toSet()))// Suponiendo que RoleEntity tiene un campo "name"
                        .isEnabled(userEntity.isEnabled())
                        .accountNotExpired(userEntity.isAccountNotExpired())
                        .accountNotLocked(userEntity.isAccountNotLocked())
                        .credentialNotExpired(userEntity.isCredentialNotExpired())
                        .createdAt(userEntity.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }





    public UserDTO saveUser(UserDTO userDTO) {

        RoleEntity userRole = roleRepository.findByRoleEnum(Erole.USER)
                .orElseGet(() -> {
                    RoleEntity newRole = RoleEntity.builder()
                            .roleEnum(Erole.USER)
                            .build();
                    return roleRepository.save(newRole);
                });

        // Convertir UserDTO a UserEntity
        UserEntity userEntity = UserEntity.builder()
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword()) // Encriptar la contraseña
                .isEnabled(userDTO.isEnabled())
                .accountNotExpired(userDTO.isAccountNotExpired())
                .accountNotLocked(userDTO.isAccountNotLocked())
                .credentialNotExpired(userDTO.isCredentialNotExpired())
                .createdAt(userDTO.getCreatedAt())
                .roles(Set.of(userRole)) // Asignar el rol USER
                .build();

        // Guardar el usuario en la base de datos
        UserEntity savedUser = userRepository.save(userEntity);

        // Convertir UserEntity a UserDTO
        return convertToDTO(savedUser);
    }






    private UserDTO convertToDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .username(userEntity.getUsername())
                .isEnabled(userEntity.isEnabled())
                .accountNotExpired(userEntity.isAccountNotExpired())
                .accountNotLocked(userEntity.isAccountNotLocked())
                .credentialNotExpired(userEntity.isCredentialNotExpired())
                .createdAt(userEntity.getCreatedAt())
                .roles(userEntity.getRoles().stream()
                        .map(roleEntity -> roleEntity.getRoleEnum().name())
                        .collect(Collectors.toSet()))
                .build();
    }

    // tudú:comprobar unicidad del email y username antes de actualizar, si ya existen, lamzar excepcion
    public UserDTO updateUser(Long id, @Valid UserDTO userDTO) {
        return userRepository.findById(id)
                .map(userEntity -> {
                    // Verifica si el username ya existe y no es del mismo usuario
                    Optional<UserEntity> existingUserWithUsername = userRepository.findUserEntityByUsername(userDTO.getUsername());

                    //validar si existe ya el username elegido
                    if (existingUserWithUsername.isPresent() && !existingUserWithUsername.get().getId().equals(id)) {
                        throw new UsernameInUseException("Username en uso, elija otro");
                    }

                    //validar si existe ya el email elegido
                    userRepository.findByEmail(userDTO.getEmail())
                                    .filter(existingUser -> !existingUser.getId().equals(id))
                                    .ifPresent(existingUser -> {
                                                throw new EmailAlreadyExistsException("Email en uso, elija otro");
                                    });

                    List<String> roleNames = userDTO.getRoles().stream()
                            .map(String::toUpperCase)
                            .map(Erole::valueOf)
                            .map(Erole::name) // pasamos de Erole.ADMIN → "ADMIN"
                            .toList();

                    List<RoleEntity> roleEntities = roleRepository.findRoleEntitiesByRoleEnumIn(roleNames);

                    userEntity.setUsername(userDTO.getUsername());
                    userEntity.setEmail(userDTO.getEmail());
                    // si la clave viene vacia desde el frontend, se mantiene la que habia
                    if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
                        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                    }
                    userEntity.setRoles(new HashSet<>(roleEntities));
                    UserEntity updatedUser = userRepository.save(userEntity);

                    return convertToDTO(updatedUser);
    })
                .orElseThrow(() -> new UserNotFoundException("Usuario con ID " + id + " no encontrado"));
    }

    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("El usuario con ID " + id + " no existe.");
        }

        userRepository.deleteById(id);
    }

    public UserDTO findByUsername(String username) {
        return userRepository.findUserEntityByUsername(username)
                .map(this::convertToDTO)
                .orElseThrow(() -> new UserNotFoundException("Usuario con username " + username + " no encontrado"));
    }

    public UserDTO updateOwnProfile(String username, UserProfileUpdateDTO updateDTO) {
        UserEntity user = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        // Validar username único (si es diferente al actual)
        if (!user.getUsername().equals(updateDTO.getUsername())) {
            userRepository.findUserEntityByUsername(updateDTO.getUsername())
                    .ifPresent(existing -> {
                        throw new UsernameInUseException("El nombre de usuario ya está en uso");
                    });
        }

        // Validar email único (si es diferente al actual)
        if (!user.getEmail().equals(updateDTO.getEmail())) {
            userRepository.findByEmail(updateDTO.getEmail())
                    .ifPresent(existing -> {
                        throw new EmailAlreadyExistsException("El email ya está en uso");
                    });
        }

        // Actualizar campos
        user.setUsername(updateDTO.getUsername());
        user.setEmail(updateDTO.getEmail());

        // Cambiar contraseña solo si se solicita
        if (updateDTO.getCurrentPassword() != null && !updateDTO.getCurrentPassword().isBlank()) {
            // Verificar contraseña actual
            if (!passwordEncoder.matches(updateDTO.getCurrentPassword(), user.getPassword())) {
                throw new IncorrectPasswordException("La contraseña actual es incorrecta");
            }
            // Verificar que las nuevas contraseñas coincidan
            if (updateDTO.getNewPassword() == null || updateDTO.getRepeatNewPassword() == null ||
                !updateDTO.getNewPassword().equals(updateDTO.getRepeatNewPassword())) {
                throw new IncorrectPasswordException("Las nuevas contraseñas no coinciden");
            }
            // Cambiar la contraseña
            user.setPassword(passwordEncoder.encode(updateDTO.getNewPassword()));
        }

        UserEntity updated = userRepository.save(user);
        return convertToDTO(updated);
    }









    /*
    Cliente → Controlador: El cliente (POSTMAN, FRONTEND) envía un JSON que se convierte en UserDTO.

    Controlador → Servicio: El controlador pasa el UserDTO al servicio.

    Servicio → Repositorio: El servicio convierte UserDTO a UserEntity y lo guarda en la base de datos.

    Repositorio → Servicio: El servicio recibe la entidad guardada y la convierte de vuelta a UserDTO.

    Servicio → Controlador: El servicio devuelve el UserDTO al controlador.

    Controlador → Cliente: El controlador devuelve el UserDTO en formato JSON al cliente.


       */
}















