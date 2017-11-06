package paco.fetcher;

import net.jcip.annotations.NotThreadSafe;
import org.junit.Test;
import paco.annotations.Fetch;
import paco.runner.Paco;

import javax.xml.XMLConstants;

@NotThreadSafe
public class XMLValidationTest extends Paco {

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
