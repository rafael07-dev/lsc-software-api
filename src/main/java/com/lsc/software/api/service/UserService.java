package com.lsc.software.api.service;

import com.lsc.software.api.Dto.UserSingUp;
import com.lsc.software.api.Utils.Patcher;
import com.lsc.software.api.model.UserEntity;
import com.lsc.software.api.repository.UserRepository;
import com.lsc.software.api.response.ResponseApi;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final Patcher patcher;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, Patcher patcher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.patcher = patcher;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles().split(","))
                .build();
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

        if (user.getRoles() == null) {
            user.setRoles("ROLE_USER");
        }
        return userRepository.save(user);
    }

    public ResponseApi register(UserSingUp user) {

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setEmail(user.getEmail());

        if (user.getRoles() == null) {
            userEntity.setRoles("ROLE_ADMIN");
        }

        userRepository.save(userEntity);

        return new ResponseApi(200, "User " + user.getFirstName() + " registered successfully");
    }

    public ResponseApi delete(Long id) {
        userRepository.deleteById(id);
        return new ResponseApi(200, "User deleted successfully");
    }

    public UserEntity update(Long id, UserEntity newUser) {
        UserEntity oldUser =  userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        oldUser.setUsername(newUser.getUsername());
        oldUser.setPassword(newUser.getPassword());
        oldUser.setRoles(newUser.getRoles());

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
