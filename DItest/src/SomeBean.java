/**
 * Created with IntelliJ IDEA.
 * User: Dizan
 * Date: 07.11.13
 * Time: 13:15
 * To change this template use File | Settings | File Templates.
 */
public interface SomeBean {

}

//@Bean(singleton =false)
class SomeBeanImp implements SomeBean{


}

@Bean(singleton =true)
class SomeBeanImp2 implements SomeBean{
    SomeBeanImp2(){
        System.out.println("created impl "+this.getClass());
    }
    @InjectedBean
    TestB2 s;

//    @InjectedBean
//    TestB2 s2;
}


