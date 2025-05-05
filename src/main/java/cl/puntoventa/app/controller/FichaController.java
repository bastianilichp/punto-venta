package cl.puntoventa.app.controller;

import cl.puntoventa.app.clases.Util;
import cl.puntoventa.app.entity.DetallesCompra;
import cl.puntoventa.app.entity.OrdenCompra;
import cl.puntoventa.app.entity.VentaDetalles;
import cl.puntoventa.app.entity.VentaNueva;
import cl.puntoventa.app.to.ProductoOrdenTO;
import cl.puntoventa.app.to.VentasTO;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.core.office.OfficeUtils;
import org.jodconverter.local.JodConverter;
import org.jodconverter.local.office.LocalOfficeManager;
import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.TableVariable;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variable;
import pl.jsolve.templ4docx.variable.Variables;

@Stateless
public class FichaController {

    @Inject
    private HttpSession httpSession;

    public File imprimirDetalleVenta(List<VentaDetalles> ventasTO, VentaNueva nueva) {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("ddMMyyyyHHmm");
        String fechaNombre = format.format(date);

        String nombreFinal = "detalle_venta-" + fechaNombre + ".docx";

        File fileOrigen = new File(httpSession.getServletContext().getRealPath("/") + "resources/templates/detalle_venta.docx");

        if (fileOrigen.exists() && !fileOrigen.isDirectory()) {

            DateFormat fecha = new SimpleDateFormat("dd MMM yyyy");
            String fechaActual = fecha.format(date);

            NumberFormat totalFormat = NumberFormat.getNumberInstance(new Locale("es", "CL"));
            Docx docx = null;
            docx = new Docx(fileOrigen.getPath());

            docx.setVariablePattern(new VariablePattern("${", "}"));
            Variables variables = new Variables();

            //0.- Cabezera
            variables.addTextVariable(new TextVariable("${NVENTA}", nueva.getId().toString()));
            variables.addTextVariable(new TextVariable("${FECHAEMISION}", fechaActual));
            variables.addTextVariable(new TextVariable("${CAJERO}", nueva.getUsuarios().getNombre() + " " + nueva.getUsuarios().getApellido()));

            TableVariable tableProductos = new TableVariable();

            List<Variable> fi1 = new ArrayList<>();
            List<Variable> fi2 = new ArrayList<>();
            List<Variable> fi3 = new ArrayList<>();
            List<Variable> fi4 = new ArrayList<>();

            if (ventasTO.isEmpty()) {
                fi1.add(new TextVariable("${PRODUCTO}", ""));
                fi2.add(new TextVariable("${CANTIDAD}", ""));
                fi3.add(new TextVariable("${VALOR}", ""));
                fi4.add(new TextVariable("${UNITARIO}", ""));

            } else {
                for (VentaDetalles v : ventasTO) {
                    Integer valor = v.getPrecio() * v.getCantidad();

                    fi1.add(new TextVariable("${PRODUCTO}", v.getProducto().getNombre()));
                    fi2.add(new TextVariable("${CANTIDAD}", v.getCantidad().toString()));
                    fi3.add(new TextVariable("${VALOR}", "$ " + totalFormat.format(valor)));
                    fi4.add(new TextVariable("${UNITARIO}", "$ " + totalFormat.format(v.getPrecio())));
                }

            }

            tableProductos.addVariable(fi1);
            tableProductos.addVariable(fi2);
            tableProductos.addVariable(fi3);
            tableProductos.addVariable(fi4);
            variables.addTableVariable(tableProductos);

            variables.addTextVariable(new TextVariable("${DESCUENTO}", "$ " + totalFormat.format(nueva.getDescuento())));
            variables.addTextVariable(new TextVariable("${SUBTOTAL}", "$ " + totalFormat.format(nueva.getSubtotal())));
            variables.addTextVariable(new TextVariable("${TOTAL}", "$ " + totalFormat.format(nueva.getTotal())));

            docx.fillTemplate(variables);
            File file;
            docx.save(System.getProperty("java.io.tmpdir") + "/" + nombreFinal);

            file = new File(System.getProperty("java.io.tmpdir") + "/" + nombreFinal);

            if (file.exists() && !file.isDirectory()) {

                return file;
            }

        }

        return null;
    }

