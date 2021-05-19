package com.giaynhap.securechat.utils;


import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.giaynhap.securechat.config.AppConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

    @Value("${gn.jwt.secret}")
    private String secret;
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    //check if the token has expired
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    //generate token for user
    public String generateToken( String userId ) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",userId);
        return doGenerateToken(claims, userId);
    }
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + AppConstant.JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String refreshToken(String prevToken){
        final String username = getUsernameFromToken(prevToken);
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",username);
        return doGenerateToken(claims, username);
    }
    public String generateRefreshToken(String accessToken){
        Claims claims =  Jwts.parser().setSigningKey(secret).parseClaimsJws(accessToken).getBody();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String ptext = claims.getExpiration().toString() + claims.getSubject() + claims.getIssuedAt().toString();
            md.update(ptext.getBytes());
            StringBuffer sb = new StringBuffer();
            byte byteData[] = md.digest();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    //validate token
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}