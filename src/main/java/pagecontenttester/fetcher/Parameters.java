package pagecontenttester.fetcher;

import java.util.Map;

import org.jsoup.Connection;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Parameters {

    private String urlToFetch;
    private Connection.Method method;
    private String userAgent;
    private String requestBody;
    private String referrer;
    private boolean followRedirects;
    private int timeout;
    private int retriesOnTimeout;
    private Map<String, String> cookie;
    private String testName;

}
