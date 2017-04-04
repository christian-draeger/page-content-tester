package fetcher;

import static fetcher.FetchedPage.fetchPage;
import static fetcher.FetchedPage.fetchPageAsMobileDevice;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;

import org.jsoup.Connection;
import org.junit.BeforeClass;
import org.junit.Test;

import runner.PageContentTest;

public class FetchedPageTest extends PageContentTest {

    private static FetchedPage fetchedPage;
    private static FetchedPage fetchedMobilePage;
    private static FetchedPage fetchedAjaxPost;

    @BeforeClass
    public static void fetcher() {
        fetchedPage = fetchPage(VALID_URL);
        fetchedMobilePage = fetchPageAsMobileDevice(VALID_URL);
        fetchedAjaxPost = fetchPriceWatcherAjaxResponse();
    }

    private static final String VALID_URL = "https://github.com/christian-draeger";

    @Test
    public void fetcher_should_return_fetched_desktop_page_for_valid_url() {
        assertThat(fetchedPage.isMobile(), is(false));
    }

    @Test
    public void fetcher_should_return_referrer() {
        assertThat(fetchedPage.getReferrer(), equalTo("http://www.google.com"));
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
    public void fetcher_should_return_fetched_mobile_page_for_valid_url() {
        assertThat(fetchedMobilePage.isMobile(), is(true));
    }

    @Test
    public void fetcher_should_return_ajax_response() {
        assertThat(fetchedAjaxPost.getPageBody(), containsString("awp"));
    }

    private static FetchedPage fetchPriceWatcherAjaxResponse() {
        HashMap<String, String> data = new HashMap<>();
        data.put("targetPrice", "0");
        data.put("email", "jon.doe@idealo.de");
        data.put("optin", "on");
        return FetchedPage.performAjaxRequest("https://www.idealo.de/mvc/RwdPostAddWatchedProduct/3423613", Connection.Method.POST, data);
    }
}
