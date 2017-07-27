package pagecontenttester.configurations;

import static org.assertj.core.api.Assertions.assertThat;
import static pagecontenttester.fetcher.FetchedPage.DeviceType.DESKTOP;
import static pagecontenttester.fetcher.FetchedPage.DeviceType.MOBILE;

import org.junit.Test;

public class ConfigTest {

    private static final String DESKTOP_USER_AGENT = "Mozilla/5.0 (X11\\; Ubuntu\\; Linux x86_64\\; rv\\:25.0)";
    private static final String MOBILE_USER_AGENT = "Mozilla/5.0 (iPhone\\; CPU iPhone OS 6_1_4 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10B350 Safari/8536.25";

    private Config config = new Config();

    @Test
    public void should_return_desktop_user_agent() {
        assertThat(config.getUserAgent(MOBILE)).isEqualTo(MOBILE_USER_AGENT);
    }

    @Test
    public void should_return_mobile_user_agent() {
        assertThat(config.getUserAgent(DESKTOP)).isEqualTo(DESKTOP_USER_AGENT);
    }

    @Test
    public void should_return_max_retry_count() {
        assertThat(config.getTimeoutMaxRetryCount()).isEqualTo(3);
    }

    @Test
    public void should_return_timeout_value() {
        assertThat(config.getTimeoutValue()).isEqualTo(10000);
    }

    @Test
    public void should_return_url_prefix() {
        assertThat(config.getUrlPrefix()).isEmpty();
    }

    @Test
    public void should_return_ignore_contenttype_config() {
        assertThat(config.isIgnoringContentType()).isTrue();
    }

    @Test
    public void should_return_follow_redirects_config() {
        assertThat(config.isFollowingRedirects()).isTrue();
    }

    @Test
    public void should_return_caching_config() {
        assertThat(config.isCacheDuplicatesActive()).isTrue();
    }

}
