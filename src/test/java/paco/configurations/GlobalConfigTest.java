package paco.configurations;

import static org.assertj.core.api.Assertions.assertThat;
import static paco.annotations.Fetch.Device.DESKTOP;
import static paco.annotations.Fetch.Device.MOBILE;

import org.junit.Test;

public class GlobalConfigTest {

    private static final String DESKTOP_USER_AGENT = "Mozilla/5.0 (X11\\; Ubuntu\\; Linux x86_64\\; rv\\:25.0)";
    private static final String MOBILE_USER_AGENT = "Mozilla/5.0 (iPhone\\; CPU iPhone OS 6_1_4 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10B350 Safari/8536.25";

    private GlobalConfig globalConfig = new GlobalConfig();

    @Test
    public void should_return_mobile_user_agent_for_device_mobile() {
        assertThat(globalConfig.getUserAgent(MOBILE)).isEqualTo(MOBILE_USER_AGENT);
    }

    @Test
    public void should_return_desktop_user_agent_for_device_desktop() {
        assertThat(globalConfig.getUserAgent(DESKTOP)).isEqualTo(DESKTOP_USER_AGENT);
    }

    @Test
    public void should_return_max_retry_count() {
        assertThat(globalConfig.getTimeoutMaxRetryCount()).isEqualTo(3);
    }

    @Test
    public void should_return_timeout_value() {
        assertThat(globalConfig.getTimeoutValue()).isEqualTo(10000);
    }

    @Test
    public void should_return_url_prefix() {
        assertThat(globalConfig.getUrlPrefix()).isEmpty();
    }

    @Test
    public void should_return_ignore_contenttype_config() {
        assertThat(globalConfig.isIgnoringContentType()).isTrue();
    }

    @Test
    public void should_return_follow_redirects_config() {
        assertThat(globalConfig.isFollowingRedirects()).isTrue();
    }

    @Test
    public void should_return_caching_config() {
        assertThat(globalConfig.isCacheDuplicatesActive()).isTrue();
    }

}
