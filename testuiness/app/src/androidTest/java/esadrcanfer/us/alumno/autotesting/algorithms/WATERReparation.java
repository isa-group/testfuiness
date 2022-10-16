package esadrcanfer.us.alumno.autotesting.algorithms;

import static esadrcanfer.us.alumno.autotesting.algorithms.BaseReparationAlgorithm.listUiObjects;
import static esadrcanfer.us.alumno.autotesting.tests.AutomaticRepairTests.labelsDetection;

import android.util.Log;
import android.util.Pair;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;
import esadrcanfer.us.alumno.autotesting.util.CheckpointUtil;
import esadrcanfer.us.alumno.autotesting.util.ObjectsMapParser;

public class WATERReparation {

    private static final String REPAIR_LOCATOR_NEEDED_PROPS = "id, text, class, description";
    private static final String SIMILARITY_INDEX_NEEDED_PROPS = "numberOfChildren, checkable, clickable, parent";

    List<Map<String, Map<String, String>>> oldVersionData;
    List<Map<String, Map<String, String>>> newVersionData;
    List<TestCase> repairs;
    CheckpointUtil checkpoints;
    String buggyTestCheckpointId;

    public WATERReparation(CheckpointUtil checkpoints, String buggyTestCheckpointId){

        this.repairs = new ArrayList<>();
        this.checkpoints = checkpoints;
        this.buggyTestCheckpointId = buggyTestCheckpointId;

    }

    public List<TestCase> repair(UiDevice device, TestCase buggyTestCase) throws UiObjectNotFoundException {

        String buggyTestCaseFileName = checkpoints.getFileName(buggyTestCheckpointId);
        this.oldVersionData = ObjectsMapParser.parse(checkpoints.getExperimentPath()+"old_versions_data/"+buggyTestCaseFileName.split("\\.")[0]+"-old.txt");

        buggyTestCase.executeBefore();
        this.newVersionData = getTestWATERData(device, buggyTestCase, null);
        buggyTestCase.executeAfter();


        int breakingPoint = Integer.parseInt(newVersionData.get(newVersionData.size()-1).get("exception").get("errorActionIndex"));

        repairLocators(device, buggyTestCase, breakingPoint, oldVersionData.get(breakingPoint), newVersionData.get(breakingPoint));

        if(repairs.size() == 0){
            TestCase lastReparationTry = removeActionFromTestCase(buggyTestCase, breakingPoint);

            TestCase lastRepairSuggestion = checkRepair(device, lastReparationTry, breakingPoint);

            if(lastRepairSuggestion != null){
                repairs.add(lastRepairSuggestion);
            }
        }

        return repairs;
    }

    public static List<Map<String, Map<String, String>>> getTestWATERData(UiDevice device, TestCase bugTestCase, String fileName){

        List<Map<String, Map<String, String>>> uiData = new ArrayList<>();
        List<Action> testActions = bugTestCase.getTestActions();
        Set<UiObject2> elements;
        Map<String, Map<String, String>> objectsMap;

        int index=0;
        Action a;
        try{

            for(index=0;index<testActions.size();index++) {

                elements = new HashSet<>();
                objectsMap = new HashMap<>();

                addScreenUiObjects(elements, device);
                updateObjectsMap(device, objectsMap, elements);
                uiData.add(objectsMap);

                a=testActions.get(index);
                a.perform();
                Thread.sleep(250);

            }

        } catch (Exception e){

            Map<String, Map<String, String>> exceptionMap = new HashMap<>();
            Map<String, String> exceptionDetails= new HashMap<>();

            exceptionDetails.put("errorMessage", e.getMessage());
            exceptionDetails.put("errorActionIndex", ""+index);

            exceptionMap.put("exception", exceptionDetails);

            Log.d("ISA", "The test failed at action " + index+1);

            uiData.add(exceptionMap);
        }

        if(!(fileName == null)){
            ObjectsMapParser.toFile(fileName, uiData);
        }

        return uiData;
    }

    private static void addScreenUiObjects(Set<UiObject2> elements, UiDevice device){
        List<UiObject2> objects = listUiObjects(device);
        elements.addAll(objects);
    }

