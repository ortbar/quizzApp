package com.quizzapp.Controller.adminController;

import com.quizzapp.DTO.QuestionDTO;
import com.quizzapp.Models.QuestionEntity;
import com.quizzapp.Repository.QuestionRepository;
import com.quizzapp.service.QuestionService;
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
public class QuestionAdminController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    QuestionRepository questionRepository;



    @GetMapping("/random")
    public ResponseEntity<List<QuestionDTO>> getRandomQuestions(
            @RequestParam(defaultValue = "10") int count) {
        List<QuestionDTO> questions = questionService.getRandomQuestions(count);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/AllQuestion")
    public ResponseEntity<List<QuestionDTO>> getAllQuestions(){
        List<QuestionDTO> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/AllPaginatedQuestion")
    public ResponseEntity<Page<QuestionDTO>> getPaginatedQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<QuestionDTO> paginatedQuestions = questionService.getPaginatedQuestions(pageable);

        return ResponseEntity.ok(paginatedQuestions);
    }

    @GetMapping("/findQuestion/{id}")
    public ResponseEntity <QuestionDTO> findQuestion(@PathVariable Long id) {
        QuestionDTO questionDTO = questionService.findQuestionById(id);
        return ResponseEntity.ok(questionDTO);
    }

    @PostMapping("/saveQuestion")
    public ResponseEntity<QuestionDTO> createQuestion(@Valid @RequestBody QuestionDTO questionDTO) {
        QuestionDTO createdQuestion = questionService.saveQuestion(questionDTO);
        return ResponseEntity.ok(createdQuestion);
    }

    @DeleteMapping("deleteQuestion/{id}")
    public ResponseEntity<Void>deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/updateQuestion/{id}")
    public ResponseEntity<QuestionDTO>updateQuestion(@RequestBody QuestionDTO questionDTO, @PathVariable Long id){
        QuestionDTO updatedQuestion = questionService.updateQuestion(id, questionDTO);
        return ResponseEntity.ok(updatedQuestion);
    }



}
