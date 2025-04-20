package cl.puntoventa.app.bean;

import cl.puntoventa.app.clases.Util;
import cl.puntoventa.app.controller.ProductosController;
import cl.puntoventa.app.controller.VentasDetallesController;
import cl.puntoventa.app.controller.VentasNuevaController;
import cl.puntoventa.app.entity.Producto;
import cl.puntoventa.app.entity.Usuarios;
import cl.puntoventa.app.entity.VentaNueva;
import cl.puntoventa.app.to.VentasTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.RollbackException;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Named("puntoVentaBean")
@ViewScoped
public class PuntoVentaBean implements AppBean, Serializable {

    private String codigo;

    private Integer subTotal;

    private Integer descuento;

    private Integer totalVenta;

    private Integer pagaCon;

    private Integer vuelto;

    private Producto productos;

    private List<VentasTO> ventasTO;

    @Inject
    private HttpSession httpSession;

    @Inject
    private ProductosController productosController;

    @Inject
    private VentasDetallesController ventasDetallesController;

    @Inject
    private VentasNuevaController ventasNuevaController;

    private Usuarios user;

    private final String HOME_PAGE_REDIRECT = "/view/mailbox/punto/index?faces-redirect=true";

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
        this.ventasTO = new ArrayList<>();
        this.codigo = new String();
        this.productos = new Producto();
        this.subTotal = 0;
        this.descuento = 0;
        this.totalVenta = 0;
        this.pagaCon = 0;
        this.user = new Usuarios();

    }

    public void buscarProductos() {

        Optional<VentasTO> existingVenta = ventasTO.stream()
                .filter(vent -> vent.getCodigo().equals(codigo))
                .findFirst();

        if (existingVenta.isPresent()) {
            VentasTO vent = existingVenta.get();
            vent.setCantidad(vent.getCantidad() + 1);
            vent.setTotal(vent.getCantidad() * vent.getPrecioVenta());
        } else {

            Producto pro = productosController.findByCodigo(codigo);
            if (pro != null) {
                if (pro.getStock() == 0) {
                    Util.avisoError("infoMsg", "Producto Sin Stock");

                } else {
                    VentasTO to = new VentasTO();
                    to.setCodigo(pro.getCodigo());
                    to.setNombre(pro.getNombre());
                    to.setPrecioVenta(pro.getPrecioVenta());
                    to.setStock(pro.getStock());
                    to.setCantidad(1);
                    to.setTotal(to.getCantidad() * to.getPrecioVenta());
                    ventasTO.add(to);
                }

            } else {

                Util.avisoError("infoMsg", "Producto No Existe");
            }
        }
        System.out.println(subTotal);
        subTotal = 0;
        for (VentasTO v : ventasTO) {
            subTotal += v.getTotal();
        }
        totalVenta = subTotal - descuento;

        this.codigo = "";
    }

    public void modificarCantidad(VentasTO to) {
        subTotal = 0;
        for (VentasTO v : ventasTO) {
            if (v.getCodigo().equals(to.getCodigo())) {
                v.setTotal(to.getCantidad() * to.getPrecioVenta());
            }

            subTotal += v.getTotal();
        }

        totalVenta = subTotal - descuento;
    }

    public void limpiarVenta(VentasTO to) {
        ventasTO.remove(to);
        subTotal = 0;
        descuento = 0;
        totalVenta = 0;
        pagaCon = 0;
//        for (VentasTO v : ventasTO) {
//            subTotal += v.getTotal();
//        }
//        totalVenta = subTotal - descuento;
    }

    public String terminarVenta() {

        if (!ventasTO.isEmpty()) {

            VentaNueva nueva = ventasNuevaController.create(this.descuento, this.subTotal, this.totalVenta, this.user);

            if (nueva == null) {
                Util.avisoError("infoMsg", "Error al crear la nueva venta. Intente nuevamente.");
                return null;
            }

            Integer contador = 0;

            for (VentasTO to : ventasTO) {
                if (ventasDetallesController.create(to, nueva)) {
                    contador++;
                    //descontar Stock
                    productosController.descontarStock(to);
                } else {
                    Util.avisoError("infoMsg", "No se guardo la venta.");
                }
            }

            if (contador == ventasTO.size()) {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.getFlash().setKeepMessages(true);
                Util.avisoInfo("infoMsg", "Nueva venta creada exitosamente.");
                return HOME_PAGE_REDIRECT;
            }

        } else {

            Util.avisoError("infoMsg", "No hay ventas para procesar.");
        }
        return null;
    }    
  

    public String cancelarVenta() {
        return HOME_PAGE_REDIRECT;
    }

    public void calcularTotal() {
        this.totalVenta = subTotal - descuento;
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

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    public Integer getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(Integer totalVenta) {
        this.totalVenta = totalVenta;
    }

    public Usuarios getUser() {
        return user;
    }

    public void setUser(Usuarios user) {
        this.user = user;
    }

    public Integer getPagaCon() {
        return pagaCon;
    }

    public void setPagaCon(Integer pagaCon) {
        this.pagaCon = pagaCon;
    }

    public Integer getVuelto() {
        return vuelto;
    }

    public void setVuelto(Integer vuelto) {
        this.vuelto = vuelto;
    }

}
