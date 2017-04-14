package fetcher;

import static fetcher.FetchedPage.DeviceType.DESKTOP;
import static fetcher.FetchedPage.DeviceType.MOBILE;
import static fetcher.FetchedPage.call;
import static fetcher.FetchedPage.fetchPage;
import static fetcher.FetchedPage.fetchPageAsMobileDevice;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.jsoup.Connection.Method.POST;

import java.util.Collections;

import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import annotations.Fetch;
import runner.PageContentTester;

public class FetchedPageTest extends PageContentTester {

    private static FetchedPage fetchedPage;
    private static FetchedPage fetchedMobilePage;

    private static final String GITHUB_URL = "https://github.com/christian-draeger";
    private static final String GOOGLE_URL = "http://www.google.de";
    private static final String VALID_SELECTOR = "h1";

    @BeforeClass
    public static void fetcher() {
        fetchedPage = fetchPage(GITHUB_URL);
        fetchedMobilePage = fetchPageAsMobileDevice(GITHUB_URL);
    }

    @Test
    public void fetcher_should_return_fetched_desktop_page_for_valid_url() {
        assertThat(fetchedPage.isMobile(), is(false));
    }

    @Test
    public void fetcher_should_return_fetched_mobile_page_for_valid_url() {
        assertThat(fetchedMobilePage.isMobile(), is(true));
    }

    @Test
    public void fetcher_should_return_referrer() {
        assertThat(fetchedPage.getConfig().getReferrer(), equalTo("http://www.google.com"));
    }

    @Test
    public void fetcher_should_return_cookie_value() {
        assertThat(fetchedPage.getCookieValue("logged_in"), equalTo("no"));
    }

    @Test
    public void fetcher_should_return_cookies() {
        assertThat(fetchedPage.getCookies(), hasEntry("logged_in", "no"));
    }

    @Test
    public void fetcher_should_return_element() {
        assertThat(fetchedPage.getElement(VALID_SELECTOR).hasText(), is(true));
    }

    @Test
    public void fetcher_should_return_status_code() {
        assertThat(fetchedPage.getStatusCode(), is(200));
    }

    @Test
    public void fetcher_should_return_true_if_certain_element_is_present() {
        assertThat(fetchedPage.isElementPresent(VALID_SELECTOR), is(true));
    }

    @Test
    @Fetch(url = GITHUB_URL)
    public void fetcher_should_return_count_of_certain_element() {
        assertThat(page.get().getElementCount(VALID_SELECTOR), is(1));
    }

    @Test
    @Fetch(url = GITHUB_URL)
    @Fetch(url = GOOGLE_URL)
    public void fetch_multiple_pages_via_annotation_and_get_pages_by_index() {
        assertThat(page.get(0).isElementPresent("h1"), is(true));
        assertThat(page.get(0).getUrl(), equalTo(GITHUB_URL));

        assertThat(page.get(1).isElementPresent("#footer"), is(true));
        assertThat(page.get(1).getUrl(), equalTo(GOOGLE_URL));
    }

    @Test
    @Fetch(url = GITHUB_URL)
    @Fetch(url = GOOGLE_URL)
    public void fetch_multiple_pages_via_annotation_and_get_pages_by_url_snippet() {
        assertThat(page.get("github").isElementPresent("h1"), is(true));
        assertThat(page.get("google").isElementPresent("#footer"), is(true));
    }

    @Test
    @Fetch(url = "http://whatsmyuseragent.org/", device = MOBILE)
    public void fetch_as_mobile_device_by_annotation() {
        String ua = page.get().getElement("p.intro-text").text();
        assertThat(ua, containsString(page.get().getConfig().getUserAgent(MOBILE)));
    }

    @Test
    public void do_post_request_and_check_response() throws Exception {
        JSONObject responseBody = call("http://httpbin.org/post", DESKTOP, POST, Collections.emptyMap()).getJsonResponse();
        assertThat(responseBody.get("data"), equalTo(""));
    }

    @Test
    public void fetch_multiple_pages_in_test_method() {
        FetchedPage github = fetchPage(GITHUB_URL);
        FetchedPage google = fetchPage(GOOGLE_URL);

        assertThat(github.isElementPresent("h1"), is(true));
        assertThat(github.getUrl(), equalTo(GITHUB_URL));

        assertThat(google.isElementPresent("#footer"), is(true));
        assertThat(google.getUrl(), equalTo(GOOGLE_URL));
    }

    @Test
    public void fetcher_should_return_true_for_certain_count_of_certain_element() {
        assertThat(fetchedPage.isElementPresentNthTimes(VALID_SELECTOR, 1), is(true));
    }

}
