package paco.configurations;

import static paco.annotations.Fetch.Device.MOBILE;

import java.net.InetSocketAddress;
import java.net.Proxy;

import org.junit.rules.Timeout;

import paco.annotations.Fetch.Device;

public class GlobalConfig {

    int getTimeoutValue() {
        return TypedProperties.getIntValue("paco.timeout");
    }

    public Timeout getGlobalTimeoutValue() {
        return Timeout.millis(TypedProperties.getIntValue("paco.globalTimeout"));
    }

    int getTimeoutMaxRetryCount() {
        return TypedProperties.getIntValue("paco.maxRetryCountOnTimeOut");
    }

    public String getUserAgent(Device device) {
        if (device.equals(MOBILE)) {
            return TypedProperties.getStringValue("paco.mobileUserAgent");
        }
        return TypedProperties.getStringValue("paco.desktopUserAgent");
    }

    public String getMobileUserAgent() {
            return TypedProperties.getStringValue("paco.mobileUserAgent");
    }

    public String getDesktopUserAgent() {
            return TypedProperties.getStringValue("paco.desktopUserAgent");
    }

    boolean isFollowingRedirects() {
        return TypedProperties.getBooleanValue("paco.followRedirects");
    }

    public boolean isIgnoringContentType() {
        return TypedProperties.getBooleanValue("paco.ignoreContentType");
    }

    public String getReferrer() {
        return TypedProperties.getStringValue("paco.referrer");
    }

    public boolean isCacheDuplicatesActive() {
        return TypedProperties.getBooleanValue("paco.cacheDuplicates");
    }

    public boolean isCacheDuplicatesLogActive() {
        return TypedProperties.getBooleanValue("paco.logCachedDuplicates");
    }

    public Proxy getProxy() {
        if (TypedProperties.getStringValue("paco.proxyHost").isEmpty() || TypedProperties.getStringValue("paco.proxyPort").isEmpty()) {
            return null;
        }
        return new Proxy(
                Proxy.Type.HTTP,
                InetSocketAddress.createUnresolved(TypedProperties.getStringValue("paco.proxyHost"), TypedProperties.getIntValue("paco.proxyPort")));
    }

    String getUrlPrefix() {
        return TypedProperties.getStringValue("paco.urlPrefix");
    }

    String getProtocol() {
        return TypedProperties.getStringValue("paco.protocol");
    }

    public String getPort() {
        return TypedProperties.getStringValue("paco.port");
    }

    public boolean isPacoAsciiActive() {
        return TypedProperties.getBooleanValue("paco.ascii");
    }

}
