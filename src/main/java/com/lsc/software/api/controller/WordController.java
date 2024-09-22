package com.lsc.software.api.controller;

import com.lsc.software.api.model.Word;
import com.lsc.software.api.service.WordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /*@GetMapping("/{letter}")
    public ResponseEntity<List<Word>> getWordsByLetter(@RequestParam Long letter) {
        return ResponseEntity.ok().body(wordService.findWordById(idLetter));
    }*/
}
