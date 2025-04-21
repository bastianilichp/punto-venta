package cl.puntoventa.app.bean;

import cl.puntoventa.app.controller.UserController;
import cl.puntoventa.app.controller.VentasDetallesController;
import cl.puntoventa.app.controller.VentasNuevaController;
import cl.puntoventa.app.entity.Usuarios;
import cl.puntoventa.app.entity.VentaDetalles;
import cl.puntoventa.app.entity.VentaNueva;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

@Named("inboxBean")
@ViewScoped
public class InboxBean implements AppBean, Serializable {

    private Usuarios user;

    private List<Usuarios> listUsuarios;

    private List<VentaDetalles> listVenta;

    @Inject
    private UserController userController;

    @Inject
    private VentasNuevaController ventasNuevaController;

    @Inject
    private VentasDetallesController ventasDetallesController;

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
        this.listVenta = new ArrayList<>();
    }

    public void dlgCrearUsuario() {
        System.out.println("dialogo");
        PrimeFaces.current().executeScript("PF('dialogCreacionUser').show()");

    }

    public void exportVentasDiarias() {
        this.listVenta = new ArrayList<>();
        this.listVenta = ventasDetallesController.findByDiaria();
        PrimeFaces.current().executeScript("PF('dialogIndex').show()");

    }

    public void exportVentasMensuales() {
        this.listVenta = new ArrayList<>();
    }

    public void exportVentasDetalles() {
        this.listVenta = new ArrayList<>();
        this.listVenta = ventasDetallesController.findAll();
        PrimeFaces.current().executeScript("PF('dialogIndex').show()");
    }

    public void exportVentasUsuario() {
        this.listVenta = new ArrayList<>();
    }

    public void onRowEdit(RowEditEvent<Usuarios> event) {
        System.out.println("editar");

    }

    public void onRowCancel(RowEditEvent<Usuarios> event) {

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

    public List<VentaDetalles> getListVenta() {
        return listVenta;
    }

    public void setListVenta(List<VentaDetalles> listVenta) {
        this.listVenta = listVenta;
    }

}
