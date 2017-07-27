package pagecontenttester.fetcher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pagecontenttester.annotations.Fetch;
import pagecontenttester.runner.Paco;

public class FetchSingleFromMethodTest extends Paco {

    @Test
    @Fetch(url = "www.idealo.de")
    public void can_fetch_from_method_annotation() {
        assertThat(page.get().getTitle()).contains("IDEALO");
    }

}