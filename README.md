<img align="left" width="250px" src="https://github.com/christian-draeger/page-content-tester/blob/master/src/main/resources/paco.png">

<h1 align="left"> Paco <sub>(the page content tester)</sub></h1>

<p align="left">
<a href="http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22io.github.christian-draeger%22%20AND%20a%3A%22page-content-tester%22"><img src="https://img.shields.io/maven-central/v/io.github.christian-draeger/page-content-tester.svg"/></a><br>
<a href="https://travis-ci.org/christian-draeger/page-content-tester"><img src="https://travis-ci.org/christian-draeger/page-content-tester.svg?branch=master"/></a><br>
<a href="https://github.com/christian-draeger/page-content-tester/issues"><img src="https://img.shields.io/github/issues/christian-draeger/page-content-tester.svg"/></a><br>
<a href="https://sonarqube.com/dashboard?id=io.github.christian-draeger%3Apage-content-tester"><img src="https://img.shields.io/badge/Sonarqube-passing-brightgreen.svg"/></a><br>
<a href="https://www.codacy.com/app/christian.draeger1/page-content-tester/dashboard?bid=4765436"><img src="https://img.shields.io/codacy/grade/5a18e89828cf47778e2679c290b4a9f4/master.svg"/></a><br>
<a href="https://www.codacy.com/app/christian.draeger1/page-content-tester/dashboard"><img src="https://img.shields.io/codacy/coverage/5a18e89828cf47778e2679c290b4a9f4/master.svg"/></a><br>
<!--<a href="https://gratipay.com/~christian-draeger/"><img src="https://img.shields.io/gratipay/user/christian-draeger.svg"/></a>-->
</p>

## About

**Paco** is a framework for non-blocking and highly parallelized Dom testing. Since it's using the jSoup Framework to scrape the HTML (working with XML as well) you can use all the provieded methods of [jSoup](https://jsoup.org/) (server-side CSS3 selector driven DOM API). Beside that **Paco** has a bunch of convenient methods on top of jsoup including test config meta data and easy access to request data (like cookies, headers, etc.) and Dom checks (like isElementPresent, get value of html tags / attributes, etc.)

## Setup

* add the following dependency to your pom.xml

```
<dependencies>
    <dependency>
        <groupId>io.github.christian-draeger</groupId>
        <artifactId>page-content-tester</artifactId>
        <version>1.5.6</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

#### Configure the Page-Fetcher
- you have the option to place a pagecontent.properties file in your project under src/test/resources/
- in the pagecontent.properties you can override all values from [default.properties](https://github.com/christian-draeger/page-content-tester/blob/master/src/test/resources/default.properties) until they fit your needs
    - these settings will be used global, but most of them can be overritten/set for certain tests via Annotation as well

- to get the best parallelization result of PageContentTester and don't having the overhead of finding the best setup add this parent pom to your pom.xml
  - it will setup all the configurations for an efficient parallelization of your jUnit tests automatically, you don't need to configure jUnit yourself anymore
    - if you want to know what the exact predefined junit settings are just have a look at the pluginManagement section of the [parent pom](https://search.maven.org/#artifactdetails%7Cio.github.christian-draeger%7Cpage-content-tester-parent%7C1.0%7Cpom)
  - if you want to setup jUnit yourself just don't use the parent pom

```
<parent>
    <groupId>io.github.christian-draeger</groupId>
    <artifactId>page-content-tester-parent</artifactId>
    <version>1.0</version>
</parent>
```
* the tests will be executed during the test phase of maven (`mvn clean test` or later)

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
    @Fetch(protocol = Fetch.Protocol.HTTPS, urlPrefix = "en", url = "wikipedia.org")
    public void fetch_page_via_annotation_and_build_url() {
        assertThat(page.get().getUrl(), equalTo("https://en.wikipedia.org"));
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
    
    @Test
    @Fetch(url = "http://whatsmyuseragent.org/", device = MOBILE)
    public void fetch_page_and_emulate_mobile_device_by_annotation() {
        String ua = page.get().getElement("p.intro-text").text();
        assertThat(ua, containsString(page.get().getConfig().getUserAgent(MOBILE)));
    }

    @Test
    public void do_post_request_and_check_response() throws Exception {
        FetchedPage somePost = call("http://httpbin.org/post", POST, Collections.emptyMap());
        JSONObject responseBody = somePost.getJsonResponse();
        assertThat(responseBody.get("url"), equalTo("http://httpbin.org/post"));
    }
    
    @Test
    @Fetch( url = "http://www.html-kit.com/tools/cookietester/",
            setCookies = @Cookie(name = "page-content-tester", value = "wtf-666"))
    public void can_set_cookie_via_annotation() {
        assertThat(page.get().getPageBody(), both(containsString("page-content-tester"))
                                            .and(containsString("wtf-666")));
    }

    @Test
    @Fetch( url = "http://www.html-kit.com/tools/cookietester/",
            setCookies = {  @Cookie(name = "page-content-tester", value = "wtf-666"),
                            @Cookie(name = "some-other-cookie", value = "666-wtf")})
    public void can_set__multiple_cookies_via_annotation() {
        String body = page.get().getPageBody();
        
        assertThat(body, both(containsString("page-content-tester"))
                        .and(containsString("wtf-666")));
        assertThat(body, both(containsString("some-other-cookie"))
                        .and(containsString("666-wtf")));
    }
}
```

## Example Project
To see the PageContentTester in action you can checkout or fork the corresponding [example project](https://github.com/christian-draeger/page-content-tester-example). it will give an overview of what and how things can be done. You will also get a feeling how long a test run will take using the PageContentTester (analysing and checking dom elements within ±1000 tests that are fetching an url is getting done in less than 10sec with a proper internet connection) 
