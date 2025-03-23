package com.quizzapp.service;

import com.quizzapp.Models.Erole;
import com.quizzapp.Models.RoleEntity;
import com.quizzapp.DTO.UserDTO;
import com.quizzapp.Models.UserEntity;
import com.quizzapp.Repository.RoleRepository;
import com.quizzapp.Repository.UserRepository;
import com.quizzapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id)
                .map(userEntity -> {
                    userEntity.setUsername(userDTO.getUsername());
                    userEntity.setEmail(userDTO.getEmail());
                    userEntity.setPassword(userDTO.getPassword());
                    UserEntity updatedUser = userRepository.save(userEntity);

                    return convertToDTO(updatedUser);
    })
                .orElseThrow(() -> new UserNotFoundException("Usuario con ID " + id + " no encontrado"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
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















