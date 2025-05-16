package com.quizzapp.Models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "answers")
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "answer_text", nullable = false, length = 255)
    private String answerText;

    @Column(name = "es_correcta", nullable = false)
    @NotNull
    private Boolean isCorrect;


    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)  // Clave foránea hacia la tabla questions
    private QuestionEntity question;

    /* RELACION CON UserAnswer */

    @OneToMany(mappedBy = "selectedAnswer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAnswerEntity> userAnswers;


}
