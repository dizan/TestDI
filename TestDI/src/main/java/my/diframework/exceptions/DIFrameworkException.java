package my.diframework.exceptions;

/**
 * <p>The class DIFrameworkException is a parent Exception class
 * for all exceptions that throws in this framework. Its a form of  {@link RuntimeException}
 * @see CycleException
 * @see ModifierException
 * @see MultipleImplementationException
 * @see NoAnnotationException
 * @see NoImplementationException
 */
public class DIFrameworkException extends RuntimeException {

    public DIFrameworkException(String s) {
        super(s);
    }
}
