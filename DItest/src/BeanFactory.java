import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Dizan
 * Date: 07.11.13
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */
public class BeanFactory {
    private BeanFactory(){}

    public static BeanFactory create() {
        return new BeanFactory();
    }

    public  SomeBean createBean() {
        return new SomeBeanImp();
    }

    //without checking @Bean yet
    public  <T> T lookup(Class<T> c) throws IllegalAccessException, InstantiationException {
        T returnedObject;
    // cheking singleton
        /*   System.out.println(c);
        Annotation[] annotations=c.getAnnotations();
        for(Annotation annotation : annotations){

                System.out.println(annotation.annotationType());

        }*/
        returnedObject= (T) c.newInstance();
        Annotation annotation = c.getAnnotation(Bean.class);
        if(annotation instanceof Bean){
            Bean myAnnotation = (Bean) annotation;
            System.out.println("singleton: " + myAnnotation.singleton());
        } else{
           return returnedObject;
        }
        //TODO: cheking @Bean and singleton

        // TODO: create new value
        Field[] fields=c.getDeclaredFields();
        for(Field field:fields){
            Annotation[] annotations = field.getDeclaredAnnotations();
            for(Annotation fAnnotation : annotations){
                if(fAnnotation instanceof InjectedBean){
                    InjectedBean myAnnotation = (InjectedBean) fAnnotation;
                    System.out.println("Here is injected field");

                    field.set(returnedObject,"fds");
                    lookup(field.getType());
                }
            }
        }

        return returnedObject;
    }
}


