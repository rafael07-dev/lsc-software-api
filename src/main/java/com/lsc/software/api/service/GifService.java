package com.lsc.software.api.service;

import com.lsc.software.api.repository.GiffRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class GifService {

    private static final Logger log = LoggerFactory.getLogger(GifService.class);
    private final GiffRepository giffRepository;

    @Value("${file.storage.path}")
    private String storagePath;

    public GifService(GiffRepository giffRepository) {
        this.giffRepository = giffRepository;
    }

    public Map<String, Object> getGiffById(Long id) throws MalformedURLException, FileNotFoundException {
        var gif = giffRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("Giff not found "+ id));

        String gifUrl = gif.getGiffUrl();

        String fileName = getFilenameFromGifUrl(gifUrl);

        log.info("Gif url: {}", gifUrl);

        Path filePath = Paths.get(storagePath + fileName);

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

    private String getFilenameFromGifUrl(String gifUrl) {
        String [] folders = gifUrl.split("/");
        return folders[folders.length - 1];
    }
}
