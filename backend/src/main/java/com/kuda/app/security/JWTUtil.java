package com.kuda.app.security;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.kuda.app.exception.Errors;
import com.kuda.app.exception.KudaException;
import com.kuda.app.exception.Resources;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@PropertySource("classpath:application-main.properties")
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire}")
    private Long expire;

    public String generate(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expire * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public boolean verify(String token) throws KudaException {
        final String username = getClaim(token, Claims::getSubject);
        return (username != null && !isExpired(token));
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) throws KudaException {
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    @SuppressWarnings("unchecked")
    public List<String> getRoles(String token, String key) throws KudaException {

        return (List<String>) getClaims(token).get(key);
    }

    public String getUsername(String token) throws KudaException {
        return getClaim(token, Claims::getSubject);
    }

    private Claims getClaims(String token) throws KudaException {
        if (token == null) {
            throw new KudaException(Errors.ACCESS_DENIED.getName(),
                    HttpStatus.FORBIDDEN, Resources.USER.getName(), "600");
        }
        try {

            return Jwts.parser().setSigningKey(secret)
                    .parseClaimsJws(token.replace(TokenAuthenticationFilter.BEARER, "").trim()).getBody();
        } catch (Exception ex) {
            log.error("", ex);
            throw new KudaException(Errors.ACCESS_DENIED.getName(),
                    HttpStatus.FORBIDDEN, Resources.USER.getName(), "604");
        }
    }

    private boolean isExpired(String token) throws KudaException {
        final Date expiration = getClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }
}
