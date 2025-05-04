
package cl.puntoventa.app.controller;

import cl.puntoventa.app.dao.AbstractDaoImpl;
import cl.puntoventa.app.entity.DetallesCompra;
import cl.puntoventa.app.entity.OrdenCompra;
import cl.puntoventa.app.to.ProductoOrdenTO;
import jakarta.ejb.Stateless;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

@Stateless
public class DetalleOrdenController extends AbstractDaoImpl<DetallesCompra> {

    public DetalleOrdenController() {
        super(DetallesCompra.class);
    }

    @Override
    public List<DetallesCompra> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<DetallesCompra> findAll(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Long count(Map<String, FilterMeta> filterBy) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
     public boolean create(ProductoOrdenTO pro, OrdenCompra orden) {
         DetallesCompra detalle = new DetallesCompra();
         detalle.setCantidad(pro.getCantidad());
         detalle.setCodigoProducto(pro.getCodigo());
         detalle.setNombreProducto(pro.getNombre());
         detalle.setOrdenCompra(orden);
         detalle.setTotal(pro.getTotal());
         detalle.setUnitario(pro.getUnitario());         
         return super.create(detalle);
     }
    
}
