package cl.puntoventa.app.controller;


import cl.puntoventa.app.clases.SendMail;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.File;
import java.util.List;

@ApplicationScoped
public class EmailController {

    @Inject
    private SendMail sendMail;

    


//    public boolean sendNuevoUser(SgaUsers user, String pass) {
//
//        StringBuilder body = new StringBuilder();
//        StringBuilder subject = new StringBuilder();
//        body.append("<html><body>");
//        body.append("<h4>Estimad@, </h4>");
//        body.append("<p>Se ha creado usuario para el Sistema de Bodega").append("</b>");
//        body.append("<p> Username: ").append(user.getUsername()).append("</p>");
//        body.append("<p> Password: ").append(pass).append("</p>");
//        body.append("<br>");
//        body.append("<p>Ingresar a:</p>");
//        body.append("<p>https://bodega.gobiernosantiago.cl/<p>");
//        body.append("<p> <img src='https://sga.gobiernosantiago.cl/img/logo_gore.png' width='100'>");
//        body.append("</p><br> ");
//        body.append("<h6>División de Administración y Finanzas</h6>");
//        body.append("<h6>Tecnologías de la Información</h6>");
//        body.append("</body></html>");
//
//        subject.append("Departamento de Informática : Creación Usuario");
//
//        return sendMail.sendNuevoUsers(subject.toString(), body.toString(), user);
//
//    }



}
