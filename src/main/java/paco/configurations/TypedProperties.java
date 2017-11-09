package paco.configurations;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import static java.lang.Boolean.parseBoolean;

class TypedProperties {

    private static final Logger LOGGER = Logger.getLogger(TypedProperties.class.getName());
    private static Properties mergedProperties;

    static {
        Properties defaultProperties = new Properties();
        try {
            defaultProperties.load(TypedProperties.class.getResourceAsStream("/default.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e); //NOSONAR
        }
        mergedProperties = new Properties(defaultProperties);
        try {
            mergedProperties.load(TypedProperties.class.getResourceAsStream("/paco.properties"));
        } catch (Exception e) {
            LOGGER.info("could not load paco.properties -> fallback to default.properties");
        }
    }

    static String getStringValue(final String key) {
        return System.getProperty(key, mergedProperties.getProperty(key));
    }

    static int getIntValue(final String key) {
        return Integer.parseInt(getStringValue(key));
    }

    static boolean getBooleanValue(final String key) {
        String value = getStringValue(key);
        if (!("true".equals(value) || "false".equals(value))) {
            throw new ConfigurationException("trying to parse non boolean value as boolean");
        }
        return parseBoolean(getStringValue(key));
    }
}
