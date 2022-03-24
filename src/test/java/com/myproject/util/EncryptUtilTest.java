package com.myproject.util;

import com.myproject.command.util.GeneralConstant;
import org.junit.Test;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import static org.junit.Assert.*;

public class EncryptUtilTest {

    @Test
    public void getRandomNonce(){
        assertNotNull(EncryptUtil.getRandomNonce(12));
    }

    @Test
    public void encrypt(){
        byte[] bytes = {1,2,3};
        try {
            assertNotNull(EncryptUtil.encrypt(bytes,"1234"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAESKeyFromPassword(){
        char[]password ={'1','2','3','4'};
        byte[] salt = {1,2,3};
        try {
            assertNotNull(EncryptUtil.getAESKeyFromPassword(password,salt));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void decryptPass(){
        char[]password ={'1','2','3','4'};

        try {
            String encryptedPass =  EncryptUtil.encrypt(String.valueOf(password).getBytes(StandardCharsets.UTF_8), GeneralConstant.Util.KEY);
            boolean isDecrypted = EncryptUtil.decryptPass(encryptedPass, GeneralConstant.Util.KEY, password);
            assertTrue(isDecrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
