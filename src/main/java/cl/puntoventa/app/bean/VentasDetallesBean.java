package cl.puntoventa.app.bean;

import cl.puntoventa.app.clases.Util;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    private VentaDetalles deleteDetalle;

    private VentaNueva deleteVenta;

    @Inject
    private VentasNuevaController ventasNuevaController;

    @Inject
    private VentasDetallesController ventasDetallesController;

    @Inject
    private UserController usuarioController;

    @Inject
    private ProductosController productoController;

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
        this.deleteDetalle = new VentaDetalles();
        this.deleteVenta = new VentaNueva();

    }

    public void detallesVentas(VentaNueva venta) {
        System.out.println(venta.getVentaDetallesSet());
        Set<VentaDetalles> detallesSet = venta.getVentaDetallesSet();
        this.listDetalle = new ArrayList<>(detallesSet);
        PrimeFaces.current().executeScript("PF('dialogDetallesVenta').show()");

    }

    public void deleteDetalle() {
        if (ventasDetallesController.delete(this.deleteDetalle)) {
            Producto p = productoController.findOneById(this.deleteDetalle.getProducto().getId());
            p.setStock(p.getStock() + deleteDetalle.getCantidad());
            //actualizar Stock
            if (productoController.update(p)) {
                //elimnar venta completa
                VentaNueva venta = ventasNuevaController.findOneById(this.deleteDetalle.getVentaNueva().getId());
                if (venta.getVentaDetallesSet().isEmpty()) {
                    ventasNuevaController.delete(venta);
                    Util.avisoInfo("infoMsg", "Venta Eliminado Completamente");
                } else {
                    //actualizar venta total

                    Util.avisoInfo("infoMsg", "Detalle Venta Eliminado");
                }
            }

        } else {
            Util.avisoError("infoMsg", "Error al eliminar el detalle");
        }

    }

    public void prepareDeleteD(VentaDetalles detalle) {
        this.deleteDetalle = detalle;
        PrimeFaces.current().executeScript("PF('dialogDeleteDetalle').show()");

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
            if (ventasNuevaController.delete(deleteVenta)) {
                Util.avisoInfo("infoMsg", "Venta Eliminada");
            }
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

    public VentaDetalles getDeleteDetalle() {
        return deleteDetalle;
    }

    public void setDeleteDetalle(VentaDetalles deleteDetalle) {
        this.deleteDetalle = deleteDetalle;
    }

    public VentaNueva getDeleteVenta() {
        return deleteVenta;
    }

    public void setDeleteVenta(VentaNueva deleteVenta) {
        this.deleteVenta = deleteVenta;
    }

}
