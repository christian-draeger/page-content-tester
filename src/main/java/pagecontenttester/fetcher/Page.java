package pagecontenttester.fetcher;

import java.util.Map;

import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pagecontenttester.configurations.Config;

public interface Page {

    /**
     * Holds information of certain fetched page's config values
     * @return Config
     */
    Config getConfig();

    /**
     * get DOM Element of first CSS-selector match
     * @return Element
     */
    Element getElement(String cssSelector);

    /**
     * get DOM Element of CSS-selector match by index
     * @return Element
     */
    Element getElement(String cssSelector, int index);

    /**
     * get DOM Elements of matching CSS-selectors
     * @return Elements
     */
    Elements getElements(String cssSelector);

    /**
     * get last DOM Element of matching CSS-selector
     * @return Element
     */
    Element getElementLastOf(String cssSelector);

    /**
     * @return true if Element is present in DOM
     */
    boolean isElementPresent(String cssSelector);

    /**
     * @return true if Element is present nth times in DOM
     */
    boolean isElementPresentNthTimes(String cssSelector, int numberOfOccurrences);

    /**
     * @return number of matching CSS-selectors
     */
    int getElementCount(String cssSelector);

    /**
     * @return HTTP status code of fetched page
     */
    int getStatusCode();

    /**
     * @return HTTP status message of fetched page
     */
    String getStatusMessage();

    /**
     * @return the raw Document object including all DOM specific data
     */
    Document getDocument();

    /**
     * @return the fetched page's body as String
     */
    String getPageBody();

    /**
     * @return the requested url. this will not be updated if redirects occur
     */
    String getUrl();

    /**
     * Get the response content type (e.g. "text/html");
     * @return the response content type
     */
    String getContentType();

    /**
     * Get the value of a header. This is a simplified header model, where a header may only have one value.
     * Header names are case insensitive.
     * @param header name (case insensitive)
     * @return value of header, or null if not set.
     */
    String getHeader(String header);

    /**
     * Retrieve all of the request/response headers as a map
     * @return headers
     */
    Map<String, String> getHeaders();

    /**
     * Check if a header is present
     * @param header name of header (case insensitive)
     * @return if the header is present in this request/response
     */
    boolean hasHeader(String header);

    /**
     * @return true if fetched page was requested with a mobile user-agent
     */
    boolean isMobile();

    /**
     * Check if a cookie is present
     * @param cookieName name of cookie
     * @return if the cookie is present in this request/response
     */
    boolean hasCookie(String cookieName);

    /**
     * Get a cookie value by name from this request/response.
     * Response objects have a simplified cookie model. Each cookie set in the response is added to the response
     * object's cookie key=value map. The cookie's path, domain, and expiry date are ignored.
     * @param cookieName name of cookie to retrieve.
     * @return value of cookie, or null if not set
     */
    String getCookieValue(String cookieName);

    /**
     * Retrieve all of the request/response cookies as a map
     * @return cookies
     */
    Map<String, String> getCookies();

    /**
     * @return the response body in JSON format
     */
    JSONObject getJsonResponse();
}
