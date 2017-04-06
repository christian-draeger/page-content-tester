package fetcher;

import static fetcher.FetchedPage.DeviceType.DESKTOP;
import static fetcher.FetchedPage.DeviceType.MOBILE;
import static java.lang.System.setProperty;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;

import configurations.Config;
import fetcher.FetchedPage.DeviceType;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public class Fetcher {

    private final Config config = new Config();
    private final DeviceType deviceType;
    private final Method method;
    private final Map<String, String> data;

    public Connection.Response fetch(String url) throws IOException {

        log.info("fetching {} ", url);
        setProperty("sun.net.http.allowRestrictedHeaders", "true");  // jvm hack for adding any custom header

        int retryCount = config.getMaxRetryCount();

        while(true) {
            try {
                return Jsoup.connect(url)
                        .validateTLSCertificates(false)
                        .timeout(config.getTimeoutValue())
                        .userAgent(deviceType.equals(MOBILE) ? config.getUserAgent(MOBILE) : config.getUserAgent(DESKTOP))
                        .ignoreHttpErrors(true)
                        .followRedirects(config.isFollowingRedirects())
                        .ignoreContentType(config.isIgnoringContentType())
                        .method(method)
                        .data(data)
                        .referrer(config.getReferrer())
                        .execute();

            } catch(SocketTimeoutException ste) {
                if(retryCount > config.getMaxRetryCount()) {
                    throw ste;
                }
                log.warn("SocketRead time out after {}. try", retryCount++);
            }
        }
    }

    public static class FetcherBuilder {
        private DeviceType device = DESKTOP;
        private Method method = Method.GET;
        private Map<String, String> data = Collections.emptyMap();
    }
}
