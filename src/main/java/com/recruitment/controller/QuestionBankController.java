package com.recruitment.controller;

import com.recruitment.model.QuestionBankTemplate;
import com.recruitment.service.QuestionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question-bank")

public class QuestionBankController {

    @Autowired
    private QuestionBankService questionBankService;

    // Bulk Add API
    @PostMapping("/bulk")
    public ResponseEntity<String> addBulkQuestions( @RequestBody List<QuestionBankTemplate> questions) {
        questionBankService.addBulkQuestions(questions);
        return ResponseEntity.ok("Questions added successfully.");
    }

    @GetMapping
    public List<QuestionBankTemplate> getAllQuestions() {
        return questionBankService.getAllQuestions();
    }

    @GetMapping("/{id}")
    public QuestionBankTemplate getQuestionById(@PathVariable Long id) {
        return questionBankService.getQuestionById(id);
    }
}
