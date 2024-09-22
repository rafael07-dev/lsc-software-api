package com.lsc.software.api.service;

import com.lsc.software.api.model.Letter;
import com.lsc.software.api.repository.LetterRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LetterService {

    private final LetterRepository letterRepository;

    public LetterService(LetterRepository letterRepository) {
        this.letterRepository = letterRepository;
    }

    public List<Letter> getLetters() {
        return letterRepository.findAll();
    }
}
