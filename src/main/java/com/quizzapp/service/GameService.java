package com.quizzapp.service;


import com.quizzapp.DTO.GameDTO;
import com.quizzapp.DTO.UserAnswerDTO;
import com.quizzapp.DTO.UserDTO;
import com.quizzapp.Models.GameEntity;
import com.quizzapp.Models.UserAnswerEntity;
import com.quizzapp.Models.UserEntity;
import com.quizzapp.Repository.*;
import com.quizzapp.exceptions.QuestionNotFoundException;
import com.quizzapp.exceptions.ResourceNotFoundException;
import com.quizzapp.exceptions.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserAnswerRepository userAnswerRepository;


    /**
     * Método para obtener el nombre de usuario autenticado.
     */
    private String getAuthenticatedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public GameDTO startGame() {
        String username = getAuthenticatedUsername();
        UserEntity user = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        return GameDTO.builder()
                .gameName("Partida-" + LocalDateTime.now())
                .score(0)
                .createdAt(LocalDateTime.now())
                .userId(user.getId())
                .username(user.getUsername())
                .answers(Collections.emptyList())
                .build();
    }







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
        String username = getAuthenticatedUsername();
        UserEntity user = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        // CONVERTIR DTO A ENTITY
        GameEntity gameEntity = GameEntity.builder()
                .gameName("Partida-" + LocalDateTime.now().toString())
                .score(gameDTO.getScore())
                .createdAt(gameDTO.getCreatedAt())
                .user(user)
                .build();

        // GUARDAR PARTIDA EN BD
        GameEntity savedGame = gameRepository.save(gameEntity);

        // 3️⃣ Convertir y guardar las respuestas del usuario
        List<UserAnswerEntity> userAnswerEntities = gameDTO.getAnswers().stream()
                .map(answerDTO -> UserAnswerEntity.builder()
                        .game(savedGame) // Asociamos la partida
                        .question(questionRepository.findById(answerDTO.getQuestionId())
                                .orElseThrow(() -> new QuestionNotFoundException("Pregunta no encontrada con ID: " + answerDTO.getQuestionId())))
                        .selectedAnswer(answerRepository.findById(answerDTO.getSelectedAnswerId())
                                .orElseThrow(() -> new ResourceNotFoundException("Respuesta no encontrada con ID: " + answerDTO.getSelectedAnswerId())))
                        .build())
                .collect(Collectors.toList());

        userAnswerRepository.saveAll(userAnswerEntities);

        // convertir entity guardado a DTO

        // 4️⃣ Convertir `GameEntity` a `GameDTO` y devolverlo
        return GameDTO.builder()
                .id(savedGame.getId())
                .gameName(savedGame.getGameName())
                .score(savedGame.getScore())
                .createdAt(savedGame.getCreatedAt())
                .userId(savedGame.getUser().getId())
                .username(savedGame.getUser().getUsername())
                .answers(userAnswerEntities.stream()
                        .map(userAnswer -> UserAnswerDTO.builder()
                                .id(userAnswer.getId())
                                .gameId(savedGame.getId())
                                .questionId(userAnswer.getQuestion().getId())
                                .selectedAnswerId(userAnswer.getSelectedAnswer().getId())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    // PARTIDAS DE UN USUARIO

    public List<GameDTO>findGamesByUserId(Long userId){
        List<GameEntity> gameEntities = gameRepository.findGameByUserId(userId);

        if(gameEntities.isEmpty()) {
            return Collections.emptyList();
        }

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
