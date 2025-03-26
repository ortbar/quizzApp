package com.quizzapp.Repository;

import com.quizzapp.Models.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {

    // FILTRA LAS PARTIDAS DE UN USUARIO
    List<GameEntity> findGameByUserId(Long userId);

    // RANKING DE PARTIDAS DE USUARIOS ORDENADAS POR PUNTOS DESCENDENTES
    List<GameEntity>findTop10ByOrderByScoreDesc();

}
