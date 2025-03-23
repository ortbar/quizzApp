package com.quizzapp.Repository;

import com.quizzapp.Models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long > {



    // metodos personalizados, creado en la interfaz de JPArEPOSITORY, los crud ya estan ahi no hace falta ni escribirlos
    Optional<UserEntity> findUserEntityByUsername(String username);

    Optional<UserEntity> findByEmail(String email);






}
