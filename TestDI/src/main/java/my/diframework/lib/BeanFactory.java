package my.diframework.lib;

import my.diframework.exceptions.*;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
    private static String packagesToSearch[];
    private String outerObject="";


    private BeanFactory(){}

    /**
     * <p>Creates new instance of Factory</p>
     *
     * @return instance of BeanFactory
     */
    public static BeanFactory create(String ...packages) {
       packagesToSearch=packages;
       System.out.println(packagesToSearch[0]);
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
        int modifiers;
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
            Annotation[] annotations = field.getDeclaredAnnotations();
            for(Annotation fAnnotation : annotations){
                if(fAnnotation instanceof InjectedBean){
                    modifiers=field.getModifiers();

                    if(Modifier.isStatic(modifiers)){
                        throw new ModifierException("Static modifier");
                    }

                    if(Modifier.isFinal(modifiers)){
                        throw new ModifierException("Final modifier");
                    }

                    System.out.println("Here is injected field "+ field.getType()+" in class "+c.getName());
                    outerObject=c.getName();   // для выдачи эксепшн в случае цикличности
                    //если поле-интерфейс, то пробуем взять реализацию, иначе отдаём инстанс класса
                    if(field.getType().isInterface()){
                        implementationName=getImplementation(field.getType());
                        Class implClass=Class.forName(implementationName);
                        currentObject=createWithSingleton(implClass);//implClass.newInstance();
                        field.setAccessible(true);
                        field.set(returnedObject,currentObject);
                        lookup(implClass);
                    } else {
                        currentObject=createWithSingleton(field.getType());//field.getType().newInstance();
                        field.setAccessible(true);
                        field.set(returnedObject,currentObject);
                        lookup(field.getType());
                    }
                }
            }
            // обнуляем список используемых классов
            firstUsage=true;
            usedClasses=new ArrayList<String>();
        }

        return returnedObject;
    }

    private void checkCycleDependency(Class c) {
        if (usedClasses.contains(c.getName())){
            System.out.println(c.getName()+" used");
            throw new CycleException("There are cycle dependency for class "+c.getName()+" in "+outerObject);
        }else{
            usedClasses.add(c.getName());
        }
    }


    private String getImplementation(Class c) throws Exception {
        String returnedImpl="";
        int beansCount=0;
        Reflections reflections = new Reflections(packagesToSearch);

        Set<String> subTypes = reflections.getStore().getSubTypesOf(c.getName());

        if(subTypes.size()==0){
            throw new NoImplementationException("There are no implementation of interface "+c.getName());
        }

        if(subTypes.size()>=1){
            for(String subType:subTypes){
                if(checkIfBean(Class.forName(subType))==true){
                    beansCount++;
                    returnedImpl=subType;
                }
            }
            if(beansCount==0){
                throw new NoImplementationException("There are no implementation of interface "+c.getName());
            }
            if(beansCount==1){
                return returnedImpl;
            }
            if(beansCount>1){
                throw new MultipleImplementationException("There are more than 1 implementation of interface "+c.getName());
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
        Bean myAnnotation;
        boolean isSingleton=false;
        T objectForReturn;

        // проверка на Bean
        Annotation annotation = c.getAnnotation(Bean.class);
        if(annotation instanceof Bean){
            myAnnotation = (Bean) annotation;
            if(myAnnotation.singleton()) isSingleton=true;
            System.out.println("singleton: " + myAnnotation.singleton());
        }else{
            throw new NoAnnotationException(c.getName()+" hasn`t annotation @Bean");
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


