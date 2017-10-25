package pagecontenttester.configurations;

import static pagecontenttester.annotations.Fetch.Device.DESKTOP;
import static pagecontenttester.annotations.Fetch.Device.MOBILE;
import static pagecontenttester.annotations.Fetch.Protocol.NONE;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import pagecontenttester.annotations.Cookie;
import pagecontenttester.annotations.Fetch;
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
                .userAgent(getUserAgent())
                .method(fetchAnnotation.method())
                .referrer(getReferrer())
                .followRedirects(isFollowingRedirects())
                .timeout(getTimeout())
                .retriesOnTimeout(getRetryCount())
                .cookie(getCookies(cookieAnnotation))
                .testName(testName)
                .build();
    }

    private String getProtocol() {
        if (fetchAnnotation.protocol().equals(NONE)) {
            return globalConfig.getProtocol();
        } else {
            return fetchAnnotation.protocol().value;
        }
    }

    private String getUserAgent() {
        if (!fetchAnnotation.userAgent().isEmpty()) {
            return fetchAnnotation.userAgent();
        }
        if (fetchAnnotation.device().equals(MOBILE)) {
            return globalConfig.getUserAgent(MOBILE);
        }
        return globalConfig.getUserAgent(DESKTOP);
    }

    private int getRetryCount() {
        if (fetchAnnotation.retriesOnTimeout() == -1) {
            return globalConfig.getTimeoutMaxRetryCount();
        } else {
            return fetchAnnotation.retriesOnTimeout();
        }
    }

    private int getTimeout() {
        if (fetchAnnotation.timeout() == 0) {
            return globalConfig.getTimeoutValue();
        } else {
            return fetchAnnotation.timeout();
        }
    }

    private String getReferrer() {
        if (fetchAnnotation.referrer().isEmpty()) {
            return globalConfig.getReferrer();
        } else {
            return fetchAnnotation.referrer();
        }
    }

    private boolean isFollowingRedirects() {
        return fetchAnnotation.followRedirects() && globalConfig.isFollowingRedirects();
    }

    private String getUrlPrefix() {
        if (fetchAnnotation.urlPrefix().isEmpty()) {
            return globalConfig.getUrlPrefix();
        } else {
            return fetchAnnotation.urlPrefix();
        }
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
