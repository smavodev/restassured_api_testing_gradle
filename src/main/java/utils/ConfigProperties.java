package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {

    private static final Properties props = new Properties();

    static {
        try (InputStream is = ConfigProperties.class.getClassLoader().getResourceAsStream("secrets.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar secrets.properties", e);
        }
    }

    /**
     * Obtiene un valor de configuración.
     * 1. Busca en variables de entorno (clave convertida a UPPER_SNAKE_CASE)
     * 2. Si no, busca en secrets.properties
     * 3. Si no existe en ninguno, lanza excepción.
     */
    public static String getValue(String key) {
        // Convertir clave a formato de variable de entorno: "todo.username" → "TODO_USERNAME"
        String envKey = key.replaceAll("[.-]", "_").toUpperCase();

        // 1. Intentar desde variable de entorno
        String envValue = System.getenv(envKey);
        if (envValue != null && !envValue.trim().isEmpty()) {
            return envValue;
        }

        // 2. Intentar desde secrets.properties
        String propValue = props.getProperty(key);
        if (propValue != null && !propValue.trim().isEmpty()) {
            return propValue;
        }

        // 3. No encontrado
        throw new IllegalStateException(
                "Configuración '" + key + "' no encontrada. " +
                        "Buscado como variable de entorno: '" + envKey + "', " +
                        "y en secrets.properties como: '" + key + "'."
        );
    }

    /**
     * Versión con valor por defecto (opcional)
     */
    public static String getValue(String key, String defaultValue) {
        try {
            return getValue(key);
        } catch (IllegalStateException e) {
            return defaultValue;
        }
    }
}
