package com.example.test.service;

import com.example.test.dto.JWTInfor;
import com.example.test.dto.request.LoginRequest;
import com.example.test.dto.response.LoginResponse;
import com.example.test.entity.RedisToken;
import com.example.test.entity.UserEntity;
import com.example.test.repository.RedisTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final RedisTokenRepository redisTokenRepository;

    public LoginResponse login(LoginRequest request){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        UserEntity user = (UserEntity) authenticate.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void logout(String token) throws ParseException {
        JWTInfor jwtInfor = jwtService.parseToken(token);
        String jwtId = jwtInfor.getJwtId();;
        Date issueTime = jwtInfor.getIssueTime();
        Date expiredTime = jwtInfor.getExpiredTime();

        if(expiredTime.before(new Date())){
            return;
        }
        RedisToken redisToken = RedisToken.builder()
                .jwtId(jwtId)
                .expiredTime(expiredTime.getTime() - issueTime.getTime())
                .build();
        redisTokenRepository.save(redisToken);
    }
}