    private static void updateObjectsMap(UiDevice device, Map<String, Map<String, String>> objectsMap, Set<UiObject2> objects){

        List<String> objectPropertiesList;
        Map<String, String> objectProperties;
        String elementId;
        String elementText;
        UiSelector selector;
        UiObject simpleObject = null;
        String objectSelector;
        String objectXPath;

        for(UiObject2 object: objects){

            elementId = object.getResourceName();
            elementText = object.getText();

            if(elementId != null) {
                selector = new UiSelector().resourceId(elementId);
                simpleObject = device.findObject(selector);
            }else if(elementText != null){
                selector = new UiSelector().text(elementText);
                simpleObject = device.findObject(selector);
            }

            if(simpleObject != null){

                objectSelector = simpleObject.getSelector().toString();

                if(!objectsMap.containsKey(objectSelector)){

                    objectProperties = new HashMap<>();

                    objectXPath = getXPath(object);

                    objectPropertiesList = Arrays.asList(object.getResourceName(), object.getText(), object.getClassName(), object.getContentDescription(),
                            object.getParent().toString(), ""+object.getChildCount(),
                            ""+object.isCheckable(), ""+object.isClickable(),
                            ""+simpleObject.hashCode(), objectXPath);

                    objectProperties.put("id", objectPropertiesList.get(0));
                    objectProperties.put("text", objectPropertiesList.get(1));
                    objectProperties.put("class", objectPropertiesList.get(2));
                    objectProperties.put("description", objectPropertiesList.get(3));
                    objectProperties.put("parent", objectPropertiesList.get(4));
                    objectProperties.put("numberOfChildren", objectPropertiesList.get(5));
                    objectProperties.put("checkable", objectPropertiesList.get(6));
                    objectProperties.put("clickable", objectPropertiesList.get(7));
                    objectProperties.put("hashCode", objectPropertiesList.get(8));
                    objectProperties.put("xpath", objectPropertiesList.get(9));

                    objectsMap.put(objectSelector, objectProperties);

                }
            }
        }
    }

    private void repairLocators(UiDevice device, TestCase buggyTestCase, int breakingPoint, Map<String, Map<String, String>> oldVersionUiObjects, Map<String, Map<String, String>> newVersionUiObjects){

        List<String> matches = new ArrayList<>();
        TestCase auxTestCase;
        TestCase repairSuggestion;

        Map<String, String> node0 = getNodeByLocator(oldVersionUiObjects, buggyTestCase.getTestActions().get(breakingPoint));

        if(node0 != null){
            for(String prop: node0.keySet()){
                if(REPAIR_LOCATOR_NEEDED_PROPS.contains(prop) && !Objects.requireNonNull(node0.get(prop)).isEmpty() && node0.get(prop) != null){
                    matches.addAll(getNodesByProperty(prop, node0.get(prop), newVersionUiObjects));
                }
            }

            for(String node: matches){
                auxTestCase = replaceLocator(device, buggyTestCase, breakingPoint, node);

                repairSuggestion = checkRepair(device, auxTestCase, breakingPoint);

                if(repairSuggestion != null){
                    repairs.add(repairSuggestion);
                }//TODO Add an else clause if interested in counting the precision of the algorithm (null retrievals in checkRepair)
            }

            if(repairs.size() == 0){
                List<String> similarNodes = new ArrayList<>();
                Double similarityIndex;

                for(String nodeLocator: newVersionUiObjects.keySet()){

                    similarityIndex = getSimilarityIndex(node0, Objects.requireNonNull(newVersionUiObjects.get(nodeLocator)));

                    if(similarityIndex>0.5){
                        similarNodes.add(nodeLocator);
                    }

                }

                for(String node: similarNodes){
                    auxTestCase = replaceLocator(device, buggyTestCase, breakingPoint, node);

                    repairSuggestion = checkRepair(device, auxTestCase, breakingPoint);

                    if(repairSuggestion != null){
                        repairs.add(repairSuggestion);
                    }//TODO Add an else clause if interested in counting the precision of the algorithm (null retrievals in checkRepair)
                }
            }
        }

    }

