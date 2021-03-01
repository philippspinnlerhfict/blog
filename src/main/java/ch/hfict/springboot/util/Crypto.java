package ch.hfict.springboot.util;

import java.nio.charset.StandardCharsets;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.hash.Hashing;

public class Crypto {
    public static String hash(String input) {
        return Hashing.sha256().hashString(input, StandardCharsets.UTF_8).toString();
    }

    public static String createJwtToken(Long userId) {
        Algorithm algorithm = Algorithm.HMAC256("_i99z.*rnXa9EF*fRFKFiKhzoRQE_8");
        return JWT.create()
            .withIssuer("hf-ict")
            .withClaim("userId", userId)
            .sign(algorithm);
    }
}
