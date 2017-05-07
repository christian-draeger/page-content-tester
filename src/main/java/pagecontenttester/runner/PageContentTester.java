package pagecontenttester.runner;

import org.junit.Rule;
import org.junit.runner.RunWith;

import com.googlecode.junittoolbox.ParallelRunner;

import pagecontenttester.annotations.FetcherRule;
import pagecontenttester.configurations.Config;

@RunWith(ParallelRunner.class)
public class PageContentTester {

    protected Config config = new Config();

    @Rule
    public FetcherRule page = new FetcherRule();
}
