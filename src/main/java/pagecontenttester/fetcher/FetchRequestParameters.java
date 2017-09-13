package pagecontenttester.fetcher;

import java.util.Map;

import org.jsoup.Connection;

import lombok.Builder;
import lombok.Value;
import pagecontenttester.annotations.Fetch.Protocol;

@Value
@Builder
public class FetchRequestParameters {

    private String urlToFetch;
    private Connection.Method method;
    private String requestBody;
    private FetchedPage.DeviceType device;
    private String referrer;
    private boolean followRedirects;
    private Protocol protocol;
    private int timeout;
    private int retriesOnTimeout;
    private Map<String, String> cookie;
    private String urlPrefix;
    private String port;
    private String testName;

    Fetcher createFetcher() {
        return Fetcher.builder()
                .method(method)
                .requestBody(requestBody)
                .deviceType(device)
                .referrer(referrer)
                .followRedirects(followRedirects)
                .timeout(timeout)
                .retriesOnTimeout(retriesOnTimeout)
                .cookie(cookie)
                .build();
    }
}
