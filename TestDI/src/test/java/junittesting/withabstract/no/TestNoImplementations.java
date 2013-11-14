package junittesting.withabstract.no;


import my.diframework.exceptions.CycleException;
import my.diframework.exceptions.NoImplementationException;
import my.diframework.lib.BeanFactory;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Dizan
 * Date: 13.11.13
 * Time: 12:33
 * To change this template use File | Settings | File Templates.
 */
public class TestNoImplementations {
    @Test(expected = NoImplementationException.class)
    public void test() throws Exception {
        BeanFactory beanFactory= BeanFactory.create("junittesting.withabstract.no");
        SomeClass testB=beanFactory.lookup(SomeClass.class);
    }
}
