package top.atluofu.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import top.atluofu.model.LoginUser;

import java.util.Date;

/**
 * @ClassName: JWTUtils
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-08Day-14:03
 * @Version: 1.0
 */
public class JWTUtils {

    private static final long EXPIRE = 1000*60*60*24*7*10L;
    private static final String SECRET = "mqa010225@163.com";
    private static final String TOKEN_PREFIX = "coupon";

    private static final String SUBJECT = "atluofu";

    /**
     * 根据用户信息，生成令牌
     *
     * @param user
     * @return
     */
    public static String geneJsonWebToken(LoginUser user) {
        // todo 绑定ip 机器码
        Long userId = user.getId();
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("head_img", user.getHeadImg())
                .claim("id", userId)
                .claim("name", user.getName())
                .claim("mail", user.getMail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();

        token = TOKEN_PREFIX + token;
        return token;
    }

    /**
     * 校验token的方法
     *
     * @param token
     * @return
     */
    public static Claims checkJWT(String token) {

        try {
            return Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
        } catch (Exception e) {
            return null;
        }
    }



}
