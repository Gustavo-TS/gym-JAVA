package com.gym.security;

import com.gym.model.usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class jwt_service {

    private static final String SECRET_KEY = "sua_chave_secreta_super_segura_com_mais_de_32_caracteres";

    private Key get_signing_key() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generate_token(usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24h
                .signWith(get_signing_key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extract_email(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(get_signing_key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean is_token_valid(String token, usuario usuario) {
        String email = extract_email(token);
        return (email.equals(usuario.getEmail()) && !is_token_expired(token));
    }

    private boolean is_token_expired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(get_signing_key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
