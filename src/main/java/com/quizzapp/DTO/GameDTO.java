package com.quizzapp.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameDTO {


    private String gameName; // Nombre del juego
    private int score; // Puntuación del juego
    private LocalDateTime createdAt;
    private Long userId;
    private String username;


}
