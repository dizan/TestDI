package junittesting.withabstract.no;

import my.diframework.lib.Bean;
import my.diframework.lib.InjectedBean;

/**
 * Created with IntelliJ IDEA.
 * User: Dizan
 * Date: 13.11.13
 * Time: 14:13
 * To change this template use File | Settings | File Templates.
 */
@Bean(singleton=true)
public class SomeClass {
    @InjectedBean
    SomeAbstract s;
}
