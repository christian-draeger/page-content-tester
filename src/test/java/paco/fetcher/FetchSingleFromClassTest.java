package paco.fetcher;

import org.junit.Test;
import paco.annotations.Fetch;
import paco.runner.Paco;

import static org.assertj.core.api.Assertions.assertThat;

@Fetch(url = "localhost/example", port = "8089")
public class FetchSingleFromClassTest extends Paco {

    @Test
    public void can_fetch_from_class_annotation() {
        assertThat(page.get().getTitle()).contains("i'm the title");
    }

}