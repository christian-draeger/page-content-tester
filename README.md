<p align="center">
<a href="https://travis-ci.org/christian-draeger/page-content-tester"><img src="https://travis-ci.org/christian-draeger/page-content-tester.svg?branch=master"/></a>
<a href="http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22io.github.christian-draeger%22%20AND%20a%3A%22page-content-tester%22"><img src="https://img.shields.io/maven-central/v/io.github.christian-draeger/page-content-tester.svg"/></a>
<a href="https://github.com/christian-draeger/page-content-tester/issues"><img src="https://img.shields.io/github/issues/christian-draeger/page-content-tester.svg"/></a>
</p>

## About

The PageContentTester is a framework for non-blocking and highly parallelized Dom testing. Since it's using the jSoup Framework to scrape the DOM you can use all the provieded methods of [jSoup](https://jsoup.org/) (server-side CSS3 selector driven DOM API). Beside that the Page Content Tester has a bunch of convenient methods on top of jsoup including test config meta data and easy access to request data (like cookies, headers, etc.) and Dom checks (like isElementPresent, follow url by css-selector, get value of html tags / attributes, etc.)

## How To Use

* add the following dependency to your pom.xml to use the convenient methods of the PageContentTester

```
<dependencies>
    <dependency>
        <groupId>io.github.christian-draeger</groupId>
        <artifactId>page-content-tester</artifactId>
        <version>1.0.1</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

#### don't loose time
- to get the best parallelization result of PageContentTester and don't having the overhead of finding the best setup add this parent pom to your pom.xml
  - it will setup all the configurations for an efficient parallelization of your jUnit tests automatically, you don't need to configure jUnit yourself anymore
    - if you want to know what the exact predefined junit settings are just have a look at the pluginManagement section in the [PageContentTester pom.xml](https://github.com/christian-draeger/page-content-tester/blob/master/pom.xml)
  - if you want to setup jUnit yourself just don't use the parent pom

```
<parent>
    <groupId>io.github.christian-draeger</groupId>
    <artifactId>page-content-tester-parent</artifactId>
    <version>1.0</version>
</parent>
```

## Example Test

```
public class ExampleUsageTest extends PageContentTester {

    private static final String GITHUB_URL = "https://github.com/christian-draeger";
    private static final String GOOGLE_URL = "http://www.google.de";

    @Test
    @FetchPage(GITHUB_URL)
    public void fetch_url_via_annotation() {
        assertThat(page.get().getElementCount("h1"), is(1));
    }

    @Test
    @FetchPage(GITHUB_URL)
    @FetchPage(GOOGLE_URL)
    public void fetch_multiple_pages_via_annotation_and_get_pages_by_url_snippet() {

        FetchedPage github = page.get("github");

        // use some convenient methods of the page-content-tester to assert behaviours
        assertThat(github.isElementPresent("h1"), is(true));
        assertThat(github.getUrl(), equalTo(GITHUB_URL));
        assertThat(github.getElement("h1").text(), containsString("christian-draeger"));
        assertThat(github.isElementPresent("img.avatar"), is(true));
        assertThat(github.getElement("img.avatar").attr("src"), containsString("githubusercontent.com"));
        assertThat(github.getCookieValue("logged_in"), equalTo("no"));
        assertThat(github.getCookies(), hasEntry("logged_in", "no"));

        FetchedPage google = page.get("google");
        assertThat(google.isElementPresent("#footer"), is(true));
    }

    @Test
    @FetchPage(GITHUB_URL)
    @FetchPage(GOOGLE_URL)
    public void fetch_multiple_pages_via_annotation_and_get_pages_by_index() {
        FetchedPage github = page.get(0);
        FetchedPage google = page.get(1);

        assertThat(github.isElementPresent("h1"), is(true));

        assertThat(google.isElementPresent("#footer"), is(true));
        assertThat(google.getUrl(), equalTo(GOOGLE_URL));
    }

    @Test
    public void fetch_multiple_pages_in_test_method() {
        FetchedPage github = fetchPage(GITHUB_URL);
        FetchedPage google = fetchPage(GOOGLE_URL);

        assertThat(github.isElementPresent("h1"), is(true));
        assertThat(github.getUrl(), equalTo(GITHUB_URL));

        assertThat(google.isElementPresent("#footer"), is(true));
        assertThat(google.getUrl(), equalTo(GOOGLE_URL));
    }

}
```

## Configure the Page-Fetcher
- the page fetcher is using a [default config](https://github.com/christian-draeger/page-content-tester/blob/master/src/main/resources/config.properties)
- it's possible to overwrite the default values by placing a config.properties file under src/main/resources/ in your project and adjust several properties until it fits your needs

## Example Project
To see the PageContentTester in action you can checkout or fork the corresponding [example project](https://github.com/christian-draeger/page-content-tester-example). it will give an overview of what and how things can be done. You will also get a feeling how long a test run will take using the PageContentTester (analysing and checking dom elements of ±1000 urls is getting done in less than 10sec. with a proper internet connection) 
