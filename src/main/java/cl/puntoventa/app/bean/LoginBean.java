package cl.puntoventa.app.bean;

import cl.puntoventa.app.clases.Util;

import cl.puntoventa.app.helpers.ConfigManager;
import cl.puntoventa.app.helpers.ContadorSession;
import cl.puntoventa.app.clases.Message;
import cl.puntoventa.app.controller.LoginController;
import cl.puntoventa.app.controller.UserController;
import cl.puntoventa.app.entity.Usuarios;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import static java.util.UUID.randomUUID;
import jakarta.annotation.PostConstruct;
import jakarta.faces.component.UIInput;
import jakarta.faces.component.UISelectOne;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.util.Map;
import static java.util.UUID.randomUUID;
import org.primefaces.PrimeFaces;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Named("loginBean")
@ViewScoped
public class LoginBean implements AppBean, Serializable {

    private final String HOME_PAGE = "/view/mailbox/index?faces-redirect=true";

    private String email;

    private static String TEMP_DIR = System.getProperty("java.io.tmpdir");

    @Inject
    private UserController userController;

    @Inject
    private LoginController loginController;

    @Inject
    private ContadorSession contadorController;

    private Usuarios users;

    private Usuarios userValidado;

    private Usuarios userMaster;

    private List<SelectItem> listaUsers = new ArrayList<>();

    private String password;

    private String confirm_password;

    public Usuarios getUsers() {
        return users;
    }

    public void setUsers(Usuarios users) {
        this.users = users;
    }

    public Usuarios getUserValidado() {
        return userValidado;
    }

    public void setUserValidado(Usuarios userValidado) {
        this.userValidado = userValidado;
    }

    public Usuarios getUserMaster() {
        return userMaster;
    }

    public void setUserMaster(Usuarios userMaster) {
        this.userMaster = userMaster;
    }

    public List<SelectItem> getListaUsers() {
        return listaUsers;
    }

    public void setListaUsers(List<SelectItem> listaUsers) {
        this.listaUsers = listaUsers;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @PostConstruct
    @Override
    public void init() {

        System.out.println(" JSF VERSION : " + FacesContext.class.getPackage().getImplementationVersion());

        this.prepareCreate();
    }

    @Override
    public void listar() {

        this.listaUsers.clear();

        List<Usuarios> lista_user = userController.findAll();

        for (Usuarios user : lista_user) {

            SelectItem itemSelect = new SelectItem(user.getId(), user.getNombre() + " " + user.getApellido());

            this.listaUsers.add(itemSelect);

        }

    }

    @Override
    public void prepareCreate() {

        this.users = new Usuarios();
        this.confirm_password = new String();
        this.password = new String();

        if (Util.getURLReal().equals("http://localhost:8080")) {

            this.users.setEmail("bastianilichp@gmail.com");
            this.users.setPassword("Ilich1991");
        }

    }

    @Override
    public void create() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String login() throws Exception {

        if (this.users.getEmail().isEmpty() || this.users.getEmail().equals("") || this.users.getEmail() == null) {

            return null;
        }

        if (this.users.getPassword().isEmpty() || this.users.getPassword().equals("") || this.users.getPassword() == null) {

            return null;
        }

        return this.loginClassic();

    }

    public String loginClassic() throws Exception {

        boolean redirect = false;

        boolean especial = false;

        userValidado = new Usuarios();

        userValidado = userController.findByUserName(this.users);

        if (userValidado != null) {
            if (BCrypt.checkpw(this.users.getPassword(), userValidado.getPassword())) {
                HttpSession httpSession = Util.getSession();
                httpSession.setAttribute("userSession", userValidado);
                httpSession.setAttribute("autologin", false);
                httpSession.setAttribute("full_name", userValidado.getNombre() + " " + userValidado.getApellido());
                httpSession.setAttribute("ADMIN", userValidado.getManager());
                httpSession.setAttribute("workDir", this.mkWorkDir(userValidado));

                redirect = true;
            } else {
                Util.avisoError("infoMsg", "Contraseña incorrecta");
            }

        } else {
            Util.avisoError("infoMsg", "Usuario o Contraseña incorrecta");
        }
        if (redirect) {
            //se ejecuta en JSGA
            //loginSessionController.updateLoginSession();
            contadorController.incrementAndGet();

            return HOME_PAGE;
        }
        return null;
    }

    public void logout() throws IOException {

//        this.users = null;
//
//        HttpSession httpSession = Util.getSession();
//
//        loginSessionController.deleteBySessionUUID((String) httpSession.getAttribute("session_uuid"));
//
//        httpSession.invalidate();
//
//        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//        externalContext.redirect(externalContext.getRequestContextPath());
        HttpSession httpSession = Util.getSession();

        boolean autologin = (boolean) httpSession.getAttribute("autologin");

        httpSession.invalidate();

        if (autologin) {

            if (Util.getURLReal().equals("http://localhost:8080")) {

                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                externalContext.redirect("http://localhost:8080/punto-venta");

            } else {

                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

                externalContext.redirect(ConfigManager.GetProperty("url_base_sistemas"));

            }

        } else {

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath());

        }

    }

