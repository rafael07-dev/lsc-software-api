package com.lsc.software.api.controller;

import com.lsc.software.api.model.UserEntity;
import com.lsc.software.api.service.GiffStorageService;
import com.lsc.software.api.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final GiffStorageService giffStorageService;

    public AdminController(UserService userService, GiffStorageService giffStorageService) {
        this.userService = userService;
        this.giffStorageService = giffStorageService;
    }

    @GetMapping("/users")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("/upload-gif/{wordId}")
    public String uploadGif(@RequestParam("file") MultipartFile file, @PathVariable Long wordId) throws IOException {
        return giffStorageService.storeGiff(file, wordId);
    }
}
