import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Dizan
 * Date: 07.11.13
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */
public class BeanFactory {
    private  ArrayList<String> usedClasses=new ArrayList<String>();
    private BeanFactory(){}

    public static BeanFactory create() {
        return new BeanFactory();
    }

    //without checking @Bean yet
    public  <T> T lookup(Class<T> c) throws Exception {
        T returnedObject;
        String implementationName="";

    // cheking singleton

        if (usedClasses.contains(c.getName())){
            System.out.println(c.getName());
            throw new Exception("There are cycle dependency");

        }
        returnedObject= (T) c.newInstance();
        usedClasses.add(c.getName());

        Annotation annotation = c.getAnnotation(Bean.class);
        if(annotation instanceof Bean){
            Bean myAnnotation = (Bean) annotation;
            System.out.println("singleton: " + myAnnotation.singleton());
        } else{
           return returnedObject;
        }
        //TODO: cheking @Bean and singleton

        // проверка на инжектируемое поле
        Field[] fields=c.getDeclaredFields();
        for(Field field:fields){
            Annotation[] annotations = field.getDeclaredAnnotations();
            for(Annotation fAnnotation : annotations){
                if(fAnnotation instanceof InjectedBean){
                    System.out.println("Here is injected field");
                    //если поле-интерфейс, то пробуем взять реализацию, иначе отдаём инстанс класса
                    if(field.getType().isInterface()){
                        implementationName=getImplementation(field.getType());
                        Class implClass=Class.forName(implementationName);
                        field.set(returnedObject,implClass.newInstance());
                        lookup(implClass);
                    } else{
                        field.set(returnedObject,field.getType().newInstance());
                        lookup(field.getType());
                    }

                }
            }
        }

        return returnedObject;
    }

    private String getImplementation(Class c) throws Exception {
        String returnedImpl="";
        int beansCount=0;
        Reflections reflections = new Reflections("");
        Set<String> subTypes = reflections.getStore().getSubTypesOf(c.getName());

        if(subTypes.size()==0){
            throw new Exception("There are no implementation of interface "+c.getName());
        }

        if(subTypes.size()>=1){
            for(String subType:subTypes){
                if(checkIfBean(Class.forName(subType))==true){
                    beansCount++;
                    returnedImpl=subType;
                }
            }
            if(beansCount==0){
                throw new Exception("There are no implementation of interface "+c.getName());
            }
            if(beansCount==1){
                return returnedImpl;
            }
            if(beansCount>1){
                throw new Exception("There are more than 1 implementation of interface "+c.getName());
            }
        }

        return returnedImpl;
    }

    private boolean checkIfBean(Class c){
        Annotation annotation = c.getAnnotation(Bean.class);
        if(annotation instanceof Bean){
            return true;
        }
        return false;
    }

}


