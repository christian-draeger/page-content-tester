package paco.fetcher;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import paco.configurations.GlobalConfig;

import java.io.IOException;
import java.net.SocketTimeoutException;

import static java.lang.System.setProperty;
import static org.fusesource.jansi.Ansi.Color.CYAN;
import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;

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
                        .userAgent(params.getUserAgent())
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

                if (!params.getRequestBody().isEmpty()) {
                    connection.requestBody(params.getRequestBody());
                }

                System.out.println("\uD83D\uDD3D " + ansi().fg(CYAN).bold().a("fetched page : ").reset() + params.getUrlToFetch());

                return connection.execute();

            } catch(SocketTimeoutException ste) {
                if(retryCount > params.getRetriesOnTimeout()) {
                    throw ste;
                }
                System.out.println("\uD83D\uDD50 " + ansi().fg(YELLOW).bold().a("fetch timeout: ").reset() + "SocketRead time out after " + retryCount++ + ". try");
            }
        }
    }
}
