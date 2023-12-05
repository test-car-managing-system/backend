package com.testcar.car.common.auth;

import static com.testcar.car.common.auth.JwtConstant.ACCESS_TOKEN_EXPIRE_TIME;
import static com.testcar.car.common.auth.JwtConstant.CLAIM_KEY;
import static com.testcar.car.common.auth.JwtConstant.HEADER_KEY;
import static com.testcar.car.common.auth.JwtConstant.HEADER_VALUE;
import static com.testcar.car.common.exception.ErrorCode.EMPTY_TOKEN;
import static com.testcar.car.common.exception.ErrorCode.INVALID_TOKEN;

import com.testcar.car.common.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    private static final String BEARER = "^Bearer( )*";
    private final JwtProperty jwtProperty;

    public String generateAccessToken(Long memberId) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(HEADER_KEY, HEADER_VALUE)
                .claim(CLAIM_KEY, memberId)
                .setIssuedAt(now)
                .setExpiration(getExpiryDate())
                .signWith(SignatureAlgorithm.HS256, jwtProperty.getSecretKey())
                .compact();
    }

    public Long getMemberId() {
        final String accessToken = getBearerToken();
        return parseMemberId(accessToken);
    }

    private String getBearerToken() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                        .getRequest();
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (accessToken == null || accessToken.isBlank()) {
            throw new UnauthorizedException(EMPTY_TOKEN);
        }

        return accessToken.replaceAll(BEARER, "");
    }

    private Long parseMemberId(String token) {
        try {
            Jws<Claims> claims =
                    Jwts.parser().setSigningKey(jwtProperty.getSecretKey()).parseClaimsJws(token);
            return claims.getBody().get(CLAIM_KEY, Long.class);
        } catch (Exception ignored) {
            throw new UnauthorizedException(INVALID_TOKEN);
        }
    }

    private Date getExpiryDate() {
        return new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME);
    }
}
