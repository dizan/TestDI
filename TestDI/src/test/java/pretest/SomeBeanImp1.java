package pretest;

import my.diframework.lib.InjectedBean;

/**
 * Created with IntelliJ IDEA.
 * User: Dizan
 * Date: 13.11.13
 * Time: 11:31
 * To change this template use File | Settings | File Templates.
 */
//@my.diframework.lib.Bean(singleton =false)
public class SomeBeanImp1 implements SomeBean{
    public SomeBeanImp1(){
        System.out.println("created impl "+this.getClass());
    }
    @InjectedBean
    TestB2 s;

    @InjectedBean
    TestB2 s2;
}
