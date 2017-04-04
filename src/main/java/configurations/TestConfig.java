package configurations;

import static fetcher.FetchedPage.DeviceType.MOBILE;

import org.apache.commons.lang3.StringUtils;

import fetcher.FetchedPage.DeviceType;

public class TestConfig {

    private final TypedProperties configs = new TypedProperties("/config.properties");

    public int getTimeoutValue() {
        return configs.getIntValue("timeout");
    }

    public int getMaxRetryCount() {
        return configs.getIntValue("max.retry.count");
    }

    public String getUserAgent(DeviceType deviceType) {
        if (deviceType.equals(MOBILE)) {
            return configs.getStringValue("mobile.userAgent");
        }
        return configs.getStringValue("desktop.userAgent");
    }

    public String getProtocol() {
        if (StringUtils.isNotBlank(configs.getStringValue("protocol"))) {
            return configs.getStringValue("protocol");
        } else {
            return "http";
        }
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
}
