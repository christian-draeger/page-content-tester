package pagecontenttester.fetcher;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
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

    public String getUrl() {
        return fetchedPage.getUrl();
    }

    public String getUrlPrefix() {
        return fetchedPage.getUrlPrefix();
    }

    public int getStatusCode() {
        return fetchedPage.getStatusCode();
    }

    public FetchedPage.DeviceType getDeviceType() {
        return fetchedPage.getDeviceType();
    }

    public boolean isMobile() {
        return fetchedPage.isMobile();
    }

    public String getContentType() {
        return fetchedPage.getContentType();
    }

    public String getPageBody() {
        return fetchedPage.getPageBody();
    }

    public JSONObject getJsonResponse() {
        return fetchedPage.getJsonResponse();
    }

    public String getHeader(String header) {
        return fetchedPage.getHeader(header);
    }

    public Map<String, String> getHeaders() {
        return fetchedPage.getHeaders();
    }

    public String getLocation() {
        return fetchedPage.getLocation();
    }

    public boolean hasHeader(String header) {
        return fetchedPage.hasHeader(header);
    }

    public Map<String, String> getCookies() {
        return fetchedPage.getCookies();
    }

    public String getCookieValue(String cookieName) {
        return fetchedPage.getCookieValue(cookieName);
    }

    public boolean hasCookie(String cookieName) {
        return fetchedPage.hasCookie(cookieName);
    }

    public String getStatusMessage() {
        return fetchedPage.getStatusMessage();
    }

    public Config getConfig() {
        return fetchedPage.getConfig();
    }

    public String getTitle() {
        return fetchedPage.getTitle();
    }

    public Elements getElements(String cssSelector) {
        hasSelector(cssSelector);
        return fetchedPage.getElements(cssSelector);
    }

    public Element getElement(String cssSelector) {
        hasSelector(cssSelector);
        return fetchedPage.getElement(cssSelector);
    }

    public Element getElementLastOf(String cssSelector) {
        hasSelector(cssSelector);
        return fetchedPage.getElementLastOf(cssSelector);
    }

    public Element getElement(String cssSelector, int index) {
        hasSelector(cssSelector);
        return fetchedPage.getElement(cssSelector, index);
    }

    public boolean isElementPresent(String cssSelector) {
        hasSelector(cssSelector);
        return fetchedPage.isElementPresent(cssSelector);
    }

    public boolean isElementPresentNthTimes(String cssSelector, int numberOfOccurrences) {
        hasSelector(cssSelector);
        return fetchedPage.isElementPresentNthTimes(cssSelector, numberOfOccurrences);
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
            FileUtils.writeStringToFile(new File("target/page-content-tester/" + folder,  getTestName() + ".html"), getPageBody());
        } catch (IOException e) {
            log.warn("could not store page body for url: {} while executing test: {}", getUrl(), getTestName());
        }
    }

    public int getElementCount(String cssSelector) {
        return fetchedPage.getElementCount(cssSelector);
    }

    private void hasSelector(String cssSelector) {
        if (getElementCount(cssSelector) == 0) {
            store("not-found");
        }
    }
}
