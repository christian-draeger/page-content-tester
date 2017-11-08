package paco.response;

import lombok.SneakyThrows;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.StringReader;

public class XmlErrorHandler implements ErrorHandler {

    @SneakyThrows
    public void validateXml(String xsdPath, boolean namespaceAware, String schemaLanguage, String pageBody) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(namespaceAware);

        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document document = builder.parse(new InputSource(new StringReader(pageBody)));

        SchemaFactory schemaFactory = SchemaFactory.newInstance(schemaLanguage);
        Source schemaSource = new StreamSource(getClass().getResourceAsStream(xsdPath));

        Schema schema = schemaFactory.newSchema(schemaSource);
        Validator validator = schema.newValidator();
        Source source = new DOMSource(document);
        validator.setErrorHandler(new XmlErrorHandler());
        validator.validate(source);
    }

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