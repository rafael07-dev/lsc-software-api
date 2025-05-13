package com.lsc.software.api.service;

import com.lsc.software.api.model.Letter;
import com.lsc.software.api.model.Word;
import com.lsc.software.api.repository.LetterRepository;
import com.lsc.software.api.repository.WordRepository;
import com.lsc.software.api.response.ResponseApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WordService {

    private static final Logger log = LoggerFactory.getLogger(WordService.class);
    private final WordRepository wordRepository;
    private final LetterRepository letterRepository;
    private final GiffStorageService giffStorageService;

    public WordService(WordRepository wordRepository, LetterRepository letterRepository, GiffStorageService giffStorageService) {
        this.wordRepository = wordRepository;
        this.letterRepository = letterRepository;
        this.giffStorageService = giffStorageService;
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

    public Optional<Word> findById(Long id) {
        return wordRepository.findById(id);
    }

    public Word saveWord(Word word) throws IOException {
        //giffStorageService.storeGiff(file, word.getId());

        return wordRepository.save(word);
    }

    public String uploadFile(Long id, MultipartFile file) throws IOException {
        return giffStorageService.storeGiff(file, id);
    }

    public ResponseApi deleteWord(Long idWord) {
        wordRepository.deleteById(idWord);
        return new ResponseApi(200, "Word deleted");
    }

    public ResponseApi updateWord(Word word, Long idWord) {

        log.info("Letter ID received: {}", word.getLetter().getId());

        Optional<Word> oldWord = wordRepository.findById(idWord);

        if (oldWord.isPresent()) {
            Word newWordObj = oldWord.get();

            newWordObj.setWord(word.getWord());

            if (word.getLetter() != null && !Objects.equals(oldWord.get().getLetter().getId(), word.getLetter().getId())) {
                newWordObj.setLetter(word.getLetter());
            }

            wordRepository.save(newWordObj);

            return new ResponseApi(200, "Word updated");
        }else {
            return new ResponseApi(400, "Word not found");
        }

        //return new ResponseApi(200, "Word updated");
    }
}
