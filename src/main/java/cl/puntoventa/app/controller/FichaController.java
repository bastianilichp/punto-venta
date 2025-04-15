package cl.puntoventa.app.controller;


import cl.puntoventa.app.clases.Util;

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

//    public File imprimirActaMensual(GenerarActaTO actaTO) {
//        Date date = new Date();
//        DateFormat format = new SimpleDateFormat("ddMMyyyyHHmm");
//        String fechaNombre = format.format(date);
//
//        String nombreFinal = Util.removeAccents(actaTO.getTipoActa().toLowerCase()) + "-" + actaTO.getRut() + "_" + fechaNombre + ".docx";
//        File fileOrigen = new File(httpSession.getServletContext().getRealPath("/") + "resources/templates/acta_componentes.docx");
//
//        if (fileOrigen.exists() && !fileOrigen.isDirectory()) {
//
//            DateFormat fecha = new SimpleDateFormat("dd MMM yyyy");
//            String fechaActual = fecha.format(date);
//
//            NumberFormat totalFormat = NumberFormat.getNumberInstance(new Locale("es", "CL"));
//            Docx docx = null;
//            docx = new Docx(fileOrigen.getPath());
//
//            docx.setVariablePattern(new VariablePattern("${", "}"));
//            Variables variables = new Variables();
//
//            //0.- Cabezera
//            variables.addTextVariable(new TextVariable("${TIPOTITULO}", actaTO.getTipoActa().toUpperCase()));
//            variables.addTextVariable(new TextVariable("${TIPO}", actaTO.getTipoActa()));
//            variables.addTextVariable(new TextVariable("${FECHA}", fechaActual));
//            variables.addTextVariable(new TextVariable("${NUMERO}", actaTO.getCorrelativo() + ""));
//
//            TableVariable tableComponente = new TableVariable();
//
//            List<Variable> fi1 = new ArrayList<>();
//            List<Variable> fi2 = new ArrayList<>();
//            List<Variable> fi3 = new ArrayList<>();
//            List<Variable> fi4 = new ArrayList<>();
//
//            if (actaTO.getComponentes().isEmpty()) {
//                fi1.add(new TextVariable("${COMPONENTE}", ""));
//                fi2.add(new TextVariable("${DESCRIPCION}", ""));
//                fi3.add(new TextVariable("${NSERIE}", ""));
//                fi3.add(new TextVariable("${NACTIVO}", ""));
//            } else {
//                for (ComponenteTO compo : actaTO.getComponentes()) {
//
//                    fi1.add(new TextVariable("${COMPONENTE}", compo.getComponente()));
//                    fi2.add(new TextVariable("${DESCRIPCION}", compo.getDescComponente()));
//                    fi3.add(new TextVariable("${NSERIE}", compo.getSerieComponente()));
//                    fi4.add(new TextVariable("${NACTIVO}", compo.getActivoComponente()));
//
//                }
//            }
//
//            tableComponente.addVariable(fi1);
//            tableComponente.addVariable(fi2);
//            tableComponente.addVariable(fi3);
//            tableComponente.addVariable(fi4);
//
//            variables.addTableVariable(tableComponente);
//
//            if (actaTO.getTipoActa().equals("Entrega")) {
//                variables.addTextVariable(new TextVariable("${QUIEN}", "QUIEN RECIBE"));
//            } else {
//                variables.addTextVariable(new TextVariable("${QUIEN}", "QUIEN DEVUELVE"));
//            }
//
//            variables.addTextVariable(new TextVariable("${RESPONSABLE}", actaTO.getResponsable()));
//            variables.addTextVariable(new TextVariable("${RUT}", actaTO.getRut()));
//            variables.addTextVariable(new TextVariable("${CORREO}", actaTO.getCorreo()));
//
//            variables.addTextVariable(new TextVariable("${OBSERVACION}", actaTO.getObservacion()));
//
//            docx.fillTemplate(variables);
//            File file;
//            docx.save(System.getProperty("java.io.tmpdir") + "/" + nombreFinal);
//
//            file = new File(System.getProperty("java.io.tmpdir") + "/" + nombreFinal);
//
//            if (file.exists() && !file.isDirectory()) {
//
//                return file;
//            }
//
//        }
//
//        return null;
//    }
//
//    public File libreOfficeToPdf(File inputFile, boolean tmpDir) throws OfficeException {
//        String file = inputFile.getName();
//        int ext = file.lastIndexOf(".");
//        File outputFile;
//
//        if (tmpDir) {
//            outputFile = new File((String) httpSession.getAttribute("workDir") + "/" + file.substring(0, ext) + ".pdf");
//        } else {
//            outputFile = new File(Util.getUploadLocation() + file.substring(0, ext) + ".pdf");
//        }
//
//        final LocalOfficeManager officeManager = LocalOfficeManager.install();
//
//        try {
//            officeManager.start();
//
//            JodConverter.convert(inputFile).to(outputFile).execute();
//
//        } finally {
//            OfficeUtils.stopQuietly(officeManager);
//        }
//
//        return outputFile;
//    }

}
