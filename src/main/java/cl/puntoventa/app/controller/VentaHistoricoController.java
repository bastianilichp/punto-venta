/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cl.puntoventa.app.controller;

import cl.puntoventa.app.dao.AbstractDaoImpl;
import cl.puntoventa.app.entity.Venta;
import cl.puntoventa.app.entity.VentaDetalles;
import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

@Stateless
public class VentaHistoricoController extends AbstractDaoImpl<Venta> {

    public VentaHistoricoController() {
        super(Venta.class);
    }

    @Override
    public List<Venta> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Long count(Map<String, FilterMeta> filterBy) {
        StringBuilder jpql = new StringBuilder();
        Long cantidad = 0L;

        try {
            jpql.append("SELECT COUNT(venta) FROM Venta venta ")
                    .append(" LEFT JOIN venta.productoVendidoSet detalle ")
                    .append(" WHERE 1=1 ")
                    .append(this.filtroDataTable(filterBy))
                    .append("ORDER BY venta.id desc");

            Query query = entityManager.createQuery(jpql.toString());
            this.filtroDataTableCheck(query, filterBy);

            cantidad = (Long) query.getSingleResult();
        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

        return cantidad;
    }

    @Override
    public List<Venta> findAll(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        StringBuilder jpql = new StringBuilder();
        List<Venta> lista = null;

        try {
            jpql.append("SELECT venta FROM Venta venta ")
                    .append(" LEFT JOIN FETCH venta.productoVendidoSet detalle ")
                    .append(" WHERE 1=1 ")
                    .append(this.filtroDataTable(filterBy))
                    .append("ORDER BY venta.id desc");

            Query query = entityManager.createQuery(jpql.toString());

            this.filtroDataTableCheck(query, filterBy);

            if (pageSize >= 0) {
                query.setMaxResults(pageSize);
            }

            if (first >= 0) {
                query.setFirstResult(first);
            }

            lista = query.getResultList();
        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

        return lista;
    }

    public String filtroDataTable(Map<String, FilterMeta> filterBy) {
        StringBuilder jpql = new StringBuilder();
        if (filterBy != null) {
            for (FilterMeta meta : filterBy.values()) {
                String filterField = meta.getField();
                Object filterValue = meta.getFilterValue();

                if (filterField.equals("id") && filterValue != null) {
                    jpql.append(" AND CAST(venta.id AS string) LIKE :id ");
                }

                if (filterField.equals("fechayhora") && filterValue != null) {
                    jpql.append(" AND venta.fechayhora LIKE :fechayhora ");
                }

            }
        }

        return jpql.toString();
    }

    public void filtroDataTableCheck(Query query, Map<String, FilterMeta> filterBy) {
        if (filterBy != null) {
            for (FilterMeta meta : filterBy.values()) {
                String filterField = meta.getField();
                Object filterValue = meta.getFilterValue();

                if (filterField.equals("id") && filterValue != null) {
                    query.setParameter("id", "%" + filterValue + "%");
                }

                if (filterField.equals("fechayhora") && filterValue != null) {
                    query.setParameter("fechayhora", "%" + filterValue + "%");
                }

            }
        }
    }

}
