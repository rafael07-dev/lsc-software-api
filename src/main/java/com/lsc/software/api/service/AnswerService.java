package com.lsc.software.api.service;

import com.lsc.software.api.model.quiz.Answer;
import com.lsc.software.api.model.quiz.Question;
import com.lsc.software.api.repository.AnswerRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    public AnswerService(AnswerRepository answerRepository, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    public List<Answer> getAnswersByQuestionId(Long questionId) {
        return answerRepository.findAnswerByQuestionId(questionId);
    }

    public Answer saveAnswer(Long questionId, Answer answer) {
        Question question = questionService.getQuestionById(questionId);
        answer.setQuestion(question);
        answer.setCreatedAt(LocalDateTime.now());
        return answerRepository.save(answer);
    }

    public void deleteAnswer(Long questionId, Long answerId) {
        Optional<Answer> answerOptional = answerRepository.findById(answerId);
        if (answerOptional.isPresent()) {
            answerRepository.deleteById(answerId);
        } else {
            throw new RuntimeException("Answer not found with ID " + answerId);
        }
    }
    public Answer updateAnswer(Long questionId, Answer answer) {
        Optional<Answer> answerOptional = answerRepository.findById(answer.getId());

        if (answerOptional.isPresent()) {
            var answerToUpdate = answerOptional.get();
            answerToUpdate.setCreatedAt(LocalDateTime.now());
            answerToUpdate.setCorrect(answer.isCorrect());
            answerToUpdate.setQuestion(questionService.getQuestionById(questionId));
            answerToUpdate.setContent(answer.getContent());
            return answerRepository.save(answerToUpdate);
        }

        throw new RuntimeException("Answer not found with ID " + answer.getId());
    }
}
