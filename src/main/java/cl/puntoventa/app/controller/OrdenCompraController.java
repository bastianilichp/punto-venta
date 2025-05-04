package cl.puntoventa.app.controller;

import cl.puntoventa.app.dao.AbstractDaoImpl;
import cl.puntoventa.app.entity.OrdenCompra;
import cl.puntoventa.app.entity.Usuarios;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

@Stateless
public class OrdenCompraController extends AbstractDaoImpl<OrdenCompra> {

    @Inject
    private ProveedoresController proveedoresController;

    public OrdenCompraController() {
        super(OrdenCompra.class);
    }

    @Override
    public List<OrdenCompra> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Long count(Map<String, FilterMeta> filterBy) {
        StringBuilder jpql = new StringBuilder();
        Long cantidad = 0L;

        try {
            jpql.append("SELECT COUNT(orden) FROM OrdenCompra orden ")
                    .append(" LEFT JOIN orden.usuarios ")
                    .append(" LEFT JOIN orden.proveedores ")
                    .append(" WHERE 1=1 ")
                    .append(this.filtroDataTable(filterBy))
                    .append(" ORDER BY orden.id DESC");

            Query query = entityManager.createQuery(jpql.toString());
            this.filtroDataTableCheck(query, filterBy);

            cantidad = (Long) query.getSingleResult();
        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

        return cantidad;
    }

    @Override
    public List<OrdenCompra> findAll(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        StringBuilder jpql = new StringBuilder();
        List<OrdenCompra> lista = null;

        try {
            jpql.append("SELECT orden FROM OrdenCompra orden ")
                    .append(" LEFT JOIN FETCH orden.usuarios ")
                    .append(" LEFT JOIN FETCH orden.proveedores ")
                    .append(" LEFT JOIN FETCH orden.detallesCompraSet detalle ")
                    .append(" WHERE 1=1 ")
                    .append(this.filtroDataTable(filterBy))
                    .append(" ORDER BY orden.id DESC");

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
                    jpql.append(" AND CAST(orden.id AS string) LIKE :id ");
                }
               
                if (filterField.equals("usuarios.email") && filterValue != null) {
                    jpql.append(" AND orden.usuarios.email LIKE :email ");
                }
                
                 if (filterField.equals("proveedores.nombre") && filterValue != null) {
                    jpql.append(" AND orden.proveedores.nombre LIKE :nombre ");
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

              
                if (filterField.equals("usuarios.email") && filterValue != null) {
                    query.setParameter("email", "%" + filterValue + "%");
                }
                if (filterField.equals("proveedores.nombre") && filterValue != null) {
                    query.setParameter("nombre", "%" + filterValue + "%");
                }

            }
        }
    }

    public OrdenCompra create(Usuarios user, String rut, Integer montoTotal) {
        OrdenCompra orden = new OrdenCompra();
        orden.setFecha(new Date());
        orden.setUsuarios(user);
        orden.setProveedores(proveedoresController.findByRut(rut));
        orden.setMontoTotal(montoTotal);
        super.create(orden);
        return orden;
    }
}
