package cl.puntoventa.app.bean;

import cl.puntoventa.app.controller.ProductosController;
import cl.puntoventa.app.entity.Producto;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

@Named("productoBean")
@ViewScoped
public class ProductosBean implements AppBean, Serializable {

    private Producto productos;

    private LazyDataModel<Producto> productosList;

    @Inject
    private ProductosController productosController;

    public Producto getProductos() {
        return productos;
    }

    public void setProductos(Producto productos) {
        this.productos = productos;
    }

    public LazyDataModel<Producto> getProductosList() {
        return productosList;
    }

    public void setProductosList(LazyDataModel<Producto> productosList) {
        this.productosList = productosList;
    }

    @Override
    @PostConstruct
    public void init() {
        this.listar();
    }

    @Override
    public void listar() {
        this.productosList = new LazyDataModel<Producto>() {
            @Override
            public int count(Map<String, FilterMeta> map) {
                return productosController.count(map).intValue();
            }

            @Override
            public List<Producto> load(int i, int i1, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                return productosController.findAll(i, i1, map, map1);
            }
        };

    }

    @Override
    public void prepareCreate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

}
