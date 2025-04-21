package cl.puntoventa.app.bean;

import cl.puntoventa.app.clases.Message;
import cl.puntoventa.app.clases.Util;
import cl.puntoventa.app.controller.UserController;
import cl.puntoventa.app.entity.Usuarios;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named("usuarioBean")
public class UsuarioBean implements AppBean, Serializable {

    private Usuarios userNuevo;

    @Inject
    private UserController usersController;

    public Usuarios getUserNuevo() {
        return userNuevo;
    }

    public void setUserNuevo(Usuarios userNuevo) {
        this.userNuevo = userNuevo;
    }

    @PostConstruct
    @Override
    public void init() {
        this.prepareCreate();
        this.listar();
    }

    @Override
    public void listar() {

    }

    @Override
    public void prepareCreate() {
        this.userNuevo = new Usuarios();
    }

    public void crearUsers() {
        if (usersController.create(this.userNuevo)) {
            this.userNuevo = new Usuarios();

            Util.avisoInfo("infoMsg", "Se ha creado su cuenta");

        } else {

            Util.avisoError("infoMsg", Message.createError());
        }
    }

    public void cerrarDglUsuario() {
        this.userNuevo = new Usuarios();
        Util.ejecutarJavaScript("PF('dialogCreacionUser').hide()");

    }

    @Override
    public void create() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
