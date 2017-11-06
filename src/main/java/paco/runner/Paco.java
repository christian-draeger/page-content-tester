package paco.runner;

import com.googlecode.junittoolbox.ParallelRunner;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import paco.annotations.FetcherRule;
import paco.configurations.GlobalConfig;

@RunWith(ParallelRunner.class)
public class Paco {

    protected static GlobalConfig globalConfig = new GlobalConfig();

    @Rule
    public FetcherRule page = new FetcherRule();

    @Rule
    public Timeout globalTimeout = globalConfig.getGlobalTimeoutValue();

}
