package com.lsc.software.api.service;

import com.lsc.software.api.model.Giff;
import com.lsc.software.api.repository.GiffRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class GifService {

    private static final Logger log = LoggerFactory.getLogger(GifService.class);
    private final GiffRepository giffRepository;
    private final GiffStorageService giffStorageService;

    public GifService(GiffRepository giffRepository, GiffStorageService giffStorageService) {
        this.giffRepository = giffRepository;
        this.giffStorageService = giffStorageService;
    }

    public Giff getGiffByWordId(Long wordId) {
        Giff giff = giffRepository.findByWordId(wordId);

        if (giff != null && giff.getGiffUrl() != null) {
            String fileName = Paths.get(giff.getGiffUrl()).getFileName().toString();

            giff.setGiffUrl("http://localhost:8080/gifs/" + fileName);
        }
        return giff;
    }

    public Map<String, Object> getGiffById(Long id) throws MalformedURLException, FileNotFoundException {
        var gif = giffRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("Giff not found "+ id));

        String gifUrl = gif.getGiffUrl();

        log.info("Gif url: {}", gifUrl);

        String fileName = Paths.get(gifUrl).getFileName().toString();

        Path filePath = Paths.get(gifUrl).toAbsolutePath().normalize();

        Resource resource = getResource(filePath, fileName);

        if (resource.exists() || resource.isReadable()) {
            HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + resource.getFilename());
            headers.add(HttpHeaders.CONTENT_TYPE, "video/mp4");

            Map<String, Object> response = new HashMap<>();
            response.put("headers", headers);
            response.put("resource", resource);

            return response;
        }else {
            throw new MalformedURLException("File not found");
        }
    }

    public Giff updateGiff(Giff gif, Long id) throws IOException {
        var gifToUpdate = giffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Giff not found "+ id));

        String fileName = getFilenameFromGifUrl(gif);


        gifToUpdate.setWord(gif.getWord());

        return giffRepository.save(gifToUpdate);
    }

    private Resource getResource(Path filePath, String fileName) throws FileNotFoundException {
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new FileNotFoundException("File not found: " + fileName);
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("error reading file: " + fileName);
        }
    }

    private String getFilenameFromGifUrl(Giff giff) {
        try {

            return Paths.get(giff.getGiffUrl()).getFileName().toString();
        }catch (InvalidPathException e) {
            log.error("error reading file: {}", giff.getGiffUrl());
        }
        return null;
    }
}
