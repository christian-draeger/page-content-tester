package annotations;

import static fetcher.FetchedPage.DeviceType.DESKTOP;
import static org.jsoup.Connection.Method.GET;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jsoup.Connection.Method;

import fetcher.FetchedPage.DeviceType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Repeatable(FetchPages.class)
public @interface Fetch {

    String url();
    DeviceType device() default DESKTOP;
    Method method() default GET;
    String referrer() default "referrer";
    int timeout() default 0;

}
