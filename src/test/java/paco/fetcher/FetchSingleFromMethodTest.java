package paco.fetcher;

import org.junit.Test;
import paco.annotations.Fetch;
import paco.runner.Paco;

import static org.assertj.core.api.Assertions.assertThat;

public class FetchSingleFromMethodTest extends Paco {

    @Test
    @Fetch(url = "localhost/example", port = "8089")
    public void can_fetch_from_method_annotation() {
        assertThat(page.get().getTitle()).contains("i'm the title");
    }

}