package com.quizzapp.Controller;


import com.quizzapp.DTO.GameDTO;
import com.quizzapp.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private GameService gameService;



    @PostMapping("/saveGame")
    public ResponseEntity<GameDTO>saveGame(@RequestBody GameDTO gameDTO) {
        GameDTO savedGameDTO = gameService.saveGame(gameDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGameDTO);
    }

    @GetMapping("/findGamesByUserId/{userId}")
    public ResponseEntity
            <List<GameDTO>>findGamesByUserId(@PathVariable Long userId) {
        List<GameDTO> dtoGames = gameService.findGamesByUserId(userId);
        return ResponseEntity.ok(dtoGames);
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<GameDTO>> getRanking() {
        List<GameDTO> ranking = gameService.getRanking();
        return ResponseEntity.ok(ranking);
    }

    @PostMapping("/startGame")
    public ResponseEntity<GameDTO> startGame() {
        GameDTO newGame = gameService.startGame();
        return ResponseEntity.ok(newGame);
    }



}
