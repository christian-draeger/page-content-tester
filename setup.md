# Setup

## Getting Super Powers

 To get the best parallelization result add this parent pom to your pom.xml \(recommended\).

* it will setup all the configurations for an efficient parallelization of your jUnit tests automatically
* you will get the full feature set with all advantages like:
  * default request configuration via properties file
  * easy to use by jUnit Rule
  * nicely printed test results
  * automatically stored DOM if element not found for better debugging

{% tabs %}
{% tab title="Maven" %}
```markup
<parent>
    <groupId>io.github.christian-draeger</groupId>
    <artifactId>page-content-tester-parent</artifactId>
    <version>${version.from.maven.central.badge}</version>
    <relativePath/>
</parent>
```
{% endtab %}

{% tab title="Gradle" %}
```groovy

```
{% endtab %}
{% endtabs %}

{% hint style="info" %}
 if you want to know what the exact predefined jUnit settings are, just have a look at the pluginManagement section of the [parent pom](https://search.maven.org/#artifactdetails%7Cio.github.christian-draeger%7Cpage-content-tester-parent%7C1.0%7Cpom)
{% endhint %}

## Custom Setup

if you want to setup jUnit and surefire yourself just don't use the parent pom and add the dependency to the dependencies-section of your pom.xml

{% tabs %}
{% tab title="Maven" %}
```markup
<dependency>
    <groupId>io.github.christian-draeger</groupId>
    <artifactId>page-content-tester</artifactId>
    <version>${version.from.maven.central.badge}</version>
</dependency>
```
{% endtab %}

{% tab title="Gradle" %}
```groovy

```
{% endtab %}
{% endtabs %}



