package com.myproject.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class SendEmailTest {
    private final SendEmail sendEmail = new SendEmail();
    private int number;
    @Test
    public void sendEmailToRecipient() {
        number = sendEmail.sendEmailToRecipient("someEmail@gmail.com");
        assertTrue(number != 0);
    }

    @Test
    public void getEmailFromSender(){
        String emailFromSender = sendEmail.getEmailFromSender();
        assertNull(emailFromSender);
    }
}
