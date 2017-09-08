package pagecontenttester.fetcher;

import static java.lang.System.setProperty;
import static org.fusesource.jansi.Ansi.Color.CYAN;
import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;
import static pagecontenttester.fetcher.FetchedPage.DeviceType.DESKTOP;
import static pagecontenttester.fetcher.FetchedPage.DeviceType.MOBILE;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import pagecontenttester.configurations.Config;
import pagecontenttester.fetcher.FetchedPage.DeviceType;

@Slf4j
@Builder
public class Fetcher {

    private static final Config CONFIG = new Config();

    private final DeviceType deviceType;
    private final Method method;
    private final String requestBody;
    private final String referrer;
    private final int timeout;
    private final int retriesOnTimeout;
    private final Map<String, String> cookie;
    private final String protocol;
    private final String urlPrefix;
    private final String port;

    public Connection.Response fetch(String url) throws IOException {

        setProperty("sun.net.http.allowRestrictedHeaders", "true");  // jvm hack for adding any custom header
        setProperty("javax.net.ssl.trustStore", "/etc/ssl/certs/java/cacerts");

        int retryCount = 0;

        while(true) {
            try {
                final Connection connection = Jsoup.connect(url)
                        .validateTLSCertificates(false)
                        .timeout(timeout)
                        .userAgent(deviceType.equals(MOBILE) ? CONFIG.getUserAgent(MOBILE) : CONFIG.getUserAgent(DESKTOP))
                        .ignoreHttpErrors(true)
                        .proxy(CONFIG.getProxy())
                        .followRedirects(CONFIG.isFollowingRedirects())
                        .ignoreContentType(CONFIG.isIgnoringContentType())
                        .method(method)
                        .maxBodySize(0)
                        .referrer(referrer);

                if (!cookie.isEmpty()) {
                    connection.cookies(cookie);
                }

                log.info("\uD83D\uDD3D " + ansi().fg(CYAN).bold().a("fetched page\t: ").reset() + "{} (UserAgent: {})", url, deviceType);
                return connection.execute();

            } catch(SocketTimeoutException ste) {
                if(retryCount > retriesOnTimeout) {
                    throw ste;
                }
                log.warn("\uD83D\uDD50 " + ansi().fg(YELLOW).bold().a("fetch timeout\t: ").reset() + "SocketRead time out after {}. try", retryCount++);
            }
        }
    }

    public static class FetcherBuilder { //NOSONAR
        private DeviceType device = DESKTOP; //NOSONAR
        private Method method = Method.GET; //NOSONAR
        private String requestBody = ""; //NOSONAR
        private Map<String,String> cookie = Collections.emptyMap(); //NOSONAR
        // take property values if not set via annotation
        private String referrer = CONFIG.getReferrer(); //NOSONAR
        private int timeout = CONFIG.getTimeoutValue(); //NOSONAR
        private int retriesOnTimeout = CONFIG.getTimeoutMaxRetryCount(); //NOSONAR
        private String protocol = CONFIG.getProtocol(); //NOSONAR
        private String urlPrefix = CONFIG.getUrlPrefix(); //NOSONAR
        private String port = CONFIG.getPort(); //NOSONAR
    }
}