    public void showMessage() {
        Map<String, String> paramMap = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();

        if (!paramMap.isEmpty()) {
            if (paramMap.get("error") != null) {
                boolean error = Boolean.parseBoolean(paramMap.get("error"));
                if (error == true) {
                    Util.avisoError("infoMsg", "Para poder ver este recurso, favor de iniciar sesión");
                }
            }

            if (paramMap.get("logout") != null) {
                boolean logout = Boolean.parseBoolean(paramMap.get("logout"));
                Util.getSession().invalidate();
                if (logout == true) {
                    PrimeFaces.current().executeScript("PF('dialogExpirada').show()");
                    //Util.avisoError("infoMsg", "Se ha cerrado la sesión por inactividad");
                }
            }

        }

    }

    public boolean validarFormMaster(String prefix, Usuarios user) {

        boolean validar = true;

        FacesContext context = FacesContext.getCurrentInstance();

        UISelectOne selectOne_smUser = (UISelectOne) context.getViewRoot().findComponent(":form" + prefix + ":smUser");

        if (user.getId() == null) {

            selectOne_smUser.setValid(false);

            validar = false;

            Util.avisoError("infoMsg", "Usuario : Campo no puede ser VACIO");
        }

        if (validar) {

            Util.ejecutarJavaScript("PF('dialog" + prefix + "').hide();PF('dialogReplyInbox').hide()");

        } else {

            Util.ejecutarJavaScript("var jqDialog = jQuery('#Dlg" + prefix + "');jqDialog.effect('shake', { times:3 }, 100);");

        }

        return validar;

    }

    public String cambioPassword() throws Exception {

        if (userController.cambioPassword(this.password)) {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.getFlash().setKeepMessages(true);

            Util.avisoInfo("infoPasswd", "Cambio de contraseña realizada");

            return HOME_PAGE;

        } else {

            Util.avisoInfo("infoPasswd", Message.updateError());

            return null;
        }

    }

    public String mkWorkDir(Usuarios userValidado) {
        String userName = this.userValidado.getNombre().toLowerCase();
        String workDir = TEMP_DIR + "/" + userName + "_" + randomUUID().toString().substring(0, 18);
        File file = new File(workDir);
        boolean bool = file.mkdir();
        if (bool) {
            System.out.println("Directorio creado con éxito");
        } else {
            System.out.println("No se pudo crear el directorio");
        }

        return workDir;
    }

    public void recuperarContrasena() {
        if (!validarFormRecovery("Recovery")) {
            return;
        }

        if (this.userController.updateAndCheck(email)) {
            Util.avisoInfo("infoMsg", "Su nueva contraseña se ha enviado a su correo");
            PrimeFaces.current().executeScript("PF('dlgRecuperarPassword').hide()");
        } else {
            Util.avisoError("infoMsg", "Ocurrio un error al generar la nueva contraseña");
        }
    }

    private boolean validarFormRecovery(String prefix) {
        boolean validar = true;
        FacesContext context = FacesContext.getCurrentInstance();
        String formName = "form" + prefix;
        String growlId = "infoMsg";

        UIInput input_txtEmail = (UIInput) context.getViewRoot().findComponent(formName + ":txtEmail");

        if (this.email == null || this.email.isEmpty()) {
            input_txtEmail.setValid(false);
            Util.avisoError(growlId, "Email:  Favor de ingresar su correo");
            validar = false;
        }

        return validar;
    }

    public String shutdown() throws IOException {

        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("uniqid", randomUUID().toString().substring(0, 8));
        return "/maintenance?faces-redirect=true";
    }

}
