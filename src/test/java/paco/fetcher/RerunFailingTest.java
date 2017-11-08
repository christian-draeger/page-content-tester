package paco.fetcher;

import org.junit.Test;

import static org.junit.Assert.fail;

public class RerunFailingTest {

    private static int counter = 0;

    @Test
    public void success_on_second_try() {
        if (counter == 0) {
            counter++;
            fail();
        }
    }
}
