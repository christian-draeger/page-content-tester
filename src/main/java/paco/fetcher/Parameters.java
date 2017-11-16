package paco.fetcher;

import lombok.Builder;
import lombok.Value;
import org.jsoup.Connection;

import java.util.Map;

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
    private Map<String, String> headers;
    private Map<String, Integer> proxy;
    private String testName;

}
