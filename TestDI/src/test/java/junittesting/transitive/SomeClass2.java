package junittesting.transitive;

import my.diframework.lib.Bean;
import my.diframework.lib.InjectedBean;

/**
 * Created with IntelliJ IDEA.
 * User: Dizan
 * Date: 13.11.13
 * Time: 18:00
 * To change this template use File | Settings | File Templates.
 */
@Bean(singleton=false)
public class SomeClass2 {
    @InjectedBean
    SomeInterface s;

    public SomeInterface getField() {
        return s;
    }
}
