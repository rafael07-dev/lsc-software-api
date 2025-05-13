package com.lsc.software.api.service;

import com.lsc.software.api.Dto.UserAuth;
import com.lsc.software.api.Dto.UserSingUp;
import com.lsc.software.api.model.ConfirmationToken;
import com.lsc.software.api.model.UserEntity;
import com.lsc.software.api.repository.ConfirmationTokenRepository;
import com.lsc.software.api.repository.UserRepository;
import com.lsc.software.api.response.ResponseApi;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;

    public AuthService(ConfirmationTokenRepository confirmationTokenRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService, AuthenticationManager authenticationManager) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
    }

    /*public UserDetails loadUser(String username) {
        return userService.loadUserByUsername(username);
    }*/

    public ResponseApi register(UserSingUp user) {

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setEmail(user.getEmail());
        userEntity.isEnabled();

        userRepository.save(userEntity);

        ConfirmationToken token = new ConfirmationToken(userEntity);
        confirmationTokenRepository.save(token);

        emailService.sendConfirmationEmail(user.getEmail(), token.getToken());

        return new ResponseApi(200, "We have sent a confirmation email to verify your account.");
    }

    public UserEntity login(UserAuth user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        return userRepository.findByEmail(user.getEmail()).orElseThrow();
    }
}
