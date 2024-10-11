package com.lsc.software.api.service;

import com.lsc.software.api.model.Giff;
import com.lsc.software.api.model.Word;
import com.lsc.software.api.repository.GiffRepository;
import com.lsc.software.api.repository.WordRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class GiffStorageService {

    private final Path fileStorageLocation;
    private final WordRepository wordRepository;
    private final GiffRepository giffRepository;

    public GiffStorageService(WordRepository wordRepository, GiffRepository giffRepository) {
        this.wordRepository = wordRepository;
        this.giffRepository = giffRepository;
        this.fileStorageLocation = Paths.get("gifs").toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        }catch (IOException e){
            System.err.println("Failed to create the directory " + this.fileStorageLocation);
        }
    }

    public String storeGiff(MultipartFile file, Long wordId) throws IOException {
        String fileName = file.getOriginalFilename();

        Word word = wordRepository.findById(wordId)
                .orElseThrow(()-> new IllegalArgumentException("Word not found"));

        assert fileName != null;
        if (fileName.contains(".")) {
            String extension = fileName.substring(fileName.lastIndexOf("."));

            if (extension.equals("mp4") || extension.equals("3gp") || extension.equals("wav") || extension.equals("gif")) {
                Giff giff = new Giff();

                giff.setGiffUrl(fileStorageLocation.toAbsolutePath() + "/" + file.getOriginalFilename());
                giff.setWord(word);

                giffRepository.save(giff);
            }
        }

        if (!Objects.requireNonNull(file.getContentType()).equalsIgnoreCase("video/mp4")){
            throw new IllegalArgumentException("Only video/mp4 files are supported");
        }

        Path targetLocation = this.fileStorageLocation.resolve(fileName);

        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return targetLocation.toString();
    }
}