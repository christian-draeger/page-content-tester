<img align="left" width="250px" src="https://github.com/christian-draeger/page-content-tester/blob/master/src/main/resources/paco.png">

<h1 align="left"> Paco <sub>(the page content tester)</sub></h1>

<p align="left">
<a target="_blank" href="http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22io.github.christian-draeger%22%20AND%20a%3A%22page-content-tester%22"><img src="https://img.shields.io/maven-central/v/io.github.christian-draeger/page-content-tester.svg?style=flat-square"/></a><br>
<a target="_blank" href="https://travis-ci.org/christian-draeger/page-content-tester"><img src="https://img.shields.io/travis/christian-draeger/page-content-tester.svg?style=flat-square"/></a><br>
<a href="https://github.com/christian-draeger/page-content-tester/issues"><img src="https://img.shields.io/github/issues/christian-draeger/page-content-tester.svg?style=flat-square"/></a><br>
<a target="_blank" href="https://sonarqube.com/dashboard?id=io.github.christian-draeger%3Apage-content-tester"><img src="https://img.shields.io/badge/Sonarqube-passing-brightgreen.svg?style=flat-square"/></a><br>
<a target="_blank" href="https://www.codacy.com/app/christian.draeger1/page-content-tester/dashboard?bid=4765436"><img src="https://img.shields.io/codacy/grade/5a18e89828cf47778e2679c290b4a9f4/master.svg?style=flat-square"/></a><br>
<a target="_blank" href="https://www.codacy.com/app/christian.draeger1/page-content-tester/dashboard"><img src="https://img.shields.io/codacy/coverage/5a18e89828cf47778e2679c290b4a9f4/master.svg?style=flat-square"/></a><br>
<a target="_blank" href="https://github.com/christian-draeger/page-content-tester/blob/master/LICENSE"><img src="https://img.shields.io/github/license/christian-draeger/page-content-tester.svg?style=flat-square"/></a><br>
<br>
</p>

<h2 align="center">About</h2>

**Paco** is a Java based framework for non-blocking and highly parallelized Dom testing.
The motivation of bringing this little buffed out guy to live have been the need of having a robust and fast solution to relieve a long running and unstable Selenium suite. After a code dive through these Selenium tests it turned out that lots of them were just checking things (like Dom elements, displayed data, cookies, etc) without the need of interacting with a web browser.
So Paco was born as an alternative and he's doing his job rapidly fast and reliable. In a bigger test project where this framework is in use it runs ~400 tests in less than 10 seconds, but the best thing is that if you use **Paco** you can focus on your tests itself instead of messing around with setting up a complex test project (or even framework) yourself. 
**Paco** allows you to configure all your test specific data individually and directly in place (on your test method and/or test class) via annotations. You only need to describe how you want to fetch an http response (e.g. requesting a  web page by using a proxy, mobile userAgent, setting cookies, add a specific referrer, doing a POST that sends some request body, etc). 
The Setup is pretty easy (see __Setup__)

The Execution of the tests is managed with jUnit und Surefire. The intention of **Paco** is to run a big amount of tests in parallel, therefore it provides a parent pom (usage is optional) thats doing all the parallelization setup for you (see __Configure the Page-Fetcher__). 

Beside that **Paco** has a bunch of convenient methods to easily write nicely readable tests. It provides easy access to request data (like cookies, headers, etc.), test-config data, and Dom checks (like isElementPresent, getElement, get value of html tags/attributes, etc, etc...)

<h2 align="center">Setup</h2>

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
- place a pagecontent.properties file in your project under src/test/resources/
    - you can override all default values from [pagecontent.properties](https://github.com/christian-draeger/page-content-tester/blob/master/src/test/resources/pagecontent.properties) in your projects pagecontent.properties file (these settings will be used global)
    - nearly all these values can be set individually for a test methods and classes via Annotation as well (see Examples)

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

<h2 align="center">Test Examples</h2>

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

<h2 align="center">Example Project</h2>

To see the PageContentTester in action you can checkout or fork the corresponding [example project](https://github.com/christian-draeger/page-content-tester-example). it will give an overview of what and how things can be done. You will also get a feeling how long a test run will take using the PageContentTester (analysing and checking dom elements within ±1000 tests that are fetching an url is getting done in less than 10sec with a proper internet connection)

<h2 align="center">License</h2>

Paco is [GPLv3+](LICENSE).
It re-distributes other open-source libraries. Please check the [third party licenses](https://github.com/christian-draeger/page-content-tester/blob/master/REDISTRIBUTED.md).
