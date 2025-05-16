package com.quizzapp.DTO;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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

    @NotBlank(message = "Debe haber al menos una pregunta")
    private String textoPregunta;  // Texto de la pregunta

    @NotEmpty(message = "La lista de respuestas no puede estar vacía")
    @Valid
    private List<AnswerDTO> answers;

}
