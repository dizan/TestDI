package my.diframework.lib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Also important annotation of mechanism. All fields for injection
 * should be marked @InjectedBean.
 * {@link BeanFactory}   see only fields marked this.
 * @author Denis Zaykov
 * @since 14 November 2013
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value= ElementType.FIELD)
public @interface InjectedBean {

}
