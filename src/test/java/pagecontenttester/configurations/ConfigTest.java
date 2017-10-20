package pagecontenttester.configurations;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ConfigTest {

    private static final String DESKTOP_USER_AGENT = "Mozilla/5.0 (X11\\; Ubuntu\\; Linux x86_64\\; rv\\:25.0)";

    private Config config = new Config();

    @Test
    public void should_return_desktop_user_agent() {
        assertThat(config.getUserAgent()).isEqualTo(DESKTOP_USER_AGENT);
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
