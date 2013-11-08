/**
 * Created with IntelliJ IDEA.
 * User: Dizan
 * Date: 08.11.13
 * Time: 13:23
 * To change this template use File | Settings | File Templates.
 */
@Bean(singleton=true)
public class TestB {
    @InjectedBean
    TestB2 s;
}
