package com.lsc.software.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "gifs")
public class Giff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 100, name = "gif_url")
    String giffUrl;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "word_id")
    Word word;

    public Giff() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGiffUrl() {
        return giffUrl;
    }

    public void setGiffUrl(String giffUrl) {
        this.giffUrl = giffUrl;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }
}
