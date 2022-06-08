package top.momoandnono.community.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import top.momoandnono.community.constant.SysConstant;

import java.util.Date;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * JWT 工具包
 * @author l
 */
public class JwtUtl {


    /**
     * 设置 JWT
     * @param consumer lambda
     * @return JWT
     */
    public static String setJwt(Consumer<JwtBuilder> consumer){
        //现在时间
        final long now = System.currentTimeMillis();
        //4个小时后过期
        final long exp = now + 4 * 60 * 60 * 1000;
//        final long exp = now +  5 * 1000;
        final JwtBuilder jwtBuilder = Jwts.builder()
                // 设置 id ,{"jti":""}
                .setId(UUID.randomUUID().toString())
                //签发用户 sub
                .setSubject("xxx")
                //签发时间 iat
                .setIssuedAt(new Date())
                //盐
                .signWith(SignatureAlgorithm.HS256, SysConstant.SYS_SIGN)
                //过期时间
                .setExpiration(new Date(exp));
        // lambda
        consumer.accept(jwtBuilder);
        //生成token
        return jwtBuilder.compact();
    }

    /**
     * 获取 JWT 的信息
     * @param token token
     * @param consumer lambda
     */
    public static void getJwt(String token, Consumer<Claims> consumer) {
        consumer.accept(Jwts.parser()
                .setSigningKey(SysConstant.SYS_SIGN)
                .parseClaimsJws(token)
                .getBody());
    }
}
