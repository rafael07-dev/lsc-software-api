package com.lsc.software.api.controller;

import com.lsc.software.api.model.Giff;
import com.lsc.software.api.repository.GiffRepository;
import com.lsc.software.api.service.GifService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gifs")
public class GifController {

    private final GiffRepository giffRepository;
    private final GifService gifService;

    public GifController(GiffRepository giffRepository, GifService gifService) {
        this.giffRepository = giffRepository;
        this.gifService = gifService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Giff>> getAllGifs(){
        return ResponseEntity.ok(giffRepository.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Resource> getGifById(@PathVariable Long id) throws MalformedURLException, FileNotFoundException {
        Map<String, Object> response = gifService.getGiffById(id);

        Resource resource = (Resource) response.get("resource");

        HttpHeaders headers = (HttpHeaders) response.get("headers");

        if (resource.exists()){
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("video/mp4"))
                    .headers(headers)
                    .body(resource);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
