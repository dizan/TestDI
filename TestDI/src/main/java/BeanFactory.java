import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author  Dizan
 * @version    0.9
 */

/**
 * <p>Factory track injected instances and have knowledge of
 * accessible beans-i.e. ones marked @Bean</p>
 *
 * @return instance of BeanFactory
 */
public class BeanFactory {
    private  ArrayList<String> usedClasses=new ArrayList<String>();
    private ArrayList<Object> singletonList=new ArrayList<Object>();
    private Object currentObject;
    boolean firstUsage=true;
    boolean hereIsInjectedField=false;
    private BeanFactory(){}

    /**
     * <p>Creates new instance of Factory</p>
     *
     * @return instance of BeanFactory
     */
    public static BeanFactory create() {
        return new BeanFactory();
    }

    /**
     * <p>This finds all the injectable classes in the classpath whitch
     * are assignable to "Class<T> c"; Hand the case when there is none
     * or more than one.</p>
     *
     * @return object with type T, that has all dependency injections
     */
    public  <T> T lookup(Class<T> c) throws Exception {
        T returnedObject=null;
        String implementationName="";

        //проверка на цикличную зависимость
        checkCycleDependency(c);

        // проверка на первое использование для избежания создания двух экземпляров одного объекта
        if(firstUsage){
            returnedObject= createWithSingleton(c);//(T) c.newInstance();
            firstUsage=false;
        }else{
            returnedObject=(T)currentObject;
        }

        // проверка на инжектируемое поле
        Field[] fields=c.getDeclaredFields();
        for(Field field:fields){
            //hereIsInjectedField=false;
            Annotation[] annotations = field.getDeclaredAnnotations();
            for(Annotation fAnnotation : annotations){
                if(fAnnotation instanceof InjectedBean){
               //     hereIsInjectedField=true;
                    System.out.println("Here is injected field "+ field.getType()+" in class "+c.getName());
                    //если поле-интерфейс, то пробуем взять реализацию, иначе отдаём инстанс класса
                    if(field.getType().isInterface()){
                        implementationName=getImplementation(field.getType());
                        Class implClass=Class.forName(implementationName);
                        currentObject=createWithSingleton(implClass);//implClass.newInstance();
                        field.set(returnedObject,currentObject);
                        lookup(implClass);
                    } else {
                        currentObject=createWithSingleton(field.getType());//field.getType().newInstance();
                        field.set(returnedObject,currentObject);
                        lookup(field.getType());
                    }
                }
            }
            // обнуляем список используемых классов
            firstUsage=true;
            usedClasses=new ArrayList<String>();
        }

        System.out.println("End of field cycle");
        return returnedObject;
    }

    private void checkCycleDependency(Class c) throws Exception {
        if (usedClasses.contains(c.getName())){
            System.out.println(c.getName()+" used");
            throw new Exception("There are cycle dependency");
        }else{
            usedClasses.add(c.getName());
        }
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
    // создаёт экземпляр с учётом параметра singleton
    private  <T> T createWithSingleton(Class<T> c) throws Exception {
         //TODO
        Bean myAnnotation;
        boolean isSingleton=false;
        T objectForReturn;

        // проверка на @Bean
        Annotation annotation = c.getAnnotation(Bean.class);
        if(annotation instanceof Bean){
            myAnnotation = (Bean) annotation;
            if(myAnnotation.singleton()) isSingleton=true;
            System.out.println("singleton: " + myAnnotation.singleton());
        }else{
            throw new Exception(c.getName()+" hasn`t annotation @Bean");
        }

        if(isSingleton==false){
            return c.newInstance();
        }else{
            for (Object o:singletonList){
                if(o.getClass().equals(c)){
                    return (T) o;
                }
            }
            objectForReturn=c.newInstance();
            singletonList.add(objectForReturn);
            return  objectForReturn;

        }

    }

}


