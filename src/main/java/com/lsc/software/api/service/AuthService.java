package com.lsc.software.api.service;

import com.lsc.software.api.Dto.UserSingUp;
import com.lsc.software.api.model.ConfirmationToken;
import com.lsc.software.api.model.UserEntity;
import com.lsc.software.api.repository.ConfirmationTokenRepository;
import com.lsc.software.api.repository.UserRepository;
import com.lsc.software.api.response.ResponseApi;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthService(@Lazy UserService userService, ConfirmationTokenRepository confirmationTokenRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userService = userService;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public UserDetails loadUser(String username) {
        return userService.loadUserByUsername(username);
    }

    public ResponseApi register(UserSingUp user) {

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setEmail(user.getEmail());
        userEntity.setActive(user.isActive());

        if (user.getRoles() == null) {
            userEntity.setRoles("ROLE_USER");
        }

        userRepository.save(userEntity);

        ConfirmationToken token = new ConfirmationToken(userEntity);
        confirmationTokenRepository.save(token);

        emailService.sendConfirmationEmail(user.getEmail(), token.getToken());

        return new ResponseApi(200, "We have sent a confirmation email to verify your account.");
    }
}
