package com.quizzapp.DTO;


import com.quizzapp.Models.GameEntity;
import com.quizzapp.Models.QuestionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAnswerDTO {

    private Long id;
    private Long gameId;
    private Long questionId;
    private Long selectedAnswerId;



}
