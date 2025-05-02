package cl.puntoventa.app.bean;

import cl.puntoventa.app.clases.Util;
import cl.puntoventa.app.controller.ProveedoresController;
import cl.puntoventa.app.entity.Producto;
import cl.puntoventa.app.entity.Proveedores;
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

@Named("proveedoresBean")
@ViewScoped
public class ProveedoresBean implements AppBean, Serializable {

    private LazyDataModel<Proveedores> proveedoresList;

    private Proveedores proveedor;

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
        this.proveedoresList = new LazyDataModel<Proveedores>() {
            @Override
            public int count(Map<String, FilterMeta> map) {
                return proveedoresController.count(map).intValue();
            }

            @Override
            public List<Proveedores> load(int i, int i1, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                return proveedoresController.findAll(i, i1, map, map1);
            }
        };
    }

    @Override
    public void prepareCreate() {
        this.proveedor = new Proveedores();

    }

    @Override
    public void create() {
        System.out.println(proveedor.getNombre());
        System.out.println(proveedor.getRut());
        if (proveedoresController.create(this.proveedor)) {
            Util.avisoInfo("infoMsg", "Proveedor Agregado");
            this.proveedor = new Proveedores();
        } else {
            Util.avisoError("infoMsg", "Error al agregar un proveedor");
        }

    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public LazyDataModel<Proveedores> getProveedoresList() {
        return proveedoresList;
    }

    public void setProveedoresList(LazyDataModel<Proveedores> proveedoresList) {
        this.proveedoresList = proveedoresList;
    }

    public Proveedores getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedores proveedor) {
        this.proveedor = proveedor;
    }

}
