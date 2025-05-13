package com.lsc.software.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "words")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "letter"})
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "word")
    private String word;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "letter_id")
    private Letter letter;

    @OneToOne(mappedBy = "word", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Giff giff;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }

    public Giff getGiff() {
        return giff;
    }

    public void setGiff(Giff giff) {
        this.giff = giff;
    }
}