    private Double getSimilarityIndex(Map<String, String> node0Properties, Map<String, String> newNodeProperties){

        double alpha = 0.9;
        double similarityIndex = 0.;
        int rho1;
        double rho2 = 0.;
        String xpathNode0 = node0Properties.get("xpath");
        String xpathNewNode = newNodeProperties.get("xpath");

        Pair<String, String> simplifiedPaths = simplifyPaths(xpathNode0, xpathNewNode);

        String pseudoXPathNode0 = simplifiedPaths.first;
        String pseudoXPathNewNode = simplifiedPaths.second;

        if(Objects.equals(node0Properties.get("class"), newNodeProperties.get("class"))){

            rho1 = 1-(levenshteinDistance(pseudoXPathNode0, pseudoXPathNewNode)/Math.max(pseudoXPathNode0.length(), pseudoXPathNewNode.length()));

            for(String prop: SIMILARITY_INDEX_NEEDED_PROPS.split(",")){

                if(Objects.equals(node0Properties.get(prop.trim()), newNodeProperties.get(prop.trim()))){
                    rho2+=1;
                }

            }

            rho2 = rho2/4; //We use 4 instead of 5 (which is in the approach) because we're using only 4 props to calculate rho2

            similarityIndex = (rho1*alpha + rho2*(1-alpha));

        }

        return similarityIndex;
    }

    private static Pair<String, String> simplifyPaths(String path1, String path2){

        String[] path1Split = null;
        String[] path2Split = null;

        if(path1 != null){
            path1Split = path1.substring(2).split("/");
        }
        if(path2 != null){
            path2Split = path2.substring(2).split("/");
        }

        StringBuilder pseudoXPath1 = new StringBuilder();
        StringBuilder pseudoXPath2 = new StringBuilder();

        pseudoXPath1.append("//");
        pseudoXPath2.append("//");

        int indexBuffer;

        if(path1Split == null && path2Split != null){
            for(int i = 0; i<path2Split.length-1; i++){
                pseudoXPath2.append(path2Split[i]).append("/");
            }
        }else if(path1Split != null && path2Split == null){
            for(int i = 0; i<path1Split.length-1; i++){
                pseudoXPath1.append(path1Split[i]).append("/");
            }
        }else if(path1Split != null && path2Split != null){
            for (indexBuffer = 0; indexBuffer<Math.min(path1Split.length-1, path2Split.length-1); indexBuffer++){

                if(!Objects.equals(path1Split[indexBuffer + 1], path2Split[indexBuffer + 1])){
                    for(int j = indexBuffer; j<Math.min(path1Split.length, path2Split.length); j++){
                        pseudoXPath1.append(path1Split[j]).append("/");
                        pseudoXPath2.append(path2Split[j]).append("/");
                    }
                    break;
                }
            }

            if(path1Split.length == indexBuffer+1){
                for(int j = indexBuffer; j<path2Split.length;j++){
                    pseudoXPath2.append(path2Split[j]).append("/");
                }
            }else if(path2Split.length == indexBuffer+1){
                for(int j = indexBuffer; j<path1Split.length;j++){
                    pseudoXPath1.append(path1Split[j]).append("/");
                }
            }
        }

        return new Pair<>(pseudoXPath1.toString(), pseudoXPath2.toString());

    }

