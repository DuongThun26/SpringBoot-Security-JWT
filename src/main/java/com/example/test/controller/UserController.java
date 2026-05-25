package com.example.test.controller;

import com.example.test.dto.request.UserRequest;
import com.example.test.dto.response.UserResponse;
import com.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/users")
    public UserResponse create(@RequestBody UserRequest request){
        UserResponse user = userService.create(request);
        return user;
    }

    @GetMapping(value = "/users")
    public List<UserResponse> getAll(){
        return userService.getAll();
    }

    @DeleteMapping(value = "/users/{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }
}
