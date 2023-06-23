package com.example.demo.services;

import com.example.demo.config.JwtService;
import com.example.demo.dto.auth.AuthenticationRequest;
import com.example.demo.dto.auth.AuthenticationResponse;
import com.example.demo.dto.auth.RegisterRequest;
import com.example.demo.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.models.User;
@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest registerRequest)
    {
        User user = new User(
                new BCryptPasswordEncoder().encode(registerRequest.getPassword()),
                registerRequest.getEmail(),
                registerRequest.getFirstname(),
                registerRequest.getLastname(),
                registerRequest.getAge(),
                registerRequest.getUsername(),
                registerRequest.getAvatar()
                );
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return  AuthenticationResponse.builder().token(jwtToken).build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User is not found"));
        var jwtToken = jwtService.generateToken(user);
        return  AuthenticationResponse.builder().token(jwtToken).build();
    }

}