    private static int minimum(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    public static int levenshteinDistance(String str1, String str2) {
        return levenshteinDistance(str1.toCharArray(),
                str2.toCharArray());
    }
    private static int levenshteinDistance(char [] str1, char [] str2) {
        int [][]distance = new int[str1.length+1][str2.length+1];

        for(int i=0;i<=str1.length;i++){
            distance[i][0]=i;
        }
        for(int j=0;j<=str2.length;j++){
            distance[0][j]=j;
        }
        for(int i=1;i<=str1.length;i++){
            for(int j=1;j<=str2.length;j++){
                distance[i][j]= minimum(distance[i-1][j]+1,
                        distance[i][j-1]+1,
                        distance[i-1][j-1]+
                                ((str1[i-1]==str2[j-1])?0:1));
            }
        }
        return distance[str1.length][str2.length];

    }

    private Map<String, String> getNodeByLocator(Map<String, Map<String, String>> oldVersionUiObjects, Action locator){

        String problematicActionLocator = locator.getTarget().getSelector().toString();

        for(String key: oldVersionUiObjects.keySet()){
            if(key.equals(problematicActionLocator)){
                return oldVersionUiObjects.get(key);
            }
        }

        return null;

    }

    private List<String> getNodesByProperty(String prop, String propValue, Map<String, Map<String, String>> newVersionUiObjects){

        List<String> result = new ArrayList<>();

        for(String key: newVersionUiObjects.keySet()){

            Map<String, String> objectProps = newVersionUiObjects.get(key);

            if(objectProps.get(prop)!=null){
                if(Objects.equals(objectProps.get(prop), propValue)) {
                    result.add(key);
                }
            }

        }

        return result;
    }

    private TestCase replaceLocator(UiDevice device, TestCase buggyTestCase, int breakingPoint, String match){

        TestCase result = new TestCase(buggyTestCase);

        List<Action> actions = result.getTestActions();

        UiObject newTarget = device.findObject(resolveUiObjectSelector(match));

        actions.get(breakingPoint).setTarget(newTarget);

        result.setTestActions(actions);

        return result;

    }

    private UiSelector resolveUiObjectSelector(String match){

        String matchValue = match.split("=")[1].trim();
        String locator = matchValue.substring(0,matchValue.indexOf("]")).trim();

        if(match.contains("RESOURCE_ID")){
            return new UiSelector().resourceId(locator);
        }else if (match.contains("TEXT")){
            return new UiSelector().text(locator);
        }else if (match.contains("DESCRIPTION")){
            return new UiSelector().description(locator);
        }else if (match.contains("CLASS")){
            int index = Integer.parseInt(
                    matchValue.substring(matchValue.indexOf("(")+1, matchValue.indexOf(")"))
            );
            String className = matchValue.substring(0, matchValue.indexOf("("));

            return new UiSelector().className(className).index(index);
        }

        return null;
    }

    private TestCase checkRepair(UiDevice device, TestCase repairedTestCaseSuggestion, int breakingPoint){

        Log.i("ISA", "Trying suggestion...");

        try{
            repairedTestCaseSuggestion.executeBefore();
            List<String> initialState = labelsDetection();
            List<Map<String, Map<String, String>>> suggestionData = getTestWATERData(device, repairedTestCaseSuggestion, null);
            List<String> finalState = labelsDetection();
            repairedTestCaseSuggestion.setInitialState(initialState);
            repairedTestCaseSuggestion.setFinalState(finalState);
            boolean predicatePassed = repairedTestCaseSuggestion.evaluate();
            repairedTestCaseSuggestion.executeAfter();

            if(suggestionData.get(suggestionData.size()-1).containsKey("exception")){

                if(Integer.parseInt(Objects.requireNonNull(suggestionData.get(suggestionData.size() - 1).get("exception").get("errorActionIndex"))) == breakingPoint){
                    Log.i("ISA", "The suggestion does not work");
                    return null;
                }else{

                    Log.i("ISA", "More reparations needed. Running...");

                    WATERReparation newReparation = new WATERReparation(checkpoints, buggyTestCheckpointId);

                    List<TestCase> newSuggestions = newReparation.repair(device, repairedTestCaseSuggestion);

                    if(newSuggestions.size() != 0){
                        return newSuggestions.get(ThreadLocalRandom.current().nextInt(0, newSuggestions.size()-1));
                    }else{
                        return null;
                    }
                }
            }else if(!predicatePassed){

                return null;

            }else{

                Log.i("ISA", "Suggestion correct, repair saved!");

                return repairedTestCaseSuggestion;
            }

        }catch (UiObjectNotFoundException e){

            Log.e("ISA", "The suggestion failed executing methods repairedTestCaseSuggestion.executeBefore() and repairedTestCaseSuggestion.executeAfter()");

            return null;
        }
    }

    private static String getXPath(UiObject2 element){

        if(element.getParent() == null){
            return "//"+element.getClassName();
        }else{
            return getXPath(element.getParent()) + "/" + element.getClassName();
        }
    }

    private TestCase removeActionFromTestCase(TestCase buggyTestCase, int breakingPoint){

        TestCase result = new TestCase(buggyTestCase);

        List<Action> actions = result.getTestActions();

        actions.remove(breakingPoint);

        result.setTestActions(actions);

        return result;

    }
}
