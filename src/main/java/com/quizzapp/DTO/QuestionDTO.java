package com.quizzapp.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDTO {

    private Long id;
    private String textoPregunta;  // Texto de la pregunta
    private List<AnswerDTO> answers;

}
