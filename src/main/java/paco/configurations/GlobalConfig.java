package paco.configurations;

import org.junit.rules.Timeout;
import paco.annotations.Fetch.Device;

import java.util.Collections;
import java.util.Map;

import static paco.annotations.Fetch.Device.MOBILE;

public class GlobalConfig {

    int getTimeoutValue() {
        return TypedProperties.getIntValue("timeout");
    }

    public Timeout getGlobalTimeoutValue() {
        return Timeout.millis(TypedProperties.getIntValue("globalTimeout"));
    }

    int getTimeoutMaxRetryCount() {
        return TypedProperties.getIntValue("maxRetryCountOnTimeOut");
    }

    public String getUserAgent(Device device) {
        if (device.equals(MOBILE)) {
            return TypedProperties.getStringValue("mobileUserAgent");
        }
        return TypedProperties.getStringValue("desktopUserAgent");
    }

    public String getMobileUserAgent() {
            return TypedProperties.getStringValue("mobileUserAgent");
    }

    public String getDesktopUserAgent() {
            return TypedProperties.getStringValue("desktopUserAgent");
    }

    boolean isFollowingRedirects() {
        return TypedProperties.getBooleanValue("followRedirects");
    }

    public boolean isIgnoringContentType() {
        return TypedProperties.getBooleanValue("ignoreContentType");
    }

    public String getReferrer() {
        return TypedProperties.getStringValue("referrer");
    }

    public boolean isCacheDuplicatesActive() {
        return TypedProperties.getBooleanValue("cacheDuplicates");
    }

    public boolean isCacheDuplicatesLogActive() {
        return TypedProperties.getBooleanValue("logCachedDuplicates");
    }

    public Map<String, Integer> getProxy() {
        String host = TypedProperties.getStringValue("proxyHost");
        String port = TypedProperties.getStringValue("proxyPort");
        if (host.isEmpty() || port.isEmpty()) {
            return Collections.emptyMap();
        }
        return Collections.singletonMap(TypedProperties.getStringValue("proxyHost"), TypedProperties.getIntValue("proxyPort"));
    }

    String getUrlPrefix() {
        return TypedProperties.getStringValue("urlPrefix");
    }

    String getUrlPrefixSeparator() {
        return TypedProperties.getStringValue("urlPrefixSeparator");
    }

    String getProtocol() {
        return TypedProperties.getStringValue("protocol");
    }

    public String getPort() {
        return TypedProperties.getStringValue("port");
    }

    public boolean isPacoAsciiActive() {
        return TypedProperties.getBooleanValue("ascii");
    }

}
