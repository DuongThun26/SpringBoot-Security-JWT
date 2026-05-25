package com.example.test.service;

import com.example.test.dto.request.UserRequest;
import com.example.test.dto.response.UserResponse;
import com.example.test.entity.UserEntity;
import com.example.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserResponse create(UserRequest request){
        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .fullName(request.getFullName())
                .dateOfBirth(request.getDateOfBirth())
                .phone(request.getPhone())
                .build();
        userRepository.save(user);
        return new UserResponse(user.getUsername(), user.getPassword());
    }

    public List<UserResponse> getAll(){
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(user -> new UserResponse(user.getUsername(), user.getPassword())).toList();
    }

    public void delete(Long Id){
        userRepository.deleteById(Id);
    }
}
