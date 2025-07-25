package com.lsc.software.api.Dto.quiz;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lsc.software.api.model.quiz.Answer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class QuestionDto {

    private Long id;
    private String title;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
    private final List<Answer> answers = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
