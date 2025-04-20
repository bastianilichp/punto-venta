package cl.puntoventa.app.controller;

import cl.puntoventa.app.clases.Util;
import cl.puntoventa.app.dao.AbstractDaoImpl;
import cl.puntoventa.app.entity.Producto;
import cl.puntoventa.app.entity.Usuarios;
import cl.puntoventa.app.to.VentasTO;
import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

@Stateless
public class ProductosController extends AbstractDaoImpl<Producto> {

    public ProductosController() {
        super(Producto.class);
    }

    @Override
    public List<Producto> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Long count(Map<String, FilterMeta> filterBy) {
        StringBuilder jpql = new StringBuilder();
        Long cantidad = 0L;

        try {
            jpql.append("SELECT COUNT(productos) FROM Producto productos ")
                    .append(" LEFT JOIN productos.categoria ")
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
    public List<Producto> findAll(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        StringBuilder jpql = new StringBuilder();
        List<Producto> lista = null;

        try {
            jpql.append("SELECT productos FROM Producto productos ")
                    .append(" LEFT JOIN FETCH productos.categoria ")
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

                if (filterField.equals("codigo") && filterValue != null) {
                    jpql.append(" AND productos.codigo like :codigo ");
                }

                if (filterField.equals("nombre") && filterValue != null) {
                    jpql.append(" AND productos.nombre like :nombre ");
                }

                if (filterField.equals("precioCompra") && filterValue != null) {
                    jpql.append(" AND CAST(productos.precioCompra as String) like :precioCompra ");

                }

                if (filterField.equals("precioVenta") && filterValue != null) {
                    jpql.append(" AND CAST(productos.precioVenta as String) like :precioVenta ");

                }

                if (filterField.equals("stock") && filterValue != null) {
                    jpql.append(" AND CAST(productos.stock as String) like :stock ");

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

                if (filterField.equals("codigo") && filterValue != null) {
                    query.setParameter("codigo", "%" + filterValue + "%");
                }

                if (filterField.equals("nombre") && filterValue != null) {
                    query.setParameter("nombre", "%" + filterValue + "%");
                }

                if (filterField.equals("precioCompra") && filterValue != null) {
                    query.setParameter("precioCompra", "%" + filterValue + "%");
                }

                if (filterField.equals("precioVenta") && filterValue != null) {
                    query.setParameter("precioVenta", "%" + filterValue + "%");
                }

                if (filterField.equals("Stock") && filterValue != null) {
                    query.setParameter("Stock", "%" + filterValue + "%");
                }

            }
        }
    }

    public Producto findByCodigo(String codigo) {
        StringBuilder jpql = new StringBuilder();
        Producto result = null;

        try {
            jpql.append("SELECT pro FROM Producto pro ")
                    .append(" LEFT JOIN FETCH pro.categoria ")
                    .append(" WHERE 1=1 ")
                    .append(" AND pro.codigo = :codigo");

            Query query = entityManager.createQuery(jpql.toString());
            query.setParameter("codigo", codigo.trim());

            result = (Producto) query.getSingleResult();
        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

        return result;
    }

    public void descontarStock(VentasTO to) {
        Integer nuevaStock = to.getStock() - to.getCantidad();
        Producto pro = findByCodigo(to.getCodigo());
        pro.setStock(nuevaStock);
        super.update(pro);

    }

    @Override
    public boolean create(Producto nuevo) {
        if (validarProducto(nuevo, "Add")) {
            return super.create(nuevo);
        }
        return false;
    }

    @Override
    public boolean update(Producto edit) {
        if (validarProducto(edit, "Edit")) {
            return super.update(edit);
        }
        return false;
    }

    public boolean validarProducto(Producto producto, String prefix) {
        boolean valido = true;
        if (prefix.equals("Add")) {
            if (producto.getCodigo() == null) {
                valido = false;
                Util.avisoError("infoMsg", "Ingresar Código");
            } else {
                Producto p = findByCodigo(producto.getCodigo());
                if (p != null) {
                    Util.avisoError("infoMsg", "Producto ya Existe");
                }
            }

            if (producto.getNombre() == null) {
                valido = false;
                Util.avisoError("infoMsg", "Ingresar Nombre");
            }

            if (producto.getPrecioCompra() == null) {
                valido = false;
                Util.avisoError("infoMsg", "Ingresar Precio Compra");
            }

            if (producto.getPrecioVenta() == null) {
                valido = false;
                Util.avisoError("infoMsg", "Ingresar Precio Venta");
            }

            if (producto.getStock() == null) {
                valido = false;
                Util.avisoError("infoMsg", "Ingresar Stock");
            }

            if (producto.getCategoria() == null) {
                valido = false;
                Util.avisoError("infoMsg", "Ingresar Categoría");
            }

        }
        if (prefix.equals("Edit")) {
            if (producto.getPrecioCompra() == null) {
                valido = false;
                Util.avisoError("infoMsg", "Ingresar Precio Compra");
            }

            if (producto.getPrecioVenta() == null) {
                valido = false;
                Util.avisoError("infoMsg", "Ingresar Precio Venta");
            }

            if (producto.getStock() == null) {
                valido = false;
                Util.avisoError("infoMsg", "Ingresar Stock");
            }

            if (producto.getCategoria() == null) {
                valido = false;
                Util.avisoError("infoMsg", "Ingresar Categoría");
            }

        }

        return valido;
    }

}
