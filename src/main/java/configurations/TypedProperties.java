package configurations;

import static java.lang.Boolean.parseBoolean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TypedProperties {

	private final Properties properties = new Properties();

	public TypedProperties(String resourceName) {
		final InputStream inputStream = getClass().getResourceAsStream(resourceName);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getStringValue(final String key) {
	    return System.getProperty(key, properties.getProperty(key));
	}

	public boolean hasProperty(final String key) {
		return getStringValue(key) != null;
	}

	public int getIntValue(final String key) {
		return Integer.parseInt(getStringValue(key));
	}

	public boolean getBooleanValue(final String key) {
		return parseBoolean(getStringValue(key));
	}
}
