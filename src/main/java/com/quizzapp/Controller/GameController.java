package com.quizzapp.Controller;


import com.quizzapp.DTO.GameDTO;
import com.quizzapp.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/game")
@Tag(name = "Game", description = "Operaciones relacionadas con partidas de juego")
@SecurityRequirement(name = "bearerAuth")
public class GameController {

    @Autowired
    private GameService gameService;



    @Operation(summary = "Guardar partida", description = "Guarda una nueva partida en la base de datos.", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/saveGame")
    public ResponseEntity<GameDTO>saveGame(@RequestBody GameDTO gameDTO) {
        GameDTO savedGameDTO = gameService.saveGame(gameDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGameDTO);
    }

    @Operation(summary = "Buscar partidas por usuario", description = "Obtiene todas las partidas de un usuario por su ID.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/findGamesByUserId/{userId}")
    public ResponseEntity<List<GameDTO>>findGamesByUserId(@PathVariable Long userId) {
        List<GameDTO> dtoGames = gameService.findGamesByUserId(userId);
        return ResponseEntity.ok(dtoGames);
    }

    @Operation(summary = "Obtener ranking", description = "Devuelve el ranking de partidas.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/ranking")
    public ResponseEntity<List<GameDTO>> getRanking() {
        List<GameDTO> ranking = gameService.getRanking();
        return ResponseEntity.ok(ranking);
    }

    @Operation(summary = "Iniciar nueva partida", description = "Crea e inicia una nueva partida.", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/startGame")
    public ResponseEntity<GameDTO> startGame() {
        GameDTO newGame = gameService.startGame();
        return ResponseEntity.ok(newGame);
    }



}
