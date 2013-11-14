package my.diframework.exceptions;

/**
 * <p>NoAnnotationException throws when there are field, marked @InjectedBean,but class of this field isnt
 * Bean class. Mechanish works only with classes marked @Bean.
 * This exception extends parent exception {@link DIFrameworkException}
 * @see CycleException
 * @see DIFrameworkException
 * @see MultipleImplementationException
 * @see ModifierException
 * @see NoImplementationException
 */
public class NoAnnotationException extends DIFrameworkException {
    public NoAnnotationException(String s) {
        super(s);
    }
}
