package com.godchigam.godchigam.global.auth;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    public String encrypt(Long userId, String password) {
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), getSalt(userId), 85319, 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] getSalt(Long userId) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String key = userId.toString();
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] keyBytes = key.getBytes("UTF-8");
        return digest.digest(keyBytes);
    }
}