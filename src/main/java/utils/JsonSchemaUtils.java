package utils;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

public class JsonSchemaUtils {

    private static final JsonSchemaFactory DRAFT_V4_SCHEMA_FACTORY = JsonSchemaFactory.newBuilder()
            .setValidationConfiguration(
                    ValidationConfiguration.newBuilder()
                            .setDefaultVersion(SchemaVersion.DRAFTV4)
                            .freeze()
            )
            .freeze();

    /**
     * Devuelve una instancia de JsonSchemaFactory preconfigurada para Draft v4.
     */
    public static JsonSchemaFactory draftV4SchemaFactory() {
        return DRAFT_V4_SCHEMA_FACTORY;
    }

}
