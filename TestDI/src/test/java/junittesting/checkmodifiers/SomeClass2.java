package junittesting.checkmodifiers;

import my.diframework.lib.Bean;
import my.diframework.lib.InjectedBean;

/**
 * Created with IntelliJ IDEA.
 * User: Dizan
 * Date: 13.11.13
 * Time: 17:47
 * To change this template use File | Settings | File Templates.
 */
@Bean(singleton = true)
public class SomeClass2 {
    @InjectedBean
    static SomeInterface s;
}
