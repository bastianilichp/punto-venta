package cl.puntoventa.app.bean;

import cl.puntoventa.app.controller.VentaHistoricoController;
import cl.puntoventa.app.controller.VentasDetallesController;
import cl.puntoventa.app.controller.VentasNuevaController;
import cl.puntoventa.app.entity.ProductoVendido;
import cl.puntoventa.app.entity.Venta;
import cl.puntoventa.app.entity.VentaDetalles;
import cl.puntoventa.app.entity.VentaNueva;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.primefaces.PrimeFaces;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

@Named("historicoBean")
@ViewScoped
public class VentaHistoricoBean implements AppBean, Serializable {

    private LazyDataModel<Venta> ventaHistorico;

    private List<ProductoVendido> listDetalleH;

    @Inject
    private VentaHistoricoController ventaHistoricoController;

    @PostConstruct
    @Override
    public void init() {
        this.prepareCreate();
        this.listar();

    }

    @Override
    public void listar() {

        this.ventaHistorico = new LazyDataModel<Venta>() {
            @Override
            public int count(Map<String, FilterMeta> map) {
                return ventaHistoricoController.count(map).intValue();
            }

            @Override
            public List<Venta> load(int i, int i1, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                return ventaHistoricoController.findAll(i, i1, map, map1);
            }
        };

    }

    @Override
    public void prepareCreate() {
        this.listDetalleH = new ArrayList<>();

    }

    public void detallesHistorico(Venta venta) {
        System.out.println(venta.getProductoVendidoSet());
        Set<ProductoVendido> detallesSet = venta.getProductoVendidoSet();
        this.listDetalleH = new ArrayList<>(detallesSet);
        PrimeFaces.current().executeScript("PF('dialogDetallesHistorico').show()");

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

    public LazyDataModel<Venta> getVentaHistorico() {
        return ventaHistorico;
    }

    public void setVentaHistorico(LazyDataModel<Venta> ventaHistorico) {
        this.ventaHistorico = ventaHistorico;
    }

    public List<ProductoVendido> getListDetalleH() {
        return listDetalleH;
    }

    public void setListDetalleH(List<ProductoVendido> listDetalleH) {
        this.listDetalleH = listDetalleH;
    }

}
