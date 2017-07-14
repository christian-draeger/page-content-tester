package pagecontenttester.annotations;

import pagecontenttester.fetcher.FetchedPage;

public class PagePicker {

    private final FetcherRule fetcherRule;

    PagePicker(FetcherRule fetcherRule) {
        this.fetcherRule = fetcherRule;
    }

    public FetchedPage get() {
        return fetcherRule.getFetchedPages().get(0);
    }

    public FetchedPage get(int index) {
        try {
            return fetcherRule.getFetchedPages().get(index);
        } catch (IndexOutOfBoundsException e) { // NOSONAR
            throw new GetFetchedPageException("could not find fetched page with index \"" + index + "\"");
        }
    }

    public FetchedPage get(String urlSnippet) {
        for (FetchedPage recentlyFetchedPage : fetcherRule.getFetchedPages()) {
            if (recentlyFetchedPage.getUrl().endsWith(urlSnippet)) {
                return recentlyFetchedPage;
            }
            if (recentlyFetchedPage.getUrl().replace(":" + fetcherRule.getConfig().getPort(), "").endsWith(urlSnippet)) {
                return recentlyFetchedPage;
            }
            if (recentlyFetchedPage.getUrl().contains(urlSnippet)) {
                return recentlyFetchedPage;
            }
        }
        throw new GetFetchedPageException("could not find fetched page with url-snippet \"" + urlSnippet + "\"");
    }

    public FetchedPage get(FetchedPage.DeviceType deviceType) {
        for (FetchedPage recentlyFetchedPage : fetcherRule.getFetchedPages()) {
            if (recentlyFetchedPage.getDeviceType().equals(deviceType)) {
                return recentlyFetchedPage;
            }
        }
        throw new GetFetchedPageException("could not find fetched page with deviceType \"" + deviceType + "\"");
    }

    public FetchedPage get(String urlSnippet, FetchedPage.DeviceType deviceType) {
        for (FetchedPage recentlyFetchedPage : fetcherRule.getFetchedPages()) {
            if (recentlyFetchedPage.getUrl().endsWith(urlSnippet) && recentlyFetchedPage.getDeviceType().equals(deviceType)) {
                return recentlyFetchedPage;
            }
            if (recentlyFetchedPage.getUrl().replace(":" + fetcherRule.getConfig().getPort(), "").endsWith(urlSnippet) && recentlyFetchedPage.getDeviceType().equals(deviceType)) {
                return recentlyFetchedPage;
            }
            if (recentlyFetchedPage.getUrl().contains(urlSnippet) && recentlyFetchedPage.getDeviceType().equals(deviceType)) {
                return recentlyFetchedPage;
            }
        }
        throw new GetFetchedPageException("could not find fetched page with url-snippet: \"" + urlSnippet + "\" (" + deviceType + ")");
    }
}