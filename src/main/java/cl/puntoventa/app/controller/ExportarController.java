package cl.puntoventa.app.controller;

import jakarta.ejb.Stateless;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import java.io.IOException;

@Stateless
public class ExportarController {

    public void imprimirActaMensual(String file) throws IOException {

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        externalContext.redirect(externalContext.getRequestContextPath() + "/documento?file=" + file);

    }

    public void descargarActaExcel() throws IOException {

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        externalContext.redirect(externalContext.getRequestContextPath() + "/actasExcel");

    }
}
