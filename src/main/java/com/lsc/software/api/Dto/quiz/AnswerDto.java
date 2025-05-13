package com.lsc.software.api.Dto.quiz;

import java.time.LocalDateTime;

public class AnswerDto {

    private Long id;

    private String content;
    private LocalDateTime createdAt;
    private boolean isCorrect;

    private Long question;
}
