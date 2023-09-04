package com.payday.accountms.security.util;

import com.payday.common.exception.UnauthorizedException;
import com.payday.common.exception.util.ExceptionCodes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtil {

    @Value("${application.security.jwt.secret-key}")
    private String jwtSigningKey;

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSigningKey).parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            final Claims claims = getClaimsFromToken(token);
            final Date expiration = claims.getExpiration();
            return !expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(ExceptionCodes.JWT_EXPIRED, e);
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException(ExceptionCodes.MALFORMED_TOKEN, e);
        } catch (Exception e) {
            throw new UnauthorizedException(ExceptionCodes.INVALID_TOKEN, e);
        }
    }
}
