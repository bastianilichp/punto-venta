package cl.puntoventa.app.bean;

import cl.puntoventa.app.clases.Util;
import cl.puntoventa.app.controller.DetalleOrdenController;
import cl.puntoventa.app.controller.FichaController;
import cl.puntoventa.app.controller.OrdenCompraController;
import cl.puntoventa.app.controller.ProductosController;
import cl.puntoventa.app.controller.ProveedoresController;
import cl.puntoventa.app.entity.OrdenCompra;
import cl.puntoventa.app.entity.Producto;
import cl.puntoventa.app.entity.Proveedores;
import cl.puntoventa.app.entity.Usuarios;
import cl.puntoventa.app.to.ProductoOrdenTO;
import cl.puntoventa.app.to.VentasTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("generarOrdenBean")
@ViewScoped
public class GenerarOrdenBean implements AppBean, Serializable {

    private OrdenCompra ordenCompra;

    private Proveedores listaProveedores;

    private String nombreProveedor;

    private String rutProveedor;

    private String nombreProducto;

    private Integer cantidad;

    private Integer montoTotal;

    private List<ProductoOrdenTO> productoOrdenTO;

    private List<Producto> listaProducto;

    private Usuarios user;

    @Inject
    private ProveedoresController proveedoresController;

    @Inject
    private ProductosController productosController;

    @Inject
    private FichaController fichaController;

    @Inject
    private DetalleOrdenController detalleOrdenController;

    @Inject
    private OrdenCompraController ordenCompraController;

    @Inject
    private HttpSession httpSession;

    @PostConstruct
    @Override
    public void init() {
        this.prepareCreate();
        this.listar();
    }

    @Override
    public void listar() {
        user = (Usuarios) httpSession.getAttribute("userSession");

    }

    @Override
    public void prepareCreate() {
        this.productoOrdenTO = new ArrayList<>();
        this.nombreProveedor = "";
        this.listaProducto = new ArrayList<>();
        this.cantidad = 1;
        this.montoTotal = 0;
        this.user = new Usuarios();

    }

    public List<String> buscarProveedores(String nombre) {
        List<Proveedores> proveedores = proveedoresController.findByNombre(nombre);
        List<String> fullName = new ArrayList<>();
        for (Proveedores pr : proveedores) {
            fullName.add(pr.getNombre());
        }
        return fullName;
    }

    public List<String> buscarProductos(String nombre) {
        List<Producto> producto = productosController.findByNombre(nombre);
        List<String> fullName = new ArrayList<>();
        for (Producto pr : producto) {
            fullName.add(pr.getNombre());
        }
        return fullName;
    }

    public void completarDatos() {
        Proveedores p = proveedoresController.findByNombreExacto(nombreProveedor);
        if (p != null) {
            this.setRutProveedor(p.getRut());
        }
    }

    public void completarProductoOrden() {
        Producto p = productosController.findByNombreExacto(nombreProducto);
        if (p != null) {
            ProductoOrdenTO po = new ProductoOrdenTO();
            po.setCodigo(p.getCodigo());
            po.setNombre(p.getNombre());
            po.setUnitario(p.getPrecioCompra());
            po.setCantidad(cantidad);
            po.setTotal(cantidad * p.getPrecioCompra());
            productoOrdenTO.add(po);
            this.nombreProducto = new String();
        }

    }

    public void modificarCantidad(ProductoOrdenTO to) {
        montoTotal = 0;
        for (ProductoOrdenTO v : productoOrdenTO) {
            if (v.getCodigo().equals(to.getCodigo())) {
                v.setTotal(to.getCantidad() * to.getUnitario());
            }
            montoTotal += v.getTotal();
        }
    }

    public void cancelarOrden() {
        this.productoOrdenTO = new ArrayList<>();
        this.nombreProveedor = new String();
        this.nombreProducto = new String();
        this.rutProveedor = new String();

    }

    public void generarOrden() {

        if (!productoOrdenTO.isEmpty()) {

            OrdenCompra nueva = ordenCompraController.create(this.user, this.rutProveedor, this.montoTotal);

            if (nueva == null) {
                Util.avisoError("infoMsg", "Error al crear orden de compra.");
                cancelarOrden();
            } else {
                Integer contador = 0;
                for (ProductoOrdenTO to : productoOrdenTO) {
                    if (detalleOrdenController.create(to, nueva)) {
                        contador++;
                    } else {
                        Util.avisoError("infoMsg", "No se guardo la venta.");
                    }
                }
                if (contador == productoOrdenTO.size()) {
                    Util.avisoInfo("infoMsg", "Orden Creada");
                    cancelarOrden();
                }

            }
        }else {
            Util.avisoError("infoMsg", "Sin productos para generar la orden");        
        }
    }

    public void limpiarVenta(ProductoOrdenTO to) {
        productoOrdenTO.remove(to);
        montoTotal = 0;
        for (ProductoOrdenTO v : productoOrdenTO) {
            montoTotal += v.getTotal();
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

    public List<ProductoOrdenTO> getProductoOrdenTO() {
        return productoOrdenTO;
    }

    public void setProductoOrdenTO(List<ProductoOrdenTO> productoOrdenTO) {
        this.productoOrdenTO = productoOrdenTO;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public List<Producto> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public String getRutProveedor() {
        return rutProveedor;
    }

    public void setRutProveedor(String rutProveedor) {
        this.rutProveedor = rutProveedor;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Integer montoTotal) {
        this.montoTotal = montoTotal;
    }

}
