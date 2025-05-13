package com.lsc.software.api.service;

import com.lsc.software.api.Dto.quiz.QuestionDto;
import com.lsc.software.api.model.Word;
import com.lsc.software.api.model.quiz.Answer;
import com.lsc.software.api.model.quiz.Question;
import com.lsc.software.api.repository.GiffRepository;
import com.lsc.software.api.repository.QuestionRepository;
import com.lsc.software.api.repository.WordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuestionService {

    private static final Logger log = LoggerFactory.getLogger(QuestionService.class);
    private final QuestionRepository questionRepository;
    private final WordRepository wordRepository;
    private final GiffRepository giffRepository;

    public QuestionService(QuestionRepository questionRepository, WordRepository wordRepository, GiffRepository giffRepository) {
        this.questionRepository = questionRepository;
        this.wordRepository = wordRepository;
        this.giffRepository = giffRepository;
    }

    public Question save(QuestionDto question) {

        var answerOptional = question.getAnswers()
                .stream()
                .filter(Answer::isCorrect)
                .map(Answer::getContent)
                .findFirst();

        String correctAnswer = null;
        Word word = null;

        if (answerOptional.isPresent()) {
            correctAnswer = answerOptional.get();
            word = wordRepository.findWordByWord(correctAnswer);

            if (word == null){
                throw new RuntimeException("Word not found");
            }
        }

        Question questionEntity = new Question();

        assert word != null;
        var giff = giffRepository.findByWordId(word.getId());

        questionEntity.setTitle(question.getTitle());
        questionEntity.setContent(question.getContent());
        questionEntity.setCreatedAt(question.getCreatedAt());
        questionEntity.setMediaUrl(giff.getGiffUrl());
        questionEntity.setMediaType("mp4");
        questionEntity.setAnswers(question.getAnswers());
        questionEntity.setCorrectAnswer(correctAnswer);

        return questionRepository.save(questionEntity);
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
    }

    public Question updateQuestion(Long id, Question question){
        var questionToUpdate = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        var correctAnswer = question.getAnswers()
                .stream()
                .filter(Answer::isCorrect)
                .map(Answer::getContent)
                .toString();

        questionToUpdate.setTitle(question.getTitle());
        questionToUpdate.setContent(question.getContent());
        questionToUpdate.setCreatedAt(question.getCreatedAt());
        questionToUpdate.setMediaUrl(question.getMediaUrl());
        questionToUpdate.setMediaType(question.getMediaType());
        questionToUpdate.setCorrectAnswer(correctAnswer);
        questionToUpdate.setAnswers(question.getAnswers());

        questionRepository.save(questionToUpdate);

        return questionToUpdate;
    }
}
