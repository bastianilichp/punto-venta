package cl.puntoventa.app.clases;

import cl.puntoventa.app.helpers.ConfigManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.awt.Desktop;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.primefaces.PrimeFaces;
import org.primefaces.model.file.UploadedFile;

public class Util {

    public static void avisoInfo(String growlFor, String mensaje) {

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, null);
        FacesContext.getCurrentInstance().addMessage(growlFor, msg);

    }

    public static void avisoError(String growlFor, String mensaje) {

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, null);
        FacesContext.getCurrentInstance().addMessage(growlFor, msg);
    }

    public static void actualizarForm(String id) {
        //RequestContext.getCurrentInstance().update(id);        
        PrimeFaces.current().ajax().update(id);
    }

    public static void resetearFormulario(String id) {
        // RequestContext.getCurrentInstance().reset(id);
        PrimeFaces.current().resetInputs(id);
    }

    public static void ejecutarJavaScript(String comando) {
        //RequestContext.getCurrentInstance().execute(comando);
        PrimeFaces.current().executeScript(comando);
    }

    public static FacesContext getCurrentInstance() {
        return FacesContext.getCurrentInstance();
    }

    public static HttpSession getSession() {

        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

        return httpSession;
    }

    public static String getRealPath() {

        return FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");

    }

    public static String getReferrer() {

        return FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap().get("referer");

    }

    public static String getViewId() {

        return FacesContext.getCurrentInstance().getViewRoot().getViewId();

    }

    public static String getRemoteAddr() {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");

        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        return ipAddress;
    }

    public static String getUserAgent() {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String userAgent = request.getHeader("user-agent");

        return userAgent;
    }

    public static String getMethod() {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        return request.getMethod();

    }

    public static String getURLReal() {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        //return String url =   request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        //http://localhost:8084/divac
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

    }

    public static String getURLRealAll() {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

    }

    public static int getServerPort() {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        //return String url =   request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        //http://localhost:8084/divac
        return request.getServerPort();

    }

    public static String getUploadLocation() {
        return FacesContext.getCurrentInstance().getExternalContext().getInitParameter("upload.location");
    }

    public static boolean validaRut(String rut_value) {

        if (!rut_value.contains("-") || rut_value.length() < 9) {
            return false;
        }

        String[] rut_dv = rut_value.split("-");

        if (rut_dv.length < 2) {
            return false;
        }

        try {

            int rut = Integer.parseInt(rut_dv[0]);
            char dv = rut_dv[1].charAt(0);

            // Validamos que sea un rut valido según la norma
            int m = 0, s = 1;
            for (; rut != 0; rut /= 10) {
                s = (s + rut % 10 * (9 - m++ % 6)) % 11;
            }

            return dv == (char) (s != 0 ? s + 47 : 75);

        } catch (NumberFormatException e) {
            System.out.println("error " + e.getMessage());
            return false;
        }

    }

    public static String leftPad(String originalString, int length, char padCharacter) {

        StringBuilder sb = new StringBuilder();

        while (sb.length() + originalString.length() < length) {
            //while (sb.length() + originalString.length() < 2) {
            sb.append(padCharacter);
        }

        sb.append(originalString);
        String paddedString = sb.toString();

        return paddedString;
    }

    public static String rightPad(String str, int size, char padChar) {

        StringBuilder padded = new StringBuilder(str);

        while (padded.length() < size) {

            padded.append(padChar);
        }

        return padded.toString();
    }

    public static boolean validaEmail(String email_value) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\."
                + "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*"
                + "(\\.[A-Za-z]{2,})$";

        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(EMAIL_PATTERN);

        matcher = pattern.matcher(email_value);

        return matcher.matches();

    }

    public static boolean upload(UploadedFile file) {

        boolean upload = false;

        try (
                OutputStream out = new FileOutputStream(new File("/tmp/" + file.getFileName()))) {
            InputStream in = file.getInputStream();

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            upload = true;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        return upload;
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

//    public static <T> T findBean(String beanName) {
//        FacesContext context = FacesContext.getCurrentInstance();
//        return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
//    }
//
    public static void copyFileUsingStream(File source, File target) throws IOException {

        InputStream is = null;
        OutputStream os = null;

        try {

            is = new FileInputStream(source);
            os = new FileOutputStream(target);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }

        } finally {
            is.close();
            os.close();
        }

    }

    public static Date fechaStringToDate(String fecha) {

        String fechaArray[] = fecha.split("-");

        int day = Integer.parseInt(fechaArray[0]);

        int month = Integer.parseInt(fechaArray[1]);

        int year = Integer.parseInt(fechaArray[2].trim());

        Calendar fechaCalendario = Calendar.getInstance();

        fechaCalendario.set(year, month - 1, day);

        Date fechaFinal = fechaCalendario.getTime();

        return fechaFinal;
    }

    public static boolean jipconfig(String ip) {

        String ip_local;

        boolean siExiste = false;

        try {

            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = iface.getInetAddresses();

                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();

                    // *EDIT*
                    if (addr instanceof Inet6Address) {
                        continue;
                    }

                    ip_local = addr.getHostAddress();

                    System.out.println(iface.getDisplayName() + " " + ip_local);

                    if (ip_local.equals(ip)) {

                        siExiste = true;
                    }

                }
            }

        } catch (SocketException e) {

            throw new RuntimeException(e);
        }

        return siExiste;
    }

    public static List<String> getListIps() {

        List<String> lista = new ArrayList<>();

        String ip;

        try {

            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = iface.getInetAddresses();

                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();

                    // *EDIT*
                    if (addr instanceof Inet6Address) {
                        continue;
                    }

                    ip = addr.getHostAddress();

                    System.out.println(iface.getDisplayName() + " " + ip);

                    lista.add(ip);

                }
            }

        } catch (SocketException e) {

            throw new RuntimeException(e);
        }

        return lista;
    }

    public static boolean pingHost(String host, int port, int timeout) {

        try (Socket socket = new Socket()) {

            socket.connect(new InetSocketAddress(host, port), timeout);

            return true;

        } catch (IOException e) {

            return false; // Either timeout or unreachable or failed DNS lookup.
        }

    }

    public static boolean checkProduction() {

        String project_stage = System.getProperty("project.stage");

        return project_stage.equalsIgnoreCase("production");

    }

    public static boolean checkDocumentServer() {

        if (Util.checkProduction()) {

            //if (Util.pingHost("documentserver.gobiernosantiago.cl", 80, 10000)) {
            if (Util.pingHost(ConfigManager.GetProperty("documentserver.ping"), 80, 10000)) {

                return true;
            }

        } else {

            String documentServer_url = System.getProperty("documentServer.url");

            String documentServer_port = System.getProperty("documentServer.port");

            if (Util.pingHost(documentServer_url, Integer.parseInt(documentServer_port), 10000)) {

                return true;
            }
        }

        return false;
    }

    //if you need rw-rw-rw  permissions 
    public static void setPermission(File file) throws IOException {

        Set<PosixFilePermission> perms = new HashSet<>();

        perms.add(PosixFilePermission.GROUP_READ);
        perms.add(PosixFilePermission.GROUP_WRITE);

        perms.add(PosixFilePermission.OWNER_READ);
        perms.add(PosixFilePermission.OWNER_WRITE);

        perms.add(PosixFilePermission.OTHERS_READ);
        perms.add(PosixFilePermission.OTHERS_WRITE);

        Files.setPosixFilePermissions(file.toPath(), perms);

        //Set<PosixFilePermission> ownerWritable = PosixFilePermissions.fromString("rw-r--r--");
        //FileAttribute<?> permissions = PosixFilePermissions.asFileAttribute(ownerWritable);
        //Files.createFile(path, permissions);
    }

    public static String creacionPeriodo() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String[] fechaHoy = sdf.format(new Date()).split("/");
        int mes = Integer.parseInt(fechaHoy[1]);
        if (mes == 13) {
            mes = 1;
        } else {
            mes += 1;
        }
        String mesF = mes + "";
        if (mesF.length() < 2) {
            mesF = "0" + mesF;
        }
        String newPeriodo = mesF + "-" + fechaHoy[2];

        return newPeriodo;
    }

    public static Integer peticionHttpGet(String session_uuid, String fileName) throws Exception {

        URL obj = new URL("https://jsga.gobiernosantiago.cl/jsga/api/bodega/" + fileName + "/" + session_uuid);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("cache-control", "no-cache");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + obj);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return responseCode;

    }

    public static String inicioMes() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String fecha = sdf.format(calendar.getTime());
        return fecha;
    }

    public static String nuevoPeriodo() {
        Calendar calendar = Calendar.getInstance();

        // Sumamos un mes a la fecha actual
        calendar.add(Calendar.MONTH, 1);

        // Obtenemos la fecha del mes siguiente
        int año = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH) + 1;

        String periodo = mes + "-" + año;

        return periodo;
    }

    public static String rutCero(String rut) {
        String[] rut_dv = rut.split("-");
        String rut_f = rut_dv[0];
        if (rut_f.length() == 7) {
            rut_f = "0" + rut_f + "-" + rut_dv[1];
        } else {
            rut_f = rut;
        }

        return rut_f;

    }

    public static String removeAccents(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }

    public static void imprimirConDialogo(File fileToPdf) throws IOException, PrinterException {
        PDDocument document = PDDocument.load(fileToPdf);
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));

