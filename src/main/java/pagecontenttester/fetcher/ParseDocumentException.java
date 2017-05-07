package pagecontenttester.fetcher;

public class ParseDocumentException extends RuntimeException {

    public ParseDocumentException(String message) {
        super(message);
    }

    public ParseDocumentException(Throwable cause) {
        super(cause);
    }

    public ParseDocumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