    public File imprimirOrdenCompra(List<DetallesCompra> detalle, OrdenCompra orden) {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("ddMMyyyyHHmm");
        String fechaNombre = format.format(date);

        String nombreFinal = "orden_compra-" + fechaNombre + ".docx";

        File fileOrigen = new File(httpSession.getServletContext().getRealPath("/") + "resources/templates/orden_compra.docx");

        if (fileOrigen.exists() && !fileOrigen.isDirectory()) {

            DateFormat fecha = new SimpleDateFormat("dd MMM yyyy");
            String fechaActual = fecha.format(date);

            NumberFormat totalFormat = NumberFormat.getNumberInstance(new Locale("es", "CL"));
            Docx docx = null;
            docx = new Docx(fileOrigen.getPath());

            docx.setVariablePattern(new VariablePattern("${", "}"));
            Variables variables = new Variables();

            //0.- Cabezera
            variables.addTextVariable(new TextVariable("${FECHA}", fechaActual));
            variables.addTextVariable(new TextVariable("${NROORDEN}", orden.getId().toString()));
            variables.addTextVariable(new TextVariable("${NPROVEEDOR}", orden.getProveedores().getNombre()));
            variables.addTextVariable(new TextVariable("${RUTPROVEEDOR}", orden.getProveedores().getRut()));

            TableVariable tableProductos = new TableVariable();

            List<Variable> fi1 = new ArrayList<>();
            List<Variable> fi2 = new ArrayList<>();
            List<Variable> fi3 = new ArrayList<>();
            List<Variable> fi4 = new ArrayList<>();
            List<Variable> fi5 = new ArrayList<>();

            if (detalle.isEmpty()) {
                fi1.add(new TextVariable("${CODIGO}", ""));
                fi2.add(new TextVariable("${DESCRIPCION}", ""));
                fi3.add(new TextVariable("${CANTIDAD}", ""));
                fi4.add(new TextVariable("${UNITARIO}", ""));
                fi5.add(new TextVariable("${TOTAL}", ""));

            } else {
                for (DetallesCompra v : detalle) {

                    fi1.add(new TextVariable("${CODIGO}", v.getCodigoProducto()));
                    fi2.add(new TextVariable("${DESCRIPCION}", v.getNombreProducto()));
                    fi3.add(new TextVariable("${CANTIDAD}", "$ " + totalFormat.format(v.getCantidad())));
                    fi4.add(new TextVariable("${UNITARIO}", "$ " + totalFormat.format(v.getUnitario())));
                    fi5.add(new TextVariable("${TOTAL}", "$ " + totalFormat.format(v.getTotal())));
                }

            }

            tableProductos.addVariable(fi1);
            tableProductos.addVariable(fi2);
            tableProductos.addVariable(fi3);
            tableProductos.addVariable(fi4);
            tableProductos.addVariable(fi5);
            variables.addTableVariable(tableProductos);

            variables.addTextVariable(new TextVariable("${MONTOTOTAL}", "$ " + totalFormat.format(orden.getMontoTotal())));

            docx.fillTemplate(variables);
            File file;
            docx.save(System.getProperty("java.io.tmpdir") + "/" + nombreFinal);

            file = new File(System.getProperty("java.io.tmpdir") + "/" + nombreFinal);

            if (file.exists() && !file.isDirectory()) {

                return file;
            }

        }

        return null;
    }

    public File libreOfficeToPdf(File inputFile, boolean tmpDir) throws OfficeException {
        String file = inputFile.getName();
        int ext = file.lastIndexOf(".");
        File outputFile;

        if (tmpDir) {
            outputFile = new File((String) httpSession.getAttribute("workDir") + "/" + file.substring(0, ext) + ".pdf");
        } else {
            outputFile = new File(Util.getUploadLocation() + file.substring(0, ext) + ".pdf");
        }

        final LocalOfficeManager officeManager = LocalOfficeManager.install();

        try {
            officeManager.start();

            JodConverter.convert(inputFile).to(outputFile).execute();

        } finally {
            OfficeUtils.stopQuietly(officeManager);
        }

        return outputFile;
    }

}
