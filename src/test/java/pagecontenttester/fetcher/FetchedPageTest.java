package pagecontenttester.fetcher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.jsoup.Connection.Method.POST;
import static pagecontenttester.fetcher.FetchedPage.DeviceType.DESKTOP;
import static pagecontenttester.fetcher.FetchedPage.DeviceType.MOBILE;
import static pagecontenttester.fetcher.FetchedPage.call;
import static pagecontenttester.fetcher.FetchedPage.fetchPage;
import static pagecontenttester.fetcher.FetchedPage.fetchPageAsMobileDevice;

import java.util.Collections;

import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import pagecontenttester.annotations.Cookie;
import pagecontenttester.annotations.Fetch;
import pagecontenttester.annotations.GetFetchedPageException;
import pagecontenttester.runner.PageContentTester;

@Fetch(url= "www.google.de")
public class FetchedPageTest extends PageContentTester {

    private static Page fetchedPage;
    private static Page fetchedMobilePage;

    private static final String GITHUB_URL = "github.com/christian-draeger";
    private static final String GOOGLE_URL = "www.google.de";
    private static final String VALID_SELECTOR = "h1";

    @BeforeClass
    public static void fetcher() {
        fetchedPage = fetchPage("http://github.com/christian-draeger");
        fetchedMobilePage = fetchPageAsMobileDevice("http://www.google.de");
    }

