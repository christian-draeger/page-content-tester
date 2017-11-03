package paco.configurations;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class TypedProperties {

    private static final String DEFAULTS_URL = "https://github.com/christian-draeger/page-content-tester/blob/master/src/test/resources/default.properties";
    private static TypedProperties instance;
    private Config config;

    TypedProperties() {
        config = ConfigFactory.load(TypedProperties.class.getClassLoader(), System.getProperty("paco.properties", "paco.properties"));
    }

    private static synchronized TypedProperties getInstance() {
        if (instance == null) {
            instance = new TypedProperties();
        }
        return instance;
    }


    static String getStringValue(String key) {
        String value = "";
        if (!key.equals("")) {
            // dots are not allowed in POSIX environmental variables
            value = System.getenv(key.replace(".", "_"));
            if (value == null) {
                value = TypedProperties.getInstance().config.getString(key);
            }

        }
        return value;
    }

    static int getIntValue(String key) {
        return TypedProperties.getInstance().config.getInt(key);
    }

    static boolean getBooleanValue(String key) {
        return TypedProperties.getInstance().config.getBoolean(key);
    }

    boolean hasProperty(final String key) {
        return getStringValue(key) != null;
    }


}
