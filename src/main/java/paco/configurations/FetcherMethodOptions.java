package paco.configurations;

import paco.fetcher.Parameters;

import java.util.Collections;

import static org.jsoup.Connection.Method.GET;
import static paco.annotations.Fetch.Device.DESKTOP;

public class FetcherMethodOptions { //NOSONAR

    private static GlobalConfig config = new GlobalConfig();

    /**
     * this will return a build that is pre-filled with data from GlobalConfig
     * @return Parameters.ParametersBuilder
     */
    public static Parameters.ParametersBuilder params() {
        return Parameters.builder()
                .urlToFetch("")
                .userAgent(config.getUserAgent(DESKTOP))
                .method(GET)
                .requestBody("")
                .referrer(config.getReferrer())
                .followRedirects(config.isFollowingRedirects())
                .cacheDuplicate(config.isCacheDuplicatesActive())
                .timeout(config.getTimeoutValue())
                .retriesOnTimeout(config.getTimeoutMaxRetryCount())
                .testName("")
                .headers(Collections.emptyMap())
                .proxy(Collections.emptyMap())
                .cookie(Collections.emptyMap());
    }
}
