package pagecontenttester.annotations;

import static org.jsoup.Connection.Method.GET;
import static pagecontenttester.annotations.Fetch.Protocol.NONE;
import static pagecontenttester.fetcher.FetchedPage.DeviceType.DESKTOP;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jsoup.Connection.Method;

import pagecontenttester.fetcher.FetchedPage.DeviceType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Repeatable(FetchPages.class)
public @interface Fetch {

    enum Protocol {
        HTTP("http://"),
        HTTPS("https://"),
        FTP("ftp://"),
        NONE("");

        public final String value;

        Protocol(String value) {
            this.value = value;
        }
    }

    /**
     * URL to fetch without protocol.
     */
    String url();

    /**
     * Protocol that will be used
     */
    Protocol protocol() default NONE;

    /**
     * Prefix that will be added between protocol and url.
     * e.g. the "en." in http://en.wikipedia.org
     */
    String urlPrefix() default "";

    /**
     * Adds a port to the url.
     */
    String port() default "";

    /**
     * Defines if the requested url should follow redirects.
     */
    boolean followRedirects() default true;

    /**
     * Defines the user Agent that will be send with the request.
     * This is helpful to emulate a websites behaviour regarding mobile devices or different browsers.
     */
    DeviceType device() default DESKTOP;

    /**
     * Defines the HTTP request method that should be used
     */
    Method method() default GET;

    /**
     * Defines the referrer that shpuld be set for a request
     */
    String referrer() default "referrer";

    /**
     * Defines the maximum time in milliseconds a request is allowed to take.
     */
    int timeout() default 0;

    /**
     * Defines the amount of retries if there has been a connection timeout
     */
    int retriesOnTimeout() default 0;

    /**
     * Defines a Cookie or several Cookies the can be send with the request.
     * See the @Cookie annotation for more information how to set values for a cookie.
     * If the requested server does not know the cookie it can happen that it won't be replayed from the server,
     * what means that under circumstances you are not able to ask for the cookie in your fetched page result,
     * but the cookie has been send.
     */
    Cookie[] setCookies() default @Cookie();
}
