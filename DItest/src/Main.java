/**
 * Created with IntelliJ IDEA.
 * User: Dizan
 * Date: 08.11.13
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        BeanFactory beanFactory=BeanFactory.create();
        TestB testB=beanFactory.lookup(TestB.class);
        System.out.println(testB.s);
    }
}
