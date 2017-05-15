package PageContentTester.configurations;

import org.junit.Test;

public class ConfigurationExceptionTest {

    @Test(expected = ConfigurationException.class)
    public void should_throw_exception_with_message() {
        throw new ConfigurationException("message");
    }

    @Test(expected = ConfigurationException.class)
    public void should_throw_exception_with_cause() {
        throw new ConfigurationException(new Throwable());
    }

    @Test(expected = ConfigurationException.class)
    public void should_throw_exception_with_message_and_cause() {
        throw new ConfigurationException("message", new Throwable());
    }
}