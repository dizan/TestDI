package my.diframework.exceptions;

/**
 * <p>CycleException throws when there are cycle dependency.
 * this exception extends parent exception {@link DIFrameworkException}
 * @see DIFrameworkException
 * @see ModifierException
 * @see MultipleImplementationException
 * @see NoAnnotationException
 * @see NoImplementationException
 */

public class CycleException extends DIFrameworkException {
    public CycleException(String s) {
        super(s);
    }
}
