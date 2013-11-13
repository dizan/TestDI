package junittesting.implementations.multiple;

import my.diframework.lib.Bean;
import my.diframework.lib.InjectedBean;

/**
 * Created with IntelliJ IDEA.
 * User: Dizan
 * Date: 13.11.13
 * Time: 14:13
 * To change this template use File | Settings | File Templates.
 */
@Bean(singleton=false)
public class SomeClass {
    @InjectedBean
    SomeInterface s;
}
