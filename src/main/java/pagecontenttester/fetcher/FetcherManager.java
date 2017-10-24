package pagecontenttester.fetcher;

import static org.fusesource.jansi.Ansi.ansi;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pagecontenttester.configurations.GlobalConfig;

@Slf4j
class FetcherManager {

    private GlobalConfig globalConfig = new GlobalConfig();

    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    private ConcurrentHashMap<Parameters, CompletableFuture<FetchedPage>> requestMap = new ConcurrentHashMap<>();

    private final Set<String> calledTestMethods = new ConcurrentSkipListSet<>();

    private static FetcherManager ourInstance = new FetcherManager();

    static FetcherManager getInstance() {
        return ourInstance;
    }

    Future<FetchedPage> submit(Parameters params, String testName) {
        final CompletableFuture<FetchedPage> future = new CompletableFuture<>();
        final Future<FetchedPage> oldValue = requestMap.putIfAbsent(params, future);
        if (oldValue == null || cacheIsOff() || calledTestMethods.contains(testName)) {
            final FetcherWorker fetcherWorker = new FetcherWorker(params, future);
            executorService.submit(fetcherWorker);
            calledTestMethods.add(testName);
            return future;
        } else {
            if (globalConfig.isCacheDuplicatesLogActive()) {
                System.out.println("\uD83D\uDC65 " + ansi().fgBrightBlack().bold().a("duplicate call: ").reset() +
                        "fetched page will be taken from cache while executing test " + ansi().bold().a(testName).reset() + " to avoid unnecessary requests");
            }
            calledTestMethods.add(testName);
            return requestMap.get(params);
        }
    }

    private boolean cacheIsOff() {
        return !globalConfig.isCacheDuplicatesActive();
    }

    private FetcherManager() {
    }

    @AllArgsConstructor
    private class FetcherWorker implements Runnable {

        private final Parameters params;
        private final CompletableFuture<FetchedPage> future;

        @Override
        public void run() {
            Fetcher fetcher = new Fetcher();
            try {
                FetchedPage fetchedPage = new FetchedPage(
                        params.getUrlToFetch(),
                        fetcher.fetch(params),
                        params.getDevice());
                future.complete(fetchedPage);
            } catch (Throwable e) {
                future.completeExceptionally(e);
            }
        }
    }
}
