package com.lsc.software.api.Dto;

import java.util.List;

public class SignPatternResponse {

    private final String word;
    private final List<List<List<Double>>> pattern;

    public SignPatternResponse(String word, List<List<List<Double>>> pattern) {
        this.word = word;
        this.pattern = pattern;
    }


    public String getWord() {
        return word;
    }

    public List<List<List<Double>>> getPattern() {
        return pattern;
    }
}
