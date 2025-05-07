package cl.puntoventa.app.bean;

import cl.puntoventa.app.clases.Util;
import cl.puntoventa.app.controller.EmailController;
import cl.puntoventa.app.controller.ProductoVendidoController;
import cl.puntoventa.app.controller.UserController;
import cl.puntoventa.app.controller.VentasDetallesController;
import cl.puntoventa.app.controller.VentasNuevaController;
import cl.puntoventa.app.entity.Usuarios;
import cl.puntoventa.app.entity.VentaDetalles;
import cl.puntoventa.app.entity.VentaNueva;
import cl.puntoventa.app.schedule.ScheduleController;
import cl.puntoventa.app.to.TopProductosTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

@Named("inboxBean")
@ViewScoped
public class InboxBean implements AppBean, Serializable {

    private Usuarios user;

    private List<Usuarios> listUsuarios;

    private List<VentaDetalles> listVenta;

    private List<TopProductosTO> topProdcutos;

    @Inject
    private UserController userController;

    @Inject
    private VentasNuevaController ventasNuevaController;

    @Inject
    private VentasDetallesController ventasDetallesController;

    @Inject
    private ProductoVendidoController productoVendidoController;

    @Inject
    private EmailController emailController;

    @Inject
    private HttpSession httpSession;

    @Inject
    private ScheduleController scheduleController;

    @PostConstruct
    @Override
    public void init() {
        this.prepareCreate();
        this.listar();

    }

    @Override
    public void listar() {
        listUsuarios = userController.findAllModificacion();
        user = (Usuarios) httpSession.getAttribute("userSession");

    }

    @Override
    public void prepareCreate() {
        this.user = new Usuarios();
        this.listUsuarios = new ArrayList<>();
        this.listVenta = new ArrayList<>();
        this.topProdcutos = new ArrayList<>();
    }

    public void dlgCrearUsuario() {
        System.out.println("dialogo");
        PrimeFaces.current().executeScript("PF('dialogCreacionUser').show()");

    }

    public void exportVentasDiarias() {
        this.listVenta = new ArrayList<>();
        this.listVenta = ventasDetallesController.findByDiaria();
        if (!listVenta.isEmpty()) {
            PrimeFaces.current().executeScript("PF('dialogIndex').show()");
        } else {
            Util.avisoInfo("infoMsg", "Sin Datos Exportar");
        }

    }

    public void exportVentasMensuales() {
        this.listVenta = new ArrayList<>();
        LocalDate fechaActual = LocalDate.now();
        LocalDate primerDia = fechaActual.withDayOfMonth(1);
        LocalDate ultimoDia = fechaActual.withDayOfMonth(fechaActual.lengthOfMonth());
        this.listVenta = ventasDetallesController.findByPeriodo(primerDia, ultimoDia);
        if (!listVenta.isEmpty()) {
            PrimeFaces.current().executeScript("PF('dialogIndex').show()");
        } else {
            Util.avisoInfo("infoMsg", "Sin Datos Exportar");
        }

    }

    public void exportVentasDetalles() {
        this.listVenta = new ArrayList<>();
        this.listVenta = ventasDetallesController.findAll();
        if (!listVenta.isEmpty()) {
            PrimeFaces.current().executeScript("PF('dialogIndex').show()");
        } else {
            Util.avisoInfo("infoMsg", "Sin Datos Exportar");
        }
    }

    public void exportVentasUsuario() {
        this.listVenta = new ArrayList<>();
    }

    public void topProductos() {
        this.topProdcutos = new ArrayList<>();
        List<Object[]> obj = ventasDetallesController.obtenerTopProductosVendidos();
        for (Object[] row : obj) {
            TopProductosTO to = new TopProductosTO();
            to.setCodigo((String) row[0]);
            to.setNombre((String) row[1]);
            to.setCantidad((Long) row[2]);
            topProdcutos.add(to);
        }
        if (!topProdcutos.isEmpty()) {
            PrimeFaces.current().executeScript("PF('dialogTopProdcutos').show()");

        }
        System.out.println(topProdcutos);

    }

    public void topProductosHistorico() {
        this.topProdcutos = new ArrayList<>();
        List<Object[]> obj = productoVendidoController.obtenerTopProductosHistorico();
        for (Object[] row : obj) {
            TopProductosTO to = new TopProductosTO();
            to.setCodigo((String) row[0]);
            to.setNombre((String) row[1]);
            to.setCantidad((Long) row[2]);
            topProdcutos.add(to);
        }
        if (!topProdcutos.isEmpty()) {
            PrimeFaces.current().executeScript("PF('dialogTopProdcutos').show()");

        }
        System.out.println(topProdcutos);

    }

    public void onRowEdit(RowEditEvent<Usuarios> event) {
        Usuarios user = userController.findOneById(event.getObject().getId());
        user.setEnabled(event.getObject().getEnabled());
        user.setManager(event.getObject().getManager());
        if (userController.update(user)) {
            Util.avisoInfo("infoMsg", "Usuario Modificado");
        }

    }

    public void onRowCancel(RowEditEvent<Usuarios> event) {
        Util.avisoInfo("infoMsg", "Usuario No Modificado");

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

    public Usuarios getUser() {
        return user;
    }

    public void setUser(Usuarios user) {
        this.user = user;
    }

    public List<Usuarios> getListUsuarios() {
        return listUsuarios;
    }

    public void setListUsuarios(List<Usuarios> listUsuarios) {
        this.listUsuarios = listUsuarios;
    }

    public List<VentaDetalles> getListVenta() {
        return listVenta;
    }

    public void setListVenta(List<VentaDetalles> listVenta) {
        this.listVenta = listVenta;
    }

    public List<TopProductosTO> getTopProdcutos() {
        return topProdcutos;
    }

    public void setTopProdcutos(List<TopProductosTO> topProdcutos) {
        this.topProdcutos = topProdcutos;
    }

}
