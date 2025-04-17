package cl.puntoventa.app.bean;

import cl.puntoventa.app.clases.Util;
import cl.puntoventa.app.controller.VentasDetallesController;
import cl.puntoventa.app.controller.VentasNuevaController;
import cl.puntoventa.app.entity.Producto;
import cl.puntoventa.app.entity.Usuarios;
import cl.puntoventa.app.entity.VentaDetalles;
import cl.puntoventa.app.entity.VentaNueva;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

@Named("ventasDiariasBean")
@ViewScoped
public class VentasDiariasBean implements AppBean, Serializable {

    @Inject
    private HttpSession httpSession;

    private Usuarios usuario;

    private List<VentaNueva> listVentas;

    private Integer subTotal;

    private Integer descuento;

    private Integer total;

    private Date fechaActual;

    @Inject
    private VentasNuevaController ventasNuevaController;

    @PostConstruct
    @Override
    public void init() {
        this.prepareCreate();
        this.listar();
    }

    @Override
    public void listar() {
        usuario = (Usuarios) httpSession.getAttribute("userSession");
        listVentas = ventasNuevaController.findByFecha(this.fechaActual);
        if (listVentas != null) {
            for (VentaNueva v : listVentas) {
                descuento += v.getDescuento();
                subTotal += v.getSubtotal();
                total += v.getTotal();
            }

        }

    }

    @Override
    public void prepareCreate() {
        this.usuario = new Usuarios();
        this.listVentas = new ArrayList<>();
        this.descuento = 0;
        this.subTotal = 0;
        this.total = 0;
        this.fechaActual = new Date();

        System.out.println(fechaActual);

    }

    public void ventasFecha() {
        this.descuento = 0;
        this.subTotal = 0;
        this.total = 0;
        listVentas = ventasNuevaController.findByFecha(this.fechaActual);
        if (!listVentas.isEmpty()) {
            for (VentaNueva v : listVentas) {
                descuento += v.getDescuento();
                subTotal += v.getSubtotal();
                total += v.getTotal();
            }
        } else {
            Util.avisoError("infoMsg", "Sin ventas para la fecha seleccionada");

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

}
