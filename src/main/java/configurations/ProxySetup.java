package configurations;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class ProxySetup {

    private final TypedProperties configs = new TypedProperties("/config.properties");

    Proxy proxy = new Proxy(
            Proxy.Type.HTTP,
            InetSocketAddress.createUnresolved(getHost(), getPort())
    );

    public Proxy getProxy() {
        return isProxyEnabled() ? proxy : null;
    }

    private boolean isProxyEnabled() {
        return configs.getBooleanValue("proxy.enabled");
    }

    private String getHost() {
        return configs.getStringValue("proxy.host");
    }

    private int getPort() {
        return configs.getIntValue("proxy.port");
    }
}
