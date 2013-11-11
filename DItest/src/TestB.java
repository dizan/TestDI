/**
 * Created with IntelliJ IDEA.
 * User: Dizan
 * Date: 08.11.13
 * Time: 13:23
 * To change this template use File | Settings | File Templates.
 */
@Bean(singleton=true)
public class TestB {
    TestB(){
        System.out.println("created "+this.getClass());
    }
    @InjectedBean
    SomeBean s;

//    @InjectedBean
//    String s2;
}


