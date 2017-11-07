package paco.response;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XmlErrorHandler implements ErrorHandler {

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        throw new RuntimeException(exception);
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        throw new RuntimeException(exception);
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        throw new RuntimeException(exception);
    }
}