<h1 align="left"> Paco <sub>(the page content tester)</sub></h1><br>

<img align="left" width="240px" border-width="0" src="https://raw.githubusercontent.com/christian-draeger/page-content-tester/master/src/main/resources/paco.png">

<p align="left">
<br>
<a target="_blank" href="http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22io.github.christian-draeger%22%20AND%20a%3A%22page-content-tester%22">
	<img border-width="0" src="https://img.shields.io/maven-central/v/io.github.christian-draeger/page-content-tester.svg?style=flat-square&logoWidth=16"/>
</a><br>
<a target="_blank" href="https://travis-ci.org/christian-draeger/page-content-tester">
	<img border-width="0" src="https://img.shields.io/travis/christian-draeger/page-content-tester/master.svg?style=flat-square&logoWidth=8&label=ci%20master%20build"/>
</a><br>
<a href="https://github.com/christian-draeger/page-content-tester/issues">
	<img  border-width="0" src="https://img.shields.io/github/issues/christian-draeger/page-content-tester.svg?style=flat-square&logoWidth=8&label=reported%20issues"/>
</a><br>
<a target="_blank" href="https://sonarqube.com/dashboard?id=io.github.christian-draeger%3Apage-content-tester">
	<img  border-width="0" src="https://img.shields.io/badge/Sonarqube-passing-brightgreen.svg?style=flat-square&logoWidth=2&label=sonarqube%20scan"/>
</a><br>
<a target="_blank" href="https://www.codacy.com/app/christian.draeger1/page-content-tester/dashboard?bid=4765436">
	<img  border-width="0" src="https://img.shields.io/codacy/grade/5a18e89828cf47778e2679c290b4a9f4/master.svg?style=flat-square&logoWidth=16&label=overall%20code%20quality"/>
</a><br>
<a target="_blank" href="https://codecov.io/gh/christian-draeger/page-content-tester">
	<img  border-width="0" src="https://img.shields.io/codecov/c/github/christian-draeger/page-content-tester/master.svg?style=flat-square&logoWidth=30&label=test%20coverage"/>
</a><br>
<a target="_blank" href="https://github.com/christian-draeger/page-content-tester/blob/master/LICENSE">
	<img  border-width="0" src="https://img.shields.io/badge/license-GNU%20GPL%20v3.0-blue.svg?style=flat-square&logoWidth=14"/>
</a><br><br>
</p>

<h2 align="center">Table of contents</h2>

