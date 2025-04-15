package cl.puntoventa.app.bean;

import cl.puntoventa.app.controller.ProductosController;
import cl.puntoventa.app.entity.Producto;
import cl.puntoventa.app.to.VentasTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("puntoVentaBean")
@ViewScoped
public class PuntoVentaBean implements AppBean, Serializable {

    private String codigo;

    private Producto productos;

    private List<VentasTO> ventasTO;

    @Inject
    private ProductosController productosController;

    @PostConstruct
    @Override
    public void init() {
        this.prepareCreate();
        this.listar();
    }

    @Override
    public void listar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void prepareCreate() {
        this.ventasTO = new ArrayList<>();
        this.codigo = new String();
        this.productos = new Producto();
    }

    public void buscarProductos() {
        System.out.println(codigo);

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Producto getProductos() {
        return productos;
    }

    public void setProductos(Producto productos) {
        this.productos = productos;
    }

    public List<VentasTO> getVentasTO() {
        return ventasTO;
    }

    public void setVentasTO(List<VentasTO> ventasTO) {
        this.ventasTO = ventasTO;
    }

}
