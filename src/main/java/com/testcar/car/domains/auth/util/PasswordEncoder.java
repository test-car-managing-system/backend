package com.testcar.car.domains.auth.util;


import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {
    private PasswordEncoder() {}

    public static String encode(String rawPw) {
        return BCrypt.hashpw(rawPw, BCrypt.gensalt());
    }

    public static boolean matches(String rawPw, String hashedPw) {
        return BCrypt.checkpw(rawPw, hashedPw);
    }
}
