package com.hoangtien2k3.ecommerce.security.validate;

import com.hoangtien2k3.ecommerce.security.JwtKeyFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AuthorityTokenUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private SecretKey getSigningKey() {
        return JwtKeyFactory.createHs512Key(jwtSecret);
    }

    public List<String> checkPermission(String token) {
        try {
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.get("authorities", List.class);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
