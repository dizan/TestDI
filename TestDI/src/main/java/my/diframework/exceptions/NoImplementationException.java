package my.diframework.exceptions;

/**
 * <p>NoImplementationException throws when there are no implementation of interface, marked @Bean.
 * Dependency injection mechanism allows only one implementation of interface, marked Bean.
 * This exception extends parent exception {@link DIFrameworkException}
 * @see CycleException
 * @see ModifierException
 * @see MultipleImplementationException
 * @see NoAnnotationException
 * @see DIFrameworkException
 */

public class NoImplementationException extends DIFrameworkException {
    public NoImplementationException(String s) {
        super(s);
    }
}
