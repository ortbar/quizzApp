package com.quizzapp.service;


import com.quizzapp.DTO.GameDTO;
import com.quizzapp.DTO.UserDTO;
import com.quizzapp.Models.GameEntity;
import com.quizzapp.Models.UserEntity;
import com.quizzapp.Repository.GameRepository;
import com.quizzapp.Repository.UserRepository;
import com.quizzapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;




    // tudu considerar la paginacion de las partidas en el servicio
    public List<GameDTO> findAll() {
        List<GameEntity> gameEntities = gameRepository.findAll();
        return gameEntities.stream()
                .map(gameEntity-> GameDTO.builder()
                        .gameName(gameEntity.getGameName())
                        .score(gameEntity.getScore())
                        .createdAt(gameEntity.getCreatedAt())
                        .userId(gameEntity.getUser().getId()) // manejar relaciones nulas con userEntity
                        .username(gameEntity.getUser().getUsername())
                        .build())
                .collect(Collectors.toList());
    }

    // guardar partida, un solo metodo al final de juego

    public GameDTO saveGame(GameDTO gameDTO) {
        //obtener usuario asociado a la partida
        UserEntity user = userRepository.findById(gameDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("usuario no encontrado"));
        // CONVERTIR DTO A ENTITY
        GameEntity gameEntity = GameEntity.builder()
                .gameName("Partida-" + LocalDateTime.now().toString())
                .score(gameDTO.getScore())
                .createdAt(gameDTO.getCreatedAt())
                .user(user)
                .build();

        // GUARDAR PARTIDA EN BD
        GameEntity savedGame = gameRepository.save(gameEntity);

        // convertir entity guardado a DTO

        return GameDTO.builder()
                .gameName(savedGame.getGameName())
                .score(savedGame.getScore())
                .createdAt(savedGame.getCreatedAt())
                .userId(savedGame.getUser().getId())
                .username(savedGame.getUser().getUsername())
                .build();
    }

    // PARTIDAS DE UN USUARIO

    public List<GameDTO>findGamesByUserId(Long userId){
        List<GameEntity> gameEntities = gameRepository.findGameByUserId(userId);
        return gameEntities.stream()
                .map(gameEntity -> GameDTO.builder()
                        .gameName(gameEntity.getGameName())
                        .score(gameEntity.getScore())
                        .createdAt(gameEntity.getCreatedAt())
                        .userId(gameEntity.getUser().getId())
                        .username(gameEntity.getUser().getUsername())
                        .build())
                .collect(Collectors.toList());
    }

    // RANKING DE PARTIDAS MEJORES PUNTUACIONES

    public List<GameDTO>getRanking() {
        List<GameEntity>topGames = gameRepository.findTop10ByOrderByScoreDesc();
        return topGames.stream()
                .map(gameEntity -> GameDTO.builder()
                        .gameName(gameEntity.getGameName())
                        .score(gameEntity.getScore())
                        .createdAt(gameEntity.getCreatedAt())
                        .userId(gameEntity.getUser().getId())
                        .username(gameEntity.getUser().getUsername())
                        .build())
                .collect(Collectors.toList());
    }

    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }

}
