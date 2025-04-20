package cl.puntoventa.app.bean;

import cl.puntoventa.app.clases.Util;
import cl.puntoventa.app.controller.CategoriaController;
import cl.puntoventa.app.controller.ProductosController;
import cl.puntoventa.app.entity.Categoria;
import cl.puntoventa.app.entity.Producto;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.primefaces.PF;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

@Named("productoBean")
@ViewScoped
public class ProductosBean implements AppBean, Serializable {

    private Producto productos;

    private Producto deleteProd;

    private Producto editProducto;

    private LazyDataModel<Producto> productosList;

    @Inject
    private ProductosController productosController;

    @Inject
    private CategoriaController categoriaController;

    private List<Categoria> listCategoria;

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

    public Producto getEditProducto() {
        return editProducto;
    }

    public void setEditProducto(Producto editProducto) {
        this.editProducto = editProducto;
    }

    public List<Categoria> getListCategoria() {
        return listCategoria;
    }

    public void setListCategoria(List<Categoria> listCategoria) {
        this.listCategoria = listCategoria;
    }

    public Producto getDeleteProd() {
        return deleteProd;
    }

    public void setDeleteProd(Producto deleteProd) {
        this.deleteProd = deleteProd;
    }

    @Override
    @PostConstruct
    public void init() {
        this.prepareCreate();
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
        this.listCategoria = categoriaController.findAll();

    }

    @Override
    public void prepareCreate() {
        this.productos = new Producto();
        this.editProducto = new Producto();
        this.listCategoria = new ArrayList<>();
        this.editProducto.setCategoria(new Categoria());
        this.productos.setCategoria(new Categoria());
        this.deleteProd = new Producto();
    }

    public void editarProductos(Producto pro) {
        this.editProducto = productosController.findByCodigo(pro.getCodigo());
        if (editProducto != null) {
            PF.current().executeScript("PF('dialogEditProdcutos').show()");
        } else {
            Util.avisoError("infoMsg", "Error al Buscar Producto");
        }

    }

    public void cancelarEdit() {
        this.editProducto = new Producto();
        PF.current().executeScript("PF('dialogEditProdcutos').hide()");

    }

    @Override
    public void create() {
        if (productosController.create(this.productos)) {
            Util.avisoInfo("infoMsg", "Producto Agregado");
        }
        this.productos = new Producto();
        this.productos.setCategoria(new Categoria());

    }

    @Override
    public void update() {
        if (productosController.update(this.editProducto)) {
            Util.avisoInfo("infoMsg", "Producto Editado");
            PF.current().executeScript("PF('dialogEditProdcutos').hide()");
        }
    }

    @Override
    public void delete() {
        if (productosController.delete(this.deleteProd)) {
            Util.avisoInfo("infoMsg", "Producto Eliminado");
        } else {
            Util.avisoError("infoMsg", "Error al eliminar el producto");
        }

    }

    public void prepareDelete(Producto pro) {
        System.out.println("cl.puntoventa.app.bean.ProductosBean.prepareDelete()");
        this.deleteProd = pro;
        PF.current().executeScript("PF('dialogDeleteProducto').show()");

    }

}
