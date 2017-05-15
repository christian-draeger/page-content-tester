package PageContentTester.configurations;

import static PageContentTester.fetcher.FetchedPage.DeviceType.DESKTOP;
import static PageContentTester.fetcher.FetchedPage.DeviceType.MOBILE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

public class ConfigTest {

    private static final String DESKTOP_USER_AGENT = "Mozilla/5.0 (X11\\; Ubuntu\\; Linux x86_64\\; rv\\:25.0)";
    private static final String MOBILE_USER_AGENT = "Mozilla/5.0 (iPhone\\; CPU iPhone OS 6_1_4 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10B350 Safari/8536.25";

    Config config = new Config();

    @Test
    public void should_return_desktop_user_agent() {
        assertThat(config.getUserAgent(MOBILE), is(MOBILE_USER_AGENT));
    }

    @Test
    public void should_return_mobile_user_agent() {
        assertThat(config.getUserAgent(DESKTOP), is(DESKTOP_USER_AGENT));
    }

    @Test
    public void should_return_max_retry_count() {
        assertThat(config.getTimeoutMaxRetryCount(), is(3));
    }

    @Test
    public void should_return_timeout_value() {
        assertThat(config.getTimeoutValue(), is(10000));
    }

    @Test
    public void should_return_ignore_contenttype_config() {
        assertThat(config.isIgnoringContentType(), is(true));
    }

    @Test
    public void should_return_follow_redirects_config() {
        assertThat(config.isFollowingRedirects(), is(true));
    }

    @Test
    public void should_return_caching_config() {
        assertThat(config.isCacheDuplicatesActive(), is(true));
    }
}
