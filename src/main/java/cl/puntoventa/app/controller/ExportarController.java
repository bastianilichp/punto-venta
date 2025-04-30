package cl.puntoventa.app.controller;

import jakarta.ejb.Stateless;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import java.io.File;
import java.io.IOException;

@Stateless
public class ExportarController {

    public void descargarDetalleVenta(String fileName) throws IOException {

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        externalContext.redirect(externalContext.getRequestContextPath() + "/detalle?file=" + fileName);

    }

}
