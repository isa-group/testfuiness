package esadrcanfer.us.alumno.autotesting.generators;


import androidx.test.uiautomator.UiObjectNotFoundException;

import net.sf.extjwnl.JWNLException;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import esadrcanfer.us.alumno.autotesting.classes.Person;

public class ReflectionGenerator extends AbstractGenerator{

    static Map<String, Object> attributeMap = new HashMap<String, Object>();
    static Object value;
    Object target;

    public ReflectionGenerator(Object target){
        this.target = target;
    }

    public void generateClass(String attributeName, Object value) throws IllegalAccessException, NoSuchFieldException {

        final Class instanceClass = target.getClass();
       Field attribute = instanceClass.getDeclaredField(attributeName);
            if(attribute!=null){
                attribute.setAccessible(true);
                attribute.set(target, value);
            }
    }


    public String generate() throws JWNLException, UiObjectNotFoundException {

        String target = this.target.toString();
        Class<?> targetClass = null;
        try {
            targetClass = Class.forName("esadrcanfer.us.alumno.autotesting.classes."+target);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object p = null;
        Constructor constructor = null;

        try {
            constructor = targetClass.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            p = constructor.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        Field[] attributes = p.getClass().getDeclaredFields();
        for(Field a: attributes){
            String fieldName = a.getName();

            if(fieldName.contains("name") || fieldName.contains("Name") || fieldName.contains("breed")){
                RandomIntegerGenerator randomInt = new RandomIntegerGenerator(1,100);
                Long seed = 0L + randomInt.generate();
                DictionaryBasedValueGenerator dict = new DictionaryBasedValueGenerator(1, seed);
                value = dict.generate();

            }else if(fieldName.contains("mail")){
                RandomRegexGenerator regex = new RandomRegexGenerator("email");
                value = regex.generate();
            }else if(fieldName.contains("chip")){
                List<Integer> integerList = new ArrayList<Integer>();
                integerList.add(123123123);
                integerList.add(678678678);
                integerList.add(456456456);
                IntegerListGenerator randomInt = new IntegerListGenerator(integerList);
                value = randomInt.generate();
            }else{
                RandomIntegerGenerator randomInt = new RandomIntegerGenerator(1,100);
                value = randomInt.generate();
            }
            attributeMap.put(fieldName, value);
            ReflectionGenerator generator = new ReflectionGenerator(p);
            try {
                generator.generateClass(a.getName(), value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return p.toString();
    }

}
