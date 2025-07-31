package com.viet.blog_api.util;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {

    @Value("${authentication.jwt.secret}")
    private String jwtSecret;

    @Value("${authentication.jwt.access.expiration}")
    private long jwtAccessExpiration;

    @Value("${authentication.jwt.refresh.expiration}")
    private long jwtRefreshExpiration;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));
    }

    private String generateToken(String subject, Map<String, Object> claims, long expiration) {
        return io.jsonwebtoken.Jwts.builder()
                .subject(subject)
                .claims(claims)
                .expiration(new java.util.Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }

    public String generateToken(Authentication auth, long expiration) {

        // get authorities as a comma-separated string
        String authoritesStr = auth.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.joining(","));

        return generateToken(auth.getName(), Map.of("authorities", authoritesStr), expiration);
    }

    public String generateAccessToken(Authentication auth) {
        return generateToken(auth, jwtAccessExpiration);
    }

    public String generateRefreshToken(Authentication auth) {
        return generateToken(auth, jwtRefreshExpiration);
    }

    private JwtParser getParse() {
        return Jwts.parser()
                .verifyWith(getKey())
                .build();
    }

    private Claims extractClaims(String token) {
        return getParse().parseSignedClaims(token).getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    private String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private List<GrantedAuthority> extractAuthorities(String token) {
        String authoritiesStr = extractClaim(token, (claim) -> claim.get("authorities", String.class));
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesStr);
    }

    public Authentication extractAuthentication(String accessToken) {
        return new UsernamePasswordAuthenticationToken(extractSubject(accessToken), null,
                extractAuthorities(accessToken));
    }

}
