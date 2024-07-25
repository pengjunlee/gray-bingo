package gray.bingo.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

public class JwtUtil {

    // 过期时间5分钟
    private static final long EXPIRE_TIME = 10 * 60 * 1000;

    // 私钥
    private static final String AUTH_SECRET = "MR.February";

    // 请求头
    public static final String AUTH_HEADER = "Authorization";

    // token 前缀
    public static final String AUTH_PREFIX = "Bearer ";

    /**
     * 验证token是否正确
     */
    public static boolean verify(String token, String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(AUTH_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    /**
     * 获得token中的自定义信息，无需secret解密也能获得
     */
    public static String getClaimFiled(String token, String filed) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(filed).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名
     */
    public static String sign(String username) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(AUTH_SECRET);
            // 附带username，nickname信息
            return JWT.create().withClaim("username", username).withIssuedAt(new Date()).withExpiresAt(date).sign(algorithm);
        } catch (JWTCreationException e) {
            return null;
        }
    }

    /**
     * 验证 token是否过期
     */
    public static boolean isExpired(String token) {
        Date now = Calendar.getInstance().getTime();
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt().before(now);
    }

    public static Date expireAt(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt();
    }

    public static Date issuedAt(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getIssuedAt();
    }

    /**
     * 刷新 token的过期时间
     */
    public static String refresh(String token) {
        DecodedJWT jwt = JWT.decode(token);
        Map<String, Claim> claims = jwt.getClaims();
        try {
            Builder builer = JWT.create();
            for (Entry<String, Claim> entry : claims.entrySet()) {
                builer.withClaim(entry.getKey(), entry.getValue().asString());
            }
            Algorithm algorithm = Algorithm.HMAC256(AUTH_SECRET);
            Date expireAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            return builer.withIssuedAt(new Date()).withExpiresAt(expireAt).sign(algorithm);
        } catch (JWTCreationException e) {
            return null;
        }
    }

    public static void main(String[] args) {
//		String sign = sign("aaaa");
//		System.out.println(sign);
//		refresh(sign);
        System.out.println(issuedAt("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTM3MzM1NzYsImlhdCI6MTY5MzczMjk3NiwidXNlcm5hbWUiOiJhZG1pbiJ9.uI56F5Ze9gGbQSyDF122egjptD2qu0mwTg5FlBsAgZ4"));
        System.out.println(getClaimFiled("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTM3MzE0MzgsImlhdCI6MTY5MzczMDgzOCwidXNlcm5hbWUiOiJhYWFhIn0.zmQ3yP_YfOyRfNrAwecLr7p2-_rpiYTF_GP53Z1MjYc", "iat"));
    }
}