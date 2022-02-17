package com.myproject.util;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class PasswordDecode {
    String strDataToEncrypt;
    String strCipherText;
    String strDecryptedText;
    private byte[] iv;
    private SecretKey secretKey;
    private byte[] byteCipherText;

    public String encryptPassword(char[] password) {
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyGen.init(128);
        secretKey = keyGen.generateKey();
        final int AES_KEYLENGTH = 128;  // change this as desired for the security level you want
        iv = new byte[AES_KEYLENGTH / 8];    // Save the IV bytes or send it in plaintext with the encrypted data so you can decrypt the data later
        SecureRandom prng = new SecureRandom();
        prng.nextBytes(iv);

        Cipher aesCipherForEncryption = null; // Must specify the mode explicitly as most JCE providers default to ECB mode!!
        try {
            aesCipherForEncryption = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }


        try {
            aesCipherForEncryption.init(Cipher.ENCRYPT_MODE, secretKey,
                    new IvParameterSpec(iv));
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        strDataToEncrypt = Arrays.toString(password);
        byte[] byteDataToEncrypt = strDataToEncrypt.getBytes();
        byteCipherText = new byte[0];
        try {
            byteCipherText = aesCipherForEncryption
                    .doFinal(byteDataToEncrypt);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        System.out.println("CODE");
        System.out.println(Arrays.toString(byteDataToEncrypt));
        strCipherText = Base64.getEncoder().withoutPadding().encodeToString(byteCipherText);
        System.out.println("Cipher Text generated using AES is "
                + strCipherText);


        return strCipherText;

    }

    public boolean decryptPassword(char[]pass) {
        Cipher aesCipherForDecryption = null; // Must specify the mode explicitly as most JCE providers default to ECB mode!!
        byte[] bytes = Base64.getDecoder().decode(strCipherText);
        System.out.println(Arrays.toString(bytes));
        try {
            aesCipherForDecryption = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            aesCipherForDecryption.init(Cipher.DECRYPT_MODE, secretKey,
                    new IvParameterSpec(iv));
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        byte[] byteDecryptedText = new byte[0];
        try {
            byteDecryptedText = aesCipherForDecryption
                    .doFinal(bytes);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        strDecryptedText = new String(byteDecryptedText);
        System.out.println(strDecryptedText.equals(String.valueOf(pass)));
        System.out
                .println(" Decrypted Text message is " + strDecryptedText);

        return false;
    }


    public static void main(String[] args) {
        PasswordDecode passwordDecode = new PasswordDecode();
        char[] pass = {'1', '2', '3'};
        passwordDecode.encryptPassword(pass);
         passwordDecode.decryptPassword(pass);

    }


}
