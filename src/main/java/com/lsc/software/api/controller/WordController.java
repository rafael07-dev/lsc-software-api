package com.lsc.software.api.controller;

import com.lsc.software.api.Dto.WordDto;
import com.lsc.software.api.model.Word;
import com.lsc.software.api.response.ResponseApi;
import com.lsc.software.api.service.WordService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/words")
public class WordController {

    private static final Logger log = LoggerFactory.getLogger(WordController.class);
    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<Word>> getAllWords(@RequestParam(value = "letter", required = false) String letter,
                                                  @RequestParam(defaultValue = "0") int pageIndex,
                                                  @RequestParam(defaultValue = "10") int pageSize) {

        return ResponseEntity.ok().body(wordService.getAllWords(letter, pageIndex, pageSize));
    }

    @GetMapping("/{idWord}")
    public ResponseEntity<Optional<Word>> findById(@PathVariable Long idWord) {
        return ResponseEntity.ok().body(wordService.findById(idWord));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Word> addWord(@RequestBody @Valid WordDto word) {
        var savedWord = wordService.saveWord(word);

        return ResponseEntity.created(URI.create("/api/words/" + savedWord.getId()))
                .body(savedWord);
    }

    @PostMapping(value = "/{id}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam MultipartFile file, @PathVariable Long id) throws IOException {
        log.info("Uploading file: {}", file.getOriginalFilename());
        return wordService.uploadFile(id, file);
    }

    @PutMapping("/{idWord}/update")
    public ResponseEntity<String> updateFile(@RequestParam MultipartFile file, @PathVariable Long idWord) throws IOException {
        return ResponseEntity.ok().body(wordService.updateFile(file, idWord));
    }

    @DeleteMapping("/delete/{idWord}")
    public ResponseEntity<ResponseApi> deleteWord(@PathVariable Long idWord) {
        return ResponseEntity.ok().body(wordService.deleteWord(idWord));
    }

    @PutMapping("/update/{idWord}")
    public ResponseEntity<Word> updateWord(@RequestBody WordDto word, @PathVariable Long idWord) {
        return ResponseEntity.ok().body(wordService.updateWord(word, idWord));
    }
}