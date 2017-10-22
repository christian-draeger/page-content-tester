package pagecontenttester.annotations;

import static pagecontenttester.fetcher.FetchedPage.annotationCall;

import java.util.ArrayList;
import java.util.List;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import pagecontenttester.configurations.ConfigResolver;
import pagecontenttester.configurations.GlobalConfig;
import pagecontenttester.fetcher.FetchedPage.DeviceType;
import pagecontenttester.fetcher.Page;
import pagecontenttester.fetcher.Parameters;

public class FetcherRule implements TestRule {

    private final PagePicker pagePicker = new PagePicker(this);
    private final AnnotationCollector annotationCollector = new AnnotationCollector();

    private List<Page> fetchedPages = new ArrayList<>();
    private GlobalConfig globalConfig = new GlobalConfig();
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
                fetchFromAnnotation(annotations, testName);
                statement.evaluate();
            }
        };
    }

    private void fetchFromAnnotation(List<Fetch> fetchAnnotations, String testName) {
        for (Fetch fetchAnnotation : fetchAnnotations) {
            Parameters parameters = new ConfigResolver(fetchAnnotation, testName).getRequestSpecificParams();
            final Page fetchedPage = annotationCall(parameters);
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

    GlobalConfig getGlobalConfig() {
        return globalConfig;
    }
}
