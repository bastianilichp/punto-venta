package cl.puntoventa.app.clases;

import cl.puntoventa.app.entity.Usuarios;
import cl.puntoventa.app.helpers.ConfigManager;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import java.util.Properties;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.mail.BodyPart;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.servlet.http.HttpSession;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.File;

@ApplicationScoped
@Named("sendMailController")
public class SendMail {

    final Properties prop = new Properties();

    final String username = ConfigManager.GetProperty("mail");
    final String password = ConfigManager.GetProperty("pass");

    public SendMail() {
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.auth", "true");

    }

    public boolean send(String subject, String body, Usuarios user) {

        Session session = Session.getInstance(prop,
                new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        boolean validar = true;

        try {

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress("botilleriadondeellito@gmail.com"));

            message.addRecipient(RecipientType.TO, new InternetAddress(user.getEmail()));

            message.setSubject(subject, "UTF-8");
            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setContent(body, "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart("alternative");
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            // Send message
            Transport.send(message);

        } catch (MessagingException e) {
            validar = false;
            throw new RuntimeException(e);

        }

        return validar;
    }

}
