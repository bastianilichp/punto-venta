package cl.puntoventa.app.schedule;

import cl.puntoventa.app.controller.ExportarController;
import cl.puntoventa.app.controller.ProductosController;
import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.io.IOException;
import java.net.ServerSocket;

@Stateless
public class ScheduleController {

    private final String tempDir = System.getProperty("java.io.tmpdir");
    
    
    
    @Inject
    private ExportarController exportarController;

    @Schedule(hour = "8", minute = "0", second = "0", persistent = false)
    public void exportarProyectosUtemEmailAudit() throws InterruptedException, IOException {

        ServerSocket serverSocket;

        try {

            serverSocket = new ServerSocket(1235);

            exportarController.exportarSinStock();

            serverSocket.close();

        } catch (IOException e) {

            System.out.println("error en socket " + e.getMessage());

        }

    }

}
