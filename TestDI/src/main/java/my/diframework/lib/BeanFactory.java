package my.diframework.lib;

import my.diframework.exceptions.*;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Set;

/**
 * <p> BeanFactory is the root class for the whole mechanism of Dependency Injection,
 * carried out here. Also the root class for accessing bean container.
 * BeanFactory searches for the required classes, makes the dependency injection
 * and returns a bean with all built-dependencies.
 * Visible mechanism classes and implementation should be labeled with annotation @ Bean.
 * Fields in which you implement dependency marked annotation @ InjectedBean.
 * <p> This implementation of Dependency Injection is taken into account in the implementation depending on the parameter
 * singleton for the annotation @ Bean.
 * Depending on the implementation of this option will be embedded object - singleton
 * or for each field in the class which singleton = false new object is will be created.
 * @author Denis Zaykov
 * @since 14 November 2013
 */

public class BeanFactory {
    private  ArrayList<String> usedClasses=new ArrayList<String>();
    private ArrayList<Object> singletonList=new ArrayList<Object>();
    private Object currentObject;
    boolean firstUsage=true;
    private static String packagesToSearch[];
    private String outerObject="";
    boolean flag=true;



    private BeanFactory(){}

    /**
     * <p>Creates new instance of {@link BeanFactory}</p>
     * @param packages allows to search classes in packages, that you specify,
     *  when you call this method.
     * @return instance of BeanFactory
     */
    public static BeanFactory create(String ...packages) {
       packagesToSearch=packages;
       return new BeanFactory();

    }


    /**
     * Return the bean instance that uniquely matches the given object type, if any.
     * @param type type the bean must match; can be an interface.
     * <p>This method does searching by-type lookup territory
     * but may also be translated into a conventional by-name lookup based on the name
     * of the given type.
     * <p>If the mechanism sees the field @ InjectedBean, it checks its type <br>
     *   1) If the type of interface that is being searched implementations   <br>
     *      1.1) If you found the only implementation that is marked @ Bean - is instantiated <br>
     *      1.2) In all other cases - the exception                              <br>
     *   2) If the type-abstract class, conducted a similar search in this class of heirs  <br>
     *      1.1) If you found the only heir, labeled @ Bean - is instantiated        <br>
     *      1.2) In all other cases - the exception                                <br>
     *   3) If the type-regular class, then                                  <br>
     *     3.1) If a class is marked with the annotation @ Bean, create an instance of this class    <br>
     *     3.2) In other ischatsya heirs of this class                                    <br>
     *     3.3) If the heirs do not, an exception is thrown                             <br>
     * @return an instance of the single bean matching the required type
     * @throws ModifierException if there are static or final modifiers on fields to inject.
     * @throws NoImplementationException if there is no implementation of interface
     * @throws MultipleImplementationException if there are more than 1 implementation of interface.
     * @throws NoAnnotationException if there is not exactly one matching bean found
     * @throws CycleException if there are cycle dependency.

     */
    public  <T> T lookup(Class<T> type) throws Exception {
        T returnedObject=null;
        String implementationName="";
        flag=true;
        int modifiers;

        //checking for circular dependency
        checkCycleDependency(type);

        // check on the first use to avoid creating two instances of the same object
        if(firstUsage){
            returnedObject= createWithSingleton(type);
            firstUsage=false;
        }else{
            returnedObject=(T)currentObject;
        }

        // check on the injected field
        Field[] fields=type.getDeclaredFields();
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
                    System.out.println("Here is injected field " + field.getType() + " in class " + type.getName());
                    outerObject=type.getName();   // for the issue in the case of cyclic exception

                    flag=checkIfBean(field.getType());// if the class is not marked @Bean check for the presence of the heirs

                    //if the field is an interface or abstract class or hasn`t @Bean annotation
                    // ,then try to take the implementation or heirs,
                    // otherwise we return an instance of class
                    if(field.getType().isInterface()||Modifier.isAbstract(field.getType().getModifiers())||!flag){
                        implementationName=getImplementation(field.getType());
                        Class implClass=Class.forName(implementationName);
                        currentObject=createWithSingleton(implClass);
                        field.setAccessible(true);
                        field.set(returnedObject,currentObject);
                        lookup(implClass);
                    } else {
                        currentObject=createWithSingleton(field.getType());
                        field.setAccessible(true);
                        field.set(returnedObject,currentObject);
                        lookup(field.getType());
                    }
                }
            }
            // clear the list of classes to be used
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
            throw new NoImplementationException("There are no implementation of interface or abstract class "+c.getName());
        }

        if(subTypes.size()>=1){
            for(String subType:subTypes){
                if(checkIfBean(Class.forName(subType))==true){
                    beansCount++;
                    returnedImpl=subType;
                }
            }
            if(beansCount==0){
                throw new NoImplementationException("There are no implementation " +
                        "of interface or abstract class "+c.getName());
            }
            if(beansCount==1){
                return returnedImpl;
            }
            if(beansCount>1){
                throw new MultipleImplementationException("There are more than 1 implementation " +
                        "of interface or abstract class "+c.getName());
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
    // creates an instance with the parameter singleton
    private  <T> T createWithSingleton(Class<T> c) throws Exception {
        Bean myAnnotation;
        boolean isSingleton=false;
        T objectForReturn;

        // check for Bean
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


