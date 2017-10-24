package pagecontenttester.configurations;

import static java.lang.Boolean.parseBoolean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class TypedProperties {

    private final Properties properties = new Properties();
    private static final String DEFAULTS_URL = "https://github.com/christian-draeger/page-content-tester/blob/master/src/test/resources/default.properties";
    private final InputStream defaultProperties = getClass().getResourceAsStream("/default.properties");

    TypedProperties() {
        try {
            final InputStream customProperties = getClass().getResourceAsStream("/paco.properties");
            properties.load(customProperties);
        } catch (Exception e) {
            log.warn("could not find 'paco.properties' file under path main/test/resources -> fallback to defaults (can be found under {})", DEFAULTS_URL);
            loadDefaultProperties();
        }
    }

    String getStringValue(final String key) {
        return System.getProperty(key, properties.getProperty(key));
    }

    boolean hasProperty(final String key) {
        return getStringValue(key) != null;
    }

    int getIntValue(final String key) {
        return Integer.parseInt(getStringValue(key));
    }

    boolean getBooleanValue(final String key) {
        String value = getStringValue(key);
        if (!("true".equals(value) || "false".equals(value))) {
            throw new ConfigurationException("trying to parse non boolean value as boolean");
        }
        return parseBoolean(getStringValue(key));
    }

    private void loadDefaultProperties() {
        try {
            properties.load(defaultProperties);
        } catch (IOException e) {
            throw new ConfigurationException("could not read default properties", e);
        }
    }
}
