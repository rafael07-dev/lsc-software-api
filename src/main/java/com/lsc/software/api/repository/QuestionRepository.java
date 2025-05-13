package com.lsc.software.api.repository;

import com.lsc.software.api.model.quiz.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    //List<Question> findQuestionByName(String name);
}
