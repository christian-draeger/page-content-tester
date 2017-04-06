## About

The PageContentTester is a framework for non-blocking and highly parallelized Dom testing.

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


- to use all the power an parallelization of PageContentTester add this parent pom to your pom.xml
  - this will setup all the jUnit parallelizations etc for you, you don't need to configure jUnit yourself anymore
    - if you want to know what the exact predefined junit settings are just have a look at the pluginManagement section in the [PageContentTester pom.xml](https://github.com/christian-draeger/page-content-tester/blob/master/pom.xml)
  - if you want to setup junit yourself just don't use the parent pom

```
    <parent>
        <groupId>io.github.christian-draeger</groupId>
        <artifactId>page-content-tester-parent</artifactId>
        <version>1.0</version>
    </parent>
```
