package com.example.test.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
public class UserResponse {
    private String username;
    private String password;
}
