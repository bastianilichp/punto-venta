package cl.puntoventa.app.bean;

import cl.puntoventa.app.controller.OrdenCompraController;
import cl.puntoventa.app.entity.OrdenCompra;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

@Named("ordenCompraBean")
@ViewScoped
public class OrdenCompraBean implements AppBean, Serializable {

    private LazyDataModel<OrdenCompra> ordenCompraLazy;

    @Inject
    private OrdenCompraController ordenCompraController;

    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String abrirGenerarOrden() {
        return "/view/mailbox/compras/ordenCompra/generarOrden/index.hsm?faces-redirect=true";

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

}
