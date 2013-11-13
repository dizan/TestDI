package junittesting.checksingleton.singletontrue;


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
public class TestSingletonField {
    @Test
    public void testIfSingletonTrue() throws Exception {
        BeanFactory beanFactory= BeanFactory.create("junittesting.checksingleton.singletontrue");
        SomeClass testB=beanFactory.lookup(SomeClass.class);
      //  Assert.assertTrue(testB.getField1().equals(testB.getField2()));
        Assert.assertSame(testB.getField1(),testB.getField2());
    }


}
