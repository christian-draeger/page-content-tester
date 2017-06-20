package pagecontenttester.fetcher;

import static org.jsoup.Connection.Method;
import static org.jsoup.Connection.Response;
import static pagecontenttester.fetcher.FetchedPage.DeviceType.DESKTOP;
import static pagecontenttester.fetcher.FetchedPage.DeviceType.MOBILE;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import pagecontenttester.annotations.Fetch;
import pagecontenttester.configurations.Config;

@Slf4j
public class FetchedPage implements Page {

    private final String url;
    private final boolean mobile;
    private final Response response;
    private Optional<Document> document = Optional.empty();

    public enum DeviceType {
        DESKTOP,
        MOBILE

    }
    private static Config config = new Config();

    private static Map<String, String> defaultCookie = new HashMap<>();

    private static final Map<CacheKey, FetchedPage> fetchedPageCache = new ConcurrentHashMap<>();

    @Deprecated
    public static FetchedPage fetchPage(String url) {
        return fetchedPages(url,
                            Method.GET,
                            Collections.emptyMap(),
                            DESKTOP,
                            config.getReferrer(),
                            config.getTimeoutValue(),
                            config.getTimeoutMaxRetryCount(),
                            defaultCookie
        );
    }

    @Deprecated
    public static FetchedPage fetchPageAsMobileDevice(String url) {
        return fetchedPages(url,
                            Method.GET,
                            Collections.emptyMap(),
                            MOBILE,
                            config.getReferrer(),
                            config.getTimeoutValue(),
                            config.getTimeoutMaxRetryCount(),
                            defaultCookie
        );
    }

    public static FetchedPage call(String url, Method method, Map<String, String> requestBody) {
        return fetchedPages(url,
                            method,
                            requestBody,
                            DESKTOP,
                            config.getReferrer(),
                            config.getTimeoutValue(),
                            config.getTimeoutMaxRetryCount(),
                            defaultCookie
        );
    }

    public static FetchedPage annotationCall(String url, DeviceType device, Method method, String referrer, int timeout,
                                            int retriesOnTimeout, Map<String, String> cookie, Fetch.Protocol protocol,
                                            String urlPrefix, String port) {

        String prefix = urlPrefix.isEmpty() ? urlPrefix : urlPrefix + ".";
        return fetchedPages(protocol.value + prefix + url,
                            method,
                            Collections.emptyMap(),
                            device,
                            referrer,
                            timeout,
                            retriesOnTimeout,
                            cookie
        );
    }

    @SneakyThrows
    private static FetchedPage fetchedPages(String urlToFetch,
                                            Method method,
                                            Map<String, String> requestBody,
                                            DeviceType device,
                                            String referrer,
                                            int timeout,
                                            int retriesOnTimeout,
                                            Map<String,String> cookie) {
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
                    .timeout(timeout)
                    .retriesOnTimeout(retriesOnTimeout)
                    .cookie(cookie)
                    .build();
            FetchedPage fetchedPage = new FetchedPage(urlToFetch, fetcher.fetch(urlToFetch), mobile);
            fetchedPageCache.put(cacheKey, fetchedPage);
            return fetchedPage;
        }
    }

    @Value
    @AllArgsConstructor
    private static class CacheKey {
        private String url;
        private DeviceType device;

    }

    private FetchedPage(String url, Response response, boolean mobile) {
        this.url = url;
        this.response = response;
        this.mobile = mobile;
    }

    @Override
    public synchronized Document getDocument() {
        if (!document.isPresent()) {
            try {
                document = Optional.of(response.parse());
            } catch (IOException e) {
                throw new ParseDocumentException("could not parse document", e);
            }
        }
        return document.get(); //NOSONAR
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public int getStatusCode() {
        return response.statusCode();
    }

    @Override
    public boolean isMobile() {
        return mobile;
    }

    @Override
    public String getContentType() {
        return response.contentType();
    }

    @Override
    public String getPageBody() {
        return response.body();
    }

    @Override
    public JSONObject getJsonResponse() {
        return new JSONObject(response.body());
    }

    @Override
    public String getHeader(String header) {
        return response.header(header);
    }

    @Override
    public Map<String, String> getHeaders() {
        return response.headers();
    }

    @Override
    public boolean hasHeader(String header) {
        return response.hasHeader(header);
    }

    @Override
    public Map<String, String> getCookies() {
        return response.cookies();
    }

    @Override
    public String getCookieValue(String cookieName) {
        return response.cookie(cookieName);
    }

    @Override
    public boolean hasCookie(String cookieName) {
        return response.hasCookie(cookieName);
    }

    @Override
    public String getStatusMessage() {
        return response.statusMessage();
    }

    @Override
    public Config getConfig() {
        return config;
    }

    @Override
    public Elements getElements(String cssSelector) {
        return getDocument().select(cssSelector);
    }

    @Override
    public Element getElement(String cssSelector) {
        return getDocument().select(cssSelector).first();
    }

    @Override
    public Element getElementLastOf(String cssSelector) {
        return getDocument().select(cssSelector).last();
    }

    @Override
    public Element getElement(String cssSelector, int index) {
        return getDocument().select(cssSelector).get(index);
    }

    @Override
    public boolean isElementPresent(String cssSelector) {
        return getElementCount(cssSelector) > 0;
    }

    @Override
    public boolean isElementPresentNthTimes(String cssSelector, int numberOfOccurrences) {
        return getElementCount(cssSelector) == numberOfOccurrences;
    }

    @Override
    public int getElementCount(String cssSelector) {
        return getDocument().select(cssSelector).size();
    }
}
