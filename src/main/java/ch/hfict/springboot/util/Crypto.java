package ch.hfict.springboot.util;

import java.nio.charset.StandardCharsets;
import com.google.common.hash.Hashing;

public class Crypto {
    public static String hash(String input) {
        return Hashing.sha256().hashString(input, StandardCharsets.UTF_8).toString();
    }
}
