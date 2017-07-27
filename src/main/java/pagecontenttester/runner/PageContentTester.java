package pagecontenttester.runner;

import org.junit.Rule;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import com.googlecode.junittoolbox.ParallelRunner;

import pagecontenttester.annotations.FetcherRule;
import pagecontenttester.configurations.Config;

@RunWith(ParallelRunner.class)
public class PageContentTester {

    protected static Config config = new Config();

    @Rule
    public FetcherRule page = new FetcherRule();

    @Rule
    public Timeout globalTimeout = config.getGlobalTimeoutValue();
}
