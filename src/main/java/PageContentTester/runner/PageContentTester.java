package PageContentTester.runner;

import org.junit.Rule;
import org.junit.runner.RunWith;

import com.googlecode.junittoolbox.ParallelRunner;

import PageContentTester.annotations.fetch.FetcherRule;
import PageContentTester.configurations.Config;

@RunWith(ParallelRunner.class)
public class PageContentTester {

    protected Config config = new Config();

    @Rule
    public FetcherRule page = new FetcherRule();

}
