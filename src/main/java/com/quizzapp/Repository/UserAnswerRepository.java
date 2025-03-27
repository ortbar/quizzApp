package com.quizzapp.Repository;


import com.quizzapp.Models.UserAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswerEntity, Long> {


    // Buscar respuestas de un usuario en un juego específico
    List<UserAnswerEntity> findByGameId(Long gameId);


}
