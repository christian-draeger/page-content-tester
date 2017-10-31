package paco.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
public @interface Cookie {

    /**
     * Defines the name of the cookie
     */
    String name() default "";

    /**
     * Defines the value of the cookie
     */
    String value() default "";

}
