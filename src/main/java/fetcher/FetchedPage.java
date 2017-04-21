package fetcher;

import static fetcher.FetchedPage.DeviceType.DESKTOP;
import static fetcher.FetchedPage.DeviceType.MOBILE;
import static org.jsoup.Connection.Method;
import static org.jsoup.Connection.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import configurations.Config;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FetchedPage {

    public enum DeviceType {
        DESKTOP,
        MOBILE
    }

    private static Config config = new Config();

    private static final Map<CacheKey, FetchedPage> fetchedPageCache = new ConcurrentHashMap<>();

    public static FetchedPage fetchPage(String url) {
        return fetchedPages(url, Method.GET, Collections.emptyMap(), DESKTOP, config.getReferrer());
    }

    public static FetchedPage fetchPageAsMobileDevice(String url) {
        return fetchedPages(url, Method.GET, Collections.emptyMap(), MOBILE, config.getReferrer());
    }

    public static FetchedPage call(String url, Method method, Map<String, String> requestBody) {
        return fetchedPages(url, method, requestBody, DESKTOP, config.getReferrer());
    }

    public static FetchedPage annotationCall(String url, DeviceType device, Method method, Map<String, String> requestBody, String referrer) {
        requestBody = Collections.emptyMap();
        return fetchedPages(url, method, requestBody, device, referrer);
    }

    @SneakyThrows
    private static FetchedPage fetchedPages(String urlToFetch, Method method, Map<String, String> requestBody, DeviceType device, String referrer) {
        CacheKey cacheKey = new CacheKey(urlToFetch, device);
        boolean mobile = device.equals(MOBILE);
        if (fetchedPageCache.containsKey(cacheKey) && config.isCacheDuplicatesActive()) {
            if (config.isCacheDuplicatesLogActive()) {
                log.info("duplicate call for fetched page: {}\n\t{}", cacheKey, Thread.currentThread().getStackTrace()[3]);
            }
            return fetchedPageCache.get(cacheKey);
        } else {
            Fetcher fetcher = Fetcher.builder()
                    .deviceType(device)
                    .method(method)
                    .requestBody(requestBody)
                    .referrer(referrer)
                    .build();
            FetchedPage fetchedPage = new FetchedPage(urlToFetch, fetcher.fetch(urlToFetch), mobile);
            fetchedPageCache.put(cacheKey, fetchedPage);
            return fetchedPage;
        }
    }

    @Value
    @AllArgsConstructor
    private static class CacheKey {
        String url;
        DeviceType device;
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

    public String getContentType() {
        return response.contentType();
    }

    public String getPageBody() {
        return response.body();
    }

    public JSONObject getJsonResponse() {
        return new JSONObject(response.body());
    }

    public String getHeader(String header) {
        return response.header(header);
    }

    public Map<String, String> getHeaders() {
        return response.headers();
    }

    public boolean hasHeader(String header) {
        return response.hasHeader(header);
    }

    public Map<String, String> getCookies() {
        return response.cookies();
    }

    public String getCookieValue(String cookieName) {
        return response.cookie(cookieName);
    }

    public boolean hasCookie(String cookieName) {
        return response.hasCookie(cookieName);
    }

    public String getStatusMessage() {
        return response.statusMessage();
    }

    public Config getConfig() {
        return config;
    }

    public Elements getElements(String cssSelector) {
        return getDocument().select(cssSelector);
    }

    public Element getElement(String cssSelector) {
        return getDocument().select(cssSelector).first();
    }

    public Element getElementLastOf(String cssSelector) {
        return getDocument().select(cssSelector).last();
    }

    public Element getElement(String cssSelector, int index) {
        return getDocument().select(cssSelector).get(index);
    }

    public boolean isElementPresent(String cssSelector) {
        return getElementCount(cssSelector) > 0;
    }

    public boolean isElementPresentNthTimes(String cssSelector, int numberOfOccurrences) {
        return getElementCount(cssSelector) == numberOfOccurrences;
    }

    public int getElementCount(String cssSelector) {
        return getDocument().select(cssSelector).size();
    }
}
