package pretest;

import my.diframework.lib.InjectedBean;

/**
 * Created with IntelliJ IDEA.
 * User: Dizan
 * Date: 08.11.13
 * Time: 13:56
 * To change this template use File | Settings | File Templates.
 */
@my.diframework.lib.Bean(singleton=true)
public class TestB2 {
    public TestB2(){
        System.out.println("created "+this.getClass());
    }
  //  @InjectedBean
  //  pretest.TestB s;


}
