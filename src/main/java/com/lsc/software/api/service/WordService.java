package com.lsc.software.api.service;

import com.lsc.software.api.model.Letter;
import com.lsc.software.api.model.Word;
import com.lsc.software.api.repository.LetterRepository;
import com.lsc.software.api.repository.WordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WordService {

    private static final Logger log = LoggerFactory.getLogger(WordService.class);
    private final WordRepository wordRepository;
    private final LetterRepository letterRepository;

    public WordService(WordRepository wordRepository, LetterRepository letterRepository) {
        this.wordRepository = wordRepository;
        this.letterRepository = letterRepository;
    }

    public List<Word> getAllWords(String letter) {

        if (letter == null || letter.isEmpty()) {
            return wordRepository.findAll();
        }

        Optional<Letter> letterObj = letterRepository.findByLetter(letter);

        if (letterObj.isPresent()) {
            List<Word> words = new ArrayList<>();

            words = letterObj.get().getWords();
            return words;
        }

        return wordRepository.findAll();

    }

    public List<Word> findWordById(Long id) {
        return null;
    }
}
