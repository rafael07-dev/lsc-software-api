package com.lsc.software.api.model;

import jakarta.persistence.*;

@Entity
public class SignPattern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;

    @Lob
    private String sequence;

    public SignPattern() {}

    public SignPattern(String word, String sequence) {
        this.word = word;
        this.sequence = sequence;
    }

    // Getters y setters
    public Long getId() { return id; }

    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }

    public String getSequence() { return sequence; }
    public void setSequence(String sequence) { this.sequence = sequence; }

}
