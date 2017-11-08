package paco.runner;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class VersionTest {

    private Version version = new Version();

    @Test
    public void check_version_fallback_if_maven_package_meta_infos_not_available() throws Exception {
        assertThat(version.getVersion()).isEqualTo("version x.x.x");
    }

}