package com.quizzapp.Controller.adminController;


import com.quizzapp.DTO.GameDTO;
import com.quizzapp.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("admin/games")
public class gameAdminController {

    @Autowired
    private GameService gameService;

    @DeleteMapping("/deleteGame/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener todas las partidas
    @GetMapping("/findAll")
    public ResponseEntity<List<GameDTO>> getAllGames() {
        List<GameDTO> games = gameService.findAll();
        return ResponseEntity.ok(games);
    }



}
