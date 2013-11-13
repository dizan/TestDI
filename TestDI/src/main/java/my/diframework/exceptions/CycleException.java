package my.diframework.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: Dizan
 * Date: 13.11.13
 * Time: 13:54
 * To change this template use File | Settings | File Templates.
 */
public class CycleException extends DIFrameworkException {
    public CycleException(String s) {
        super(s);
    }
}
