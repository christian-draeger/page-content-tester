package pagecontenttester.fetcher;

import java.util.Map;

import org.jsoup.Connection;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
class FetchRequestParameters {

    private String urlToFetch;
    private Connection.Method method;
    private Map<String, String> requestBody;
    private FetchedPage.DeviceType device;
    private String referrer;
    private int timeout;
    private int retriesOnTimeout;
    private Map<String, String> cookie;
    private String urlPrefix;
    private String testName;
}
