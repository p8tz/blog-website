package cc.p8t.blog.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @author PolarNight
 * @date 2020/9/27 15:14
 */
public class JWTUtil {
    /**
     * header.payload.signature
     */

    private static final String SECRET = "%#)@^%&3@43#BDS&^WE45UHBDC^DTFUBafysdfb7sgrfhb^%(";

    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);

    public static String getToken(Map<String, String> info) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);

        JWTCreator.Builder builder = JWT.create();
        // payload
        info.forEach(builder::withClaim);
        // sign
        String token = builder.withExpiresAt(calendar.getTime())
                .sign(ALGORITHM);
        return token;
    }

    public static DecodedJWT verify(String token) {
        return JWT.require(ALGORITHM).build().verify(token);
    }

    public static Integer getUserId(String token) {
        DecodedJWT decodedJWT = verify(token);
        String userId = decodedJWT.getClaim("userId").asString();
        return Integer.parseInt(userId);
    }
}
