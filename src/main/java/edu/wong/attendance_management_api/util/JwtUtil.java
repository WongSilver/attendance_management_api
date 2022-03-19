package edu.wong.attendance_management_api.util;

import io.jsonwebtoken.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "markerhub.jwt")
public class JwtUtil {

    private String secret;    //    密钥
    private long expire;    //    超时时间
    private String header;    //    数据头

    //    生成Token
    public String generateToken(long userId) {
        Date date = new Date();
        Date expireDate = new Date(date.getTime() + expire * 1000);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userId + "")
                .setIssuedAt(date)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.ES512, secret)
                .compact();
    }

    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.debug("验证Token错误" + e);
            return null;
        }
    }

    //    校验Token是否过期。true：过期
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}
