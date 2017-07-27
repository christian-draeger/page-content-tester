package pagecontenttester.fetcher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pagecontenttester.annotations.Fetch;
import pagecontenttester.runner.Paco;

@Fetch(url = "github.com/christian-draeger")
@Fetch(url = "www.idealo.de")
public class FetchMultipleFromClassTest extends Paco {

    // TODO: holt seite pro methode neu sollte bei Class aber nur einmal pro annotation passieren

    @Test
    public void can_fetch_from_class_annotation() {
        assertThat(page.get(0).getTitle()).contains("GitHub");
        assertThat(page.get(1).getTitle()).contains("IDEALO");
    }

    @Test
    public void can_fetch_from_class_annotation2() {
        assertThat(page.get(1).getTitle()).contains("IDEALO");
        assertThat(page.get(0).getTitle()).contains("GitHub");
    }
}