* __[About](#about)__
* __[Setup](#setup)__
* __[Configuration](#configuration)__
	* __[configure your request](#configure-your-request)__
		* __[the fetch annotation](#the-fetch-annotation)__
			* __[on methods](#on-methods)__
			* __[on classes](#on-classes)__
			* __[mutliple fetches (repeatable)](#mutliple-fetches-repeatable)__
				* __[page picker](#page-picker)__
		* __[the fetcher method](#the-fetcher-method)__
		* __[exclude tests from parallel execution](#exclude-tests-from-parallel-execution)__
		* __[caching](#caching)__
	* __[Test Examples](#test-examples)__
		* __[Overview of possibilities on your Page object (the response)](#overview-of-possibilities-on-your-page-object-the-response)__
* __[Example Project](#example-project)__
* __[License](#license)__

<h2 align="center">About</h2>

**Paco** is a Java based framework for non-blocking and highly parallelized Dom testing.
The motivation of bringing this little buffed out guy to live have been the need of having a robust and fast solution to relieve a long running and unstable Selenium suite. After a code dive through these Selenium tests it turned out that lots of them were just checking things (like Dom elements, displayed data, cookies, etc) without the need of interacting with a web browser.
So Paco was born as an alternative and he's doing his job rapidly fast and reliable. In a bigger test project where this framework is in use it runs ~400 tests in less than 10 seconds. When using **Paco** you can focus on your tests itself instead of messing around with setting up a complex test project yourself. 

**Paco** allows you to configure all your test specific data individually and directly in place (on your test method and/or test class) via annotations. You only need to describe how you want to fetch an http response (e.g. requesting a  web page by using a proxy, mobile userAgent, setting cookies, add a specific referrer, doing a POST that sends some request body, etc). 
The Setup is pretty easy (see __[Setup](#setup)__)

The Execution of the tests is managed with jUnit and Surefire/Failsafe. The intention of **Paco** is to run a big amount of tests in parallel, therefore it provides a parent pom (usage is optional) that is doing all the parallelization setup for you (see __[configure your request](#configure-your-request)__). 
All test classes placed under `/src/test/java` and names matching `**/*Test.java` will be executed by surefire (maven-phase: test),
all with name matching `**/*IT.java` will be executed by fail-safe (maven-phase: integration-test).

Beside that **Paco** has a bunch of convenient methods to easily write nicely readable tests. It provides easy access to request data (like cookies, headers, etc.), test-config data, and Dom checks (like isElementPresent, getElement, get value of html tags/attributes, etc, etc...)

<h2 align="center">Setup</h2>

- to get the best parallelization result add this parent pom to your pom.xml
  - it will setup all the configurations for an efficient parallelization of your jUnit tests automatically
    - if you want to know what the exact predefined junit settings are just have a look at the pluginManagement section of the [parent pom](https://search.maven.org/#artifactdetails%7Cio.github.christian-draeger%7Cpage-content-tester-parent%7C1.0%7Cpom)

```
<parent>
    <groupId>io.github.christian-draeger</groupId>
    <artifactId>page-content-tester-parent</artifactId>
    <version>${version.from.maven.central.badge}</version>
    <relativePath/>
</parent>
```
- if you want to setup jUnit and surefire yourself just don't use the parent pom and add the dependency to the dependencies-section of your pom.xml (not recommended because you will loose the test runner and parallelization)
```
<dependency>
    <groupId>io.github.christian-draeger</groupId>
    <artifactId>page-content-tester</artifactId>
    <version>${version.from.maven.central.badge}</version>
</dependency>
```

<h2 align="center">Configuration</h2>

## configure your request
Set your global fetcher settings by placing a `paco.properties` file in your project under `src/test/resources/` and paste the values [paco.properties](https://github.com/christian-draeger/page-content-tester/blob/master/src/test/resources/paco.properties) to the file.

From time to time you may have specific behaviour for a certain test, so it's not always suffice to only have global settings.
In this case use the configuration possibilities of the __[`@Fetch`-annotation](#the-fetch-annotation)__ or the  __[`fetcher()`-method](#the-fetcher-method)__.
- nearly all values can be set individually for test methods and classes via Annotation or parameters builder
- annotated values or values set via parameters builder will always win over global config
        
### the fetch annotation
If you want to configure your http call using constant values using the annotation is preferred.
The fetch process will be finished before your actual test starts, which is good because you can not run into race conditions.
You can set test specific values for the used protocol, referrer, user-agent, device (if you don't need a specific user-agent string), the used http method (get, post, delete, ...), port, url-prefix, request timeout, retries on timeout, setting cookies and if the request should follow redirects.
All these parameters are optional and if not set taken from your global config.
The only required parameter is url.

#### on methods  
```
public class UsingFetchAnnotationTest extends Paco {

    @Test
    @Fetch(url = "localhost:8089/example")
    public void get_page_and_check_title() {
        assertThat(page.getTitle()).isEqualTo("example title");
    }
    
    // assuming we have a rest endpoint that will answer with {"data": "some value"}
    @Test
    @Fetch(url = "localhost:8089/example", method = POST)
    public void post_and_check_response_body() {
        assertThat(page.getContentType()).isEqualTo("application/json");
        assertThat(page.getJsonResponse().get("data")).isEqualTo("some value");
    }
    // POST with request body
    @Test
    @Fetch(url = "localhost:8089/example", method = POST, requestBody = "{\"key\":\"value\"}")
    public void post_and_check_response_body() {
        assertThat(page.getContentType()).isEqualTo("application/json");
        assertThat(page.getJsonResponse().get("data")).isEqualTo("some value");
    }
}
```   

#### on classes
The **@Fetch** annotation can be used on methods as well as on classes.
```
@Fetch(url = "localhost:8089/example")
public class UsingFetchAnnotationTest extends Paco {

    @Test
    public void get_page_and_check_title() {
        assertThat(page.getTitle()).isEqualTo("example title");
    }
}
```
#### mutliple fetches (repeatable)
Furthermore the **@Fetch** annotation is repeatable. This allows you to fetch multiple pages before running your actual test.
If you fetch multiple pages there are 3 possibilities to get a page object inside your test and do your stuff with the response.

##### page picker
###### 1. by index (possible because all fetches via annotation on a class or method will be done sequentially, means they are always in correct order)

```
public class UsingMultipleFetchAnnotationsTest extends Paco {

    @Test
    @Fetch(url = "localhost:8089/example")
    @Fetch(url = "localhost:8089/anotherExample")
    public void get_page_and_check_title() {
        assertThat(page.get(0).getTitle()).isEqualTo("example title");
        assertThat(page.get(1).getTitle()).isEqualTo("anotherExample title");
    }
}
```   
###### 2. by url snippet (which should be better readable as using the index in most cases)
```
public class UsingMultipleFetchAnnotationsTest extends Paco {

    @Test
    @Fetch(url = "localhost:8089/example")
    @Fetch(url = "localhost:8089/anotherExample")
    public void get_page_and_check_title() {
        assertThat(page.get("example").getTitle()).isEqualTo("example title");
        assertThat(page.get("anotherExample").getTitle()).isEqualTo("anotherExample title");
    }
}
```   
###### 3. by device type
```
public class UsingMultipleFetchAnnotationsTest extends Paco {

    @Test
    @Fetch(url = "localhost:8089/example")
    @Fetch(url = "localhost:8089/example", device = MOBILE)
    public void get_page_and_check_user_agent() {
	assertThat(page.get(DESKTOP).getUserAgent()).isEqualTo(DESKTOP.value);
	assertThat(page.get(MOBILE).getUserAgent()).isEqualTo(MOBILE.value);
    }
}
```   

###### 4. by url snipped and device type (useful if you want to fetch a page with a desktop user-agent and a mobile user-agent to check differences etc.)
```
public class UsingMultipleFetchAnnotationsTest extends Paco {

    @Test
    @Fetch(url = "localhost:8089/example")
    @Fetch(url = "localhost:8089/example", device = MOBILE)
    @Fetch(url = "localhost:8089/anotherExample")
    public void get_page_and_check_user_agent() {
	assertThat(page.get("example", DESKTOP).getUserAgent()).isEqualTo(DESKTOP.value);
	assertThat(page.get("example", MOBILE).getUserAgent()).isEqualTo(MOBILE.value);
	assertThat(page.get("anotherExample").getUserAgent()).isEqualTo(DESKTOP.value);
    }
}
```   


### the fetcher method
From time to time it's necessary to pass dynamic values to your http call configuration. Another possible scenario could be that you need to do a http call, do something different and doing another http call afterwards.
In these cases you should use the fetcher method and pass your configuration values by using the params method and override the global config values you want to change via the builder the params method returns.
     
```
public class UsingFetcherMethodTest extends Paco {

    @Test
    public void get_page_and_check_title() {
        final Page page = fetcher(params().urlToFetch("http://localhost:8089/example").build());
        assertThat(page.getTitle()).isEqualTo("i'm the title");
    }

    @Test
    public void post_and_check_response_body() {
        String body = "{\"data\":\"test2\"}";
        final Page page = fetcher(params()
                .urlToFetch("http://localhost:8089/replay-post-body")
                .method(POST)
                .requestBody(body)
                .build());
        assertThat(page.getContentType()).isEqualTo("application/json");
        assertThat(page.getJsonResponse().get("data")).isEqualTo("test2");
    }
}
```     
     
        
### Exclude Tests from parallel execution
Some tests are not intended to run parallel or it would be big hassle to make them work in parallel?
You can annotate single test classes with `@NotThreadSafe` if there are race conditions in your tests. This way you can isolate conflicting groups of tests, run them sequentially and still run other test classes in parallel. 
In general it's always a matter of your test setup or the tests itself if race conditions prevent you from a parallel execution. The `@NotThreadSafe`-annotation should just be seen as little workaround as long as you make the affected tests work in parallel.
You should always try to isolate your tests enough that they don't affect each other either running in parallel or running sequentially.

### Caching
**Paco** provides an built in loading cache that is enabled by default. It avoids you from making duplicate calls and make your test suite run faster. if don't want your duplicate calls taken from cache it can be deactivated via `paco.properties`.

<h2 align="center">Test Examples</h2>

### Overview of possibilities on your Page object (the response)
```
public class ExampleUsageTest extends PageContentTester {

    private static final String GITHUB_URL = "https://github.com/christian-draeger";
    private static final String GOOGLE_URL = "http://www.google.de";

    @Test
    @Fetch(GITHUB_URL)
    public void fetch_url_via_annotation() {
        assertThat(page.get().getElementCount("h1"), is(1));
    }
    
    @Test
    @Fetch(protocol = Fetch.Protocol.HTTPS, urlPrefix = "en", url = "wikipedia.org")
    public void fetch_page_via_annotation_and_build_url() {
        assertThat(page.get().getUrl(), equalTo("https://en.wikipedia.org"));
    }

    @Test
    @Fetch(GITHUB_URL)
    @FetchGOOGLE_URL)
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
    @Fetch(GITHUB_URL)
    @Fetch(GOOGLE_URL)
    public void fetch_multiple_pages_via_annotation_and_get_pages_by_index() {
        FetchedPage github = page.get(0);
        FetchedPage google = page.get(1);

        assertThat(github.isElementPresent("h1"), is(true));
        assertThat(google.isElementPresent("#footer"), is(true));
        assertThat(google.getUrl(), equalTo(GOOGLE_URL));
    }
    
    @Test
    @Fetch(url = "whatsmyuseragent.org/", userAgent = MOBILE_USER_AGENT)
    public void fetch_as_mobile_user_agent_by_annotation() {
        String ua = page.get().getElement("p.intro-text").text();
        assertThat(ua).contains(MOBILE_USER_AGENT);
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
It just works because of other awesome open-source libraries. Please check the [third party licenses](https://github.com/christian-draeger/page-content-tester/blob/master/LIBRARIES.md).
