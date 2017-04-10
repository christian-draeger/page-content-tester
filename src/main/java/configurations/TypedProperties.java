package configurations;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TypedProperties {

	private final Properties properties = new Properties();

	public TypedProperties(String resourceName) {
		final InputStream inputStream = getClass().getResourceAsStream(resourceName);
		try {
			properties.load(inputStream);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getStringValue(final String key) {
	    return System.getProperty(key, properties.getProperty(key));
	}

	public int getIntValue(final String key) {
		return parseInt(getStringValue(key));
	}

	public boolean getBooleanValue(final String key) {
		return parseBoolean(getStringValue(key));
	}
}
