package com.lsc.software.api.service;

import com.lsc.software.api.Dto.WordDto;
import com.lsc.software.api.model.Word;
import com.lsc.software.api.repository.LetterRepository;
import com.lsc.software.api.repository.WordRepository;
import com.lsc.software.api.response.ResponseApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
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

    public Page<Word> getAllWords(String letter, int pageIndex, int pageSize) {

        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        if (letter == null || letter.isEmpty()) {
            return wordRepository.findAll(pageable);
        }

        return wordRepository.findByLetter_Letter(letter, pageable);
    }

    public Optional<Word> findById(Long id) {
        return wordRepository.findById(id);
    }

    public Word saveWord(WordDto word) {
        Word newWord = new Word();

        var letter = letterRepository.findById(word.getLetter_id())
                .orElseThrow(() -> new IllegalArgumentException("Letter not found"));

        newWord.setWord(word.getWord());
        newWord.setLetter(letter);

        return wordRepository.save(newWord);
    }

    public String uploadFile(Long id, MultipartFile file) throws IOException {
        return giffStorageService.storeGiff(file, id);
    }

    public String updateFile(MultipartFile file, Long id) throws IOException {
        return giffStorageService.updateGiff(file, id);
    }

    public ResponseApi deleteWord(Long idWord) {
        wordRepository.deleteById(idWord);
        return new ResponseApi(200, "Word deleted");
    }

    public Word updateWord(WordDto word, Long idWord) {

        log.info("Letter ID received: {}", word.getLetter_id());

        var letterRequest = letterRepository.findById(word.getLetter_id())
                .orElseThrow(() -> new IllegalArgumentException("Letter not found"));

        Optional<Word> oldWord = wordRepository.findById(idWord);

        if (oldWord.isPresent()) {
            Word newWordObj = oldWord.get();

            newWordObj.setWord(word.getWord());

            if (letterRequest != null && !Objects.equals(oldWord.get().getLetter().getId(), word.getLetter_id())) {
                newWordObj.setLetter(letterRequest);
            }

            return wordRepository.save(newWordObj);
        }else {
            return null;
        }
    }
}
