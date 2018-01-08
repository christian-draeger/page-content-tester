#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package test.java.testcases;

import org.junit.Test;
import paco.annotations.Fetch;
import paco.runner.Paco;
import urls.Urls;

import static org.assertj.core.api.Assertions.assertThat;
import static paco.annotations.Fetch.Device.DESKTOP;
import static paco.annotations.Fetch.Device.MOBILE;

/**
 * This test class exemplifies the possibility of fetching multiple pages and check
 * the results within one test method. This is especially helpful if you either want to
 *      - compare the content from two different pages
 *      - execute a test against multiple pages
 *      - execute a test against same page that have been fetched with different parameters
 *        (e.g. fetching the google markup using a desktop user-agent as well as using a mobile user-agent)
 *
 * if you fetched multiple pages either using class annotations or method annotations
 * it is possible to pick them by:
 *      - index         (see {@link #can_fetch_from_class_and_pick_by_index()}
 *                       and {@link #can_fetch_from_method_and_pick_by_index()})
 *      - url-snippet   (see {@link #can_fetch_from_class_and_pick_by_snippet()}
 *                       and {@link #can_fetch_from_method_and_pick_by_snippet()})
 *
 * NOTE: in the example class the multiple fetches for the same URL will be cached by default
 * to reduce the amount of unnecessary calls and speed optimization. if you don't want them to be cached
 * you can add 'cacheDuplicates=false' to the paco.properties file.
 *
 */
@Fetch(url = Urls.GITHUB)
@Fetch(url = Urls.GOOGLE)
@Fetch(url = Urls.GOOGLE, device = MOBILE)
public class FetchMultiplePagesInOneTest extends Paco {

    @Test
    public void can_fetch_from_class_and_pick_by_index() {
        assertThat(page.get(0).getTitle()).containsIgnoringCase("GitHub");
        assertThat(page.get(1).getTitle()).containsIgnoringCase("Google");
        assertThat(page.get(2).getTitle()).containsIgnoringCase("Google");
    }

    @Test
    public void can_fetch_from_class_and_pick_by_snippet() {
        assertThat(page.get("github").getTitle()).containsIgnoringCase("GitHub");
        assertThat(page.get("google").getTitle()).containsIgnoringCase("Google");
        assertThat(page.get("google", MOBILE).getTitle()).containsIgnoringCase("Google");
    }

    @Test
    @Fetch(url = Urls.GITHUB)
    @Fetch(url = Urls.GOOGLE)
    @Fetch(url = Urls.GOOGLE, device = MOBILE)
    public void can_fetch_from_method_and_pick_by_index() {
        assertThat(page.get(0).getTitle()).containsIgnoringCase("GitHub");
        assertThat(page.get(1).getTitle()).containsIgnoringCase("Google");
        assertThat(page.get(2).getTitle()).containsIgnoringCase("Google");
    }

    @Test
    @Fetch(url = Urls.GITHUB)
    @Fetch(url = Urls.GOOGLE)
    @Fetch(url = Urls.GOOGLE, device = MOBILE)
    public void can_fetch_from_method_and_pick_by_snippet() {
        assertThat(page.get("github").getTitle()).containsIgnoringCase("GitHub");
        assertThat(page.get("google").getTitle()).containsIgnoringCase("Google");
        assertThat(page.get("google", MOBILE).getTitle()).containsIgnoringCase("Google");
    }

    @Test
    @Fetch(url = Urls.GOOGLE)
    @Fetch(url = Urls.GOOGLE, device = MOBILE)
    public void can_fetch_from_method_and_pick_by_user_agent() {
        assertThat(page.get(DESKTOP).getTitle()).containsIgnoringCase("Google");
        assertThat(page.get(MOBILE).getTitle()).containsIgnoringCase("Google");
    }

    @Test
    @Fetch(url = Urls.GITHUB)
    @Fetch(url = Urls.GOOGLE)
    @Fetch(url = Urls.GOOGLE, device = MOBILE)
    public void can_fetch_from_method_and_pick_by_snippet_and_user_agent() {
        assertThat(page.get("github").getTitle()).containsIgnoringCase("GitHub");
        assertThat(page.get("google").getTitle()).containsIgnoringCase("Google");
        assertThat(page.get("google", MOBILE).getTitle()).containsIgnoringCase("Google");
    }
}