package org.silvachristian.searchfilms.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.silvachristian.searchfilms.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    private final String secretKey = "chaveSecreta";

    public Instant getExpirationDate() {
        return Instant.now().plusSeconds(3600);
    }

    public String generateToken(UserEntity user) {
        System.out.println("Creating JWT");
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            String token = JWT.create()
                    .withIssuer("user-auth-service")
                    .withSubject(user.getUsername())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
            System.out.println("JWT created: " + token);
            return token;
        }
        catch (JWTCreationException exception) {
           throw new RuntimeException("JWT creation exception");
        }
    }

    public String validateToken(String token) {
        System.out.println("Validating JWT: " + token);
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer("user-auth-service")
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch(JWTVerificationException e) {
            System.out.println("JWT Verification Exception: " + e.getMessage());
            return null;
        }
    }
}
