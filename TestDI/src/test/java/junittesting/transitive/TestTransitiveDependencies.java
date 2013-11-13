package junittesting.transitive;


import junit.framework.Assert;
import my.diframework.exceptions.CycleException;
import my.diframework.lib.BeanFactory;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Dizan
 * Date: 13.11.13
 * Time: 12:33
 * To change this template use File | Settings | File Templates.
 */
public class TestTransitiveDependencies {
    @Test
    public void test() throws Exception {
        BeanFactory beanFactory= BeanFactory.create("junittesting.transitive");
        SomeClass testB=beanFactory.lookup(SomeClass.class);
        Assert.assertTrue(testB.getField().getField() instanceof SomeImpl1);
    }
}
