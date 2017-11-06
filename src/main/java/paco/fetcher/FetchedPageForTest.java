package paco.fetcher;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

@Slf4j
class FetchedPageForTest implements Page {

    private final FetchedPage fetchedPage;
    private String testName;

    FetchedPageForTest(FetchedPage fetchedPage, String testName) {
        this.fetchedPage = fetchedPage;
        this.testName = testName;
    }

    public Document getDocument() {
        return fetchedPage.getDocument();
    }

    private Response getResponse() {
        return fetchedPage.getResponse();
    }

    public String getUrl() {
        return fetchedPage.getUrl();
    }

    public int getStatusCode() {
        return getResponse().statusCode();
    }

    public String getContentType() {
        return getResponse().contentType();
    }

    public String getPageBody() {
        return getResponse().body();
    }

    public JSONObject getJsonResponse() {
        String clean = getResponse().body().replace("&amp;", "&").replace("&quot;", "\"");
        return new JSONObject(clean);
    }

    public String getHeader(String header) {
        return getResponse().header(header);
    }

    public Map<String, String> getHeaders() {
        return getResponse().headers();
    }

    public String getUserAgent() {
        return getHeader("User-Agent");
    }

    public String getLocation() {
        return getHeader("Location");
    }

    public String getReferrer() {
        return getHeader("Referer");
    }

    public boolean hasHeader(String header) {
        return getResponse().hasHeader(header);
    }

    public boolean hasHeaderWithValue(String header, String value) {
        return getResponse().hasHeaderWithValue(header, value);
    }

    public Map<String, String> getCookies() {
        return getResponse().cookies();
    }

    public String getCookieValue(String cookieName) {
        return getResponse().cookie(cookieName);
    }

    public boolean hasCookie(String cookieName) {
        return getResponse().hasCookie(cookieName);
    }

    public String getStatusMessage() {
        return getResponse().statusMessage();
    }

    public String getTitle() {
        return getDocument().title();
    }

    public Elements getElements(String cssSelector) {
        storeIfNotFound(cssSelector);
        return getDocument().select(cssSelector);
    }

    public Element getElement(String cssSelector) {
        storeIfNotFound(cssSelector);
        return getElements(cssSelector).first();
    }

    public Element getElementLastOf(String cssSelector) {
        storeIfNotFound(cssSelector);
        return getElements(cssSelector).last();
    }

    public Element getElement(String cssSelector, int index) {
        storeIfNotFound(cssSelector);
        return getElements(cssSelector).get(index);
    }

    public boolean isElementPresent(String cssSelector) {
        storeIfNotFound(cssSelector);
        return getElementCount(cssSelector) > 0;
    }

    public boolean isElementPresentNthTimes(String cssSelector, int numberOfOccurrences) {
        storeIfNotFound(cssSelector);
        return getElementCount(cssSelector) == numberOfOccurrences;
    }

    public int getElementCount(String cssSelector) {
        return getDocument().select(cssSelector).size();
    }

    public String getTestName() {
        return testName;
    }

    @Override
    public void storePageBody() {
        store("stored");
    }

    @Override
    public void store(String folder) {
        try {
            FileUtils.writeStringToFile(new File("target/paco/" + folder,  getTestName() + ".html"), getPageBody());
        } catch (IOException e) {
            log.warn("could not store page body for url: {} while executing test: {}", getUrl(), getTestName());
        }
    }

    private void storeIfNotFound(String cssSelector) {
        if (getElementCount(cssSelector) == 0) {
            store("not-found");
        }
    }

    @Override
    public void validateXml(String xsdPath){
        validateXml(xsdPath, true, W3C_XML_SCHEMA_NS_URI);
    }

    @Override
    public void validateXml(String xsdPath, boolean namespaceAware){
        validateXml(xsdPath, namespaceAware, W3C_XML_SCHEMA_NS_URI);
    }

    @Override
    @SneakyThrows
    public void validateXml(String xsdPath, boolean namespaceAware, String schemaLanguage) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(namespaceAware);

        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document document = builder.parse(new InputSource(new StringReader(getPageBody())));

        SchemaFactory schemaFactory = SchemaFactory.newInstance(schemaLanguage);
        Source schemaSource = new StreamSource(getClass().getResourceAsStream(xsdPath));

        Schema schema = schemaFactory.newSchema(schemaSource);
        Validator validator = schema.newValidator();
        Source source = new DOMSource(document);
        validator.setErrorHandler(new XmlErrorHandler());
        validator.validate(source);
    }

    private static class XmlErrorHandler implements ErrorHandler {

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
}
