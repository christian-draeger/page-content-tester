# Introduction

> ### Paco is a Java based framework for non-blocking and highly parallelized Dom testing.

### why?

The motivation of bringing this little buffed out guy to live have been the need of having a robust and fast solution to relieve a long running and unstable Selenium suite. After a code dive through these Selenium tests it turned out that lots of them were just checking things \(like Dom elements, displayed data, cookies, etc\) without the need of interacting with a web browser.

### Benefits

#### Speed

Paco is doing his job rapidly fast and reliable. In a bigger test project where this framework is in use it runs ~400 tests in less than 10 seconds. When using Paco you can focus on your tests itself instead of messing around with setting up a complex test project yourself.

#### Convenient Config

Paco allows you to configure all your test specific data individually and directly in place \(on your test method and/or test class\) via annotations. You only need to describe how you want to fetch an http response \(e.g. requesting a web page by using a proxy, mobile userAgent, setting cookies, add a specific referrer, doing a POST that sends some request body, etc\).

#### Readability and Focus

Due to the given convenient methods regarding response relevant data and using the provided jUnit rule, you'll end up with nicely readable and easy to write tests without boilerplate code.

### 

