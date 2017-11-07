package paco.configurations;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

import static java.lang.Boolean.parseBoolean;

@Slf4j
class TypedProperties {

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
            // do nothing
        }
    }

    static String getStringValue(final String key) {
        String customValue = System.getProperty(key, mergedProperties.getProperty(key));
        if (customValue != null) {
            return customValue;
        }
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
