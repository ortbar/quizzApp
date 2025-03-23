package com.quizzapp.service;

import com.quizzapp.DTO.AuthCreateUserRequest;
import com.quizzapp.DTO.AuthLoginRequest;
import com.quizzapp.DTO.AuthResponse;
import com.quizzapp.Models.RoleEntity;
import com.quizzapp.Models.UserEntity;
import com.quizzapp.Repository.RoleRepository;
import com.quizzapp.Repository.UserRepository;
import com.quizzapp.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RoleRepository roleRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + "no existe."));

        //COGEMOS LOS ROLES  y permisos de uusario y los estamos conviertiendo a un objto de springSecurity PARA SETEAR EL OBJETO DE

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        // hay que retornnar un usuario de userDetailsService, pa que lo entienda SpringSecurity
        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNotExpired(),
                userEntity.isCredentialNotExpired(),
                userEntity.isAccountNotLocked(),
                authorityList);

    }

    public AuthResponse loginUser (AuthLoginRequest authLoginRequest) {
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.createToken(authentication);

        AuthResponse authResponse = new AuthResponse(username,"User logged succesfully", accessToken, true);

        return authResponse;
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);

        if(userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if(!passwordEncoder.matches(password,userDetails.getPassword())) {

            throw new BadCredentialsException("invalid password");
        }

        return new UsernamePasswordAuthenticationToken(username,userDetails.getPassword(),userDetails.getAuthorities());
    }

    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest) {
        String username = authCreateUserRequest.username();
        String password = authCreateUserRequest.password();

        List<String>roleRequest = authCreateUserRequest.roleRequest().roleListName();

      Set<RoleEntity> roleEntitySet =  roleRepository.findRoleEntitiesByRoleEnumIn(roleRequest)
              .stream()
              .collect(Collectors.toSet());

      if(roleEntitySet.isEmpty()) {
          throw new IllegalArgumentException("los roles especificados no existen");
      }

      UserEntity userEntity = UserEntity.builder()
              .username(username)
              .password(passwordEncoder.encode(password))
              .roles(roleEntitySet)
              .isEnabled(true)
              .accountNotLocked(true)
              .accountNotExpired(true)
              .credentialNotExpired(true)
              .build();

       UserEntity userCreated = userRepository.save(userEntity);

       ArrayList<SimpleGrantedAuthority> authorityList = new ArrayList<>();

       userCreated.getRoles().forEach(role-> authorityList.add(new SimpleGrantedAuthority("ROLE_" .concat(role.getRoleEnum().name()))));

       userCreated.getRoles()
               .stream()
               .flatMap(role->role.getPermissionList().stream())
               .forEach(permission->authorityList.add(new SimpleGrantedAuthority(permission.getName())));


        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated.getUsername(),userCreated.getPassword(),authorityList);

        String accessToken = jwtUtils.createToken(authentication);

        AuthResponse authResponse = new AuthResponse(userCreated.getUsername(), "user created successfully", accessToken,true);


        return authResponse;
    }




}
