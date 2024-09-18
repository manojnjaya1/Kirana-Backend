package com.example.Kirana.serviceImpl;

import com.example.Kirana.CustomExceptions.TokenGenerationException;
import com.example.Kirana.CustomExceptions.TokenValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service class for handling JWT operations including token generation, extraction, and validation.
 * <p>
 * This service provides methods to generate JWT tokens, extract user information from tokens, and validate token authenticity and expiration.
 * </p>
 */
@Service
public class JWTService {

    private static final Logger logger = LoggerFactory.getLogger(JWTService.class);
    private String secretKey;

    /**
     * Constructor to initialize the secret key for JWT signing.
     * <p>
     * Generates a new secret key using HmacSHA256 algorithm and encodes it in Base64.
     * </p>
     */
    public JWTService() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKeyObject = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(secretKeyObject.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error generating secret key", e);
            throw new TokenGenerationException("Error generating secret key", e);
        }
    }

    /**
     * Generates a JWT token for the specified username.
     * <p>
     * Creates a token with claims and subject set to the username, and sets the token expiration time.
     * </p>
     *
     * @param username the username for which the token is to be generated.
     * @return the generated JWT token as a String.
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        try {
            return Jwts.builder()
                    .claims()
                    .add(claims)
                    .subject(username)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis()+ 60*60*100*30))
                    .and()
                    .signWith(getKey())
                    .compact();
        } catch (Exception e) {
            logger.error("Error generating token for username: {}", username, e);
            throw new TokenGenerationException("Error generating token", e);
        }
    }

    /**
     * Extracts the username from the provided JWT token.
     * <p>
     * Retrieves the subject from the token, which is the username.
     * </p>
     *
     * @param token the JWT token from which the username is to be extracted.
     * @return the username extracted from the token.
     */
    public String extractUserName(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (Exception e) {
            logger.error("Error extracting username from token", e);
            throw new TokenValidationException("Error extracting username from token", e);
        }
    }

    /**
     * Validates the provided JWT token against the user details.
     * <p>
     * Checks if the username in the token matches the provided user details and if the token has not expired.
     * </p>
     *
     * @param token the JWT token to be validated.
     * @param userDetails the user details to match against the token.
     * @return true if the token is valid, false otherwise.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        try {
            return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            logger.error("Error validating token", e);
            throw new TokenValidationException("Error validating token", e);
        }
    }

    /**
     * Extracts a specific claim from the JWT token.
     * <p>
     * Retrieves the claim using the provided claim resolver function.
     * </p>
     *
     * @param token the JWT token from which the claim is to be extracted.
     * @param claimResolver function to resolve the claim from the token.
     * @param <T> the type of the claim to be extracted.
     * @return the extracted claim value.
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    /**
     * Extracts all claims from the JWT token.
     * <p>
     * Parses the token and retrieves all claims.
     * </p>
     *
     * @param token the JWT token from which claims are to be extracted.
     * @return the claims from the token.
     */
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            logger.error("Error extracting claims from token", e);
            throw new TokenValidationException("Error extracting claims from token", e);
        }
    }

    /**
     * Checks if the JWT token has expired.
     * <p>
     * Compares the token's expiration date with the current date.
     * </p>
     *
     * @param token the JWT token to be checked for expiration.
     * @return true if the token has expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     * <p>
     * Retrieves the expiration claim from the token.
     * </p>
     *
     * @param token the JWT token from which the expiration date is to be extracted.
     * @return the expiration date of the token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Returns the secret key used for signing the JWT tokens.
     * <p>
     * Decodes the Base64 encoded secret key and returns a SecretKey object.
     * </p>
     *
     * @return the SecretKey used for signing JWT tokens.
     */
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
