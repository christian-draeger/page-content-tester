package paco.runner;

import org.junit.Rule;
import org.junit.rules.Timeout;
import paco.annotations.FetcherRule;
import paco.configurations.GlobalConfig;

public class Paco {

    protected static GlobalConfig globalConfig = new GlobalConfig();

    @Rule
    public FetcherRule page = new FetcherRule();

    @Rule
    public Timeout globalTimeout = globalConfig.getGlobalTimeoutValue();

}
