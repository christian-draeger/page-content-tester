package pagecontenttester.configurations;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;

import pagecontenttester.annotations.Cookie;
import pagecontenttester.annotations.Fetch;
import pagecontenttester.fetcher.FetchedPage;
import pagecontenttester.fetcher.Parameters;

public class ConfigResolver {

    private final GlobalConfig globalConfig = new GlobalConfig();
    private Fetch fetchAnnotation;
    private String testName;

    public ConfigResolver(Fetch fetchAnnotation, String testName) {
        this.fetchAnnotation = fetchAnnotation;
        this.testName = testName;
    }

    public Parameters getRequestSpecificParams() {

        Cookie[] cookieAnnotation = fetchAnnotation.setCookies();
        return Parameters.builder()
                .urlToFetch(getUrl(fetchAnnotation.url()))
                .device(getDevice())
                .userAgent(getUserAgent())
                .method(getMethod())
                .referrer(getReferrer())
                .followRedirects(isFollowingRedirects())
                .timeout(getTimeout())
                .retriesOnTimeout(getRetryCount())
                .cookie(getCookies(cookieAnnotation))
                .testName(testName)
                .build();
    }

    private String getProtocol() {
        return StringUtils.isNotEmpty(fetchAnnotation.protocol().value) ? fetchAnnotation.protocol().value : globalConfig.getProtocol();
    }

    private Connection.Method getMethod() {
        return fetchAnnotation.method();
    }

    private String getUserAgent() {
        return fetchAnnotation.userAgent();
    }

    private FetchedPage.DeviceType getDevice() {
        return fetchAnnotation.device();
    }

    private int getRetryCount() {
        return fetchAnnotation.retriesOnTimeout() == -1  ? globalConfig.getTimeoutMaxRetryCount() : fetchAnnotation.retriesOnTimeout();
    }

    private int getTimeout() {
        return fetchAnnotation.timeout() == 0 ? globalConfig.getTimeoutValue() : fetchAnnotation.timeout();
    }

    private String getReferrer() {
        return "referrer".equals(fetchAnnotation.referrer()) ? globalConfig.getReferrer() : fetchAnnotation.referrer();
    }

    private boolean isFollowingRedirects() {
        return fetchAnnotation.followRedirects() && globalConfig.isFollowingRedirects();

    }

    private String getUrlPrefix() {
        return fetchAnnotation.urlPrefix().isEmpty() ? globalConfig.getUrlPrefix() : fetchAnnotation.urlPrefix();
    }

    private String getPort() {
        return fetchAnnotation.port().isEmpty() ? globalConfig.getPort() : fetchAnnotation.port();
    }

    private Map<String, String> getCookies(Cookie[] annotationCookies) {

        HashMap<String, String> cookies = new HashMap<>();

        for (Cookie annotationCookie : annotationCookies) {
            if (annotationCookie.name().isEmpty()) {
                return Collections.emptyMap();
            }
            cookies.put(annotationCookie.name(), annotationCookie.value());
        }
        return cookies;
    }

    private String getUrl(String url) {

        String portFromAnnotation = fetchAnnotation.port();

        String portFallBackCheck = StringUtils.isNotEmpty(portFromAnnotation) ? ":" + portFromAnnotation : ":" + globalConfig.getPort();
        String port = ":".equals(portFallBackCheck) ? "" : portFallBackCheck;

        try {

            URL urlRaw = new URL(getProtocol() + getUrlPrefix() + url);

            if (url.matches(".*:[0-9]{2,6}.*") && StringUtils.isEmpty(portFromAnnotation)) {
                port =  ":" + urlRaw.getPort();
            }

            return getProtocol() + urlRaw.getHost() + port + urlRaw.getFile();

        } catch (MalformedURLException e) {
            // log.error(e.getMessage(), e);
        }

        return null;
    }
}
