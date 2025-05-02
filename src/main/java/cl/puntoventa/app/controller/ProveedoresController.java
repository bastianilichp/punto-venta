package cl.puntoventa.app.controller;

import cl.puntoventa.app.clases.Util;
import cl.puntoventa.app.dao.AbstractDaoImpl;
import cl.puntoventa.app.entity.Proveedores;
import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

@Stateless
public class ProveedoresController extends AbstractDaoImpl<Proveedores> {

    public ProveedoresController() {
        super(Proveedores.class);
    }

    @Override
    public List<Proveedores> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Long count(Map<String, FilterMeta> filterBy) {
        StringBuilder jpql = new StringBuilder();
        Long cantidad = 0L;

        try {
            jpql.append("SELECT COUNT(proveedores) FROM Proveedores proveedores ")
                    .append(" WHERE 1=1 ")
                    .append(this.filtroDataTable(filterBy));

            Query query = entityManager.createQuery(jpql.toString());
            this.filtroDataTableCheck(query, filterBy);

            cantidad = (Long) query.getSingleResult();
        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

        return cantidad;
    }

    @Override
    public List<Proveedores> findAll(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        StringBuilder jpql = new StringBuilder();
        List<Proveedores> lista = null;

        try {
            jpql.append("SELECT proveedores FROM Proveedores proveedores ")
                    .append(" WHERE 1=1 ")
                    .append(this.filtroDataTable(filterBy));

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

                if (filterField.equals("rut") && filterValue != null) {
                    jpql.append(" AND productos.rut like :rut ");
                }

                if (filterField.equals("nombre") && filterValue != null) {
                    jpql.append(" AND productos.nombre like :nombre ");
                }

                if (filterField.equals("id") && filterValue != null) {
                    jpql.append(" AND CAST(productos.id as String) like :id ");

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

                if (filterField.equals("nombre") && filterValue != null) {
                    query.setParameter("nombre", "%" + filterValue + "%");
                }

                if (filterField.equals("rut") && filterValue != null) {
                    query.setParameter("rut", "%" + filterValue + "%");
                }

            }
        }
    }

    public boolean create(Proveedores proveedor) {

        if (validar(proveedor)) {
            return super.create(proveedor);
        }

        return false;

    }

    public boolean validar(Proveedores proveedor) {
        boolean valido = true;
        if (proveedor.getNombre().isBlank()) {
            Util.avisoError("infoMsg", "Ingrese Nombre");
            valido = false;
        }

        if (proveedor.getRut().isBlank()) {
            Util.avisoError("infoMsg", "Ingrese RUT");
            valido = false;
        } else {
            Proveedores nuevo = findByRut(proveedor.getRut());
            if (nuevo != null) {
                Util.avisoError("infoMsg", "Proveedor ya existe");
                valido = false;

            }

        }

        return valido;

    }

    public Proveedores findByRut(String rut) {
        StringBuilder jpql = new StringBuilder();
        Proveedores reult = null;

        try {
            jpql.append("SELECT proveedores FROM Proveedores proveedores ")
                    .append(" WHERE 1=1 ")
                    .append(" and proveedores.rut = :rut ");

            Query query = entityManager.createQuery(jpql.toString());
            query.setParameter("rut", rut);

            reult = (Proveedores) query.getSingleResult();

        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

        return reult;

    }

    public List<Proveedores> findByNombre(String nombre) {
        StringBuilder jpql = new StringBuilder();
        List<Proveedores> lista = null;

        try {
            jpql.append("SELECT proveedores FROM Proveedores proveedores ")
                    .append(" WHERE 1=1 ")
                    .append(" and proveedores.nombre like :nombre ");

            Query query = entityManager.createQuery(jpql.toString());
            query.setParameter("nombre", "%" + nombre + "%");

            lista = query.getResultList();

        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

        return lista;

    }
    
      public Proveedores findByNombreExacto(String nombre) {
        StringBuilder jpql = new StringBuilder();
        Proveedores result = null;

        try {
            jpql.append("SELECT proveedores FROM Proveedores proveedores ")
                    .append(" WHERE 1=1 ")
                    .append(" and proveedores.nombre = :nombre ");

            Query query = entityManager.createQuery(jpql.toString());
            query.setParameter("nombre",nombre );

            result = (Proveedores) query.getSingleResult();

        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

        return result;

    }

}
