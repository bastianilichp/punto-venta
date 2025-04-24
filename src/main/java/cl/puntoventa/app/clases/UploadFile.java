package cl.puntoventa.app.clases;


import jakarta.faces.context.FacesContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import static java.util.UUID.randomUUID;
import java.util.logging.Logger;

import org.primefaces.model.file.UploadedFile;

public class UploadFile {

    private static final Logger LOG = Logger.getLogger(UploadFile.class.getName());

    public static String[] upload(UploadedFile file, String tipo) throws IOException {

        String[] data_array = null;

        //String destination = Util.getRealPath() + "/resources/upload/";
        String destination = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("upload.location");
        System.out.println(destination);
        String name_original = file.getFileName();

        //2018-06-01
        int ext = file.getFileName().lastIndexOf(".");
        UUID name_new = randomUUID();
        String name_destino = name_new + file.getFileName().substring(ext);

        File archivo;

        switch (tipo) {

            case "/(\\.|\\/)(pdf)$/i":

                archivo = new File(destination + name_destino);

                try ( // write the inputStream to a FileOutputStream

                        OutputStream out = new FileOutputStream(archivo)) {
                    //OutputStream out = new FileOutputStream(new File(destination + name_destino))) {
                    InputStream in = file.getInputStream();

                    int read = 0;
                    byte[] bytes = new byte[1024];

                    while ((read = in.read(bytes)) != -1) {
                        out.write(bytes, 0, read);
                    }

                    in.close();
                    out.flush();
                    out.close();

                } catch (IOException ex) {

                    System.err.println("An IOException was caught!" + ex.getMessage());
                    ex.printStackTrace();

                }
                break;

        }

        //Runtime.getRuntime().exec("chmod 775 "+  destination + name_destino);
        data_array = new String[2];

        data_array[0] = name_destino;
        data_array[1] = destination + name_destino;

        return data_array;
    }

    public static String[] uploadActa(File file) throws IOException {

        String[] data_array = null;
        String destination = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("upload.location");

        //int ext = file.getName().lastIndexOf(".");
        String name_destino = file.getName();

        try ( // write the inputStream to a FileOutputStream                       
                OutputStream out = new FileOutputStream(new File(destination + name_destino))) {
            FileInputStream in = new FileInputStream(file);

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

        } catch (IOException ex) {

            System.err.println("An IOException was caught!" + ex.getMessage());
            ex.printStackTrace();

        }

        //Runtime.getRuntime().exec("chmod 775 "+  destination + name_destino);
        data_array = new String[2];

        data_array[0] = name_destino;
        data_array[1] = destination + name_destino;

        return data_array;
    }



}
