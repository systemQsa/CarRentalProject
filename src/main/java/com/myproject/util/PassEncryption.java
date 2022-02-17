package com.myproject.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;


public class PassEncryption {

    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int INIT_VECTOR_LENGTH_BYTE = 16;
    private static final int AES_KEY_BIT = 256;
    private static final int SALT_LENGTH_BYTE = 16;
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    private PassEncryption() {
    }

    public static byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    // AES secret key
    public static SecretKey getAESKey(int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keySize, SecureRandom.getInstanceStrong());
        return keyGen.generateKey();
    }

    // Password derived AES 256 bits secret key
    public static SecretKey getAESKeyFromPassword(char[] password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        // iterationCount = 65536
        // keyLength = 256
        KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        return secret;

    }

    // hex representation
    public static String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    public static String getHexString(byte[] b) throws Exception {
        StringBuilder result = new StringBuilder();
        for (int i=0; i < b.length; i++) {
            result.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

    public static byte[] getByteArray(String hexString) {
        return  new BigInteger(hexString,16).toByteArray();
    }
    // AES-GCM needs GCMParameterSpec
    public static byte[] encrypt(byte[] pText, SecretKey secret, byte[] initVector) throws Exception {

        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BIT, initVector));
        byte[] encryptedText = cipher.doFinal(pText);
        return encryptedText;

    }

    // prefix IV length + IV bytes to cipher text
    public static byte[] encryptWithPrefixIV(byte[] pText, SecretKey secret, byte[] initVector) throws Exception {

        byte[] cipherText = encrypt(pText, secret, initVector);

        return ByteBuffer.allocate(initVector.length + cipherText.length)
                .put(initVector)
                .put(cipherText)
                .array();

    }

    public static String decrypt(byte[] cText, SecretKey secret, byte[] initVector) throws Exception {

        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BIT, initVector));
        byte[] plainText = cipher.doFinal(cText);
        return new String(plainText, UTF_8);

    }

    public static String decryptWithPrefixIV(byte[] cText, SecretKey secret) throws Exception {

        ByteBuffer bb = ByteBuffer.wrap(cText);

        byte[] initVector = new byte[INIT_VECTOR_LENGTH_BYTE];
        bb.get(initVector);

        byte[] cipherText = new byte[bb.remaining()];
        bb.get(cipherText);

        return decrypt(cipherText, secret, initVector);

    }

    public static String encryptThePass(char[] password) {
        CharBuffer charBuffer = CharBuffer.wrap(password);
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(byteBuffer.array(), (byte) 0);
        Arrays.fill(password, '\u0000');
        try {
            SecretKey secretKey = getAESKey(AES_KEY_BIT);
            byte[] initVector = getRandomNonce(INIT_VECTOR_LENGTH_BYTE);
            byte[] encryptedText = encryptWithPrefixIV(bytes, secretKey, initVector);
            return hex(encryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean verifyPasswordIdentity(char[] inputPass, String realPass) {
        String inputResult = encryptThePass(inputPass);
        assert inputResult != null;
        return inputResult.equals(realPass);
    }

    public static String encryptPassword(String password) throws Exception {
        SecretKey secretKey = getAESKey(AES_KEY_BIT);
        byte[] initVector = getRandomNonce(INIT_VECTOR_LENGTH_BYTE);
        byte[] encryptedText = encryptWithPrefixIV(password.getBytes(UTF_8), secretKey, initVector);
        return hex(encryptedText);
    }

//
//    public static String encrypt(byte[] pText, String password) throws Exception {
//
//        // 16 bytes salt
//        byte[] salt = getRandomNonce(SALT_LENGTH_BYTE);
//
//        // GCM recommended 12 bytes iv?
//        byte[] iv = getRandomNonce(INIT_VECTOR_LENGTH_BYTE);
//
//        // secret key from password
//        SecretKey aesKeyFromPassword = getAESKeyFromPassword(password.toCharArray(), salt);
//
//        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
//
//        // ASE-GCM needs GCMParameterSpec
//        cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
//
//        byte[] cipherText = cipher.doFinal(pText);
//
//        // prefix IV and Salt to cipher text
//        byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length)
//                .put(iv)
//                .put(salt)
//                .put(cipherText)
//                .array();
//
//        // string representation, base64, send this string to other for decryption.
//        return Base64.getEncoder().encodeToString(cipherTextWithIvSalt);
//
//    }
//
//    private static String decrypt(String cText, String password) throws Exception {
//
//        byte[] decode = Base64.getDecoder().decode(cText.getBytes(UTF_8));
//
//        // get back the iv and salt from the cipher text
//        ByteBuffer bb = ByteBuffer.wrap(decode);
//
//        byte[] iv = new byte[INIT_VECTOR_LENGTH_BYTE];
//        bb.get(iv);
//
//        byte[] salt = new byte[SALT_LENGTH_BYTE];
//        bb.get(salt);
//
//        byte[] cipherText = new byte[bb.remaining()];
//        bb.get(cipherText);
//
//        // get back the aes key from the same password and salt
//        SecretKey aesKeyFromPassword = getAESKeyFromPassword(password.toCharArray(), salt);
//
//        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
//
//        cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
//
//        byte[] plainText = cipher.doFinal(cipherText);
//
//        return new String(plainText, UTF_8);
//
//    }

    public static byte[] hexToBinary(String s){

        /*
         * skipped any input validation code
         */

        byte[] data = new byte[s.length()/2];

        for( int i=0, j=0;
             i<s.length() && j<data.length;
             i+=2, j++)
        {
            data[j] = (byte)Integer.parseInt(s.substring(i, i+2), 16);
        }

        return data;
    }
    public static void main(String[] args) throws Exception {
        String PASSWORD = "123";
        String password = "123";
        String anotherPass = "567";

        String OUTPUT_FORMAT = "%-30s%s";
        SecretKey secretKey = getAESKey(AES_KEY_BIT);
        byte[] iv = getRandomNonce(INIT_VECTOR_LENGTH_BYTE);
        byte[] encryptedText = encryptWithPrefixIV(password.getBytes(UTF_8), secretKey, iv);
        System.out.println(String.format(OUTPUT_FORMAT, "Key (hex)", hex(secretKey.getEncoded())));
        System.out.println(String.format(OUTPUT_FORMAT, "Encrypted (hex) ", hex(encryptedText)));
        String decryptedText = decryptWithPrefixIV(encryptedText, secretKey);

        System.out.println(String.format(OUTPUT_FORMAT, "Decrypted (plain text)", decryptedText));

        // encrypt and decrypt need the same key.
        // get AES 256 bits (32 bytes) key
      //  SecretKey secretKey = getAESKey(AES_KEY_BIT);

        // encrypt and decrypt need the same IV.
        // AES-GCM needs IV 96-bit (12 bytes)
//        byte[] iv = getRandomNonce(INIT_VECTOR_LENGTH_BYTE);
//
//        byte[] encryptedText = encrypt(pText.getBytes(UTF_8), secretKey, iv);
//        System.out.println(Arrays.toString(encryptedText));
//        System.out.println(getHexString(encryptedText));
//        byte[] bytes = getByteArray(hex(encryptedText));
//        System.out.println(Arrays.toString(bytes));
//         System.out.println(decrypt(bytes,secretKey,iv));
        //String decryptedText = decrypt(encryptedText, secretKey);


    }
}
