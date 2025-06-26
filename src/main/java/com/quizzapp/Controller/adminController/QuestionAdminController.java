package com.quizzapp.Controller.adminController;

import com.quizzapp.DTO.QuestionDTO;
import com.quizzapp.Models.QuestionEntity;
import com.quizzapp.Repository.QuestionRepository;
import com.quizzapp.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/admin/questions")
@Tag(name = "Admin Questions", description = "Operaciones administrativas sobre preguntas")
@SecurityRequirement(name = "bearerAuth")
public class QuestionAdminController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    QuestionRepository questionRepository;



    @Operation(summary = "Obtener preguntas aleatorias", description = "Devuelve una lista de preguntas aleatorias.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/random")
    public ResponseEntity<List<QuestionDTO>> getRandomQuestions(
            @RequestParam(defaultValue = "10") int count) {
        List<QuestionDTO> questions = questionService.getRandomQuestions(count);
        return ResponseEntity.ok(questions);
    }

    @Operation(summary = "Obtener todas las preguntas", description = "Devuelve todas las preguntas registradas.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/AllQuestion")
    public ResponseEntity<List<QuestionDTO>> getAllQuestions(){
        List<QuestionDTO> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @Operation(summary = "Obtener preguntas paginadas", description = "Devuelve preguntas paginadas.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/AllPaginatedQuestion")
    public ResponseEntity<Page<QuestionDTO>> getPaginatedQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<QuestionDTO> paginatedQuestions = questionService.getPaginatedQuestions(pageable);

        return ResponseEntity.ok(paginatedQuestions);
    }

    @Operation(summary = "Buscar pregunta por ID", description = "Devuelve los datos de una pregunta específica por su ID.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/findQuestion/{id}")
    public ResponseEntity <QuestionDTO> findQuestion(@PathVariable Long id) {
        QuestionDTO questionDTO = questionService.findQuestionById(id);
        return ResponseEntity.ok(questionDTO);
    }

    @Operation(summary = "Crear pregunta", description = "Crea una nueva pregunta.", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/saveQuestion")
    public ResponseEntity<QuestionDTO> createQuestion(@Valid @RequestBody QuestionDTO questionDTO) {
        QuestionDTO createdQuestion = questionService.saveQuestion(questionDTO);
        return ResponseEntity.ok(createdQuestion);
    }

    @Operation(summary = "Eliminar pregunta", description = "Elimina una pregunta por su ID.", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("deleteQuestion/{id}")
    public ResponseEntity<Void>deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualizar pregunta", description = "Actualiza los datos de una pregunta existente.", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/updateQuestion/{id}")
    public ResponseEntity<QuestionDTO>updateQuestion(@RequestBody QuestionDTO questionDTO, @PathVariable Long id){
        QuestionDTO updatedQuestion = questionService.updateQuestion(id, questionDTO);
        return ResponseEntity.ok(updatedQuestion);
    }



}
