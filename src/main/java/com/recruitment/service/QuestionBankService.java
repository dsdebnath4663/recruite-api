package com.recruitment.service;

import com.recruitment.model.QuestionBankTemplate;
import com.recruitment.repository.QuestionBankTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionBankService {

    @Autowired
    private QuestionBankTemplateRepository questionBankTemplateRepository;

    public List<QuestionBankTemplate> getAllQuestions() {
        return questionBankTemplateRepository.findAll();
    }

    public QuestionBankTemplate getQuestionById(Long id) {
        return questionBankTemplateRepository.findById(id)
                                             .orElseThrow(() -> new RuntimeException("Question not found"));
    }

    // Bulk Add Method
    public void addBulkQuestions(List<QuestionBankTemplate> questions) {
        questionBankTemplateRepository.saveAll(questions);
    }
}
