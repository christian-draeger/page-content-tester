package pagecontenttester.watcher;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import pagecontenttester.fetcher.FetchedPage;

public class StorePageBodyOnFailure extends TestWatcher {
    private final FetchedPage fetchedPage;

    public StorePageBodyOnFailure(FetchedPage fetchedPage) {
        this.fetchedPage = fetchedPage;
    }

    @Override
    protected void failed(final Throwable e, final Description description) {
        if (e.getMessage().contains("BROWSER_TIMEOUT")) {
            fetchedPage.store("failing-tests");
        }
    }
}
