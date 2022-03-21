package edu.wong.attendance_management_api.util;

import edu.wong.attendance_management_api.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "attend.jwt")
public class JwtUtil {

    private String secret;    //    密钥
    private long expire;    //    超时时间
    private String header;    //    数据头

    //    生成Token
    public String generateToken(User user) {
        //    设置过期时间
        Date date = new Date();
        Date expireDate = new Date(date.getTime() + expire * 1000);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")//jwt的header
                .setSubject(user.getId().toString())//私有申明
                .setIssuedAt(date)//签发时间
                .setExpiration(expireDate)//过期时间
                .signWith(SignatureAlgorithm.HS512, secret)//签名
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
