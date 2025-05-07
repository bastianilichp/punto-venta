package cl.puntoventa.app.controller;

import cl.puntoventa.app.clases.SendMail;
import cl.puntoventa.app.entity.Usuarios;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.File;
import java.util.List;

@ApplicationScoped
public class EmailController {

    @Inject
    private SendMail sendMail;

    public boolean sendNuevoUser(Usuarios user, String pass) {

        StringBuilder body = new StringBuilder();
        StringBuilder subject = new StringBuilder();
        body.append("<html><body>");
        body.append("<h4>Estimad@, </h4>");
        body.append("<p>Se ha creado usuario para el Punto de Venta").append("</b>");
        body.append("<p> Username: ").append(user.getEmail()).append("</p>");
        body.append("<p> Password: ").append(pass).append("</p>");
        body.append("<br>");
        body.append("<p>Ingresar a:</p>");
        body.append("</p><br> ");

        body.append("</body></html>");

        subject.append("Creación Usuario");

        return sendMail.send(subject.toString(), body.toString(), user);

    }

    public boolean sendSinStock(Usuarios user, File file) {

        StringBuilder body = new StringBuilder();
        StringBuilder subject = new StringBuilder();
        body.append("<html><body>");
        body.append("<h4>Estimad@, </h4>");
        body.append("<p> Ha continuación, se adjunta total de productos sin stock ").append("PRODUCTOS_SIN_STOCK").append("</p>");
        body.append("</p><br> ");

        body.append("</body></html>");

        subject.append("Sistema Punto Venta Donde El Lito");

        return sendMail.sendSinStock(subject.toString(), body.toString(), user, file);

    }

}
