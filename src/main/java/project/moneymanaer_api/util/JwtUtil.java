package project.moneymanaer_api.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private static final long MILLIS_IN_HOUR = 60 * 60 * 1000;


    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generates a jwt token for the given user email, with an expiration date of 1 hour.
     * @param userEmail user email.
     * @return a jwt token valid for 1 hour.
     */
    public String generateToken(String userEmail) {
        return Jwts.builder()
                .subject(userEmail)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + MILLIS_IN_HOUR))
                .signWith(secretKey)
                .compact();
    }

    public String getEmailFromUserToken(String jwt) {
        return parseJwtToken(jwt);
    }

    private String parseJwtToken(String jwt) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String jwt, UserDetails userDetails) {
        String email = getEmailFromUserToken(jwt);
        return email.equals(userDetails.getUsername());
    }
}