package pretest;


import my.diframework.lib.BeanFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Dizan
 * Date: 08.11.13
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BeanFactory beanFactory= BeanFactory.create("my", "pretest");
        TestB testB=beanFactory.lookup(TestB.class);
      //  System.out.println(testB.s);
      //  System.out.println(testB.s2);


    }
}
