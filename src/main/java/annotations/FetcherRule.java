package annotations;

import static fetcher.FetchedPage.annotationCall;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import fetcher.FetchedPage;
import fetcher.FetchedPage.DeviceType;

public class FetcherRule implements MethodRule {

    private FetchedPage fetchedPage;
    private List<FetchedPage> fetchedPages = new ArrayList<>();


    @Override
    public Statement apply(Statement base, FrameworkMethod method, Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {

                List<Annotation> annotations = Arrays.asList(method.getAnnotations());
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Fetch) {
                        Fetch fetchPage = (Fetch) annotation;
                        String url = fetchPage.url();
                        Method method = fetchPage.method();
                        DeviceType device = fetchPage.device();
                        String referrer = fetchPage.referrer();
                        fetchedPage = annotationCall(url, device, method, Collections.emptyMap(), referrer);
                    }
                    if (annotation instanceof FetchPages) {
                        FetchPages fetchPages = (FetchPages) annotation;
                        fetchFromAnnotation(fetchPages.value());
                    }
                }

                base.evaluate();
            }
        };
    }

    private void fetchFromAnnotation(Fetch... fetches) {
        for (Fetch fetchPage : fetches) {
            fetchedPages.add(fetch( fetchPage.url(),
                                    fetchPage.device(),
                                    fetchPage.method(),
                                    Collections.emptyMap(),
                                    fetchPage.referrer()
            ));
        }
    }

    private FetchedPage fetch(String url, DeviceType device, Method method, Map<String, String> data, String referrer) {
        return annotationCall(url, device, method, data, referrer);
    }

    public FetchedPage get() {
        return fetchedPage;
    }

    public FetchedPage get(int index) {
        return fetchedPages.get(index);
    }

    public FetchedPage get(String urlSnippet) {
        for (FetchedPage fetchedPage : fetchedPages){
            if (fetchedPage.getUrl().contains(urlSnippet)){
                return fetchedPage;
            }
        }
        return fetchedPages.get(0);
    }
}