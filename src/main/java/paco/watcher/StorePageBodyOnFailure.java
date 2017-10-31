package paco.watcher;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import paco.fetcher.Page;

public class StorePageBodyOnFailure extends TestWatcher {
    private final Page fetchedPage;

    public StorePageBodyOnFailure(Page fetchedPage) {
        this.fetchedPage = fetchedPage;
    }

    @Override
    protected void failed(final Throwable e, final Description description) {
        if (e.getMessage().contains("BROWSER_TIMEOUT")) {
            fetchedPage.store("failing-tests");
        }
    }
}
