package pagecontenttester.annotations;

import static pagecontenttester.fetcher.FetchedPage.annotationCall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import pagecontenttester.configurations.Config;
import pagecontenttester.fetcher.FetchedPage;
import pagecontenttester.fetcher.FetchedPage.DeviceType;

public class FetcherRule extends ExternalResourceRule {

    private final PagePicker pagePicker = new PagePicker(this);

    private List<FetchedPage> fetchedPages = new ArrayList<>();
    private Config config = new Config();
    private Map<String, String> cookie = new HashMap<>();
    private String testName;

    @Override
    public Statement apply(Statement statement, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {

                List<Fetch> annotations = getAnnotations(description);
                fetchFromAnnotation(annotations);
                statement.evaluate();
            }
        };
    }

    private List<Fetch> getAnnotations(Description description) {
        List<Fetch> annotations = new LinkedList<>();

        if (hasMultipleMethodAnnotation(description)) {
            annotations.addAll(Arrays.asList(description.getAnnotation(FetchPages.class).value()));
        }
        else if (hasSingleMethodAnnotation(description)) {
            annotations.addAll(Collections.singletonList(description.getAnnotation(Fetch.class)));
        }
        else if (hasMultipleClassAnnotation(description)) {
            annotations.addAll(Arrays.asList(description.getTestClass().getAnnotation(FetchPages.class).value()));
        }
        else if (hasSingleClassAnnotation(description)) {
            annotations.addAll(Collections.singletonList(description.getTestClass().getAnnotation(Fetch.class)));
        }

        if (!annotations.isEmpty()) {
            testName = description.getDisplayName();
        }
        return annotations;
    }

    private boolean hasSingleClassAnnotation(Description description) {
        return description.getTestClass().getAnnotation(Fetch.class) != null;
    }

    private boolean hasMultipleClassAnnotation(Description description) {
        return description.getTestClass().isAnnotationPresent(FetchPages.class);
    }

    private boolean hasSingleMethodAnnotation(Description description) {
        return description.getAnnotation(Fetch.class) != null;
    }

    private boolean hasMultipleMethodAnnotation(Description description) {
        return description.getAnnotation(FetchPages.class) != null;
    }

    private void fetchFromAnnotation(List<Fetch> fetches) {
        for (Fetch fetchAnnotation : fetches) {
            Cookie[] cookieAnnotation = fetchAnnotation.setCookies();
            fetchedPages.add(annotationCall( fetchAnnotation.url(),
                                    fetchAnnotation.device(),
                                    fetchAnnotation.method(),
                                    getReferrer(fetchAnnotation),
                                    getTimeout(fetchAnnotation),
                                    getRetryCount(fetchAnnotation),
                                    getCookies(cookieAnnotation),
                                    fetchAnnotation.protocol(),
                                    getUrlPrefix(fetchAnnotation),
                                    getPort(fetchAnnotation),
                                    testName)
            );
        }
    }

    public FetchedPage get() {
        return pagePicker.get();
    }

    public FetchedPage get(int index) {
        return pagePicker.get(index);
    }

    public FetchedPage get(String urlSnippet) {
        return pagePicker.get(urlSnippet);
    }

    public FetchedPage get(DeviceType deviceType) {
        return pagePicker.get(deviceType);
    }

    public FetchedPage get(String urlSnippet, DeviceType deviceType) {
        return pagePicker.get(urlSnippet, deviceType);
    }

    List<FetchedPage> getFetchedPages() {
        return fetchedPages;
    }

    Config getConfig() {
        return config;
    }

    private int getRetryCount(Fetch fetchPage) {
        return fetchPage.retriesOnTimeout() == 0 ? config.getTimeoutMaxRetryCount() : fetchPage.retriesOnTimeout();
    }

    private int getTimeout(Fetch fetchPage) {
        return fetchPage.timeout() == 0 ? config.getTimeoutValue() : fetchPage.timeout();
    }

    private String getReferrer(Fetch fetchPage) {
        return "referrer".equals(fetchPage.referrer()) ? config.getReferrer() : fetchPage.referrer();
    }

    private String getUrlPrefix(Fetch fetchPage) {
        return fetchPage.urlPrefix().isEmpty() ? config.getUrlPrefix() : fetchPage.urlPrefix();
    }

    private String getPort(Fetch fetchPage) {
        return fetchPage.port().isEmpty() ? config.getPort() : fetchPage.port();
    }

    private Map<String, String> getCookies(Cookie[] annotationCookies) {

        HashMap<String, String> cookies = new HashMap<>();

        for (Cookie annotationCookie : annotationCookies) {
            if ("".equals(annotationCookie.name())) {
                return Collections.emptyMap();
            }
            cookies.put(annotationCookie.name(), annotationCookie.value());
        }
        return cookies;
    }
}
