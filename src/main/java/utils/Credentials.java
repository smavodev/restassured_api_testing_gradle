package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Credentials {

    private static final String USERNAME_KEY = "create.username";
    private static final String PASSWORD_KEY = "create.password";

    private static final String ENV_USERNAME = "TODO_USERNAME";
    private static final String ENV_PASSWORD = "TODO_PASSWORD";

    private static Properties props = new Properties();

    static {
        // Cargar secrets.properties si existe (solo en desarrollo local)
        try (InputStream is = Credentials.class.getClassLoader().getResourceAsStream("secrets.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar secrets.properties", e);
        }
    }

    public static String getUsername() {
        String envUser = System.getenv(ENV_USERNAME);
        if (envUser != null && !envUser.trim().isEmpty()) {
            return envUser;
        }
        String propUser = props.getProperty(USERNAME_KEY);
        if (propUser == null || propUser.trim().isEmpty()) {
            throw new IllegalStateException("Credencial 'create.username' no encontrada ni en variables de entorno ni en secrets.properties");
        }
        return propUser;
    }

    public static String getPassword() {
        String envPass = System.getenv(ENV_PASSWORD);
        if (envPass != null && !envPass.trim().isEmpty()) {
            return envPass;
        }
        String propPass = props.getProperty(PASSWORD_KEY);
        if (propPass == null || propPass.trim().isEmpty()) {
            throw new IllegalStateException("Credencial 'create.password' no encontrada ni en variables de entorno ni en secrets.properties");
        }
        return propPass;
    }

}
