package cl.puntoventa.app.bean;

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

@Named("ventasUserBean")
@ViewScoped
public class VentasUserBean implements AppBean, Serializable {

    @Inject
    private HttpSession httpSession;

    private Usuarios usuario;

    private LazyDataModel<VentaNueva> ventaNuevaLazy;

    private List<VentaDetalles> listDetalle;

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

    }

    @Override
    public void prepareCreate() {
        this.usuario = new Usuarios();
        this.listDetalle = new ArrayList<>();

    }

    public void detalleVentaUser(VentaNueva venta) {
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

}
