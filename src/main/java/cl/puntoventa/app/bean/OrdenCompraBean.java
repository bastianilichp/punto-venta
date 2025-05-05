package cl.puntoventa.app.bean;

import cl.puntoventa.app.controller.ExportarController;
import cl.puntoventa.app.controller.FichaController;
import cl.puntoventa.app.controller.OrdenCompraController;
import cl.puntoventa.app.entity.DetallesCompra;
import cl.puntoventa.app.entity.OrdenCompra;
import cl.puntoventa.app.entity.VentaDetalles;
import cl.puntoventa.app.entity.VentaNueva;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jodconverter.core.office.OfficeException;
import org.primefaces.PrimeFaces;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

@Named("ordenCompraBean")
@ViewScoped
public class OrdenCompraBean implements AppBean, Serializable {

    private LazyDataModel<OrdenCompra> ordenCompraLazy;

    private List<DetallesCompra> listaDetalleOrden;

    @Inject
    private OrdenCompraController ordenCompraController;

    @Inject
    private FichaController fichaController;

    @Inject
    private HttpSession httpSession;

    @Inject
    private ExportarController exportarController;

    @PostConstruct
    @Override
    public void init() {
        this.prepareCreate();
        this.listar();
    }

    @Override
    public void listar() {
        this.ordenCompraLazy = new LazyDataModel<OrdenCompra>() {
            @Override
            public int count(Map<String, FilterMeta> map) {
                return ordenCompraController.count(map).intValue();
            }

            @Override
            public List<OrdenCompra> load(int i, int i1, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                return ordenCompraController.findAll(i, i1, map, map1);
            }
        };
    }

    @Override
    public void prepareCreate() {
        this.listaDetalleOrden = new ArrayList<>();

    }

    public String abrirGenerarOrden() {
        return "/view/mailbox/compras/ordenCompra/generarOrden/index.hsm?faces-redirect=true";

    }

    public void detallesVentas(OrdenCompra orden) {
        System.out.println("detalleorden");
        Set<DetallesCompra> detallesSet = orden.getDetallesCompraSet();
        this.listaDetalleOrden = new ArrayList<>(detallesSet);
        PrimeFaces.current().executeScript("PF('dialogDetallesOrden').show()");

    }

    public void imprimirOrden(OrdenCompra orden) throws OfficeException, IOException {
        Set<DetallesCompra> detallesSet = orden.getDetallesCompraSet();
        List<DetallesCompra> ventaDetalle = new ArrayList<>(detallesSet);
        File fileDocx = fichaController.imprimirOrdenCompra(ventaDetalle, orden);
        File fileToPdf = fichaController.libreOfficeToPdf(fileDocx, true);
        httpSession.setAttribute("fileToPdfOrden", fileToPdf);
       
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

    public LazyDataModel<OrdenCompra> getOrdenCompraLazy() {
        return ordenCompraLazy;
    }

    public void setOrdenCompraLazy(LazyDataModel<OrdenCompra> ordenCompraLazy) {
        this.ordenCompraLazy = ordenCompraLazy;
    }

    public List<DetallesCompra> getListaDetalleOrden() {
        return listaDetalleOrden;
    }

    public void setListaDetalleOrden(List<DetallesCompra> listaDetalleOrden) {
        this.listaDetalleOrden = listaDetalleOrden;
    }

}
