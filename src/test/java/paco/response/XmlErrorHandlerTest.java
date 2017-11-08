package paco.response;

import org.junit.Test;
import org.xml.sax.SAXParseException;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

public class XmlErrorHandlerTest {

    @Test(expected = SAXParseException.class)
    public void throwExceptionIfPageBodyNotParsable() throws Exception {
        new XmlErrorHandler().validateXml("", false, "", "");
    }

    @Test(expected = RuntimeException.class)
    public void error() throws Exception {
        new XmlErrorHandler().validateXml("/xsds/test.xsd", false, W3C_XML_SCHEMA_NS_URI, "<test>value</test>");
    }

    @Test(expected = RuntimeException.class)
    public void warning() throws Exception {
        new XmlErrorHandler().warning(new SAXParseException(null, null, null, 0, 0));
    }

    @Test(expected = RuntimeException.class)
    public void fatalError() throws Exception {
        new XmlErrorHandler().fatalError(new SAXParseException(null, null, null, 0, 0));
    }

}