package pagecontenttester.fetcher;

class ParseDocumentException extends RuntimeException {

    ParseDocumentException(String message) {
        super(message);
    }

    ParseDocumentException(Throwable cause) {
        super(cause);
    }

    ParseDocumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
