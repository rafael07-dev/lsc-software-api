package com.lsc.software.api.controller;

import com.lsc.software.api.model.UserEntity;
import com.lsc.software.api.response.ResponseApi;
import com.lsc.software.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserEntity>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUsers());
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