// Verificamos modo gráfico
        if (!GraphicsEnvironment.isHeadless()) {
            System.out.println("🖨️ Mostrando diálogo de impresión...");
            if (job.printDialog()) {
                job.print();
                System.out.println("✅ Impresión completada.");
            } else {
                System.out.println("❌ Impresión cancelada por el usuario.");
            }
        } else {
            System.out.println("⚠️ Modo headless detectado. Imprimiendo directamente.");
            job.print();
        }

        document.close();

    }

    public static void imprimirPdfDirectamente(File fileToPdf) {
        try (PDDocument document = PDDocument.load(fileToPdf)) {

            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));

            // Imprimir directamente (sin diálogo)
            job.print();

            System.out.println("✅ PDF enviado a la impresora predeterminada.");

        } catch (Exception e) {
            System.err.println("❌ Error al imprimir el PDF directamente:");
            e.printStackTrace();
        }
    }

    public static void imprimirEnImpresoraEspecifica(File fileToPdf, String nombreImpresora) {
        try (PDDocument document = PDDocument.load(fileToPdf)) {

            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));

            // Buscar la impresora por nombre
            PrintService[] servicios = PrinterJob.lookupPrintServices();
            boolean encontrada = false;

            for (PrintService service : servicios) {
                if (service.getName().equalsIgnoreCase(nombreImpresora)) {
                    job.setPrintService(service);
                    encontrada = true;
                    break;
                }
            }

            if (!encontrada) {
                System.err.println("❌ Impresora '" + nombreImpresora + "' no encontrada.");
                System.out.println("🖨️ Impresoras disponibles:");
                for (PrintService service : servicios) {
                    System.out.println(" - " + service.getName());
                }
                return;
            }

            job.print();
            System.out.println("✅ PDF enviado a la impresora '" + nombreImpresora + "'.");

        } catch (Exception e) {
            System.err.println("❌ Error al imprimir en impresora específica:");
            e.printStackTrace();
        }
    }

    public static void listarImpresorasDisponibles() {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

        if (services.length == 0) {
            System.out.println("❌ No se encontraron impresoras disponibles.");
        } else {
            System.out.println("🖨️ Impresoras disponibles:");
            for (PrintService service : services) {
                System.out.println(" - " + service.getName());
            }
        }
    }

}
