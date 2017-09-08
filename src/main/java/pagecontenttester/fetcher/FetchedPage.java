package pagecontenttester.fetcher;

import static org.jsoup.Connection.Method;
import static org.jsoup.Connection.Response;
import static pagecontenttester.fetcher.FetchedPage.DeviceType.MOBILE;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pagecontenttester.annotations.Fetch;
import pagecontenttester.configurations.Config;

@Slf4j
public class FetchedPage {

    private final String url;
    private final String urlPrefix;
    private final DeviceType deviceType;
    private final Response response;
    private Optional<Document> document = Optional.empty();

    public enum DeviceType {
        DESKTOP,
        MOBILE
    }

    private static Config config = new Config();

    public static Page annotationCall(String url, DeviceType device, Method method, String referrer, int timeout,
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
        String port = ":".equals(portFallBackCheck) ? "" : portFallBackCheck;
        try {
            URL urlRaw = new URL(protocol.value + prefix + url);
            return protocol.value + urlRaw.getHost() + port + urlRaw.getFile();

        } catch (MalformedURLException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @SneakyThrows
    private static Page fetchedPages(String urlToFetch,
                                            Method method,
                                            Map<String, String> requestBody,
                                            DeviceType device,
                                            String referrer,
                                            int timeout,
                                            int retriesOnTimeout,
                                            Map<String,String> cookie,
                                            String urlPrefix,
                                            String testName) {

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

        FetchedPage page = FetcherManager.getInstance().submit(cacheKey, testName).get();

        return new FetchedPageForTest(page, testName);
    }

    public FetchedPage(String url, Response response, DeviceType deviceType, String urlPrefix) {
        this.url = url;
        this.response = response;
        this.deviceType = deviceType;
        this.urlPrefix = urlPrefix;
    }

    synchronized Document getDocument() {
        if (!document.isPresent()) {
            try {
                document = Optional.of(response.parse());
            } catch (IOException e) {
                throw new ParseDocumentException("could not parse document", e);
            }
        }
        return document.get(); //NOSONAR
    }
    
    public String getUrl() {
        return url;
    }

    
    public String getUrlPrefix() {
        return urlPrefix;
    }


    Response getResponse() {
        return response;
    }
    
    DeviceType getDeviceType() {
        return deviceType;
    }

    
    boolean isMobile() {
        return deviceType.equals(MOBILE);
    }

    
    public Config getConfig() {
        return config;
    }

}
