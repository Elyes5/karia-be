package com.example.demo.config;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Signature;
import java.util.*;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtService {
    private static final String SECRET_KEY = "owkg6L/z1OJglfbTuIVRCSzWf0KyNHHLb6XhknR9L0M=";
    private final UserRepository userRepository;
    public String extractUserEmail(String token) {
        return extractClaim(token,Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims,T> ClaimResolver)
    {
        final Claims claims = extractAllClaims(token);
        return ClaimResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.
                parserBuilder().
                setSigningKey(getSignInKey()).
                build().
                parseClaimsJws(token).
                getBody();
    }
    public Long compareJWT(String token,Long id){
       String subToken = token.substring(7);
       String email =  extractUserEmail(subToken);
       Optional<User> user = userRepository.findByEmail(email);
       if (user.isPresent()) {
           if (user.get().getId().equals(id))
               return user.get().getId();
       }
       return null;

    }

    public String generateToken(
            Map<String,Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24*20))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateToken(UserDetails userDetails){
        HashMap<String, Object> userRoles = new HashMap<String, Object>();
        userRoles.put("role",userDetails.getAuthorities().stream().findFirst().get().toString());
        return generateToken(userRoles,userDetails);
    }
    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public boolean isTokenValid(String token,UserDetails userDetails){
        final String userEmail = extractUserEmail(token);
        return (userEmail.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

}
