package paco.configurations;

import static org.jsoup.Connection.Method.GET;
import static paco.annotations.Fetch.Device.DESKTOP;

import java.util.Collections;

import paco.fetcher.Parameters;

public class FetcherMethodOptions {

    private FetcherMethodOptions(){}

    private static GlobalConfig config = new GlobalConfig();

    public static Parameters.ParametersBuilder params() {
        return Parameters.builder()
                .urlToFetch("")
                .userAgent(config.getUserAgent(DESKTOP))
                .method(GET)
                .referrer(config.getReferrer())
                .followRedirects(config.isFollowingRedirects())
                .timeout(config.getTimeoutValue())
                .retriesOnTimeout(config.getTimeoutMaxRetryCount())
                .testName("")
                .cookie(Collections.emptyMap());
    }
}
