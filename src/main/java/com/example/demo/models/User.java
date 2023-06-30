package com.example.demo.models;
import com.example.demo.enums.ERole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users",uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User implements UserDetails {
    public User(String password,String email,String firstname,String lastname,int age,String username,byte[] avatar)
    {
        this.role = ERole.USER;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.username = username;
        this.avatar = avatar;
    }
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private ERole role;
    @JsonIgnore
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotBlank(message =  "Name is mandatory")
    private String firstname;
    @NotBlank(message = "Surname is mandatory")
    private String lastname;
    private int age;
    @NotBlank(message = "Username is mandatory")
    private String username;
    @Lob
    private byte[] avatar;
    @OneToMany(cascade = CascadeType.REMOVE,mappedBy="user")
    private Set<Rating> ratings;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    @JsonIgnore
    private Set<Property> properties;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    private Set<Review> reviews;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    public String getUsername(){
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
