package cl.puntoventa.app.bean;

import cl.puntoventa.app.controller.ProductosController;
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
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.primefaces.PrimeFaces;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

@Named("ventasPeriodoBean")
@ViewScoped
public class VentasPeriodoBean implements AppBean, Serializable {

    @Inject
    private HttpSession httpSession;

    private Usuarios usuario;

    private List<Usuarios> listUsuario;

    private LazyDataModel<VentaNueva> ventaNuevaPeriodo;

    private List<VentaDetalles> listDetallePeriodo;

    @Inject
    private VentasNuevaController ventasNuevaController;

    @Inject
    private VentasDetallesController ventasDetallesController;

    @Inject
    private UserController usuarioController;

    @Inject
    private ProductosController productoController;

    private Date fechaDesde;
    private Date fechaHasta;

    @PostConstruct
    @Override
    public void init() {
        this.prepareCreate();
        this.listar();

    }

    @Override
    public void listar() {
        listUsuario = usuarioController.findAll();

    }

    @Override
    public void prepareCreate() {
        this.usuario = new Usuarios();
        this.listDetallePeriodo = new ArrayList<>();
        this.listUsuario = new ArrayList<>();
        this.fechaDesde = new Date();
        this.fechaHasta = new Date();

    }

    public void buscarVentasPeriodo() {
        if (fechaDesde != null || fechaHasta != null) {
            this.ventaNuevaPeriodo = new LazyDataModel<VentaNueva>() {
                @Override
                public int count(Map<String, FilterMeta> map) {
                    return ventasNuevaController.countPeriodo(map, fechaDesde, fechaHasta).intValue();
                }

                @Override
                public List<VentaNueva> load(int i, int i1, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                    return ventasNuevaController.findAllPeriodo(i, i1, map, map1, fechaDesde, fechaHasta);
                }
            };

        }
        System.out.println(ventaNuevaPeriodo.getRowCount());

    }

    public void detallesVentas(VentaNueva venta) {
        System.out.println(venta.getVentaDetallesSet());
        Set<VentaDetalles> detallesSet = venta.getVentaDetallesSet();
        this.listDetallePeriodo = new ArrayList<>(detallesSet);
        PrimeFaces.current().executeScript("PF('dialogDetallesPeriodo').show()");

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

    public LazyDataModel<VentaNueva> getVentaNuevaPeriodo() {
        return ventaNuevaPeriodo;
    }

    public void setVentaNuevaPeriodo(LazyDataModel<VentaNueva> ventaNuevaPeriodo) {
        this.ventaNuevaPeriodo = ventaNuevaPeriodo;
    }

    public List<VentaDetalles> getListDetallePeriodo() {
        return listDetallePeriodo;
    }

    public void setListDetallePeriodo(List<VentaDetalles> listDetallePeriodo) {
        this.listDetallePeriodo = listDetallePeriodo;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public List<Usuarios> getListUsuario() {
        return listUsuario;
    }

    public void setListUsuario(List<Usuarios> listUsuario) {
        this.listUsuario = listUsuario;
    }

}
