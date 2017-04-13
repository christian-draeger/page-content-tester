package runner;

import org.junit.Rule;
import org.junit.runner.RunWith;

import com.googlecode.junittoolbox.ParallelRunner;

import annotations.FetcherRule;

@RunWith(ParallelRunner.class)
public class PageContentTester {

    @Rule
    public FetcherRule page = new FetcherRule();
}
