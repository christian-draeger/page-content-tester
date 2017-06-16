package pagecontenttester.fetcher;

import java.util.Map;

import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pagecontenttester.configurations.Config;

public interface Page {

    Config getConfig();

    Element getElement(String cssSelector);

    Element getElement(String cssSelector, int index);

    Elements getElements(String cssSelector);

    Element getElementLastOf(String cssSelector);

    boolean isElementPresent(String cssSelector);

    boolean isElementPresentNthTimes(String cssSelector, int numberOfOccurrences);

    int getElementCount(String cssSelector);

    int getStatusCode();

    String getStatusMessage();

    Document getDocument();

    String getPageBody();

    String getUrl();

    String getContentType();

    String getHeader(String header);

    Map<String, String> getHeaders();

    boolean hasHeader(String header);

    boolean isMobile();

    boolean hasCookie(String cookieName);

    String getCookieValue(String cookieName);

    Map<String, String> getCookies();

    JSONObject getJsonResponse();
}
