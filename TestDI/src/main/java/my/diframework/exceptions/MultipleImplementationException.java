package my.diframework.exceptions;

/**
 * <p>MultipleImplementationException throws when there are more tha one implementation of interface.
 * Dependency injection mechanism allows only one implementation of interface, marked Bean
 * This exception extends parent exception {@link DIFrameworkException}
 * @see CycleException
 * @see DIFrameworkException
 * @see MultipleImplementationException
 * @see NoAnnotationException
 * @see NoImplementationException
 */

public class MultipleImplementationException extends DIFrameworkException {
    public MultipleImplementationException(String s) {
        super(s);
    }
}
