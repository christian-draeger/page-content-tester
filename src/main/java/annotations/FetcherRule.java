package annotations;

import static fetcher.FetchedPage.fetchPage;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import fetcher.FetchedPage;

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
                    if (annotation instanceof FetchPage) {
                        FetchPage fetchPage = (FetchPage) annotation;
                        fetchedPage = fetch(fetchPage.value());
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

    private void fetchFromAnnotation(FetchPage... fetches) {
        for (FetchPage fetchPage : fetches) {
            fetchedPages.add(fetch(fetchPage.value()));
        }
    }

    private FetchedPage fetch(String url) {
        return fetchPage(url);
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