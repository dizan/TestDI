/**
 * Created with IntelliJ IDEA.
 * User: Dizan
 * Date: 08.11.13
 * Time: 13:56
 * To change this template use File | Settings | File Templates.
 */
@Bean(singleton=true)
public class TestB2 {
    TestB2(){
        System.out.println("created "+this.getClass());
    }
//    @InjectedBean
//    TestB s;


}
