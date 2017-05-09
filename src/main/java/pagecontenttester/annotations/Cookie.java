package pagecontenttester.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Cookie {

    String name() default "1e97fa4a-34d3-11e7-a919-92ebcb67fe33";
    String value() default "";

}
