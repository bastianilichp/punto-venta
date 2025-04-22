/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cl.puntoventa.app.controller;

import static cl.puntoventa.app.clases.GenerateSecurePassword.generatePassword;
import cl.puntoventa.app.clases.SendMail;
import cl.puntoventa.app.clases.Util;
import cl.puntoventa.app.dao.AbstractDaoImpl;
import cl.puntoventa.app.entity.Usuarios;
import jakarta.ejb.Stateless;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Stateless
public class UserController extends AbstractDaoImpl<Usuarios> {

    @Inject
    private SendMail sendMail;

    public UserController() {
        super(Usuarios.class);
    }

    @Override
    public List<Usuarios> findAll() {

        List<Usuarios> user = null;
        StringBuilder jpql = new StringBuilder();

        try {
            jpql.append("SELECT usuario FROM Usuarios usuario ");
            jpql.append(" WHERE 1=1 ");
            jpql.append(" AND usuario.enabled = true");

            Query query = entityManager.createQuery(jpql.toString());
            user = query.getResultList();

        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

        return user;

    }

    public List<Usuarios> findAllModificacion() {

        List<Usuarios> user = null;
        StringBuilder jpql = new StringBuilder();

        try {
            jpql.append("SELECT usuario FROM Usuarios usuario ");
            jpql.append(" WHERE 1=1 ");            

            Query query = entityManager.createQuery(jpql.toString());
            user = query.getResultList();

        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

        return user;

    }

    @Override
    public List<Usuarios> findAll(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Long count(Map<String, FilterMeta> filterBy) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Usuarios findByUserName(Usuarios users) {
        Usuarios user = null;
        StringBuilder jpql = new StringBuilder();

        try {
            jpql.append("SELECT usuario FROM Usuarios usuario ");
            jpql.append(" WHERE 1=1 ");
            jpql.append(" AND usuario.email = :email ");
            jpql.append(" AND usuario.enabled = true");

            Query query = entityManager.createQuery(jpql.toString());
            query.setParameter("email", users.getEmail());
            query.setMaxResults(1);

            user = (Usuarios) query.getSingleResult();

        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

        return user;
    }

    public Usuarios findByEmailUpdate(String email) {
        StringBuilder jpql = new StringBuilder();
        Usuarios user = null;

        try {
            jpql.append("SELECT user FROM Usuarios user ")
                    .append(" WHERE 1=1 ")
                    .append(" AND user.email = :email ")
                    .append(" AND usuario.enabled = true");

            Query query = entityManager.createQuery(jpql.toString())
                    .setParameter("email", email.trim())
                    .setMaxResults(1);

            user = (Usuarios) query.getSingleResult();
        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

        return user;
    }

    @Override
    public boolean create(Usuarios userNuevo) {
        if (!this.validarForm(userNuevo)) {
            return false;
        }
        userNuevo.setPassword(BCrypt.hashpw(userNuevo.getPassword(), BCrypt.gensalt()));
        userNuevo.setEmail(userNuevo.getEmail().toLowerCase());
        userNuevo.setEnabled(true);

        return super.create(userNuevo);
    }

    public boolean validarForm(Usuarios users) {
        boolean valido = true;

        if (users.getNombre().isBlank()) {
            valido = false;
            Util.avisoError("infoMsg", "Nombre : Campo no puede ser VACIO");
        }
        if (users.getApellido().isBlank()) {
            valido = false;
            Util.avisoError("infoMsg", "Apellido : Campo no puede ser VACIO");
        }

        if (users.getPassword().isBlank()) {
            valido = false;
            Util.avisoError("infoMsg", "Poassword : Campo no puede ser VACIO");
        }

        if (users.getEmail().isBlank()) {
            valido = false;
            Util.avisoError("infoMsg", "Email : Campo no puede ser VACIO");
        } else {
            Usuarios existe = findByEmail(users);
            if (existe != null) {
                valido = false;
                Util.avisoError("infoMsg", "Usuario ya existe");
            }

        }

        return valido;

    }

    public boolean updateAndCheck(String email) {

        Usuarios user = this.findByEmailUpdate(email);

        if (user != null) {
            String password = generatePassword(7);

            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            // this.sendMail(user, password, "update");

            return this.update(user);
        }

        return false;
    }

    public Usuarios findByEmail(Usuarios users) {
        Usuarios user = null;
        StringBuilder jpql = new StringBuilder();

        try {
            jpql.append("SELECT usuario FROM Usuarios usuario ");
            jpql.append(" WHERE 1=1 ");
            jpql.append(" AND usuario.email = :email ");

            Query query = entityManager.createQuery(jpql.toString());
            query.setParameter("email", users.getEmail().trim());

            query.setMaxResults(1);

            user = (Usuarios) query.getSingleResult();

        } catch (Exception ex) {
            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
        }

        return user;
    }

    public boolean cambioPassword(String password) {

        HttpSession httpSession = (HttpSession) Util.getSession();

        Usuarios user = (Usuarios) httpSession.getAttribute("userSession");

        Date hoy = new Date();

        if (this.validarFormPass("Password")) {

            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));

            if (super.update(user)) {

            }
        }

        return false;

    }

    public boolean validarFormPass(String prefix) {

        boolean validar = true;

        boolean largo_password = false;
        boolean largo_confirm_password = false;

        FacesContext context = FacesContext.getCurrentInstance();

        UIInput input_password = (UIInput) context.getViewRoot().findComponent(":formPassword:password");

        if (input_password.getValue().equals("") || input_password.getValue() == null) {
            input_password.setValid(false);
            validar = false;

        } else {

            String password = (String) input_password.getValue();
            if (password.length() < 7) {
                Util.avisoError("infoPasswd", "Contraseña debe tener un minimo de 7 caracteres");
                input_password.setValid(false);
                validar = false;
            } else {
                largo_password = true;
            }

        }

        UIInput input_confirm_password = (UIInput) context.getViewRoot().findComponent(":formPassword:confirm_password");

        if (input_confirm_password.getValue().equals("") || input_confirm_password.getValue() == null) {
            input_confirm_password.setValid(false);
            validar = false;

        } else {

            String confirm_password = (String) input_confirm_password.getValue();
            if (confirm_password.length() < 7) {
                Util.avisoError("infoPasswd", "Confirma Contraseña debe tener un minimo de 7 caracteres");
                input_confirm_password.setValid(false);
                validar = false;
            } else {

                largo_confirm_password = true;

            }
        }

        if (largo_password && largo_confirm_password) {

            if (!input_password.getValue().equals(input_confirm_password.getValue())) {

                Util.avisoError("infoPasswd", "Contraseña y Confirma Contraseña no coinciden");
                input_password.setValid(false);
                input_confirm_password.setValid(false);
                validar = false;
            }
        }

        if (validar) {

            Util.ejecutarJavaScript("PF('dialog" + prefix + "').hide()");

        } else {

            Util.ejecutarJavaScript("var jqDialog = jQuery('#DlgPassword');jqDialog.effect('shake', { times:3 }, 100);");
        }

        return validar;
    }

//    private void sendMail(Usuarios user, String password, String accion) {
//
//        StringBuilder body;
//
//        body = new StringBuilder();
//
//        body.append("<html><body> ")
//                .append("	<style> ")
//                .append("		* { ")
//                .append("			font-family: Arial, Helvetica, sans-serif; ")
//                .append("			font-size: 12px ")
//                .append("		} ")
//                .append("		html, ")
//                .append("		body { ")
//                .append("			width: 100%; ")
//                .append("			padding: 0; ")
//                .append("			margin: 0; ")
//                .append("		} ")
//                .append("	</style> ")
//                .append("	<div style='width: 100%; padding: 50px;'> ")
//                .append("		<div> ")
//                .append("			<h2 style='font-size: 16px;'>Estimad@ ${USER_NAME}</h2> ")
//                .append("			<p style='font-size: 14px;display: block;font-weight: normal'> ")
//                .append("				Se ha generado una nueva contraseña para su cuenta, los datos a continuación: ")
//                .append("			</p> ")
//                .append("			<p style='font-size: 12px;display: block;font-weight: bold'> ")
//                .append("				Nombre de usuario : ${USER} ")
//                .append("			</p> ")
//                .append("			<p style='font-size: 12px;display: block;font-weight: bold'> ")
//                .append("				Contraseña : ${PASSWORD} ")
//                .append("			</p> ")
//                .append("		</div> ")
//                .append("		<div style='margin-top:40px; > ")
//                .append("			<p style='font-size: 10px;font-weight: normal'> ")
//                .append("				Este correo fue enviado por el Sistema de CORESOC del Gobierno Regional ")
//                .append("				Metropolitano de Santiago ")
//                .append("				<hr> ")
//                .append("				Gobierno Regional Metropolitano ")
//                .append("                               <br> ")
//                .append("				Bandera 46 - Santiago - Chile ")
//                .append("			</p> ")
//                .append("                       <img style='margin-top: 10px;width: 150px;height: auto;' src=\"cid:image\" /> ")
//                .append("		</div> ")
//                .append("	</div> ")
//                .append("</html></body>");
//
//        String cuerpoFinal = body.toString().replace("${USER_NAME}", user.getNombre())
//                .replace("${USER}", user.getEmail()).replace("${PASSWORD}", password);
//
//        sendMail = new SendMail();
//
//        switch (accion) {
//
//            case "create" ->
//                sendMail.send(user.getEmail(), "Creación de cuenta Proceso electoral CORESOC 2025-2027", cuerpoFinal);
//
//            case "update" ->
//                sendMail.send(user.getEmail(), "Recuperacion de cuenta Proceso electoral CORESOC 2025-2027", cuerpoFinal);
//        }
//
//    }
}
