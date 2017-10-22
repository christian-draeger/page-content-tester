package pagecontenttester.configurations;

import static pagecontenttester.fetcher.FetchedPage.DeviceType.MOBILE;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.Proxy;

import org.junit.rules.Timeout;

import pagecontenttester.fetcher.FetchedPage.DeviceType;

public class GlobalConfig {

    private final TypedProperties configs = new TypedProperties("/paco.properties");

    public int getTimeoutValue() {
        return configs.getIntValue("timeout");
    }

    public Timeout getGlobalTimeoutValue() {
        return Timeout.millis(configs.getIntValue("global.timeout"));
    }

    public int getTimeoutMaxRetryCount() {
        return configs.getIntValue("timeout.max.retry.count");
    }

    public String getUserAgent(DeviceType deviceType) {
        if (deviceType.equals(MOBILE)) {
            return configs.getStringValue("mobile.userAgent");
        }
        return configs.getStringValue("desktop.userAgent");
    }

    public boolean isFollowingRedirects() {
        return configs.getBooleanValue("follow.redirects");
    }

    public boolean isIgnoringContentType() {
        return configs.getBooleanValue("ignore.content-type");
    }

    public String getReferrer() {
        return configs.getStringValue("referrer");
    }

    public boolean isCacheDuplicatesActive() {
        return configs.getBooleanValue("cache.duplicates");
    }

    public boolean isCacheDuplicatesLogActive() {
        return configs.getBooleanValue("cache.log.duplicates");
    }

    public Proxy getProxy() {
        if (configs.getStringValue("proxy.host").isEmpty() || configs.getStringValue("proxy.port").isEmpty()) {
            return null;
        }
        return new Proxy(
                Proxy.Type.HTTP,
                InetSocketAddress.createUnresolved(configs.getStringValue("proxy.host"), configs.getIntValue("proxy.port")));
    }

    public String getUrlPrefix() {
        return configs.getStringValue("urlPrefix");
    }

    public String getProtocol() {
        return configs.getStringValue("protocol");
    }

    public String getPort() {
        return configs.getStringValue("port");
    }

    public boolean isPacoAsciiActive() {
        return configs.getBooleanValue("paco.ascii");
    }

    private static String getPropsPath() {
        final String customPropertiesPath = "/paco.properties";
        File customProperties = new File(customPropertiesPath);
        if (customProperties.exists()) {
            return customPropertiesPath;
        }
        return "/default.properties";
    }
}
