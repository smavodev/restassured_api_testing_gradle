package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvironmentConfig {

    private static final Properties props = new Properties();

    static {
        // 1. Leer el entorno desde -Denvironment=...
        String env = System.getProperty("environment", "dev"); // por defecto: dev

        // 2. Construir nombre del archivo
        String fileName = "env." + env + ".properties";

        // 3. Cargar desde classpath
        try (InputStream is = EnvironmentConfig.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                throw new RuntimeException("Archivo de entorno no encontrado: " + fileName);
            }
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar: " + fileName, e);
        }
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

}
