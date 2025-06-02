package com.quizzapp.service;


import com.quizzapp.DTO.AnswerDTO;
import com.quizzapp.DTO.QuestionDTO;
import com.quizzapp.Models.AnswerEntity;
import com.quizzapp.Models.QuestionEntity;
import com.quizzapp.Models.UserEntity;
import com.quizzapp.Repository.QuestionRepository;
import com.quizzapp.exceptions.QuestionNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                        .id(questionEntity.getId())
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

    public Page<QuestionDTO> getPaginatedQuestions(Pageable pageable) {
        Page<QuestionEntity> questionEntities = questionRespository.findAll(pageable);

        return questionEntities.map(questionEntity -> QuestionDTO.builder()
                .id(questionEntity.getId())
                .textoPregunta(questionEntity.getTextoPregunta())
                .answers(
                        questionEntity.getAnswers().stream()
                                .map(answerEntity -> AnswerDTO.builder()
                                        .id(answerEntity.getId())
                                        .answerText(answerEntity.getAnswerText())
                                        .esCorrecta(answerEntity.getIsCorrect())
                                        .build())
                                .collect(Collectors.toList())
                )
                        .build()
                );

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

    public QuestionDTO saveQuestion(@Valid QuestionDTO questionDTO) {

        // 🔒 Validación 1: exactamente 3 respuestas
        if (questionDTO.getAnswers().size() != 3) {
            throw new IllegalArgumentException("Debe proporcionar exactamente 3 respuestas.");
        }

        // 🔒 Validación 2: solo una respuesta puede ser correcta
        long correctCount = questionDTO.getAnswers().stream()
                .filter(AnswerDTO::isEsCorrecta)
                .count();
        if (correctCount != 1) {
            throw new IllegalArgumentException("Debe marcar exactamente una respuesta como correcta.");
        }

        // 📦 Crear entidad de pregunta
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setId(questionDTO.getId());
        questionEntity.setTextoPregunta(questionDTO.getTextoPregunta());

        // 📦 Convertir respuestas
        List<AnswerEntity> answers = questionDTO.getAnswers().stream()
                .map(answerDTO -> {
                    AnswerEntity answerEntity = new AnswerEntity();
                    answerEntity.setId(answerDTO.getId());
                    answerEntity.setAnswerText(answerDTO.getAnswerText());
                    answerEntity.setIsCorrect(answerDTO.isEsCorrecta());
                    answerEntity.setQuestion(questionEntity);  // relación inversa
                    return answerEntity;
                })
                .collect(Collectors.toList());

        questionEntity.setAnswers(answers);

        // 💾 Guardar en base de datos
        QuestionEntity savedQuestion = questionRespository.save(questionEntity);

        // 🔄 Convertir de vuelta a DTO
        List<AnswerDTO> savedAnswers = savedQuestion.getAnswers().stream()
                .map(answer -> AnswerDTO.builder()
                        .id(answer.getId())
                        .answerText(answer.getAnswerText())
                        .esCorrecta(answer.getIsCorrect())
                        .build())
                .collect(Collectors.toList());

        return QuestionDTO.builder()
                .id(savedQuestion.getId())
                .textoPregunta(savedQuestion.getTextoPregunta())
                .answers(savedAnswers)
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
