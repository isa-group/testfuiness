package esadrcanfer.us.alumno.autotesting.util;

import androidx.test.uiautomator.UiObject2;

import java.util.HashMap;
import java.util.Map;

public class ObjectsMapParser {

    public static Map<Integer, Map<String, String>> parse(String path){

        Map<Integer, Map<String, String>> objectsMapParsed = new HashMap<>();

        ReadUtil reader = new ReadUtil(path);
        String fileText = reader.readText();
        String[] fileTextSplitted = fileText.split("\n");
        String propertySubstring;
        Integer elementHashCode;
        String property;
        String propertyValue;
        Map<String, String> properties;

        for(String elementProperty: fileTextSplitted){

            propertySubstring = elementProperty.substring(elementProperty.indexOf(".")+1);
            elementHashCode = Integer.parseInt(elementProperty.substring(0, elementProperty.indexOf(".")).trim());
            property = propertySubstring.substring(0, propertySubstring.indexOf(":")).trim();
            propertyValue = propertySubstring.substring(propertySubstring.indexOf(":")+1).trim();

            if(objectsMapParsed.containsKey(elementHashCode)){
                objectsMapParsed.get(elementHashCode).put(property, propertyValue);
            }else{
                properties = new HashMap<>();
                properties.put(property, propertyValue);

                objectsMapParsed.put(elementHashCode, properties);
            }

        }

        return objectsMapParsed;
    }

    public static void toFile(String fileName, Map<Integer, Map<String, String>> objectsMap){

        WriterUtil writer = new WriterUtil(fileName);

        Map<String, String> properties;

        for(Integer key: objectsMap.keySet()){

            properties = objectsMap.get(key);

            for(String property: properties.keySet()){
                writer.write(key.hashCode() + "." + property + ": " + properties.get(property));
            }

        }
    }

}
