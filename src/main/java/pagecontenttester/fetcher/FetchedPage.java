package pagecontenttester.fetcher;

import static org.jsoup.Connection.Response;
import static pagecontenttester.fetcher.FetchedPage.DeviceType.MOBILE;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pagecontenttester.annotations.Fetch.Protocol;
import pagecontenttester.configurations.Config;

@Slf4j
public class FetchedPage {

    private final String url;
    private final String urlPrefix;
    private final DeviceType deviceType;
    private final Response response;
    private Optional<Document> document = Optional.empty();

    public enum DeviceType {
        DESKTOP,
        MOBILE
    }

    private static Config config = new Config();

    @SneakyThrows
    public static Page annotationCall(FetchRequestParameters params) {

        String urlWithPrefix = getUrl(params.getUrlToFetch(), params.getProtocol(), params.getUrlPrefix(), params.getPort());

        final FetchRequestParameters cacheKey = FetchRequestParameters.builder()
                .urlToFetch(urlWithPrefix)
                .method(params.getMethod())
                .requestBody(params.getRequestBody())
                .device(params.getDevice())
                .referrer(params.getReferrer())
                .followRedirects(params.isFollowRedirects())
                .timeout(params.getTimeout())
                .retriesOnTimeout(params.getRetriesOnTimeout())
                .cookie(params.getCookie())
                .urlPrefix(params.getUrlPrefix())
                .build();

        FetchedPage page = FetcherManager.getInstance().submit(cacheKey, params.getTestName()).get();

        return new FetchedPageForTest(page, params.getTestName());
    }

    public FetchedPage(String url, Response response, DeviceType deviceType, String urlPrefix) {
        this.url = url;
        this.response = response;
        this.deviceType = deviceType;
        this.urlPrefix = urlPrefix;
    }

    synchronized Document getDocument() {
        if (!document.isPresent()) {
            try {
                document = Optional.of(response.parse());
            } catch (IOException e) {
                throw new ParseDocumentException("could not parse document", e);
            }
        }
        return document.get(); //NOSONAR
    }

    public String getUrl() {
        return url;
    }


    public String getUrlPrefix() {
        return urlPrefix;
    }


    Response getResponse() {
        return response;
    }

    DeviceType getDeviceType() {
        return deviceType;
    }


    boolean isMobile() {
        return deviceType.equals(MOBILE);
    }


    public Config getConfig() {
        return config;
    }

    private static String getUrl(String url, Protocol protocol, String urlPrefix, String portFromAnnotation) {

        String prefix = urlPrefix.isEmpty() ? urlPrefix : urlPrefix + ".";

        String portFallBackCheck = StringUtils.isNotEmpty(portFromAnnotation) ? ":" + portFromAnnotation : ":" + config.getPort();
        String port = ":".equals(portFallBackCheck) ? "" : portFallBackCheck;

        String protocolValue = StringUtils.isNotEmpty(protocol.value) ? protocol.value : config.getProtocol();

        try {
            if (StringUtils.isNotEmpty(protocolValue)) {
                // TODO: string replaces are ignored atm
                url = removeProtocolFromString(url);
            }

            URL urlRaw = new URL(protocolValue + prefix + url);

            if (url.matches(".*:[0-9]{4,6}.*") && StringUtils.isEmpty(portFromAnnotation)) {
                port =  ":" + urlRaw.getPort();
            }

            return protocolValue + urlRaw.getHost() + port + urlRaw.getFile();

        } catch (MalformedURLException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    private static String removeProtocolFromString(String url) {
        url.replace("http://", "");
        url.replace("https://", "");
        url.replace("ftp://", "");
        return url;
    }

}
