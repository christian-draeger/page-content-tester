---
description: >-
  **Paco** is a Java based framework for non-blocking and highly parallelized
  Dom testing.
---

# Introduction

The motivation of bringing this little buffed out guy to live have been the need of having a robust and fast solution to relieve a long running and unstable Selenium suite. After a code dive through these Selenium tests it turned out that lots of them were just checking things \(like Dom elements, displayed data, cookies, etc\) without the need of interacting with a web browser.

So Paco was born as an alternative and he's doing his job rapidly fast and reliable. In a bigger test project where this framework is in use it runs ~400 tests in less than 10 seconds. When using Paco you can focus on your tests itself instead of messing around with setting up a complex test project yourself.

