package pagecontenttester.runner;

import org.junit.Rule;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import com.googlecode.junittoolbox.ParallelRunner;

import pagecontenttester.annotations.FetcherRule;
import pagecontenttester.configurations.GlobalConfig;

@RunWith(ParallelRunner.class)
public class PageContentTester {

    protected static GlobalConfig globalConfig = new GlobalConfig();

    @Rule
    public FetcherRule page = new FetcherRule();

    @Rule
    public Timeout globalTimeout = globalConfig.getGlobalTimeoutValue();
}
