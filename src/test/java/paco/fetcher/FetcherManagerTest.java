package paco.fetcher;

import org.jsoup.Connection;
import org.junit.Test;

import java.util.Collections;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

public class FetcherManagerTest {

    private static int COUNTER = 0;

    @Test
    public void submit() throws Exception {
        final Future<FetchedPage> future = FetcherManager.getInstance().submit(Parameters.builder().build(), getUniqueTestName());
        assertThat(future).isNotNull();
    }

    @Test
    public void singleFutureForDuplicateRequest() throws Exception {
        final Future<FetchedPage> future1 = FetcherManager.getInstance().submit(Parameters.builder().build(), getUniqueTestName());
        final Future<FetchedPage> future2 = FetcherManager.getInstance().submit(Parameters.builder().build(), getUniqueTestName());
        assertThat(future1).isSameAs(future2);
    }

    @Test
    public void dontCacheSameTest() throws Exception {
        final String testName = getUniqueTestName();
        final Future<FetchedPage> future1 = FetcherManager.getInstance().submit(Parameters.builder().build(), testName);
        final Future<FetchedPage> future2 = FetcherManager.getInstance().submit(Parameters.builder().build(), testName);
        assertThat(future1).isNotSameAs(future2);
    }

    @Test(expected = Exception.class, timeout = 1000)
    public void abortsFetchWithException() throws Exception {
        final Future<FetchedPage> future1 = FetcherManager.getInstance().submit(Parameters.builder().build(), getUniqueTestName());
        future1.get();
    }

    @Test(timeout = 5000)
    public void canFetchPage() throws Exception {
        final Future<FetchedPage> future1 = FetcherManager.getInstance()
                .submit(aValidRequest(), getUniqueTestName());
        assertThat(future1.get().getResponse().statusCode()).isEqualTo(200);
    }

    @Test
    public void fetchingTwiceCachesDuplicate() throws Exception {
        final Future<FetchedPage> future1 = FetcherManager.getInstance()
                .submit(aValidRequest(), getUniqueTestName());
        final Future<FetchedPage> future2 = FetcherManager.getInstance()
                .submit(aValidRequest(), getUniqueTestName());
        assertThat(future1.get()).isSameAs(future2.get());
    }

    @Test(timeout = 5000)
    public void dontCachePageForSameTest() throws Exception {
        final String testName = getUniqueTestName();
        final Future<FetchedPage> future1 = FetcherManager.getInstance()
                .submit(aValidRequest(), testName);
        final Future<FetchedPage> future2 = FetcherManager.getInstance()
                .submit(aValidRequest(), testName);
        assertThat(future1.get()).isNotSameAs(future2.get());
    }

    private Parameters aValidRequest() {
        return Parameters.builder()
                .urlToFetch("http://localhost:8089/example")
                .userAgent("")
                .referrer("")
                .requestBody("")
                .cookie(Collections.emptyMap())
                .headers(Collections.emptyMap())
                .method(Connection.Method.GET)
                .build();
    }

    private String getUniqueTestName() {
        return "test" + COUNTER++;
    }
}
