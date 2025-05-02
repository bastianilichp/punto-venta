package cl.puntoventa.app.bean;

import cl.puntoventa.app.clases.Util;
import cl.puntoventa.app.controller.ProveedoresController;
import cl.puntoventa.app.entity.Producto;
import cl.puntoventa.app.entity.Proveedores;
import cl.puntoventa.app.entity.Usuarios;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.primefaces.PF;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

@Named("proveedoresBean")
@ViewScoped
public class ProveedoresBean implements AppBean, Serializable {

    private LazyDataModel<Proveedores> proveedoresList;

    private Proveedores proveedor;
    
    private Proveedores deleteProveedor;

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
        this.deleteProveedor = new Proveedores();

    }

    @Override
    public void create() {  
        if (proveedoresController.create(this.proveedor)) {
            Util.avisoInfo("infoMsg", "Proveedor Agregado");
             PF.current().executeScript("PF('dialogAddProveedor').hide()");
            this.proveedor = new Proveedores();
        } else {
            Util.avisoError("infoMsg", "Error al agregar un proveedor");
        }

    }
    
        public void onRowEdit(RowEditEvent<Proveedores> event) {
        Proveedores proveedor = proveedoresController.findOneById(event.getObject().getId());
        proveedor.setNombre(event.getObject().getNombre());
        proveedor.setRut(event.getObject().getRut());
        if (proveedoresController.update(proveedor)) {
            Util.avisoInfo("infoMsg", "Proveedor Modificado");
        }

    }

    public void onRowCancel(RowEditEvent<Usuarios> event) {
        Util.avisoInfo("infoMsg", "Proveedor No Modificado");

    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete() {
        if(proveedoresController.delete(this.deleteProveedor)){
             Util.avisoInfo("infoMsg", "Proveedor Eliminado");
        
        } else {
            Util.avisoError("infoMsg", "Error al Eliminar un proveedor");
        }
        
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

    public Proveedores getDeleteProveedor() {
        return deleteProveedor;
    }

    public void setDeleteProveedor(Proveedores deleteProveedor) {
        this.deleteProveedor = deleteProveedor;
    }
    

}
