#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package testcases;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import paco.annotations.Fetch;
import paco.runner.Paco;
import urls.Urls;

/**
 * This test class exemplifies the possibly most basic use case.
 * It just fetches a page and checks the title.
 */
@Fetch(url = Urls.GITHUB)
public class BasicExampleTest extends Paco {

    @Test
    public void can_fetch_from_class_annotation() {
        assertThat(page.get().getTitle()).containsIgnoringCase("GitHub");
    }

    @Test
    public void also_uses_fetch_from_class_annotation() {
        assertThat(page.get().getTitle()).containsIgnoringCase("GitHub");
    }

    @Test
    @Fetch(url = Urls.GOOGLE)
    public void can_fetch_from_method_annotation() {
        assertThat(page.get().getTitle()).containsIgnoringCase("google");
    }

}