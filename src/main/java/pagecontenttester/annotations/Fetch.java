package pagecontenttester.annotations;

import static org.jsoup.Connection.Method.GET;
import static pagecontenttester.annotations.Fetch.Protocol.HTTP;
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

    Protocol protocol() default HTTP;
    String urlPrefix() default "";
    String url();
    boolean followRedirects() default true;
    String port() default "";
    DeviceType device() default DESKTOP;
    Method method() default GET;
    String referrer() default "referrer";
    int timeout() default 0; // in milliseconds
    int retriesOnTimeout() default 0;
    Cookie[] setCookies() default @Cookie();
}
