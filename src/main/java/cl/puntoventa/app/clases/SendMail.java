package cl.puntoventa.app.clases;



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
import java.io.File;

@ApplicationScoped
@Named("sendMailController")
public class SendMail {

    final Properties prop = new Properties();

    public SendMail() {

        prop.put("mail.smtp.host", "10.13.10.50"); //optional, defined in SMTPTransport   
        prop.put("mail.smtp.port", "25"); // default port 25
        prop.put("mail.debug", "true");
    }

    public boolean sendReMailActa(String subject, String body, File filename) {

        Session session = Session.getInstance(prop, null);

        boolean validar = true;

        try {

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress("sistemas@gobiernosantiago.cl"));

//            message.addRecipient(RecipientType.TO, new InternetAddress(acta.getCorreo()));
//
//            message.addRecipient(RecipientType.CC, new InternetAddress(acta.getSgaUsers().getEmail()));

            message.setSubject(subject, "UTF-8");
            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setContent(body, "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart("alternative");
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filename.getPath());
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename.getName());
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart, "text/html; charset=utf-8");

            // Send message
            Transport.send(message);

        } catch (MessagingException e) {

            //throw new RuntimeException(e);
            validar = false;
        }

        return validar;
    }

 

}
