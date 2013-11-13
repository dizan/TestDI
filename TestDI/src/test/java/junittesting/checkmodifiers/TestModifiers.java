package junittesting.checkmodifiers;


import junit.framework.Assert;
import my.diframework.exceptions.ModifierException;
import my.diframework.lib.BeanFactory;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Dizan
 * Date: 13.11.13
 * Time: 12:33
 * To change this template use File | Settings | File Templates.
 */
public class TestModifiers {
    @Test (expected = ModifierException.class)
    public void testStatic() throws Exception {
        BeanFactory beanFactory= BeanFactory.create("junittesting.checkmodifiers");
        SomeClass testB=beanFactory.lookup(SomeClass.class);

    }
    @Test(expected = ModifierException.class )
    public void testFinal() throws Exception {
        BeanFactory beanFactory= BeanFactory.create("junittesting.checkmodifiers");
        SomeClass2 testB=beanFactory.lookup(SomeClass2.class);

    }
}
