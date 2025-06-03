package com.lsc.software.api.controller;

import com.lsc.software.api.model.Giff;
import com.lsc.software.api.service.GifService;
import com.lsc.software.api.service.GiffStorageService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gifs")
public class GifController {

    private final GifService gifService;
    private final GiffStorageService giffStorageService;

    public GifController(GifService gifService, GiffStorageService giffStorageService) {
        this.gifService = gifService;
        this.giffStorageService = giffStorageService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<Giff>> getAllGifs(@RequestParam(defaultValue = "0") int pageIndex,
                                                 @RequestParam(defaultValue = "10") int pageSize){
        return ResponseEntity.ok(gifService.getGifs(pageIndex, pageSize));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Resource> getGifById(@PathVariable Long id) throws MalformedURLException, FileNotFoundException {
        Map<String, Object> response = gifService.getGiffById(id);

        return getResourceResponseEntity(response);
    }

    @GetMapping("/word/{wordId}")
    public ResponseEntity<Giff> getGifByWordId(@PathVariable Long wordId){
        Giff giff = gifService.getGiffByWordId(wordId);
        return ResponseEntity.ok(giff);
    }

    @PostMapping("/migrate")
    public ResponseEntity<List<Giff>> migrate(){
        return ResponseEntity.ok(giffStorageService.moveGiffToNewLocation());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Giff> updateGiff(@RequestBody Giff giff, @PathVariable Long id) throws IOException {
        return ResponseEntity.ok(gifService.updateGiff(giff, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Giff> deleteGiff(@PathVariable Long id){
        return ResponseEntity.ok(gifService.deleteGiff(id));
    }

    private ResponseEntity<Resource> getResourceResponseEntity(Map<String, Object> response) {
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
