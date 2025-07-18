package cl.puntoventa.app.controller;

import cl.puntoventa.app.clases.Util;
import cl.puntoventa.app.dao.AbstractDaoImpl;
import cl.puntoventa.app.entity.Producto;
import cl.puntoventa.app.entity.Usuarios;
import cl.puntoventa.app.entity.VentaDetalles;
import cl.puntoventa.app.entity.VentaNueva;
import cl.puntoventa.app.to.VentasDetalleTO;

import cl.puntoventa.app.to.VentasTO;
import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

@Stateless
public class VentasDetallesController extends AbstractDaoImpl<VentaDetalles> {

    @Resource(lookup = "java:global/_puntoventa")
    private DataSource dataSource;

    @Inject
    private ProductosController productosController;

    public VentasDetallesController() {
        super(VentaDetalles.class);
    }

    @Override
    public List<VentaDetalles> findAll() {
        StringBuilder jpql = new StringBuilder();
        List<VentaDetalles> lista = null;

        try {
            jpql.append("SELECT detalle FROM VentaDetalles detalle ")
                    .append(" LEFT JOIN FETCH detalle.producto ")
                    .append(" LEFT JOIN FETCH detalle.usuarios ")
                    .append(" LEFT JOIN FETCH detalle.ventaNueva ")
                    .append(" WHERE 1=1 ");

            Query query = entityManager.createQuery(jpql.toString());

            lista = query.getResultList();
        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

        return lista;
    }

    @Override
    public Long count(Map<String, FilterMeta> filterBy) {
        StringBuilder jpql = new StringBuilder();
        Long cantidad = 0L;

        try {
            jpql.append("SELECT COUNT(detalle) FROM VentaDetalles detalle ")
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
    public List<VentaDetalles> findAll(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        StringBuilder jpql = new StringBuilder();
        List<VentaDetalles> lista = null;

        try {
            jpql.append("SELECT detalle FROM VentaDetalles detalle ")
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
                    jpql.append(" AND detalle.codigo like :codigo ");
                }

                if (filterField.equals("nombre") && filterValue != null) {
                    jpql.append(" AND detalle.nombre like :nombre ");
                }

                if (filterField.equals("precioCompra") && filterValue != null) {
                    jpql.append(" AND CAST(detalle.precioCompra as String) like :precioCompra ");

                }

                if (filterField.equals("precioVenta") && filterValue != null) {
                    jpql.append(" AND CAST(detalle.precioVenta as String) like :precioVenta ");

                }

                if (filterField.equals("stock") && filterValue != null) {
                    jpql.append(" AND CAST(detalle.stock as String) like :stock ");

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

    public boolean create(VentasTO venta, VentaNueva nueva) {
        HttpSession httpSession = Util.getSession();
        VentaDetalles detalles = new VentaDetalles();
        detalles.setCantidad(venta.getCantidad());
        detalles.setPrecio(venta.getPrecioVenta());
        detalles.setProducto(productosController.findByCodigo(venta.getCodigo()));
        detalles.setUsuarios((Usuarios) httpSession.getAttribute("userSession"));
        detalles.setVentaNueva(nueva);

        return super.create(detalles);
    }

    public List<VentaDetalles> findByVenta(Integer id) {
        StringBuilder jpql = new StringBuilder();
        List<VentaDetalles> lista = null;

        try {
            jpql.append("SELECT detalle FROM VentaDetalles detalle ")
                    .append(" LEFT JOIN FETCH detalle.producto ")
                    .append(" WHERE 1=1 ")
                    .append(" AND detalle.ventaNueva.id = :id");

            Query query = entityManager.createQuery(jpql.toString());
            query.setParameter("id", id);

            lista = query.getResultList();
        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

        return lista;

    }

    public List<VentaDetalles> findByDiaria() {
        StringBuilder jpql = new StringBuilder();
        List<VentaDetalles> lista = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String fechaActual = dateFormat.format(new Date());

        try {
            jpql.append("SELECT detalle FROM VentaDetalles detalle ")
                    .append(" LEFT JOIN FETCH detalle.producto ")
                    .append(" LEFT JOIN FETCH detalle.usuarios ")
                    .append(" LEFT JOIN FETCH detalle.ventaNueva ")
                    .append(" WHERE 1=1 ")
                    .append(" AND detalle.ventaNueva.fechayhora like :fecha");

            Query query = entityManager.createQuery(jpql.toString());
            query.setParameter("fecha", "%" + fechaActual + "%");

            lista = query.getResultList();
        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

        return lista;

    }

    public List<VentasDetalleTO> findByPeriodo(LocalDate fechaD, LocalDate fechaH) {
        List<VentasDetalleTO> lista = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String fechaDesde = fechaD.atStartOfDay().format(formatter);        // 00:00:00
        String fechaHasta = fechaH.atTime(23, 59, 59).format(formatter);    // 23:59:59

        try {
            String sql = "SELECT p.codigo, p.nombre, p.precio_compra, vd.precio, "
                    + "vd.cantidad, u.nombre as nombre_user, u.apellido as apellido_user, "
                    + "vn.fechayhora, vn.id "
                    + "FROM venta_detalles vd "
                    + "JOIN producto p ON vd.producto_id = p.id "
                    + "JOIN usuarios u ON vd.usuario_id = u.id "
                    + "JOIN venta_nueva vn ON vd.venta_id = vn.id "
                    + "WHERE STR_TO_DATE(vn.fechayhora, '%d-%m-%Y %H:%i:%s') "
                    + "BETWEEN STR_TO_DATE(?, '%d-%m-%Y %H:%i:%s') "
                    + "AND STR_TO_DATE(?, '%d-%m-%Y %H:%i:%s')";

            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, fechaDesde);
            stmt.setString(2, fechaHasta);

            rs = stmt.executeQuery();

            while (rs.next()) {
                VentasDetalleTO v = new VentasDetalleTO();
                v.setCodigo(rs.getString("codigo"));
                v.setProductoNombre(rs.getString("nombre"));
                v.setPrecioCompra(rs.getInt("precio_compra"));
                v.setPrecioVenta(rs.getInt("precio"));
                v.setCantidad(rs.getInt("cantidad"));

                String nombreCompleto = rs.getString("nombre_user") + " " + rs.getString("apellido_user");
                v.setUsuarioNombre(nombreCompleto);

                v.setFechaVenta(rs.getString("fechayhora"));
                v.setVentaId(rs.getInt("id"));

                lista.add(v);
            }

        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ignored) {
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ignored) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ignored) {
            }
        }

        return lista;
    }

    public List<VentasDetalleTO> findByPeriodoVentas(Date fechaD, Date fechaH) {
        List<VentasDetalleTO> lista = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        String fechaDesde = dateFormat.format(fechaD);
        String fechaHasta = dateFormat.format(fechaH);

        try {
            String sql = "SELECT p.codigo, p.nombre, p.precio_compra, vd.precio, "
                    + "vd.cantidad, u.nombre as nombre_user, u.apellido as apellido_user, "
                    + "vn.fechayhora, vn.id "
                    + "FROM venta_detalles vd "
                    + "JOIN producto p ON vd.producto_id = p.id "
                    + "JOIN usuarios u ON vd.usuario_id = u.id "
                    + "JOIN venta_nueva vn ON vd.venta_id = vn.id "
                    + "WHERE STR_TO_DATE(vn.fechayhora, '%d-%m-%Y %H:%i:%s') "
                    + "BETWEEN STR_TO_DATE(?, '%d-%m-%Y %H:%i:%s') "
                    + "AND STR_TO_DATE(?, '%d-%m-%Y %H:%i:%s')";

            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, fechaDesde);
            stmt.setString(2, fechaHasta);

            rs = stmt.executeQuery();

            while (rs.next()) {
                VentasDetalleTO v = new VentasDetalleTO();
                v.setCodigo(rs.getString("codigo"));
                v.setProductoNombre(rs.getString("nombre"));
                v.setPrecioCompra(rs.getInt("precio_compra"));
                v.setPrecioVenta(rs.getInt("precio"));
                v.setCantidad(rs.getInt("cantidad"));

                String nombreCompleto = rs.getString("nombre_user") + " " + rs.getString("apellido_user");
                v.setUsuarioNombre(nombreCompleto);

                v.setFechaVenta(rs.getString("fechayhora"));
                v.setVentaId(rs.getInt("id"));

                lista.add(v);
            }

        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ignored) {
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ignored) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ignored) {
            }
        }

        return lista;
    }

    public List<Object[]> obtenerTopProductosVendidos(Date fechaD, Date fechaH) {
        List<Object[]> resultados = null;
        StringBuilder jpql = new StringBuilder();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String fechaDesde = dateFormat.format(fechaD);
        String fechaHasta = dateFormat.format(fechaH);

        try {
            jpql.append("SELECT detalle.producto.codigo, detalle.producto.nombre, SUM(detalle.cantidad) ")
                    .append("FROM VentaDetalles detalle ") // Use the entity name, not the full class path
                    .append(" JOIN detalle.producto ") // Use JOIN instead of LEFT JOIN FETCH
                    .append(" JOIN detalle.ventaNueva venta ")
                    .append(" WHERE venta.fechayhora BETWEEN :fechaDesde AND :fechaHasta ")
                    .append("GROUP BY detalle.producto.nombre ")
                    .append("ORDER BY SUM(detalle.cantidad) DESC");

            // Crear la consulta
            Query query = entityManager.createQuery(jpql.toString());
            query.setParameter("fechaDesde", fechaDesde + " 00:00:00");
            query.setParameter("fechaHasta", fechaHasta + " 23:59:59");
            query.setMaxResults(50); // Limitar a los 10 productos más vendidos

            resultados = query.getResultList();

        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }
        return resultados;
    }

}
