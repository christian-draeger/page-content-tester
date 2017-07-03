package pagecontenttester.fetcher;

import static org.jsoup.Connection.Method;
import static org.jsoup.Connection.Response;
import static pagecontenttester.fetcher.FetchedPage.DeviceType.MOBILE;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pagecontenttester.annotations.Fetch;
import pagecontenttester.configurations.Config;

@Slf4j
public class FetchedPage implements Page {

    private final String url;
    private final String urlPrefix;
    private final DeviceType deviceType;
    private final Response response;
    private Optional<Document> document = Optional.empty();
    private static final ThreadLocal<String> nameOfTest = new ThreadLocal<>();

    public enum DeviceType {
        DESKTOP,
        MOBILE
    }

    private static Config config = new Config();

    private static final Map<FetchRequestParameters, FetchedPage> fetchedPageCache = new ConcurrentHashMap<>();
    private static final Set<String> calledTestMethods = new ConcurrentSkipListSet<>();

    public static FetchedPage annotationCall(String url, DeviceType device, Method method, String referrer, int timeout,
                                            int retriesOnTimeout, Map<String, String> cookie, Fetch.Protocol protocol,
                                            String urlPrefix, String port, String testName) {

        String urlWithPrefix = getUrl(url, protocol, urlPrefix, port);

        return fetchedPages(urlWithPrefix,
                            method,
                            Collections.emptyMap(),
                            device,
                            referrer,
                            timeout,
                            retriesOnTimeout,
                            cookie,
                            urlPrefix,
                            testName
        );
    }

    private static String getUrl(String url, Fetch.Protocol protocol, String urlPrefix, String portFromAnnotation) {
        String prefix = urlPrefix.isEmpty() ? urlPrefix : urlPrefix + ".";
        String portFallBackCheck = StringUtils.isNotEmpty(portFromAnnotation) ? ":" + portFromAnnotation : ":" + config.getPort();
        String port = portFallBackCheck.equals(":") ? "" : portFallBackCheck;
        try {
            URL urlRaw = new URL(protocol.value + prefix + url);
            return protocol.value + urlRaw.getHost() + port + urlRaw.getFile();

        } catch (MalformedURLException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @SneakyThrows
    private static FetchedPage fetchedPages(String urlToFetch,
                                            Method method,
                                            Map<String, String> requestBody,
                                            DeviceType device,
                                            String referrer,
                                            int timeout,
                                            int retriesOnTimeout,
                                            Map<String,String> cookie,
                                            String urlPrefix,
                                            String testName) {

        nameOfTest.set(testName);

        final FetchRequestParameters cacheKey = FetchRequestParameters.builder()
                .urlToFetch(urlToFetch)
                .method(method)
                .requestBody(requestBody)
                .device(device)
                .referrer(referrer)
                .timeout(timeout)
                .retriesOnTimeout(retriesOnTimeout)
                .cookie(cookie)
                .urlPrefix(urlPrefix)
                .build();

        if (fetchedPageCache.containsKey(cacheKey) && config.isCacheDuplicatesActive() && !calledTestMethods.contains(testName)) {
            if (config.isCacheDuplicatesLogActive()) {
                log.info("duplicate call for fetched page: {}\n\twill take page from cache while running test: {}", cacheKey, testName);
            }
            return fetchedPageCache.get(cacheKey);
        } else {
            Fetcher fetcher = Fetcher.builder()
                    .method(method)
                    .requestBody(requestBody)
                    .deviceType(device)
                    .referrer(referrer)
                    .timeout(timeout)
                    .retriesOnTimeout(retriesOnTimeout)
                    .cookie(cookie)
                    .build();
            FetchedPage fetchedPage = new FetchedPage(urlToFetch, fetcher.fetch(urlToFetch), device, urlPrefix);
            if (config.isCacheDuplicatesActive() && !calledTestMethods.contains(testName)) {
                fetchedPageCache.put(cacheKey, fetchedPage);
            }
            calledTestMethods.add(testName);
            return fetchedPage;
        }
    }

    private FetchedPage(String url, Response response, DeviceType deviceType, String urlPrefix) {
        this.url = url;
        this.response = response;
        this.deviceType = deviceType;
        this.urlPrefix = urlPrefix;
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
    public String getUrlPrefix() {
        return urlPrefix;
    }

    @Override
    public int getStatusCode() {
        return response.statusCode();
    }

    @Override
    public DeviceType getDeviceType() {
        return deviceType;
    }

    @Override
    public boolean isMobile() {
        return deviceType.equals(MOBILE);
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
    public String getLocation() {
        return response.header("Location");
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
        hasSelector(cssSelector);
        return getDocument().select(cssSelector);
    }

    @Override
    public Element getElement(String cssSelector) {
        hasSelector(cssSelector);
        return getElements(cssSelector).first();
    }

    @Override
    public Element getElementLastOf(String cssSelector) {
        hasSelector(cssSelector);
        return getElements(cssSelector).last();
    }

    @Override
    public Element getElement(String cssSelector, int index) {
        hasSelector(cssSelector);
        return getElements(cssSelector).get(index);
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
    public String getTestName() {
        return nameOfTest.get();
    }

    @Override
    public void storePageBody() {
        store("stored");
    }

    @Override
    public int getElementCount(String cssSelector) {
        return getDocument().select(cssSelector).size();
    }

    private void store(String folder) {
        try {
            FileUtils.writeStringToFile(new File("target/page-content-tester/" + folder,  getTestName() + ".html"), getPageBody());
        } catch (IOException e) {
            log.warn("could not store page body for url: {} while executing test: {}", getUrl(), getTestName());
        }
    }

    private void hasSelector(String cssSelector) {
        if (getElementCount(cssSelector) == 0) {
            store("not-found");
        }
    }

}
