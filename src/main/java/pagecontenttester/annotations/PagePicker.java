package pagecontenttester.annotations;

import pagecontenttester.fetcher.FetchedPage;
import pagecontenttester.fetcher.Page;

public class PagePicker {

    private final FetcherRule fetcherRule;

    PagePicker(FetcherRule fetcherRule) {
        this.fetcherRule = fetcherRule;
    }

    public Page get(int index) {
        try {
            return fetcherRule.getFetchedPages().get(index);
        } catch (IndexOutOfBoundsException e) { // NOSONAR
            throw new GetFetchedPageException("could not find fetched page with index \"" + index + "\"");
        }
    }

    public Page get(String urlSnippet) {
        for (Page recentlyFetchedPage : fetcherRule.getFetchedPages()) {
            if (recentlyFetchedPage.getUrl().endsWith(urlSnippet)) {
                return recentlyFetchedPage;
            }
            if (recentlyFetchedPage.getUrl().replace(":" + fetcherRule.getGlobalConfig().getPort(), "").endsWith(urlSnippet)) {
                return recentlyFetchedPage;
            }
            if (recentlyFetchedPage.getUrl().contains(urlSnippet)) {
                return recentlyFetchedPage;
            }
        }
        throw new GetFetchedPageException("could not find fetched page with url-snippet \"" + urlSnippet + "\"");
    }

    public Page get(FetchedPage.DeviceType deviceType) {
        for (Page recentlyFetchedPage : fetcherRule.getFetchedPages()) {
            if (recentlyFetchedPage.getDeviceType().equals(deviceType)) {
                return recentlyFetchedPage;
            }
        }
        throw new GetFetchedPageException("could not find fetched page with deviceType \"" + deviceType + "\"");
    }

    public Page get(String urlSnippet, FetchedPage.DeviceType deviceType) {
        for (Page recentlyFetchedPage : fetcherRule.getFetchedPages()) {
            if (recentlyFetchedPage.getUrl().endsWith(urlSnippet) && recentlyFetchedPage.getDeviceType().equals(deviceType)) {
                return recentlyFetchedPage;
            }
            if (recentlyFetchedPage.getUrl().replace(":" + fetcherRule.getGlobalConfig().getPort(), "").endsWith(urlSnippet) && recentlyFetchedPage.getDeviceType().equals(deviceType)) {
                return recentlyFetchedPage;
            }
            if (recentlyFetchedPage.getUrl().contains(urlSnippet) && recentlyFetchedPage.getDeviceType().equals(deviceType)) {
                return recentlyFetchedPage;
            }
        }
        throw new GetFetchedPageException("could not find fetched page with url-snippet: \"" + urlSnippet + "\" (" + deviceType + ")");
    }
}