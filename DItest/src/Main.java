import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Dizan
 * Date: 08.11.13
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BeanFactory beanFactory=BeanFactory.create();
        TestB testB=beanFactory.lookup(TestB.class);
     /*   Reflections reflections = new Reflections("");
        Set<String> subTypes = reflections.getStore().getSubTypesOf(SomeBean.class.getName());

        for(String c:subTypes){
            System.out.println(c);
        }*/


    }
}
