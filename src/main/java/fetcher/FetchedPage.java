package fetcher;

import static fetcher.FetchedPage.DeviceType.DESKTOP;
import static fetcher.FetchedPage.DeviceType.MOBILE;
import static org.jsoup.Connection.Method;
import static org.jsoup.Connection.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.jsoup.nodes.Document;

import configurations.TestConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FetchedPage {

    public enum DeviceType {
        DESKTOP,
        MOBILE
    }

    TestConfig config = new TestConfig();

    public static FetchedPage fetchPage(String url) {
        Fetcher fetcher = Fetcher.builder().deviceType(DESKTOP).build();
        try {
            return new FetchedPage(url, fetcher.fetch(url), false);
        } catch (IOException e) {
            log.error("Exception while trying to fetch page: ", e);
        }
        return null;
    }

    public static FetchedPage fetchPageAsMobileDevice(String url) {
        Fetcher fetcher = Fetcher.builder().deviceType(MOBILE).build();
        try {
            return new FetchedPage(url, fetcher.fetch(url), true);
        } catch (IOException e) {
            log.error("Exception while trying to fetch mobile page: ", e);
        }
        return null;
    }

    public static FetchedPage performAjaxRequest(String url, Method method, Map<String, String> data) {
        Fetcher fetcher = Fetcher.builder().deviceType(DESKTOP).method(method).data(data).build();
        try {
            return new FetchedPage(url, fetcher.fetch(url), false);
        } catch (IOException e) {
            log.error("Exception while trying to fetch ajax call: ", e);
        }
        return null;
    }

    private final String url;
    private final boolean mobile;
    private final Response response;

    private Optional<Document> document = Optional.empty();

    private FetchedPage(String url, Response response, boolean mobile) {
        this.url = url;
        this.response = response;
        this.mobile = mobile;
    }

    public synchronized Document getDocument() {
        if (!document.isPresent()) {
           try {
                document = Optional.of(response.parse());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return document.get();
    }

    public String getUrl() {
        return url;
    }

    public int getStatusCode() {
        return response.statusCode();
    }

    public boolean isMobile() {
        return mobile;
    }

    public String getCookieValue(String cookieName) {
        return response.cookie(cookieName);
    }

    public String getContentType() {
        return response.contentType();
    }

    public String getPageBody() {
        return response.body();
    }

    public String getCustomHeader(String header) {
        return response.header(header);
    }

    public Map<String, String> getHeaders() {
        return response.headers();
    }

    public Map<String, String> getCookies() {
        return response.cookies();
    }

    public String getStatusMessage() {
        return response.statusMessage();
    }

    public boolean hasCookie(String cookieName) {
        return response.hasCookie(cookieName);
    }

    public boolean hasHeader(String header) {
        return response.hasHeader(header);
    }

    public String getReferrer() {
        return config.getReferrer();
    }
}
