package cl.puntoventa.app.bean;

import cl.puntoventa.app.clases.Util;
import cl.puntoventa.app.controller.UserController;
import cl.puntoventa.app.controller.VentasNuevaController;
import cl.puntoventa.app.entity.Usuarios;
import cl.puntoventa.app.entity.VentaNueva;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named("ventaUsuariosBean")
@ViewScoped
public class VentasUsuariosBean implements AppBean, Serializable {

    @Inject
    private HttpSession httpSession;

    private Usuarios usuario;

    private List<VentaNueva> listVentas;

    private List<Usuarios> listUsuarios;

    private Integer subTotal;

    private Integer descuento;

    private Integer total;

    private Date fechaActual;

    @Inject
    private VentasNuevaController ventasNuevaController;

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
        this.listUsuarios = userController.findAll();
    }

    @Override
    public void prepareCreate() {
        this.usuario = new Usuarios();
        this.listVentas = new ArrayList<>();
        this.listUsuarios = new ArrayList<>();
        this.descuento = 0;
        this.subTotal = 0;
        this.total = 0;
        this.fechaActual = new Date();

    }

    public void ventasUsuarios() {
        if (fechaActual != null && usuario.getEmail() != null) {
            this.descuento = 0;
            this.subTotal = 0;
            this.total = 0;
            listVentas = ventasNuevaController.findByFechaUsuario(this.fechaActual, this.usuario);
            if (!listVentas.isEmpty()) {
                for (VentaNueva v : listVentas) {
                    descuento += v.getDescuento();
                    subTotal += v.getSubtotal();
                    total += v.getTotal();
                }
            } else {
                Util.avisoWarn("infoMsg", "Sin ventas para la fecha y el usuario seleccionado");

            }

        }else{
            Util.avisoError("infoMsg", "Ingresar Fecha y Usuario");

        
        }

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

    public HttpSession getHttpSession() {
        return httpSession;
    }

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public List<VentaNueva> getListVentas() {
        return listVentas;
    }

    public void setListVentas(List<VentaNueva> listVentas) {
        this.listVentas = listVentas;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public List<Usuarios> getListUsuarios() {
        return listUsuarios;
    }

    public void setListUsuarios(List<Usuarios> listUsuarios) {
        this.listUsuarios = listUsuarios;
    }

}
