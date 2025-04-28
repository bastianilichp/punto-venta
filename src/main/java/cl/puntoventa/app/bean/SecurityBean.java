package cl.puntoventa.app.bean;


import cl.puntoventa.app.clases.Util;
import cl.puntoventa.app.helpers.ContadorSession;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;

@Named("securityBean")
@ViewScoped
public class SecurityBean implements Serializable {
    
    private final String HOME = "/view/mailbox/index.hsm";

    @Inject
    private HttpSession httpSession;

    @Inject
    private ContadorSession contadorSession;
 
    public void checkSession() throws IOException {

        if (httpSession.getAttribute("userSession") == null) {

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath());

        } else if (httpSession.getAttribute("userSession") != null) {

            if (Util.getReferrer() == null) {

                if (httpSession.getAttribute("HOME_PAGE_REDIRECT") == null) {

                    httpSession.setAttribute("HOME_PAGE_REDIRECT", true);

                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    externalContext.redirect(externalContext.getRequestContextPath() + HOME);

                } else {

                    httpSession.removeAttribute("HOME_PAGE_REDIRECT");

                }

            } // end if ( Util.getReferrer() == null) {

        }

    }

    public String checkBrowser() {

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String userAgent = externalContext.getRequestHeaderMap().get("User-Agent");

        if (userAgent.contains("Chrome")) {

            return null;
        }

        if (userAgent.contains("MSIE")) {
            try {
                externalContext.redirect(externalContext.getRequestContextPath() + "/message.hsm");
            } catch (IOException ex) {
                
            }
        }

        if (userAgent.contains("Edge")) {
            try {
                externalContext.redirect(externalContext.getRequestContextPath() + "/message.hsm");
            } catch (IOException ex) {
                
            }
        }

        if (userAgent.contains("Firefox")) {
            try {
                externalContext.redirect(externalContext.getRequestContextPath() + "/message.hsm");
            } catch (IOException ex) {
                
            }

        }

        if (userAgent.contains("Opera")) {
            try {
                externalContext.redirect(externalContext.getRequestContextPath() + "/message.hsm");
            } catch (IOException ex) {
                
            }
        }

        if (userAgent.contains("Safari")) {
            try {
                externalContext.redirect(externalContext.getRequestContextPath() + "/message.hsm");
            } catch (IOException ex) {
                
            }
        }

        return null;
    }

    public int getActiveSessions() {
        return contadorSession.getActiveSessions().get();
    }

    
}
