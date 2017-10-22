package pagecontenttester.fetcher;

import static java.lang.System.setProperty;
import static org.fusesource.jansi.Ansi.Color.CYAN;
import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;
import static pagecontenttester.fetcher.FetchedPage.DeviceType.DESKTOP;
import static pagecontenttester.fetcher.FetchedPage.DeviceType.MOBILE;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import lombok.extern.slf4j.Slf4j;
import pagecontenttester.configurations.GlobalConfig;

@Slf4j
public class Fetcher {

    private static final GlobalConfig GLOBAL_CONFIG = new GlobalConfig();

    public Connection.Response fetch(Parameters params) throws IOException {

        setProperty("sun.net.http.allowRestrictedHeaders", "true");
        setProperty("javax.net.ssl.trustStore", "/etc/ssl/certs/java/cacerts");

        int retryCount = 0;

        while(true) {
            try {
                final Connection connection = Jsoup.connect(params.getUrlToFetch())
                        .validateTLSCertificates(false)
                        .timeout(params.getTimeout())
                        .userAgent(getUserAgent(params))
                        .ignoreHttpErrors(true)
                        .proxy(GLOBAL_CONFIG.getProxy())
                        .followRedirects(params.isFollowRedirects())
                        .ignoreContentType(GLOBAL_CONFIG.isIgnoringContentType())
                        .method(params.getMethod())
                        .maxBodySize(0)
                        .referrer(params.getReferrer());

                if (!params.getCookie().isEmpty()) {
                    connection.cookies(params.getCookie());
                }

                System.out.println("\uD83D\uDD3D " + ansi().fg(CYAN).bold().a("fetched page : ").reset()
                        + params.getUrlToFetch() + " (for test: " + params.getTestName() + ")");

                return connection.execute();

            } catch(SocketTimeoutException ste) {
                if(retryCount > params.getRetriesOnTimeout()) {
                    throw ste;
                }
                System.out.println("\uD83D\uDD50 " + ansi().fg(YELLOW).bold().a("fetch timeout: ").reset() + "SocketRead time out after " + retryCount++ + ". try");
            }
        }
    }

    private String getUserAgent(Parameters params) {
        if (params.getUserAgent().isEmpty()) {
            return params.getDevice().equals(MOBILE) ? GLOBAL_CONFIG.getUserAgent(MOBILE) : GLOBAL_CONFIG.getUserAgent(DESKTOP);
        }
        return params.getUserAgent();
    }
}
