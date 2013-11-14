package my.diframework.exceptions;

/**
 * <p>ModifierException throws when there are static or final fields, that cant processed by DI mechanism.
 * This exception extends parent exception {@link DIFrameworkException}
 * @see CycleException
 * @see DIFrameworkException
 * @see MultipleImplementationException
 * @see NoAnnotationException
 * @see NoImplementationException
 */

public class ModifierException extends DIFrameworkException {
    public ModifierException(String s) {
        super(s);
    }
}
