package junittesting.checkfields;


import junit.framework.Assert;
import my.diframework.lib.BeanFactory;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Dizan
 * Date: 13.11.13
 * Time: 12:33
 * To change this template use File | Settings | File Templates.
 */
public class TestFieldsInjection {
    @Test
    public void testInjection() throws Exception {
        BeanFactory beanFactory= BeanFactory.create("junittesting.checkfields");
        SomeClass testB=beanFactory.lookup(SomeClass.class);
        Assert.assertTrue(testB.getField() instanceof SomeImpl1);
    }
}
