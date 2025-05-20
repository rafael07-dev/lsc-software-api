package com.lsc.software.api.controller;

import com.lsc.software.api.model.UserEntity;
import com.lsc.software.api.repository.UserRepository;
import com.lsc.software.api.response.ResponseApi;
import com.lsc.software.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserEntity>> getAllUser() {
        UserEntity user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("User not found"));

        log.info(String.valueOf(user.getAuthorities().size()));

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/authenticated")
    public ResponseEntity<UserEntity> getCurrentUser() {
        UserEntity user = userService.getCurrentUser();
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
        return ResponseEntity.ok().body(userService.update(id, user));
    }

    @PatchMapping("/update")
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity user) {
        return ResponseEntity.ok().body(userService.updateByPatcher(user));
    }

    @PostMapping("/create")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        return ResponseEntity.created(URI.create("/api/users/" + user.getId())).body(userService.save(user));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseApi> createUser(@PathVariable Long id, @RequestBody UserEntity user) {
        return ResponseEntity.ok().body(userService.delete(id));
    }
}
