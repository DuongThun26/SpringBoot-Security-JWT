package com.example.test.dto;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JWTInfor {
    private String jwtId;
    private Date issueTime;
    private Date expiredTime;
}
