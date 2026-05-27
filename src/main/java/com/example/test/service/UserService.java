package com.example.test.service;

import com.example.test.dto.request.UserRequest;
import com.example.test.dto.response.UserResponse;
import com.example.test.entity.UserEntity;
import com.example.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserResponse create(UserRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("User already exists");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return UserResponse.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    public List<UserResponse> getAll(){
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(user -> UserResponse.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build()).toList();
    }

    public void delete(Long Id){
        userRepository.deleteById(Id);
    }
}
