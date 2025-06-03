package com.lsc.software.api.controller;

import com.lsc.software.api.Utils.LandmarkNormalizer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsc.software.api.Dto.SignPatternRequest;
import com.lsc.software.api.Dto.SignPatternResponse;
import com.lsc.software.api.model.SignPattern;
import com.lsc.software.api.repository.SignPatternRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/signs")
@CrossOrigin
public class SignPatternController {

    private final SignPatternRepository repository;
    private final ObjectMapper mapper;

    public SignPatternController(SignPatternRepository repository, ObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<?> createSign(@RequestBody SignPatternRequest request) {
        try {
            // Normalizar secuencia antes de guardar
            List<List<List<Double>>> normalizedSequence = LandmarkNormalizer.normalizeSequence(request.getSequence());

            String sequenceJson = mapper.writeValueAsString(normalizedSequence);

            SignPattern pattern = new SignPattern();
            pattern.setWord(request.getWord().toLowerCase());
            pattern.setSequence(sequenceJson);

            return ResponseEntity.ok(repository.save(pattern));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al procesar la solicitud: " + e.getMessage());
        }
    }

    @GetMapping("/{word}")
    public ResponseEntity<SignPattern> getByWord(@PathVariable String word) {
        return repository.findByWord(word)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patterns")
    public List<SignPatternResponse> getAllPatterns() {
        return repository.findAll().stream().map(sign -> {
            try {
                List<List<List<Double>>> pattern = mapper.readValue(
                        sign.getSequence(), new TypeReference<>() {}
                );
                return new SignPatternResponse(sign.getWord(), pattern);
            } catch (Exception e) {
                return new SignPatternResponse(sign.getWord(), new ArrayList<>());
            }
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<String> deleteAllSigns() {
        repository.deleteAll();
        return ResponseEntity.ok("Todos los patrones de señas han sido eliminados.");
    }
}
