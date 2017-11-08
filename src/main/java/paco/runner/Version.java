package paco.runner;

import java.io.InputStream;
import java.util.Properties;

public class Version {

    public synchronized String getVersion() {
        String version = null;

        // try to load from maven properties first
        try {
            Properties properties = new Properties();
            InputStream inputStream = getVersionFromMetaInf();
            if (inputStream != null) {
                properties.load(inputStream);
                version = properties.getProperty("version", "");
            }
        } catch (Exception e) {
            // ignore
        }

        // fallback to using Java API
        if (version == null) {
            Package aPackage = getClass().getPackage();
            if (aPackage != null) {
                version = aPackage.getImplementationVersion();
                if (version == null) {
                    version = aPackage.getSpecificationVersion();
                }
            }
        }

        if (version == null) {
            version = "x.x.x";
        }

        return "version " + version;
    }

    private InputStream getVersionFromMetaInf() {
        final String path = "/META-INF/maven/io.github.christian-draeger/page-content-tester/pom.properties";
        return getClass().getResourceAsStream(path);
    }
}
