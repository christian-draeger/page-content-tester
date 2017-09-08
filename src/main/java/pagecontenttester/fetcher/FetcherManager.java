package pagecontenttester.fetcher;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import lombok.extern.slf4j.Slf4j;
import pagecontenttester.configurations.Config;

@Slf4j
public class FetcherManager {

    private Config config = new Config();

    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    private ConcurrentHashMap<FetchRequestParameters, CompletableFuture<FetchedPage>> requestMap = new ConcurrentHashMap<>();

    private final Set<String> calledTestMethods = new ConcurrentSkipListSet<>();

    private static FetcherManager ourInstance = new FetcherManager();

    public static FetcherManager getInstance() {
        return ourInstance;
    }

    public Future<FetchedPage> submit(FetchRequestParameters params, String testName) {
        final CompletableFuture<FetchedPage> future = new CompletableFuture<>();
        final Future<FetchedPage> oldValue = requestMap.putIfAbsent(params, future);
        if (oldValue == null || cacheIsOff() || calledTestMethods.contains(testName)) {
            final FetcherWorker fetcherWorker = new FetcherWorker(params, future);
            executorService.submit(fetcherWorker);
            calledTestMethods.add(testName);
            return future;
        } else {
            if (config.isCacheDuplicatesLogActive()) {
                log.info("duplicate call for fetched page: {}", params);
                log.info("---> will take page from cache while running test: {}", testName);
            }
            calledTestMethods.add(testName);
            return requestMap.get(params);
        }
    }

    private boolean cacheIsOff() {
        return !config.isCacheDuplicatesActive();
    }

    private FetcherManager() {
    }

    private class FetcherWorker implements Runnable {

        private final FetchRequestParameters params;
        private final CompletableFuture<FetchedPage> future;

        private FetcherWorker(FetchRequestParameters params, CompletableFuture<FetchedPage> future) {
            this.params = params;
            this.future = future;
        }

        @Override
        public void run() {
            Fetcher fetcher = params.createFetcher();
            try {
                FetchedPage fetchedPage = new FetchedPage(params.getUrlToFetch(), fetcher.fetch(params.getUrlToFetch()), params.getDevice(), params.getUrlPrefix());
                future.complete(fetchedPage);
            } catch (Throwable e) {
                future.completeExceptionally(e);
            }
        }
    }
}
