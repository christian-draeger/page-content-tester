package pagecontenttester.fetcher;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.extern.slf4j.Slf4j;
import pagecontenttester.configurations.Config;

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

    public String getUrlPrefix() {
        return fetchedPage.getUrlPrefix();
    }

    public int getStatusCode() {
        return getResponse().statusCode();
    }

    public FetchedPage.DeviceType getDeviceType() {
        return fetchedPage.getDeviceType();
    }

    public boolean isMobile() {
        return fetchedPage.isMobile();
    }

    public String getContentType() {
        return getResponse().contentType();
    }

    public String getPageBody() {
        return getResponse().body();
    }

    public JSONObject getJsonResponse() {
        return new JSONObject(getResponse().body());
    }

    public String getHeader(String header) {
        return getResponse().header(header);
    }

    public Map<String, String> getHeaders() {
        return getResponse().headers();
    }

    public String getLocation() {
        return getResponse().header("Location");
    }

    public boolean hasHeader(String header) {
        return getResponse().hasHeader(header);
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

    public Config getConfig() {
        return fetchedPage.getConfig();
    }

    public String getTitle() {
        return getDocument().title();
    }

    public Elements getElements(String cssSelector) {
        hasSelector(cssSelector);
        return getDocument().select(cssSelector);
    }

    public Element getElement(String cssSelector) {
        hasSelector(cssSelector);
        return getElements(cssSelector).first();
    }

    public Element getElementLastOf(String cssSelector) {
        hasSelector(cssSelector);
        return getElements(cssSelector).last();
    }

    public Element getElement(String cssSelector, int index) {
        hasSelector(cssSelector);
        return getElements(cssSelector).get(index);
    }

    public boolean isElementPresent(String cssSelector) {
        hasSelector(cssSelector);
        return getElementCount(cssSelector) > 0;
    }

    public boolean isElementPresentNthTimes(String cssSelector, int numberOfOccurrences) {
        hasSelector(cssSelector);
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

    private void hasSelector(String cssSelector) {
        if (getElementCount(cssSelector) == 0) {
            store("not-found");
        }
    }
}
