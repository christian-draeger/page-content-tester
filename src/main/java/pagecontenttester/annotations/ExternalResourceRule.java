package pagecontenttester.annotations;

import org.junit.rules.ExternalResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExternalResourceRule extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        // can do something before every test
        log.info("starting to fetch");
    }

    @Override
    protected void after() {
        // can do something after every test
        log.info("fetch finished");
    }
}
