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

### Let the Framework do the annoying setup stuff
- to use all the power and parallelization of PageContentTester add this parent pom to your pom.xml
  - this will setup all the configurations for an efficient parallelization of your jUnit tests automatically, you don't need to configure jUnit yourself anymore
    - if you want to know what the exact predefined junit settings are just have a look at the pluginManagement section in the [PageContentTester pom.xml](https://github.com/christian-draeger/page-content-tester/blob/master/pom.xml)
  - if you want to setup junit yourself just don't use the parent pom

```
<parent>
    <groupId>io.github.christian-draeger</groupId>
    <artifactId>page-content-tester-parent</artifactId>
    <version>1.0</version>
</parent>
```

## Example Project
To see the PageContentTester in action you can checkout or fork the corresponding [example project](https://github.com/christian-draeger/page-content-tester-example). it will give an overview of what and how things can be done. You will also get a feeling how long a test run will take using the PageContentTester (analysing the dom of ±1000 urls is getting done less than 10sec. with a proper internet connection) 
