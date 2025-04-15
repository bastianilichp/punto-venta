package cl.puntoventa.app.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.util.UUID.randomUUID;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpSession;

@ApplicationScoped
public class SerializationUtils {

    @Inject
    private HttpSession httpSession;

    @SuppressWarnings("unchecked")
    public String writeObject(Object object) throws Exception {

        String uuid = randomUUID().toString().substring(0, 8);

        String file = uuid + ".obj";

        FileOutputStream fileOutputStream = new FileOutputStream((String) httpSession.getAttribute("workDir") + "/" + file);

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
        }

        return uuid;
    }

    @SuppressWarnings("unchecked")
    public <T> T readObject(String uuid) throws Exception {

        String file = uuid + ".obj";

        FileInputStream fileInputStream = new FileInputStream((String) httpSession.getAttribute("workDir") + "/" + file);
        T object;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            object = (T) objectInputStream.readObject();
        }

        return object;

    }

    @SuppressWarnings("unchecked")
    public boolean deleteObject(String uuid) throws Exception {

        String obj = uuid + ".obj";

        File file = new File((String) httpSession.getAttribute("workDir") + "/" + obj);

        return file.delete();
    }

}
