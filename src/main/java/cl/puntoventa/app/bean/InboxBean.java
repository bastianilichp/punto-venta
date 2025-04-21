package cl.puntoventa.app.bean;

import cl.puntoventa.app.controller.UserController;
import cl.puntoventa.app.entity.Usuarios;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.PrimeFaces;

@Named("inboxBean")
@ViewScoped
public class InboxBean implements AppBean, Serializable {

    private Usuarios user;

    private List<Usuarios> listUsuarios;

    @Inject
    private UserController userController;

    @PostConstruct
    @Override
    public void init() {
        this.prepareCreate();
        this.listar();

    }

    @Override
    public void listar() {
        listUsuarios = userController.findAll();

    }

    @Override
    public void prepareCreate() {
        this.user = new Usuarios();
        this.listUsuarios = new ArrayList<>();
    }

    public void dlgCrearUsuario() {
        System.out.println("dialogo");
        PrimeFaces.current().executeScript("PF('dialogCreacionUser').show()");

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

    public Usuarios getUser() {
        return user;
    }

    public void setUser(Usuarios user) {
        this.user = user;
    }

    public List<Usuarios> getListUsuarios() {
        return listUsuarios;
    }

    public void setListUsuarios(List<Usuarios> listUsuarios) {
        this.listUsuarios = listUsuarios;
    }

}
