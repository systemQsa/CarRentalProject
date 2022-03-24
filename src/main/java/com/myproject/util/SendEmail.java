package com.myproject.util;

import java.util.Properties;
import java.io.*;
import java.util.*;
import javax.mail.Part;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 * Sends message on Gmail in order to change receiver must be changed host and  probably need additional settings
 * Before usage require change settings in gmail account
 * turn off double validation
 * turn on less secure apps
 * turn on imap
 * go to cmd
 * import certificate to JRE lib security
 * keytool -importcert -trustcacerts -alias cert3  -file (to .cer)
 * -keystore  (path to) "\security\carcets" -storepass changeit
 */


public class SendEmail {
    private boolean textIsHtml = false;

    public int sendEmailToRecipient(String emailTo) {
        int secretCode = (int)(Math.random() *((9999-1000)+1000));
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try {

            Properties properties = new Properties();
            properties.load(classLoader.getResourceAsStream("smtp_send_email.properties"));
            Session s = Session.getInstance(properties, null);

            MimeMessage msg = new MimeMessage(s);

            InternetAddress to = new InternetAddress(properties.getProperty("email"));

            InternetAddress fromAddr = new InternetAddress(properties.getProperty("email"));

            msg.setSentDate(new java.util.Date());
            msg.setFrom(fromAddr);
            msg.addRecipient(Message.RecipientType.TO, to);

            msg.setSubject("Reset password");
            msg.setContent(String.valueOf(secretCode), "text/html");

            Transport transport = s.getTransport("smtp");

            transport.connect(properties.getProperty("mail.smtp.host"),
                              properties.getProperty("email"),
                              properties.getProperty("password"));
            msg.saveChanges();
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return secretCode;
    }

    public String getEmailFromSender() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        String result = null;
        try {
            properties.load(classLoader.getResourceAsStream("smtp_receive_email.properties"));
            Session session = Session.getDefaultInstance(properties, null);

            Store store = session.getStore("imaps");
            store.connect(properties.getProperty("host"),
                          properties.getProperty("email"),
                          properties.getProperty("password"));
            Folder folder = store.getFolder("inbox");
            folder.open(Folder.READ_ONLY);
            Message[] messages = folder.getMessages();
            int index = ((messages.length) - 1);
            Message message = messages[index];

              if (message.getContent() instanceof Multipart) {
                Multipart multipart = (Multipart) message.getContent();
                for (int i = 0; i < multipart.getCount(); i++) {
                    BodyPart bodyPart = multipart.getBodyPart(i);
                    result = getText(bodyPart);
                    result = result.replaceAll("<div dir=\"ltr\">","")
                                    .replaceAll("</div>","").trim();
                }
            }

            folder.close(true);
            store.close();
            return result;
        } catch (IOException | MessagingException e) {
            System.out.println("Something went wrong in receive message");
            e.printStackTrace();
        }
        return null;
    }

    private String getText(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String) p.getContent();
            textIsHtml = p.isMimeType("text/html");
            return s;
        }
        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart) p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
    }

}
