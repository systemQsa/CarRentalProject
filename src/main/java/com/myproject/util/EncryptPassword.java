package com.myproject.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class EncryptPassword {
    private static final String SALT_ALGORITHM = "SHA1PRNG";
    private static final String HASH_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int SALT_LENGTH = 20;
    private static final int HASH_ITERATION = 1000;
    private static final int HASH_LENGTH = 512;

    private EncryptPassword() {}

    public static String createHash(String pass, String salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
        char[] chars = (pass).toCharArray();
        PBEKeySpec spec = new PBEKeySpec(chars, salt.getBytes(), HASH_ITERATION, HASH_LENGTH);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(HASH_ALGORITHM);
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return new String(hash);
    }

    public static String createSalt() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance(SALT_ALGORITHM);
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);
        return new String(salt);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        System.out.println(EncryptPassword.createHash("123", createSalt()));
    }

}
