package PageContentTester.configurations;

import static java.lang.Boolean.parseBoolean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TypedProperties {

	private final Properties properties = new Properties();

	TypedProperties(String resourceName) {
		final InputStream inputStream = getClass().getResourceAsStream(resourceName);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			throw new ConfigurationException("could not read " + resourceName, e);
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
		if (!("true".equals(value) || "false".equals(value))){
			throw new ConfigurationException("trying to parse non boolean value as boolean");
		}
		return parseBoolean(getStringValue(key));
	}
}
