package paco.fetcher;

import lombok.SneakyThrows;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Optional;

import static org.jsoup.Connection.Response;

public class FetchedPage {

    private final String url;
    private final Response response;
    private Optional<Document> document = Optional.empty();

    public FetchedPage(String url, Response response) {
        this.url = url;
        this.response = response;
    }

    @SneakyThrows
    public static Page fetcher(Parameters params) {

        final Parameters cacheKey = Parameters.builder()
                .urlToFetch(params.getUrlToFetch())
                .method(params.getMethod())
                .requestBody(params.getRequestBody())
                .proxy(params.getProxy())
                .userAgent(params.getUserAgent())
                .referrer(params.getReferrer())
                .cacheDuplicate(params.isCacheDuplicate())
                .followRedirects(params.isFollowRedirects())
                .timeout(params.getTimeout())
                .retriesOnTimeout(params.getRetriesOnTimeout())
                .cookie(params.getCookie())
                .headers(params.getHeaders())
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

    Response getResponse() {
        return response;
    }

}
