package ch.hfict.springboot.util;

import java.nio.charset.StandardCharsets;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.hash.Hashing;

public class Crypto {
    public static String hash(String input) {
        return Hashing.sha256().hashString(input, StandardCharsets.UTF_8).toString();
    }

    private static Algorithm algorithm = Algorithm.HMAC256("_i99z.*rnXa9EF*fRFKFiKhzoRQE_8");
    private static String issuer = "hf-ict";

    public static String createJwtToken(Long userId) {
        return JWT.create()
            .withIssuer(Crypto.issuer)
            .withClaim("userId", userId)
            .sign(Crypto.algorithm);
    }

    public static boolean isJwtTokenValid(String token) {
        try {
            JWTVerifier verifier = JWT.require(Crypto.algorithm).withIssuer(Crypto.issuer).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }
}
