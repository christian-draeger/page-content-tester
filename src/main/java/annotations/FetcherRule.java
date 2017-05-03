package annotations;

import static fetcher.FetchedPage.annotationCall;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Connection.Method;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import configurations.Config;
import fetcher.FetchedPage;
import fetcher.FetchedPage.DeviceType;

public class FetcherRule implements MethodRule {

    private FetchedPage fetchedPage;
    private List<FetchedPage> fetchedPages = new ArrayList<>();
    private Config config = new Config();


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
                        String referrer = "referrer".equals(fetchPage.referrer()) ? config.getReferrer() : fetchPage.referrer();
                        fetchedPage = annotationCall(url, device, method, referrer);
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
                                    fetchPage.referrer()
            ));
        }
    }

    private FetchedPage fetch(String url, DeviceType device, Method method, String referrer) {
        return annotationCall(url, device, method, referrer);
    }

    public FetchedPage get() {
        return fetchedPage;
    }

    public FetchedPage get(int index) {
        return fetchedPages.get(index);
    }

    public FetchedPage get(String urlSnippet) {
        for (FetchedPage recentlyFetchedPage : fetchedPages){
            if (recentlyFetchedPage.getUrl().contains(urlSnippet)){
                return recentlyFetchedPage;
            }
        }
        return fetchedPages.get(0);
    }
}