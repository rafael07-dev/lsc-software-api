package com.lsc.software.api.Dto;

import java.util.List;

public class SignPatternRequest {

    private String word;

    private List<List<List<Double>>> sequence;

    // Getters y setters
    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }

    public List<List<List<Double>>> getSequence() { return sequence; }
    public void setSequence(List<List<List<Double>>> sequence) { this.sequence = sequence; }
}
