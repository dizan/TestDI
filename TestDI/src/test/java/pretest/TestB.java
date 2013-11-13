package pretest;

/**
 * Created with IntelliJ IDEA.
 * User: Dizan
 * Date: 08.11.13
 * Time: 13:23
 * To change this template use File | Settings | File Templates.
 */
@my.diframework.lib.Bean(singleton=true)
public class TestB {
   public TestB(){
        System.out.println("created "+this.getClass());
    }
    @my.diframework.lib.InjectedBean
   private SomeBean s;

    @my.diframework.lib.InjectedBean
   private SomeBean s2;
//    @my.diframework.lib.InjectedBean
//    String s2;
}


