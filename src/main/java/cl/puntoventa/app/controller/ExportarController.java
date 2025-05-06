package cl.puntoventa.app.controller;

import cl.puntoventa.app.entity.Producto;
import cl.puntoventa.app.entity.Usuarios;
import jakarta.ejb.Stateless;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;

@Stateless
public class ExportarController {

    @Inject
    private ProductosController productoController;

    @Inject
    private UserController usuarioController;

    @Inject
    private EmailController emailController;

    public void descargarDetalleVenta(String fileName) throws IOException {

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        externalContext.redirect(externalContext.getRequestContextPath() + "/detalle?file=" + fileName);

    }

    public void descargarDetalleOrden() throws IOException {

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        externalContext.redirect(externalContext.getRequestContextPath() + "/orden");

    }

    public void exportarSinStock() throws InterruptedException, IOException {

        //List<PostUser> lista = postUserController.findAllEmailAudit();
        List<Usuarios> lista = usuarioController.findAll();
        List<Producto> listaProyecto = productoController.findAll();

        File file = exportarProyectosUtem(listaProyecto);

        if (file.exists() && !file.isDirectory()) {

            for (Usuarios postUser : lista) {

                emailController.sendNuevoUser(postUser,"");

            }
        }

    }

    private File exportarProyectosUtem(List<Producto> lista) throws InterruptedException, FileNotFoundException, IOException {

        DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

        String destination = System.getProperty("java.io.tmpdir") + "/";

        String nombre = "proyectos-" + fecha.format(new Date()) + ".xls";

        FileOutputStream fileOut = null;

        try {

            fileOut = new FileOutputStream(destination + nombre);

        } catch (FileNotFoundException ex) {

        }

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet worksheet = workbook.createSheet("ProyectosUtem");

        HSSFSheet sheet = workbook.getSheet("ProyectosUtem");

        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();

        //font.setFontHeightInPoints((short) 11);
        style.setFillForegroundColor(IndexedColors.AQUA.index);

        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(IndexedColors.BLACK.index);

        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());

        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());

        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());

        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        style.setVerticalAlignment(VerticalAlignment.CENTER);

        style.setFont(font);

        CellStyle style2 = workbook.createCellStyle();

        //font.setFontHeightInPoints((short) 11);
        style2.setFillForegroundColor(IndexedColors.ORANGE.index);

        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(IndexedColors.BLACK.index);

        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBottomBorderColor(IndexedColors.BLACK.getIndex());

        style2.setBorderLeft(BorderStyle.THIN);
        style2.setLeftBorderColor(IndexedColors.BLACK.getIndex());

        style2.setBorderRight(BorderStyle.THIN);
        style2.setRightBorderColor(IndexedColors.BLACK.getIndex());

        style2.setBorderTop(BorderStyle.THIN);
        style2.setTopBorderColor(IndexedColors.BLACK.getIndex());

        style2.setVerticalAlignment(VerticalAlignment.CENTER);

        style2.setFont(font);

        //
        /* Get access to HSSFCellStyle */
        HSSFCellStyle my_style_0 = workbook.createCellStyle();
        HSSFCellStyle my_style_1 = workbook.createCellStyle();
        HSSFCellStyle my_style_2 = workbook.createCellStyle();
        HSSFCellStyle my_style_3 = workbook.createCellStyle();
        HSSFCellStyle my_style_4 = workbook.createCellStyle();

        HSSFCellStyle my_style_5 = workbook.createCellStyle();

        HSSFCellStyle my_style_6 = workbook.createCellStyle();

        HSSFCellStyle my_style_7 = workbook.createCellStyle();

        HSSFCellStyle my_style_8 = workbook.createCellStyle();


        /* Top Left alignment */
 /* Left aligned horizontally */
        my_style_0.setAlignment(HorizontalAlignment.LEFT);
        /* top aligned vertically */
        my_style_0.setVerticalAlignment(VerticalAlignment.TOP);

        /* Center Align Cell Contents */
        my_style_1.setAlignment(HorizontalAlignment.CENTER);
        my_style_1.setVerticalAlignment(VerticalAlignment.CENTER);

        /* Bottom Right alignment */
        my_style_2.setAlignment(HorizontalAlignment.RIGHT);
        my_style_2.setDataFormat((short) 5);   //con signo $ y separador de miles

        //my_style_2.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);

        /* Justified Alignment */
        my_style_3.setAlignment(HorizontalAlignment.JUSTIFY);
        my_style_3.setVerticalAlignment(VerticalAlignment.JUSTIFY);
        my_style_3.setWrapText(true);

        /* Bottom Right alignment */
        my_style_4.setAlignment(HorizontalAlignment.RIGHT);
        my_style_4.setDataFormat((short) 3);   //solo separador de miles

        /* Bottom Right alignment */
        CreationHelper createHelper = workbook.getCreationHelper();
        my_style_5.setDataFormat(createHelper.createDataFormat().getFormat("dd-mm-yyyy HH:mm"));
        my_style_5.setAlignment(HorizontalAlignment.RIGHT);


        /* Bottom Right alignment */
        my_style_6.setAlignment(HorizontalAlignment.RIGHT);
        my_style_6.setDataFormat((short) 0);   //sin separadore de miles formato general

        /* Justified Alignment */
        my_style_7.setAlignment(HorizontalAlignment.LEFT);
        my_style_7.setWrapText(true);

        /* ALIGN_RIGHT */
        my_style_8.setAlignment(HorizontalAlignment.RIGHT);

        //titulo
        HSSFRow titulo = worksheet.createRow((short) 0);
        titulo.createCell(0).setCellValue("Gobierno Regional Metropolitano");

        titulo.setHeight((short) 500);

        titulo.getCell(0).setCellStyle(style);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));

        //CellUtil.setCellStyleProperty(titulo.getCell(0), workbook, CellUtil.ALIGNMENT);
        CellUtil.setCellStyleProperty(titulo.getCell(0), CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

        // create header row
        HSSFRow header = worksheet.createRow(3);

        header.createCell(0).setCellValue("Proyectos - [ Periodo : " +" ]");

        header.getCell(0).setCellStyle(style2);

        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 2));

        CellUtil.setCellStyleProperty(header.getCell(0), CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

        header = worksheet.createRow(4);

        header.createCell(0).setCellValue("Codigo");
        header.getCell(0).setCellStyle(style);

        header.createCell(1).setCellValue("Nombre");
        header.getCell(1).setCellStyle(style);

        header.createCell(2).setCellValue("Tipo Institución");
        header.getCell(2).setCellStyle(style);

        header.createCell(3).setCellValue("Institución");
        header.getCell(3).setCellStyle(style);

        int fila = 5;

        StringBuilder comunas = new StringBuilder();

        StringBuilder provincias = new StringBuilder();

        StringBuilder tipodeBeneficiario = new StringBuilder();

        StringBuilder estrategiaRegional = new StringBuilder();

        StringBuilder estrategiaSantiago = new StringBuilder();

        //List<PostProyecto> lista = proyectoController.findAll(finashed);
        System.out.println("lista " + lista.size());
        int contador = 1;

        for (Producto producto : lista) {

            contador++;

            HSSFRow row = worksheet.createRow(fila);

            row.createCell(0).setCellValue(producto.getCodigo());

            row.createCell(1).setCellValue(producto.getNombre());

            row.createCell(2).setCellValue(producto.getStock());

        }

        fila++;

        worksheet.setAutoFilter(CellRangeAddress.valueOf("A5:D5"));
        worksheet.createFreezePane(0, 5);

        for (int i = 0; i < 4; i++) {

            worksheet.autoSizeColumn(i);

        }

       
        try {

            workbook.write(fileOut);

            fileOut.flush();

            fileOut.close();

        } catch (IOException ex) {

        }

        return new File(destination + nombre);

    }

}
