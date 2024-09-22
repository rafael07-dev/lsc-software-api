package com.lsc.software.api.controller;

import com.lsc.software.api.model.Letter;
import com.lsc.software.api.service.LetterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/letters")
public class LetterController {

    private final LetterService letterService;

    public LetterController(LetterService letterService) {
        this.letterService = letterService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Letter>> getAllLetters(){
        return ResponseEntity.ok().body(letterService.getLetters());
    }
}
