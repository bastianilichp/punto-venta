package cl.puntoventa.app.bean;

import cl.puntoventa.app.controller.ProveedoresController;
import cl.puntoventa.app.entity.OrdenCompra;
import cl.puntoventa.app.entity.Proveedores;
import cl.puntoventa.app.to.OrdenCompraTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.model.LazyDataModel;

@Named("ordenCompraBean")
@ViewScoped
public class OrdenCompraBean implements AppBean, Serializable {

    private OrdenCompra ordenCompra;

    private Proveedores listaProveedores;

    private String nombreProveedor;

    private OrdenCompraTO ordenCompraTO;

    private LazyDataModel<OrdenCompra> ordenLazy;

    @Inject
    private ProveedoresController proveedoresController;

    @PostConstruct
    @Override
    public void init() {
        this.prepareCreate();
        this.listar();
    }

    @Override
    public void listar() {

    }

    @Override
    public void prepareCreate() {
        this.ordenCompraTO = new OrdenCompraTO();
        this.nombreProveedor = "";

    }

    public List<String> buscarProveedores(String nombre) {
        List<Proveedores> proveedores = proveedoresController.findByNombre(nombre);
        List<String> fullName = new ArrayList<>();
        for (Proveedores pr : proveedores) {
            fullName.add(pr.getNombre());
        }
        return fullName;
    }

    public void completarDatos() {
        Proveedores p = proveedoresController.findByNombreExacto(nombreProveedor);
        if (p != null) {
            ordenCompraTO.setRut(p.getRut());
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

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public Proveedores getListaProveedores() {
        return listaProveedores;
    }

    public void setListaProveedores(Proveedores listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public OrdenCompraTO getOrdenCompraTO() {
        return ordenCompraTO;
    }

    public void setOrdenCompraTO(OrdenCompraTO ordenCompraTO) {
        this.ordenCompraTO = ordenCompraTO;
    }

    public LazyDataModel<OrdenCompra> getOrdenLazy() {
        return ordenLazy;
    }

    public void setOrdenLazy(LazyDataModel<OrdenCompra> ordenLazy) {
        this.ordenLazy = ordenLazy;
    }

}
