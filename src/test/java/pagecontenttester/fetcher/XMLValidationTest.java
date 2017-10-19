package pagecontenttester.fetcher;

import javax.xml.XMLConstants;

import org.junit.Test;

import pagecontenttester.annotations.Fetch;
import pagecontenttester.runner.PageContentTester;

public class XMLValidationTest extends PageContentTester {

    @Test
    @Fetch(url = "localhost:8089/xml")
    public void can_validate_xml() throws Exception {
        page.get().validateXml("/xsds/test.xsd");
    }

    @Test
    @Fetch(url = "localhost:8089/xml")
    public void can_validate_xml_without_namespace_awareness() throws Exception {
        page.get().validateXml("/xsds/test.xsd", false);
    }

    @Test
    @Fetch(url = "localhost:8089/xml")
    public void can_validate_xml_with_namespace_choosen() throws Exception {
        page.get().validateXml("/xsds/test.xsd", false, XMLConstants.W3C_XML_SCHEMA_NS_URI);
    }
}
