package com.quizzapp.service;


import com.quizzapp.DTO.UserAnswerDTO;
import com.quizzapp.Models.UserAnswerEntity;
import com.quizzapp.Repository.AnswerRepository;
import com.quizzapp.Repository.GameRepository;
import com.quizzapp.Repository.QuestionRepository;
import com.quizzapp.Repository.UserAnswerRepository;
import com.quizzapp.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAnswerService {

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;



        public UserAnswerDTO saveUserAnswer(UserAnswerDTO userAnswerDTO){
            UserAnswerEntity savedUserAnswerEntity = UserAnswerEntity.builder()
                    .game(gameRepository.findById(userAnswerDTO.getGameId())
                            .orElseThrow(()-> new ResourceNotFoundException("Partida no encontrada con id " + userAnswerDTO.getGameId())))
                    .question(questionRepository.findById(userAnswerDTO.getQuestionId())
                            .orElseThrow(()-> new ResourceNotFoundException("Pregunta no encontrada con id " + userAnswerDTO.getQuestionId())))
                    .selectedAnswer(answerRepository.findById(userAnswerDTO.getSelectedAnswerId())
                            .orElseThrow(()-> new ResourceNotFoundException("Respuesta no encontrada con id" + userAnswerDTO.getSelectedAnswerId())))
                    .build();
            UserAnswerEntity savedEntity = userAnswerRepository.save(savedUserAnswerEntity);

            return UserAnswerDTO.builder()
                    .id(savedEntity.getId())
                    .gameId(savedEntity.getGame().getId())
                    .questionId(savedEntity.getQuestion().getId())
                    .selectedAnswerId(savedEntity.getSelectedAnswer().getId())
                    .build();
        }

}
