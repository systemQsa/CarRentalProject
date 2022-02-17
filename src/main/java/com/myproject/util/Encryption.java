package com.myproject.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.logging.Logger;

public class Encryption {
    private static final Logger logger = Logger.getLogger(Encryption.class.getName());
    private static final int SALT_SIZE = 16;
    private static final String ENCRYPT_ALGORITHM = "SHA-512";
    private static final String SALT_ALGORITHM = "SHA1PRNG";


    public byte[] encryptPassword(byte[] password, byte[] salt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ENCRYPT_ALGORITHM);
            messageDigest.update(salt);
            byte[] bytes = messageDigest.digest(password);
            byte[] generatedPassword = new byte[bytes.length + salt.length];
            System.arraycopy(salt, 0, generatedPassword, 0, salt.length);
            for (int i = 0; i < bytes.length; i++) {
                generatedPassword[salt.length + i] = (byte) ((bytes[i] & 0xFF) + 0x100);
            }
            erasePasswords(bytes, password, salt);
            return generatedPassword;

        } catch (NoSuchAlgorithmException e) {
            logger.warning("CANT ENCRYPT THE PASSWORD");
        }
        return null;
    }


    public byte[] createSalt() {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance(SALT_ALGORITHM);
            byte[] salt = new byte[SALT_SIZE];
            secureRandom.nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException e) {
            logger.warning("CANT CREATE SALT FOR PASSWORD");
        }

        return null;
    }

    private void erasePasswords(byte[]... arrays) {
        for (byte[] password : arrays) {
            if (password != null) {
                Arrays.fill(password, (byte) 0);
            }
        }
    }

    public String encryptPassToHexStr(byte[] password) {
        StringBuilder result = new StringBuilder();
        for (byte b : password) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    public static byte[] getByteArrayFromHexStr(String hexString) {
        return new BigInteger(hexString, 16).toByteArray();
    }

    public boolean matchPasswords(byte[] password, byte[] hashedPassword) {
        byte[] salt = new byte[SALT_SIZE];
        System.arraycopy(hashedPassword, 0, salt, 0, SALT_SIZE);


        byte[] generatedPassword = encryptPassword(password, salt);
        boolean match = Arrays.equals(generatedPassword, hashedPassword);
        erasePasswords(generatedPassword, hashedPassword);

        return match;
    }


    public static void main(String[] args) {
        String pass = "123";
        Encryption encryption = new Encryption();
        byte[] stored = encryption.encryptPassword(pass.getBytes(), encryption.createSalt());
        assert stored != null;
        System.out.println(encryption.encryptPassToHexStr(stored));
        byte[] byteArrayFromHexStr = getByteArrayFromHexStr(encryption.encryptPassToHexStr(stored));
        System.out.println();
        System.out.println(Arrays.toString(stored));

        System.out.println(encryption.matchPasswords(pass.getBytes(), byteArrayFromHexStr));
    }
}
