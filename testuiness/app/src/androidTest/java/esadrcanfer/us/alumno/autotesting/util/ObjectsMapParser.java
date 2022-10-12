package esadrcanfer.us.alumno.autotesting.util;

import androidx.test.uiautomator.UiObject2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectsMapParser {

    public static List<Map<String, Map<String, String>>> parse(String path){

        List<Map<String, Map<String, String>>> uiDataParsed = new ArrayList<>();

        ReadUtil reader = new ReadUtil(path);
        String fileText = reader.readText();
        String[] fileTextSplitted = fileText.split("\n");
        String propertySubstring;
        String elementSelector;
        String property;
        String propertyValue;
        Map<String, Map<String, String>> actionData = new HashMap<>();
        Map<String, String> properties;

        for(String line: fileTextSplitted){

            if(line.startsWith("--")){
                if(actionData.size() > 0) uiDataParsed.add(actionData);
                actionData = new HashMap<>();
            }else{
                propertySubstring = line.substring(line.indexOf("#")+1).trim();
                elementSelector = line.substring(0, line.indexOf("#")).trim();
                property = propertySubstring.substring(0, propertySubstring.indexOf(":")).trim();
                propertyValue = propertySubstring.substring(propertySubstring.indexOf(":")+1).trim();

                if(actionData.containsKey(elementSelector)){
                    actionData.get(elementSelector).put(property, propertyValue);
                }else{
                    properties = new HashMap<>();
                    properties.put(property, propertyValue);

                    actionData.put(elementSelector, properties);
                }

            }

        }

        uiDataParsed.add(actionData);

        return uiDataParsed;
    }

    public static void toFile(String fileName, List<Map<String, Map<String, String>>> uiData){

        WriterUtil writer = new WriterUtil(fileName);

        Map<String, String> properties;

        int index = 1;

        for(Map<String, Map<String, String>> actionMap: uiData){

            writer.write("-- ACTION_" + index + " --");

            for(String key: actionMap.keySet()){

                properties = actionMap.get(key);

                for(String property: properties.keySet()){
                    writer.write(key + " # " + property + ": " + properties.get(property));
                }

            }

            index++;
        }
    }

}
