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
import jakarta.mail.Message.RecipientType;
import java.io.File;

@ApplicationScoped
@Named("sendMailController")
public class SendMail {

    final Properties props = new Properties();

    final Session session;

    public SendMail() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");

        session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ConfigManager.GetProperty("mail"), ConfigManager.GetProperty("pass"));
            }
        });

        System.out.println(props);

    }

    public boolean send(String subject, String body, Usuarios user) {

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

    public boolean sendSinStock(String subject, String body, Usuarios user, File file) {
        boolean validar = true;

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("botilleriadondeellito@gmail.com"));
            message.addRecipient(RecipientType.TO, new InternetAddress(user.getEmail()));

            message.setSubject(subject, "UTF-8");

            // Parte del cuerpo del mensaje
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(body, "text/html; charset=utf-8");

            // Parte del archivo adjunto
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(file.getPath());
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(file.getName());

            // Agregar ambas partes al multipart
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            // Configurar el contenido del mensaje
            message.setContent(multipart);

            // Enviar mensaje
            Transport.send(message);
            System.out.println(body);

        } catch (MessagingException e) {
            validar = false;
            e.printStackTrace();  // Mejor que RuntimeException para depurar
        }

        return validar;
    }

}
