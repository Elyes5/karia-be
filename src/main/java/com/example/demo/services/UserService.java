package com.example.demo.services;

import com.example.demo.config.JwtService;
import com.example.demo.repositories.UserRepository;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.models.User;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    public Long checkJWTService(Long id,String token){
        return jwtService.compareJWT(token,id);

    }
    public List<User> findAll()
    {
        return userRepository.findAll();
    }

}
