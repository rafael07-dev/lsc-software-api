package com.lsc.software.api.service;

import com.lsc.software.api.model.Giff;
import com.lsc.software.api.model.Word;
import com.lsc.software.api.repository.GiffRepository;
import com.lsc.software.api.repository.WordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GiffStorageService {

    private static final Logger log = LoggerFactory.getLogger(GiffStorageService.class);
    private final Path fileStorageLocation;
    private final WordRepository wordRepository;
    private final GiffRepository giffRepository;

    public GiffStorageService(WordRepository wordRepository, GiffRepository giffRepository) {
        this.wordRepository = wordRepository;
        this.giffRepository = giffRepository;
        this.fileStorageLocation = Paths.get("uploads/gifs").toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        }catch (IOException e){
            System.err.println("Failed to create the directory " + this.fileStorageLocation);
        }
    }

    public String storeGiff(MultipartFile file, Long wordId) throws IOException {

        Word word = wordRepository.findById(wordId)
                .orElseThrow(()-> new IllegalArgumentException("Word not found"));

        //we changed the original name of the file to the name of the word
        String fileName = word.getWord() + ".mp4";

        if (fileName.contains(".")) {
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

            if (!extension.matches("mp4|3gp|wav|gif")) {
                throw new IllegalArgumentException("Invalid file extension");
            }
            if (!Objects.requireNonNull(file.getContentType()).equalsIgnoreCase("video/mp4")){
                throw new IllegalArgumentException("Only video/mp4 files are supported");
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Giff giff = new Giff();

            giff.setGiffUrl("/gifs/"+ fileName);
            giff.setWord(word);

            giffRepository.save(giff);

            return giff.getGiffUrl();
        }
        throw new IllegalArgumentException("Invalid file");
    }

    public List<Giff> moveGiffToNewLocation() {

        List<Giff> updated = new ArrayList<>();

        Path source = Paths.get("src/main/resources/static/gifs").toAbsolutePath();

        if (!Files.exists(source)) {
            return updated;
        }

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(source)){

            for (Path file : directoryStream) {
                if (!Files.isRegularFile(file)){
                    return updated;
                }

                String filename = file.getFileName().toString();
                Path targetLocation = this.fileStorageLocation.resolve(filename);

                try {
                    if (!Files.exists(file)) {
                        log.info("File not found");
                    }
                    Files.move(file, targetLocation, StandardCopyOption.REPLACE_EXISTING);

                    Giff giff = giffRepository.findByGiffUrl("C:/Users/Deiner Rafael/Desktop/Rafael-dev/api-lsc-software/gifs/"+filename);

                    if (giff != null) {
                        giff.setGiffUrl("/gifs/"+ filename);

                        giffRepository.save(giff);

                        updated.add(giff);
                    }else{
                        log.warn("Could not move gif file {}", filename);
                    }

                } catch (IOException e){
                    log.error("Failed to move gif file", e);
                }
            }
        } catch (IOException e) {
            log.error("Failed to move gif file", e);
        }
        return updated;
    }
}