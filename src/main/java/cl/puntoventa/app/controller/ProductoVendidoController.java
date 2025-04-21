package cl.puntoventa.app.controller;

import cl.puntoventa.app.dao.AbstractDaoImpl;
import cl.puntoventa.app.entity.ProductoVendido;
import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

@Stateless
public class ProductoVendidoController extends AbstractDaoImpl<ProductoVendido> {

    public ProductoVendidoController() {
        super(ProductoVendido.class);
    }

    @Override
    public List<ProductoVendido> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ProductoVendido> findAll(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Long count(Map<String, FilterMeta> filterBy) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<ProductoVendido> findByVenta(Integer id) {
        StringBuilder jpql = new StringBuilder();
        List<ProductoVendido> lista = null;

        try {
            jpql.append("SELECT vendido FROM ProductoVendido vendido ")
                    .append(" WHERE 1=1 ")
                    .append(" AND vendido.venta.id = :id");

            Query query = entityManager.createQuery(jpql.toString());
            query.setParameter("id", id);

            lista = query.getResultList();
        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

        return lista;

    }

}
