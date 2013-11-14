package my.diframework.lib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Basic annotation of mechanism.Mechanism works only with classes and
 * implementations marked @Bean
 * {@link BeanFactory}   see only classes marked this.
 * @author Denis Zaykov
 * @since 14 November 2013
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value= ElementType.TYPE)
public @interface Bean {
    boolean singleton() default false;
}
