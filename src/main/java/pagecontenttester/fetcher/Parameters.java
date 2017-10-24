package pagecontenttester.fetcher;

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
    private Protocol protocol;
    private String urlPrefix;
    private String port;
    private Connection.Method method;
    private FetchedPage.DeviceType device;
    private String userAgent;
    private String requestBody;
    private String referrer;
    private boolean followRedirects;
    private int timeout;
    private int retriesOnTimeout;
    private Map<String, String> cookie;
    private String testName;

}
