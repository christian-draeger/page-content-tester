package pagecontenttester.fetcher;

import static pagecontenttester.fetcher.FetchedPage.DeviceType.DESKTOP;
import static pagecontenttester.fetcher.FetchedPage.DeviceType.MOBILE;
import static java.lang.System.setProperty;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;

import pagecontenttester.configurations.Config;
import pagecontenttester.fetcher.FetchedPage.DeviceType;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public class Fetcher {

    private static final Config CONFIG = new Config();

    private final DeviceType deviceType;
    private final Method method;
    private final Map<String, String> requestBody;
    private final String referrer;
    private final int timeout;
    private final int retriesOnTimeout;


    public Connection.Response fetch(String url) throws IOException {

        log.info("fetching {} (UserAgent: {})", url, deviceType);
        setProperty("sun.net.http.allowRestrictedHeaders", "true");  // jvm hack for adding any custom header

        int retryCount = 0;

        while(true) {
            try {
                return Jsoup.connect(url)
                        .validateTLSCertificates(false)
                        .timeout(timeout)
                        .userAgent(deviceType.equals(MOBILE) ? CONFIG.getUserAgent(MOBILE) : CONFIG.getUserAgent(DESKTOP))
                        .ignoreHttpErrors(true)
                        .proxy(CONFIG.getProxy())
                        .followRedirects(CONFIG.isFollowingRedirects())
                        .ignoreContentType(CONFIG.isIgnoringContentType())
                        .method(method)
                        .data(requestBody)
                        .referrer(referrer)
                        .execute();

            } catch(SocketTimeoutException ste) {
                if(retryCount > retriesOnTimeout) {
                    throw ste;
                }
                log.warn("SocketRead time out after {}. try", retryCount++);
            }
        }
    }

    public static class FetcherBuilder { //NOSONAR
        private DeviceType device = DESKTOP; //NOSONAR
        private Method method = Method.GET; //NOSONAR
        private Map<String, String> requestBody = Collections.emptyMap(); //NOSONAR
        private String referrer = CONFIG.getReferrer(); //NOSONAR
        private int timeout = CONFIG.getTimeoutValue(); //NOSONAR
        private int retriesOnTimeout = CONFIG.getTimeoutMaxRetryCount(); //NOSONAR
    }
}
