package com.quizzapp.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerDTO {

    private Long id;

    @NotBlank(message = "El texto de la respuesta es obligatorio")
    private String answerText;  // Texto de la respuesta
    private boolean esCorrecta;
}
