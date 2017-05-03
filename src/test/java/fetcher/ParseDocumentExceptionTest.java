package fetcher;

import org.junit.Test;

public class ParseDocumentExceptionTest {

    @Test(expected = ParseDocumentException.class)
    public void should_throw_exception_with_message() {
        throw new ParseDocumentException("message");
    }

    @Test(expected = ParseDocumentException.class)
    public void should_throw_exception_with_cause() {
        throw new ParseDocumentException(new Throwable());
    }

    @Test(expected = ParseDocumentException.class)
    public void should_throw_exception_with_message_and_cause() {
        throw new ParseDocumentException("message", new Throwable());
    }
}