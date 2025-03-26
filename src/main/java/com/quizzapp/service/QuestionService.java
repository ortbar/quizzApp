package com.quizzapp.service;


import com.quizzapp.DTO.AnswerDTO;
import com.quizzapp.DTO.QuestionDTO;
import com.quizzapp.Models.AnswerEntity;
import com.quizzapp.Models.QuestionEntity;
import com.quizzapp.Models.UserEntity;
import com.quizzapp.Repository.QuestionRepository;
import com.quizzapp.exceptions.QuestionNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRespository;

    public List<QuestionDTO>getAllQuestions(){
        List<QuestionEntity> questionEntityList = questionRespository.findAll();

        if(questionEntityList.isEmpty()) {
            return Collections.emptyList();
        }

        return questionEntityList.stream()
                .map(questionEntity -> QuestionDTO.builder()
                        .textoPregunta(questionEntity.getTextoPregunta())
                        .answers(
                                questionEntity.getAnswers().stream()
                                        .map(answerEntity -> AnswerDTO.builder()
                                                .id(answerEntity.getId())
                                                .answerText(answerEntity.getAnswerText())
                                                .esCorrecta(answerEntity.getIsCorrect()) // se podria omitir
                                                .build())
                                        .collect(Collectors.toList())
                        )
                        .build())
                .collect(Collectors.toList());
    }



    public List<QuestionDTO>getRandomQuestions(int count){
        List<QuestionEntity> questions = questionRespository.findRandomQuestions(count);
        return questions.stream()
                .map(questionEntity -> QuestionDTO.builder()
                        .id(questionEntity.getId())
                        .textoPregunta(questionEntity.getTextoPregunta())
                        .answers(questionEntity.getAnswers().stream()
                                .map(answerEntity -> AnswerDTO.builder()
                                        .id(answerEntity.getId())
                                        .answerText(answerEntity.getAnswerText())
                                        .esCorrecta(answerEntity.getIsCorrect())
                                        .build())
                                .collect(Collectors.toList()))
                                .build())
                .collect(Collectors.toList());
    }

    public QuestionDTO saveQuestion(QuestionDTO questionDTO){
        QuestionEntity questionEntity = QuestionEntity.builder()
                .textoPregunta(questionDTO.getTextoPregunta())
                .build();

        // 2️⃣ Convertir las respuestas y asignarles la relación con la pregunta
        List<AnswerEntity> answers = questionDTO.getAnswers().stream()
                .map(answerDTO -> AnswerEntity.builder()
                        .answerText(answerDTO.getAnswerText())
                        .isCorrect(answerDTO.isEsCorrecta())
                        .question(questionEntity) // ✅ Asignamos la relación con la pregunta
                        .build())
                .collect(Collectors.toList());

        // 3️⃣ Asignar la lista de respuestas a la pregunta
        questionEntity.setAnswers(answers);


        // guarar en la bd
        QuestionEntity savedQuestion = questionRespository.save(questionEntity);

        // convertir la entidad guardada a DTO para la respuesta
        return QuestionDTO.builder()
                .id(savedQuestion.getId())
                .textoPregunta(savedQuestion.getTextoPregunta())
                .answers(
                        savedQuestion.getAnswers().stream()
                                .map(answerEntity -> AnswerDTO.builder()
                                        .id(answerEntity.getId())
                                        .answerText(answerEntity.getAnswerText())
                                        .esCorrecta(answerEntity.getIsCorrect())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }

    public QuestionDTO updateQuestion(Long id, QuestionDTO questionDTO) {
        return questionRespository.findById(id) // Devuelve Optional<QuestionEntity>
                .map(questionEntity -> {
                    // Actualizar el texto de la pregunta
                    questionEntity.setTextoPregunta(questionDTO.getTextoPregunta());

                    // Actualizar las respuestas existentes
                    List<AnswerEntity> updatedAnswers = questionDTO.getAnswers().stream()
                            .map(answerDTO -> {
                                AnswerEntity answerEntity = new AnswerEntity();
                                answerEntity.setId(answerDTO.getId()); // Mantener el ID si existe
                                answerEntity.setAnswerText(answerDTO.getAnswerText());
                                answerEntity.setIsCorrect(answerDTO.isEsCorrecta());
                                answerEntity.setQuestion(questionEntity); // Relación con la pregunta
                                return answerEntity;
                            })
                            .collect(Collectors.toList());

                    // Limpiar y reasignar respuestas
                    questionEntity.getAnswers().clear();
                    questionEntity.getAnswers().addAll(updatedAnswers);

                    // Guardar la pregunta actualizada en la BD
                    QuestionEntity savedQuestion = questionRespository.save(questionEntity);

                    // Convertir a DTO para devolverlo
                    return QuestionDTO.builder()
                            .id(savedQuestion.getId())
                            .textoPregunta(savedQuestion.getTextoPregunta())
                            .answers(savedQuestion.getAnswers().stream()
                                    .map(answer -> AnswerDTO.builder()
                                            .id(answer.getId())
                                            .answerText(answer.getAnswerText())
                                            .esCorrecta(answer.getIsCorrect())
                                            .build())
                                    .collect(Collectors.toList()))
                            .build();
                })
                .orElseThrow(() -> new EntityNotFoundException("Pregunta no encontrada con ID: " + id)); // Aquí extraemos el DTO o lanzamos la excepción
    }

    public void deleteQuestion(Long id) {
        questionRespository.deleteById(id);
    }

    public QuestionDTO  findQuestionById (Long id){
        Optional<QuestionEntity> questionOptional = questionRespository.findById(id);


        if (questionOptional.isPresent()) {
            QuestionEntity questionEntity = questionOptional.get();

            QuestionDTO questionDTO = QuestionDTO.builder()
                    .id(questionEntity.getId())
                    .textoPregunta(questionEntity.getTextoPregunta())
                    .answers(questionEntity.getAnswers().stream()
                            .map(answerEntity -> AnswerDTO.builder()
                                    .id(answerEntity.getId())
                                    .answerText(answerEntity.getAnswerText())
                                    .esCorrecta(answerEntity.getIsCorrect())
                                    .build())
                            .collect(Collectors.toList()))
                    .build();

            return questionDTO;

        }else {
            throw new QuestionNotFoundException("Pregunta no encontrada con id " + id);
        }

    }








}
