package esadrcanfer.us.alumno.autotesting.algorithms;

import android.os.Environment;
import android.util.Log;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;
import esadrcanfer.us.alumno.autotesting.util.ObjectsMapParser;

public class WATERReparation extends BaseReparationAlgorithm{

    Map<UiObject, List<String>> oldVersionData;
    Map<Integer, Map<String, String>> newVersionData;
    List<Action> repairs;

    public WATERReparation(){
        this.repairs = new ArrayList<>();
    }

    @Override
    public TestCase repair(UiDevice device, TestCase buggyTestCase, int breakingPoint) throws UiObjectNotFoundException {
        //TODO
        return null;
    }

    public static Map<Integer, Map<String, String>> getTestWATERData(UiDevice device, TestCase bugTestCase, String fileName){

        List<Action> testActions = bugTestCase.getTestActions();
        Set<UiObject2> elements = new HashSet<>();
        Map<Integer, Map<String, String>> objectsMap = new HashMap<>();

        int index=0;
        Action a;
        try{

            for(index=0;index<testActions.size();index++) {

                addScreenUiObjects(elements, device);
                updateObjectsMap(objectsMap, elements);
                a=testActions.get(index);
                a.perform();

            }

        } catch (Exception e){

            Map<String, String> errorMap= new HashMap<>();

            errorMap.put("errorMessage", e.getMessage());
            errorMap.put("errorActionIndex", ""+index);

            objectsMap.put(0, errorMap);

            Log.d("ISA", "The test failed at action " + index+1);
        }

        if(!(fileName == null)){
            ObjectsMapParser.toFile(fileName, objectsMap);
        }

        return objectsMap;
    }

    public static void addScreenUiObjects(Set<UiObject2> elements, UiDevice device){
        List<UiObject2> objects = listUiObjects(device);
        elements.addAll(objects);
    }

    public static void updateObjectsMap(Map<Integer, Map<String, String>> objectsMap, Set<UiObject2> objects){

        List<String> objectPropertiesList;
        Map<String, String> objectProperties;

        for(UiObject2 object: objects){

            if(!objectsMap.containsKey(object.hashCode())){

                objectProperties = new HashMap<>();

                objectPropertiesList = Arrays.asList(object.getResourceName(), object.getText(), object.getClassName(),
                        object.getParent().getResourceName(), ""+object.getChildren().size(),
                        ""+object.isCheckable(), ""+object.isClickable(),
                        ""+object.hashCode());

                objectProperties.put("id", objectPropertiesList.get(0));
                objectProperties.put("text", objectPropertiesList.get(1));
                objectProperties.put("class", objectPropertiesList.get(2));
                objectProperties.put("parentId", objectPropertiesList.get(3));
                objectProperties.put("numberOfChildren", objectPropertiesList.get(4));
                objectProperties.put("checkable", objectPropertiesList.get(5));
                objectProperties.put("clickable", objectPropertiesList.get(6));
                objectProperties.put("hashCode", objectPropertiesList.get(7));

                objectsMap.put(object.hashCode(), objectProperties);

            }

        }

    }
}
