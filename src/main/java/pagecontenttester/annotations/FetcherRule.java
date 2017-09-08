package pagecontenttester.annotations;

import static pagecontenttester.fetcher.FetchedPage.annotationCall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import pagecontenttester.configurations.Config;
import pagecontenttester.fetcher.FetchedPage.DeviceType;
import pagecontenttester.fetcher.Page;

public class FetcherRule implements TestRule {

    private final PagePicker pagePicker = new PagePicker(this);
    private final AnnotationCollector annotationCollector = new AnnotationCollector();

    private List<Page> fetchedPages = new ArrayList<>();
    private Config config = new Config();
    private String testName;

    @Override
    public Statement apply(Statement statement, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {

                List<Fetch> annotations = annotationCollector.getAnnotations(description);
                if (!annotations.isEmpty()) {
                    testName = description.getDisplayName();
                }
                fetchFromAnnotation(annotations);
                statement.evaluate();
            }
        };
    }

    private void fetchFromAnnotation(List<Fetch> fetchAnnotations) {
        for (Fetch fetchAnnotation : fetchAnnotations) {
            Cookie[] cookieAnnotation = fetchAnnotation.setCookies();
            final Page fetchedPage = annotationCall(fetchAnnotation.url(),
                    fetchAnnotation.device(),
                    fetchAnnotation.method(),
                    getReferrer(fetchAnnotation),
                    getTimeout(fetchAnnotation),
                    getRetryCount(fetchAnnotation),
                    getCookies(cookieAnnotation),
                    fetchAnnotation.protocol(),
                    getUrlPrefix(fetchAnnotation),
                    getPort(fetchAnnotation),
                    testName);
            fetchedPages.add(fetchedPage);
        }
    }

    public Page get() {
        return pagePicker.get(0);
    }

    public Page get(int index) {
        return pagePicker.get(index);
    }

    public Page get(String urlSnippet) {
        return pagePicker.get(urlSnippet);
    }

    public Page get(DeviceType deviceType) {
        return pagePicker.get(deviceType);
    }

    public Page get(String urlSnippet, DeviceType deviceType) {
        return pagePicker.get(urlSnippet, deviceType);
    }

    List<Page> getFetchedPages() {
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