    @Test
    public void can_fetch_from_class_annotation() {
        assertThat(page.get().getUrl(), containsString("google"));
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
    public void fetcher_should_return_device_type_for_fetched_mobile_page() {
        assertThat(fetchedMobilePage.getDeviceType(), equalTo(MOBILE));
    }

    @Test
    public void fetcher_should_return_device_type_for_fetched_desktop_page() {
        assertThat(fetchedPage.getDeviceType(), equalTo(DESKTOP));
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
    public void fetcher_should_return_cookie() {
        assertThat(fetchedPage.hasCookie("logged_in"), is(true));
    }

    @Test
    public void fetcher_should_return_content_type() {
        assertThat(fetchedPage.getContentType(), equalTo("text/html; charset=utf-8"));
    }

    @Test
    public void fetcher_should_return_element() {
        assertThat(fetchedPage.getElement(VALID_SELECTOR).hasText(), is(true));
    }

    @Test
    public void fetcher_should_return_page_body() {
        assertThat(fetchedPage.getPageBody(), containsString("<!DOCTYPE html>"));
        assertThat(fetchedPage.getPageBody(), containsString("christian-draeger"));
    }

    @Test
    public void fetcher_should_return_status_message() {
        assertThat(fetchedPage.getStatusMessage(), equalTo("OK"));
    }

    @Test
    public void fetcher_should_return_status_code() {
        assertThat(fetchedPage.getStatusCode(), is(200));
    }

    @Test
    public void should_return_true_if_certain_element_is_present() {
        assertThat(fetchedPage.isElementPresent(VALID_SELECTOR), is(true));
    }

    @Test
    public void should_return_false_if_certain_element_is_not_present() {
        assertThat(fetchedPage.isElementPresent("dgfhkdgs"), is(false));
    }

    @Test
    public void fetcher_should_return_elements_by_selector() {
        assertThat(fetchedPage.getElements(VALID_SELECTOR).size(), greaterThanOrEqualTo(1));
    }

    @Test
    public void fetcher_should_return_last_matching_element_by_selector() {
        assertThat(fetchedPage.getElementLastOf(VALID_SELECTOR).text(), containsString("christian-draeger"));
    }

    @Test
    public void fetcher_should_return_nth_matching_element_by_selector() {
        assertThat(fetchedPage.getElement(VALID_SELECTOR, 0).text(), containsString("christian-draeger"));
    }

    @Test
    @Fetch(url = GITHUB_URL)
    public void fetcher_should_return_count_of_certain_element() {
        assertThat(page.get().getElementCount(VALID_SELECTOR), is(1));
    }

    @Test
    public void fetcher_should_return_headers() {
        assertThat(fetchedPage.getHeaders(), hasEntry("Server", "GitHub.com"));
    }

    @Test
    public void fetcher_should_return_certain_header() {
        assertThat(fetchedPage.getHeader("Server"), equalTo("GitHub.com"));
    }

    @Test
    public void fetcher_should_check_is_certain_header_is_present() {
        assertThat(fetchedPage.hasHeader("Server"), is(true));
    }

    @Test
    @Fetch(url = GITHUB_URL)
    @Fetch(url = GOOGLE_URL)
    public void fetch_multiple_pages_via_annotation_and_get_pages_by_index() {
        assertThat(page.get(0).isElementPresent("h1"), is(true));
        assertThat(page.get(0).getUrl(), equalTo("http://" + GITHUB_URL));

        assertThat(page.get(1).isElementPresent("#footer"), is(true));
        assertThat(page.get(1).getUrl(), equalTo("http://" + GOOGLE_URL));
    }

    @Test
    @Fetch(url = GITHUB_URL)
    @Fetch(url = GOOGLE_URL)
    public void fetch_multiple_pages_via_annotation_and_get_pages_by_url_snippet() {
        assertThat(page.get("github").isElementPresent("h1"), is(true));
        assertThat(page.get("google").isElementPresent("#footer"), is(true));
    }

    @Test(expected = GetFetchedPageException.class)
    @Fetch(url = GITHUB_URL)
    public void fetch_page_via_annotation_and_try_to_get_fetched_page_by_unknown_url_snippet() {
        page.get("unknown");
    }

    @Test
    @Fetch(protocol = Fetch.Protocol.HTTPS, urlPrefix = "en", url = "wikipedia.org")
    public void fetch_page_via_annotation_and_build_url() {
        assertThat(page.get().getUrl(), equalTo("https://en.wikipedia.org"));
    }

    @Test(expected = GetFetchedPageException.class)
    @Fetch(url = GITHUB_URL)
    public void fetch_page_via_annotation_and_try_to_get_fetched_page_by_invalid_index() {
        page.get(1);
    }

    @Test
    @Fetch(url = GITHUB_URL)
    @Fetch(url = GITHUB_URL, device = MOBILE)
    public void fetch_as_desktop_and_mobile_device_by_annotation() {
        assertThat(page.get(GITHUB_URL, DESKTOP).getDeviceType(), equalTo(DESKTOP));
        assertThat(page.get(GITHUB_URL, MOBILE).getDeviceType(), equalTo(MOBILE));
    }

    @Test
    @Fetch(url = GITHUB_URL)
    @Fetch(url = GITHUB_URL, device = MOBILE)
    public void can_pick_fetched_page_by_device_type() {
        assertThat(page.get(DESKTOP).getDeviceType(), equalTo(DESKTOP));
        assertThat(page.get(MOBILE).getDeviceType(), equalTo(MOBILE));
    }


    @Test
    @Fetch(url = "whatsmyuseragent.org/", device = MOBILE)
    public void fetch_as_mobile_device_by_annotation() {
        String ua = page.get().getElement("p.intro-text").text();
        assertThat(ua, containsString(page.get().getConfig().getUserAgent(MOBILE)));
    }

    @Test
    @Fetch(url = "www.whatismyreferer.com/", referrer = "my.custom.referrer")
    public void fetch_page_by_setting_custom_referrer() {
        String referrer = page.get().getElement("strong").text();
        assertThat(referrer, equalTo("my.custom.referrer"));
    }

    @Test
    @Fetch(url = "www.whatismyreferer.com/")
    public void fetch_page_should_use_referrer_from_properties_by_default() {
        String referrer = page.get().getElement("strong").text();
        assertThat(referrer, equalTo(config.getReferrer()));
    }

    @Ignore("html-kit is dowm atm")
    @Test
    @Fetch( url = "www.html-kit.com/tools/cookietester/",
            setCookies = @Cookie(name = "page-content-tester", value = "wtf-666"))
    public void can_set_cookie_via_annotation() throws Exception {
        assertThat(page.get().getDocument().body().text(),
                both(containsString("page-content-tester"))
                        .and(containsString("wtf-666")));
    }

    @Ignore("html-kit is dowm atm")
    @Test
    @Fetch( url = "www.html-kit.com/tools/cookietester/",
            setCookies = {  @Cookie(name = "page-content-tester", value = "wtf-666"),
                            @Cookie(name = "some-other-cookie", value = "666-wtf") })
    public void can_set__multiple_cookies_via_annotation() throws Exception {
        String body = page.get().getDocument().body().text();
        assertThat(body, both(containsString("page-content-tester"))
                        .and(containsString("wtf-666")));
        assertThat(body, both(containsString("some-other-cookie"))
                        .and(containsString("666-wtf")));
    }

    @Test
    public void do_post_request_and_check_response() throws Exception {
        JSONObject responseBody = call("http://httpbin.org/post", POST, Collections.emptyMap()).getJsonResponse();
        assertThat(responseBody.get("data"), equalTo(""));
    }

    @Test
    public void fetch_multiple_pages_in_test_method() {
        FetchedPage github = fetchPage("http://github.com/christian-draeger");
        FetchedPage google = fetchPage("http://www.google.de");

        assertThat(github.isElementPresent("h1"), is(true));
        assertThat(github.getUrl(), equalTo("http://github.com/christian-draeger"));

        assertThat(google.isElementPresent("#footer"), is(true));
        assertThat(google.getUrl(), equalTo("http://www.google.de"));
    }

    @Test
    public void should_return_true_for_certain_count_of_certain_element() {
        assertThat(fetchedPage.isElementPresentNthTimes(VALID_SELECTOR, 1), is(true));
    }

    @Test
    public void should_return_false_for_invalid_count_of_certain_element() {
        assertThat(fetchedPage.isElementPresentNthTimes(VALID_SELECTOR, 100), is(false));
    }

}
