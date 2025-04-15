package cl.puntoventa.app.helpers;


import jakarta.enterprise.context.ApplicationScoped;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class ContadorSession {

    private final AtomicInteger activeSessions;

    public ContadorSession() {
        activeSessions = new AtomicInteger();
    }

    public int incrementAndGet() {
        return activeSessions.incrementAndGet();
    }

    public int decrementAndGet() {
        return activeSessions.decrementAndGet();
    }

    public AtomicInteger getActiveSessions() {
        return activeSessions;
    }

}
