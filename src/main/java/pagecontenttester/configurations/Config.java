package pagecontenttester.configurations;

import static pagecontenttester.fetcher.FetchedPage.DeviceType.MOBILE;

import java.net.InetSocketAddress;
import java.net.Proxy;

import pagecontenttester.fetcher.FetchedPage.DeviceType;

public class Config {

    private final TypedProperties configs = new TypedProperties("/pagecontent.properties");
    private final TypedProperties defaults = new TypedProperties("/default.properties");

    public int getTimeoutValue() {
        return getIntValue("timeout");
    }

    public int getTimeoutMaxRetryCount() {
        return getIntValue("timeout.max.retry.count");
    }

    public String getUserAgent(DeviceType deviceType) {
        if (deviceType.equals(MOBILE)) {
            return getStringValue("mobile.userAgent");
        }
        return getStringValue("desktop.userAgent");
    }

    public boolean isFollowingRedirects() {
        return getBooleanValue("follow.redirects");
    }

    public boolean isIgnoringContentType() {
        return getBooleanValue("ignore.content-type");
    }

    public String getReferrer() {
        return getStringValue("referrer");
    }

    public boolean isCacheDuplicatesActive() {
        return getBooleanValue("cache.duplicates");
    }

    public boolean isCacheDuplicatesLogActive() {
        return getBooleanValue("cache.log.duplicates");
    }

    public Proxy getProxy() {
        if (configs.hasProperty("proxy.host") && configs.hasProperty("proxy.port")) {
            return new Proxy(
                    Proxy.Type.HTTP,
                    InetSocketAddress.createUnresolved(configs.getStringValue("proxy.host"), configs.getIntValue("proxy.port")));
        } else {
            return null;
        }
    }

    public String getUrlPrefix() {
        return getStringValue("urlPreFix");
    }

    public String getProtocol() {
        return getStringValue("protocol");
    }

    public String getPort() {
        return getStringValue("port");
    }

    int getIntValue(String key) {
        if (configs.hasProperty(key)) {
            return configs.getIntValue(key);
        }
        return defaults.getIntValue(key);
    }

    String getStringValue(String key) {
        if (configs.hasProperty(key)) {
            return configs.getStringValue(key);
        }
        return defaults.getStringValue(key);
    }

    boolean getBooleanValue(String key) {
        if (configs.hasProperty(key)) {
            return configs.getBooleanValue(key);
        }
        return defaults.getBooleanValue(key);
    }
}
