package com.authlib.tokenverify;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class JwtUtil {

    public static Claims validateToken(String token, String secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static boolean hasMenuAccess(String token, String secretKey, String requiredMenu) {
        try {
            Claims claims = validateToken(token, secretKey);
            List<String> menus = claims.get("menus", List.class);
            return menus != null && menus.contains(requiredMenu);
        } catch (Exception e) {
            System.out.println("Token invalid: " + e.getMessage());
            return false;
        }
    }
}
