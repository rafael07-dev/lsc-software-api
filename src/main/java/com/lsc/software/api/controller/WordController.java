package com.lsc.software.api.controller;

import com.lsc.software.api.model.Word;
import com.lsc.software.api.response.ResponseApi;
import com.lsc.software.api.service.WordService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/words")
public class WordController {

    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Word>> getAllWords(@RequestParam(value = "letter", required = false) String letter) {
        return ResponseEntity.ok().body(wordService.getAllWords(letter));
    }

    @GetMapping("/{idWord}")
    public ResponseEntity<Optional<Word>> findById(@PathVariable Long idWord) {
        return ResponseEntity.ok().body(wordService.findById(idWord));
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<Word> addWord(@RequestBody @Valid Word word) {
        return ResponseEntity.created(URI.create("/api/words/" + word.getId()))
                .body(wordService.saveWord(word));
    }

    @DeleteMapping("/delete/{idWord}")
    public ResponseEntity<ResponseApi> deleteWord(@PathVariable Long idWord) {
        return ResponseEntity.ok().body(wordService.deleteWord(idWord));
    }
}
