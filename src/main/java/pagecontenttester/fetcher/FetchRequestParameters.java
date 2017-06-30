package pagecontenttester.fetcher;

import java.util.Map;

import org.jsoup.Connection;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FetchRequestParameters {

    String urlToFetch;
    Connection.Method method;
    Map<String, String> requestBody;
    FetchedPage.DeviceType device;
    String referrer;
    int timeout;
    int retriesOnTimeout;
    Map<String, String> cookie;
    String urlPrefix;
    String testName;
}
