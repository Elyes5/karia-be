package com.example.demo.controllers;

import com.example.demo.config.JwtService;
import com.example.demo.dto.auth.MessageDto;
import com.example.demo.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
@AllArgsConstructor
public class UserController
{
    private final UserService userService;
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers()
    {
        return ResponseEntity.status(200).body(userService.findAll());
    }
    @PutMapping("/users/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestHeader(name="Authorization") String token)
    {
        Long id = userService.checkJWTService(userId,token);
        if (id != null)
        {
            return ResponseEntity.status(200).body(new MessageDto("The user has been updated successfully"));
        }
        return ResponseEntity.status(404).body(new MessageDto("The user does not exist"));
    }
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId,@RequestHeader(name="Authorization") String token)
    {
        Long id = userService.checkJWTService(userId,token);
        if (id != null)
        {
            return ResponseEntity.status(200).body(new MessageDto("The user has been deleted successfully"));
        }
        return ResponseEntity.status(404).body(new MessageDto("The user does not exist"));
    }
}
