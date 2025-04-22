package cl.puntoventa.app.controller;

import cl.puntoventa.app.dao.AbstractDaoImpl;
import cl.puntoventa.app.entity.ProductoVendido;
import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        StringBuilder jpql = new StringBuilder();
        List<ProductoVendido> lista = null;

        try {
            jpql.append("SELECT vendido FROM ProductoVendido vendido ")
                    .append(" LEFT JOIN FETCH vendido.venta ")
                    .append(" WHERE 1=1 ")
                    .append(" ORDER BY vendido.id desc ");

            Query query = entityManager.createQuery(jpql.toString());

            lista = query.getResultList();
        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

        return lista;

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

    public List<ProductoVendido> findPeriodo(Date fechaD, Date fechaH) {
        StringBuilder jpql = new StringBuilder();
        List<ProductoVendido> lista = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaDesde = dateFormat.format(fechaD);
        String fechaHasta = dateFormat.format(fechaH);

        try {
            jpql.append("SELECT vendido FROM ProductoVendido vendido ")
                    .append(" LEFT JOIN FETCH vendido.venta ")
                    .append(" WHERE 1=1 ")
                    .append(" AND vendido.venta.fechayhora BETWEEN :fechaDesde AND :fechaHasta ")
                    .append(" ORDER BY vendido.id desc ");

            Query query = entityManager.createQuery(jpql.toString());
            query.setParameter("fechaDesde", fechaDesde + " 00:00:00");
            query.setParameter("fechaHasta", fechaHasta + " 23:59:59");

            lista = query.getResultList();
        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

        return lista;

    }

    public List<Object[]> obtenerTopProductosHistorico() {
        List<Object[]> resultados = null;
        StringBuilder jpql = new StringBuilder();

        try {
            jpql.append("SELECT vendido.codigo ,vendido.nombre, SUM(vendido.cantidad) ")
                    .append("FROM ProductoVendido vendido ")
                    .append("GROUP BY vendido.nombre ")
                    .append("ORDER BY SUM(vendido.cantidad) DESC");

            // Crear la consulta
            Query query = entityManager.createQuery(jpql.toString());
            query.setMaxResults(30); // Limitar a los 10 productos más vendidos

            resultados = query.getResultList();

        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
        return resultados;
    }

}
