package cl.puntoventa.app.bean;

import cl.puntoventa.app.clases.Util;
import cl.puntoventa.app.controller.FichaController;
import cl.puntoventa.app.controller.ProductosController;
import cl.puntoventa.app.controller.UserController;
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
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jodconverter.core.office.OfficeException;
import org.primefaces.PrimeFaces;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

@Named("ventasDetallesBean")
@ViewScoped
public class VentasDetallesBean implements AppBean, Serializable {

    @Inject
    private HttpSession httpSession;

    private Usuarios usuario;

    private List<Usuarios> listUsuario;

    private LazyDataModel<VentaNueva> ventaNuevaLazy;

    private List<VentaDetalles> listDetalle;

    private VentaNueva deleteVenta;

    @Inject
    private VentasNuevaController ventasNuevaController;

    @Inject
    private VentasDetallesController ventasDetallesController;

    @Inject
    private UserController usuarioController;

    @Inject
    private ProductosController productoController;

    @Inject
    private FichaController fichaController;

    @PostConstruct
    @Override
    public void init() {
        this.prepareCreate();
        this.listar();
    }

    @Override
    public void listar() {
        usuario = (Usuarios) httpSession.getAttribute("userSession");
        this.ventaNuevaLazy = new LazyDataModel<VentaNueva>() {
            @Override
            public int count(Map<String, FilterMeta> map) {
                return ventasNuevaController.count(map).intValue();
            }

            @Override
            public List<VentaNueva> load(int i, int i1, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                return ventasNuevaController.findAll(i, i1, map, map1);
            }
        };

        listUsuario = usuarioController.findAll();

    }

    @Override
    public void prepareCreate() {
        this.usuario = new Usuarios();
        this.listDetalle = new ArrayList<>();
        this.listUsuario = new ArrayList<>();
        this.deleteVenta = new VentaNueva();

    }

    public void imprimirDetalle(VentaNueva venta) throws OfficeException {
        Set<VentaDetalles> detallesSet = venta.getVentaDetallesSet();
        List<VentaDetalles> ventaDetalle = new ArrayList<>(detallesSet);
        File fileDocx = fichaController.imprimirDetalleVenta(ventaDetalle, venta);
        File fileToPdf = fichaController.libreOfficeToPdf(fileDocx, true);
        httpSession.setAttribute("fileToPdf", fileToPdf);
    }

    public void detallesVentas(VentaNueva venta) {
        System.out.println(venta.getVentaDetallesSet());
        Set<VentaDetalles> detallesSet = venta.getVentaDetallesSet();
        this.listDetalle = new ArrayList<>(detallesSet);
        PrimeFaces.current().executeScript("PF('dialogDetallesVenta').show()");

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
        List<VentaDetalles> detalles = ventasDetallesController.findByVenta(deleteVenta.getId());
        Integer contador = 0;
        for (VentaDetalles d : detalles) {
            Integer stock = d.getCantidad();
            Producto p = productoController.findOneById(d.getProducto().getId());
            p.setStock(stock + p.getStock());
            productoController.update(p);
            if (ventasDetallesController.delete(d)) {
                contador++;
            }

        }
        if (contador == detalles.size()) {
            Util.avisoInfo("infoMsg", "Detalle Venta Eliminado");
            VentaNueva venta = ventasNuevaController.findOneById(this.deleteVenta.getId());
            ventasNuevaController.delete(venta);
        }

    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public LazyDataModel<VentaNueva> getVentaNuevaLazy() {
        return ventaNuevaLazy;
    }

    public void setVentaNuevaLazy(LazyDataModel<VentaNueva> ventaNuevaLazy) {
        this.ventaNuevaLazy = ventaNuevaLazy;
    }

    public List<VentaDetalles> getListDetalle() {
        return listDetalle;
    }

    public void setListDetalle(List<VentaDetalles> listDetalle) {
        this.listDetalle = listDetalle;
    }

    public List<Usuarios> getListUsuario() {
        return listUsuario;
    }

    public void setListUsuario(List<Usuarios> listUsuario) {
        this.listUsuario = listUsuario;
    }

    public VentaNueva getDeleteVenta() {
        return deleteVenta;
    }

    public void setDeleteVenta(VentaNueva deleteVenta) {
        this.deleteVenta = deleteVenta;
    }

}
