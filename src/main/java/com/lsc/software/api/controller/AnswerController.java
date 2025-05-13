package com.lsc.software.api.controller;

import com.lsc.software.api.model.quiz.Answer;
import com.lsc.software.api.service.AnswerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping
    public List<Answer> getAnswersByQuestionId(@PathVariable Long questionId) {
        return answerService.getAnswersByQuestionId(questionId);
    }

    @PostMapping
    public Answer createAnswer(@PathVariable Long questionId, @RequestBody Answer answer) {
        return answerService.saveAnswer(questionId, answer);
    }

    @DeleteMapping("/{answerId}")
    public void deleteAnswer(@PathVariable Long questionId, @PathVariable Long answerId) {
        answerService.deleteAnswer(questionId, answerId);
    }

    @PutMapping
    public Answer updateAnswer(@PathVariable Long questionId, @RequestBody Answer answer) {
        return answerService.updateAnswer(questionId, answer);
    }
}

