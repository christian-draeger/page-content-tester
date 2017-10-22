package pagecontenttester.fetcher;

import static org.jsoup.Connection.Method.GET;
import static pagecontenttester.annotations.Fetch.Protocol.HTTP;
import static pagecontenttester.fetcher.FetchedPage.DeviceType.DESKTOP;

import java.util.Collections;
import java.util.Map;

import org.jsoup.Connection;

import lombok.Builder;
import lombok.Value;
import pagecontenttester.annotations.Fetch.Protocol;
import pagecontenttester.configurations.GlobalConfig;

@Value
@Builder
public class Parameters {

    private static final GlobalConfig GLOBAL_CONFIG = new GlobalConfig();

    private String urlToFetch;
    @Builder.Default
    private Protocol protocol = HTTP;
    @Builder.Default
    private String urlPrefix = GLOBAL_CONFIG.getUrlPrefix();
    @Builder.Default
    private String port = GLOBAL_CONFIG.getPort();
    @Builder.Default
    private Connection.Method method = GET;
    @Builder.Default
    private FetchedPage.DeviceType device = DESKTOP;
    @Builder.Default
    private String userAgent = GLOBAL_CONFIG.getUserAgent(DESKTOP);
    @Builder.Default
    private String requestBody = "";
    @Builder.Default
    private String referrer = GLOBAL_CONFIG.getReferrer();
    @Builder.Default
    private boolean followRedirects = GLOBAL_CONFIG.isFollowingRedirects();
    @Builder.Default
    private int timeout = GLOBAL_CONFIG.getTimeoutValue();
    @Builder.Default
    private int retriesOnTimeout = GLOBAL_CONFIG.getTimeoutMaxRetryCount();
    @Builder.Default
    private Map<String, String> cookie = Collections.emptyMap();
    @Builder.Default
    private String testName = "xxx";

}
