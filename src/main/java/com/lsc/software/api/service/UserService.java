package com.lsc.software.api.service;

import com.lsc.software.api.Utils.Patcher;
import com.lsc.software.api.model.UserEntity;
import com.lsc.software.api.repository.UserRepository;
import com.lsc.software.api.response.ResponseApi;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final Patcher patcher;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, Patcher patcher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.patcher = patcher;
    }

    public UserEntity getCurrentUser() {
        Authentication userAuthenticated = SecurityContextHolder.getContext().getAuthentication();

        if (userAuthenticated != null && userAuthenticated.getPrincipal() instanceof UserEntity) {
            return (UserEntity) userAuthenticated.getPrincipal();
        }

        return null;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserEntity save(UserEntity user) {
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        return userRepository.save(user);
    }

    public ResponseApi delete(Long id) {
        userRepository.deleteById(id);
        return new ResponseApi(200, "User deleted successfully");
    }

    public UserEntity update(Long id, UserEntity newUser) {
        UserEntity oldUser =  userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        oldUser.setFirstName(newUser.getFirstName());
        oldUser.setLastName(newUser.getLastName());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        Set<String> roles = newUser.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet());

        oldUser.setRoles(roles);

        userRepository.save(oldUser);

        return oldUser;
    }

    public UserEntity updateByPatcher(UserEntity user) {

        UserEntity oldUser = userRepository.findById(user.getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        try {
            patcher.userPatcher(oldUser, user);

            userRepository.save(oldUser);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return oldUser;
    }
}
