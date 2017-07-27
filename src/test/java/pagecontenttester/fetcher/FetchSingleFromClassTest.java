package pagecontenttester.fetcher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pagecontenttester.annotations.Fetch;
import pagecontenttester.runner.Paco;

@Fetch(url = "www.idealo.de")
public class FetchSingleFromClassTest extends Paco {

    @Test
    public void can_fetch_from_class_annotation() {
        assertThat(page.get().getTitle()).contains("IDEALO");
    }

}