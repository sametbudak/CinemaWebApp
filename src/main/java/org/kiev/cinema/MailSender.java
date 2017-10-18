package org.kiev.cinema;

import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class MailSender {

    private static Properties props;
    static {
        props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); // needed to work on windows
    }

    private static String fromEmail = "movie7theater@gmail.com";
    private static String password = "Movie3Theater2Kiev9";
    private static String username = "movie7theater";

    public boolean isEmailValid(String email) {
        try {
            new InternetAddress(fromEmail);
            return true;
        } catch (AddressException e) {
            return false;
        }
    }

     public void sendVerificationCode(String email, String tokens) {
        String subject = "Your confirmation code";
        String body = "verification code: " + tokens;
        sendMessage(email, subject, body);
     }

    public void sendCancelBookingsMessage(String email) {
        String subject = "Your ticket's booking has been cancelled";
        String body = "Your ticket's booking is cancelled, since the booking has not been redeemed within 24 hours.";
        sendMessage(email, subject, body);
    }

    private void sendMessage(String email, String subject, String body) {
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);
        Message message = new MimeMessage(session);
        try {
            InternetAddress fromAddress = new InternetAddress(fromEmail);
            InternetAddress toAddress = new InternetAddress(email);
            message.setFrom(fromAddress);
            message.addRecipient(Message.RecipientType.TO, toAddress);
            message.addRecipient(Message.RecipientType.CC, fromAddress);
            message.setSubject(subject);
            message.setContent(body, "text/html");

            Transport transport = session.getTransport("smtp");
            transport.connect(fromEmail, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            System.out.println("Message has been sent successfully ...");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error sending email...");
        }
    }
}
