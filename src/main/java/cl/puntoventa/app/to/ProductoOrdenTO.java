package cl.puntoventa.app.to;

import java.util.Date;

public class ProductoOrdenTO {

    private String codigo;
    private String nombre;
    private Integer cantidad;
    private Integer unitario;
    private Integer total;
    private Date fecha;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getUnitario() {
        return unitario;
    }

    public void setUnitario(Integer unitario) {
        this.unitario = unitario;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
