package paco.fetcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jsoup.Connection.Method.POST;
import static paco.configurations.FetcherMethodOptions.params;
import static paco.fetcher.FetchedPage.fetcher;

import org.junit.Test;

import paco.runner.Paco;

public class UsingParamsBuilderTest extends Paco {

    @Test
    public void get_page_and_check_title() {
        final Page page = fetcher(params().urlToFetch("http://localhost:8089/example").build());
        assertThat(page.getTitle()).isEqualTo("i'm the title");
    }

    @Test
    public void post_and_check_response_body() {
        final Page page = fetcher(params().urlToFetch("http://localhost:8089/example").method(POST).build());
        assertThat(page.getContentType()).isEqualTo("application/json");
        assertThat(page.getJsonResponse().get("data")).isEqualTo("some value");
    }
}
