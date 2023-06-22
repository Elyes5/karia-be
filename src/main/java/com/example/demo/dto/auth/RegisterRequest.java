package com.example.demo.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RegisterRequest
{
 private String email;
 private String password;
 private String firstname;
 private String lastname;
 private int age;
 private String username;
 private byte[] avatar;
}
