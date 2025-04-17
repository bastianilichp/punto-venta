/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cl.puntoventa.app.controller;

import cl.puntoventa.app.dao.AbstractDaoImpl;
import cl.puntoventa.app.entity.Usuarios;
import cl.puntoventa.app.entity.VentaNueva;
import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

@Stateless
public class VentasNuevaController extends AbstractDaoImpl<VentaNueva> {
    
    public VentasNuevaController() {
        super(VentaNueva.class);
    }
    
    @Override
    public List<VentaNueva> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public Long count(Map<String, FilterMeta> filterBy) {
        StringBuilder jpql = new StringBuilder();
        Long cantidad = 0L;
        
        try {
            jpql.append("SELECT COUNT(nueva) FROM VentaNueva nueva ")
                    .append(" LEFT JOIN nueva.ventaDetallesSet detalle ")
                    .append(" LEFT JOIN detalle.producto")
                    .append(" LEFT JOIN detalle.usuarios")
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
    public List<VentaNueva> findAll(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        StringBuilder jpql = new StringBuilder();
        List<VentaNueva> lista = null;
        
        try {
            jpql.append("SELECT nueva FROM VentaNueva nueva ")
                    .append(" LEFT JOIN FETCH nueva.ventaDetallesSet detalle ")
                    .append(" LEFT JOIN FETCH detalle.producto")
                    .append(" LEFT JOIN FETCH detalle.usuarios")
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
                
                if (filterField.equals("id") && filterValue != null) {
                    jpql.append(" AND CAST(nueva.id as String) like :id ");
                }
                
                if (filterField.equals("fechayhora") && filterValue != null) {
                    jpql.append(" AND CAST(nueva.fechayhora as String) like :fechayhora ");
                }
                
                if (filterField.equals("descuento") && filterValue != null) {
                    jpql.append(" AND CAST(nueva.descuento as String) like :descuento ");
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
                
                if (filterField.equals("descuento") && filterValue != null) {
                    query.setParameter("descuento", "%" + filterValue + "%");
                }
                
            }
        }
    }
    
    public VentaNueva create(Integer desc, Integer sub, Integer total) {
        VentaNueva nueva = new VentaNueva();
        nueva.setDescuento(desc);
        nueva.setFechayhora(new Date());
        nueva.setSubtotal(sub);
        nueva.setTotal(total);
        super.create(nueva);
        
        return nueva;
    }
    
}
