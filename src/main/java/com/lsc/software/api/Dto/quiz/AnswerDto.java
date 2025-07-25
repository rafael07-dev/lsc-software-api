package com.lsc.software.api.Dto.quiz;

import java.time.LocalDate;

public class AnswerDto {

    private Long id;

    private String content;
    private LocalDate createdAt;
    private boolean isCorrect;

    private Long question;
}
