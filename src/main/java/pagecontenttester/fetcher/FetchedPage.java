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
import pagecontenttester.configurations.GlobalConfig;

@Slf4j
public class FetchedPage {

    private final String url;
    private final String urlPrefix;
    private final DeviceType deviceType;
    private final Response response;
    private Optional<Document> document = Optional.empty();

    public FetchedPage(String url, Response response, DeviceType deviceType, String urlPrefix) {
        this.url = url;
        this.response = response;
        this.deviceType = deviceType;
        this.urlPrefix = urlPrefix;
    }

    public enum DeviceType {
        DESKTOP,
        MOBILE
    }

    private static GlobalConfig globalConfig = new GlobalConfig();

    @SneakyThrows
    public static Page annotationCall(Parameters params) {

        String urlWithPrefix = getUrl(params.getUrlToFetch(), params.getProtocol(), params.getUrlPrefix(), params.getPort());

        final Parameters cacheKey = Parameters.builder()
                .urlToFetch(urlWithPrefix)
                .method(params.getMethod())
                .requestBody(params.getRequestBody())
                .device(params.getDevice())
                .userAgent(params.getUserAgent())
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


    public GlobalConfig getConfig() {
        return globalConfig;
    }

    private static String getUrl(String url, Protocol protocol, String urlPrefix, String portFromAnnotation) {

        String prefix = urlPrefix.isEmpty() ? urlPrefix : urlPrefix + ".";

        String portFallBackCheck = StringUtils.isNotEmpty(portFromAnnotation) ? ":" + portFromAnnotation : ":" + globalConfig.getPort();
        String port = ":".equals(portFallBackCheck) ? "" : portFallBackCheck;

        String protocolValue = StringUtils.isNotEmpty(protocol.value) ? protocol.value : globalConfig.getProtocol();

        try {
            if (StringUtils.isNotEmpty(protocolValue)) {
                // TODO: string replaces are ignored atm
                url = removeProtocolFromString(url);
            }

            URL urlRaw = new URL(protocolValue + prefix + url);

            if (url.matches(".*:[0-9]{2,6}.*") && StringUtils.isEmpty(portFromAnnotation)) {
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
