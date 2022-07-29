package esadrcanfer.us.alumno.autotesting.util;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.CloseAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.EnterAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.GoBackAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.ScreenshotAction;
import esadrcanfer.us.alumno.autotesting.inagraph.StartAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.ButtonAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.CheckBoxAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.CheckedTextAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.CountDownAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.RadioButtonAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.RadioButtonInputGenerator;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.ScrollToAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.SpinnerAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.SwitchAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.TextInputAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.TextInputGenerator;

public class ReadUtil {
    String path;
    Boolean sameSeed;

    public ReadUtil(String path, Boolean sameSeed){
        this.path = path;
        this.sameSeed = sameSeed;
    }
    public ReadUtil(String path){
        this.path = path;
    }

    public String getPath(){
        return this.path;
    }

    /**
     * This method finds the file located by {@link ReadUtil#path} inside the
     * assets folder. It's possible to use ** to search in any folder/subfolder
     * the file.
     *
     * @return String with the content of the file.
     */
    public String readText(){

        Context appContext = getInstrumentation().getTargetContext();
        AssetManager assetManager = appContext.getAssets();

        StringBuilder text = new StringBuilder();
        BufferedReader reader = null;

        try {

            String filename = searchFile(getPath());

            if(filename.equals(""))
                throw new FileNotFoundException();

            reader = new BufferedReader(
                    new InputStreamReader(assetManager.open(filename)));

            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return text.toString();
    }

    /**
     * This method generates a TestCase object from a test written in text plain with the
     * particular syntax inside the location referenced by {@link ReadUtil#path}.
     *
     * @return TestCase generated from the text plain file located by {@link ReadUtil#path}.
     */
    public TestCase generateTestCase(){
        List<Action> testActions = new ArrayList<>();
        String text = readText();
        String action;
        String predicate = null;

        String[] lines = text.split("\n");
        String appPackage = lines[0];
        Long seed = sameSeed ? new Long(lines[1]) : Math.abs(new Random().nextLong());
        int actionsSize = new Integer(lines[2]);

        String generatorType = "";
        String firstGeneratorParameter = "";
        String secondGeneratorParameter = "";
        int textInputCounter = 0;
        ReadUtil ru = new ReadUtil("config.txt");
        String configFile = ru.readText();
        String[] configLines = configFile.split("\n");

        for(int i = 3; i < actionsSize + 2; i++){

            action = lines[i];

            if(action.startsWith("TEXT")) {

                String configLine = configLines[textInputCounter];
                textInputCounter++;
                String[] splitConfigLine = configLine.split("-");
                generatorType = splitConfigLine[0];

                String conditions = splitConfigLine[1];
                String[] splitConditions = conditions.split("/");
                firstGeneratorParameter = splitConditions[0];
                if(!conditions.endsWith("/")) secondGeneratorParameter = splitConditions[1];

            }

            if(action.startsWith("CUSTOM ASSERTION")){
                predicate = action;
            }else{
                testActions.add(generateActionFromString(action, seed, generatorType, firstGeneratorParameter, secondGeneratorParameter));
            }

        }

        if (lines.length != actionsSize + 3) predicate = lines[actionsSize + 3];

        return createTestCase(appPackage, testActions, predicate);
    }

    /**
     * This method parse a given action written with the text plain action's syntax
     * to an object of type {@link Action}.
     * @param action A string written with the text plain action's syntax.
     * @param seed Long value that indicates the seed to be applied to create the test.
     * @param generatorType Input generator type to be used in TEXT type actions.
     * @param firstGeneratorParameter First parameter for the given input generator.
     * @param secondGeneratorParameter Second parameter for the given input generator (if needed).
     * @return An {@link Action} that represents the one given in String format.
     */
    public Action generateActionFromString(String action, Long seed, String generatorType, String firstGeneratorParameter, String secondGeneratorParameter){

        Log.d("ISA", action);

        String[] splitAction = action.split(",");
        String type = splitAction[0];
        String selector = "";
        String selectorType = "";
        String selectorValue = "";
        String value = "";

        if(splitAction.length>1) {
            selector = splitAction[1].trim();
            value = splitAction.length == 2 ? "" : splitAction[2].trim();
        }

        if(!(selector.equals("UiSelector[backButton]") || selector.equals("UiSelector[enterButton]")
                 || selector.startsWith("UiSelector[onClass=]") || selector.equals(""))) {

                selectorType = selector.substring(selector.indexOf("[") + 1, selector.indexOf("=")).trim();
                selectorValue = selector.substring(selector.indexOf("=") + 1, selector.length() - 1);

        }

        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject object = objectSelector(device, type, selectorType, selectorValue);
        Action generatedAction = parseAction(type, device, object, seed, value, generatorType, firstGeneratorParameter, secondGeneratorParameter);

        Log.d("ISA", "Action: " + action);
        Log.d("ISA", "Value: " + value);

        try{
            generatedAction.setValue(value.trim());
        }catch(NullPointerException e){
            Log.e("ISA", "Variable 'value' is null!");
        }

        return generatedAction;
    }

    public static Action generateActionFromSimpleString(String action, Long seed){

        Log.d("ISA", action);

        UiDevice device = UiDevice.getInstance(getInstrumentation());
        String value = null;
        Action res = null;
        String[] splitAction = action.split(",");
        String type = splitAction[0];
        String resourceId = splitAction[1];
        resourceId = splitAction[1].substring(resourceId.indexOf("=") + 1 ,resourceId.length()-1);

        UiObject object = device.findObject(new UiSelector().resourceId(resourceId));

        switch (type){
            case "BUTTON":
                res = new ButtonAction(object);
                break;
            case "TEXT":
                TextInputGenerator textInputGenerator = new TextInputGenerator(seed, value, null, null, null);
                res = new TextInputAction(object, textInputGenerator);
                break;
            case "CHECKBOX":
                res = new CheckBoxAction(object);
                break;
            case "RADIO_BUTTON":
                RadioButtonInputGenerator radioButtonInputGenerator = new RadioButtonInputGenerator(seed);
                res = new RadioButtonAction(object, radioButtonInputGenerator);
                break;
            case "SCROLL_TO":
                res = new ScrollToAction(object);
                break;
            case "COUNT_DOWN":
                res = new CountDownAction(object);
                break;
            case "SPINNER":
                res = new SpinnerAction(object);
                break;
            case "CHECKED_TEXT":
                res = new CheckedTextAction(object);
                break;
            case "SWITCH":
                res = new SwitchAction(object);
        }
        return res;
    }

    /**
     *  ------------------- Auxiliar methods ------------------------
     **/

    private String searchFile(@NonNull String fileName){

        if(fileName.contains("**")){

            String [] splitted = fileName.split("\\*\\*");

            String rootPath = "";

            if(!splitted[0].equals(""))
                rootPath = splitted[0].substring(0, splitted[0].length()-1);


            fileName = splitted[1].substring(splitted[1].indexOf("/")+1);

            return recursiveSearch(rootPath, fileName);

        }else{
            return fileName;
        }

    }

    private String recursiveSearch(String rootPath, String fileName){

        Context appContext = getInstrumentation().getTargetContext();

        AssetManager assetManager = appContext.getAssets();

        String result = "";

        String[] assets;

        try {
            assets = assetManager.list(rootPath);
            if (assets.length == 0) {
                String[] splitted = rootPath.split("/");

                if(splitted[splitted.length-1].equals(fileName)){
                    return rootPath;
                }
            } else {
                for (int i = 0; i < assets.length; ++i) {

                    if(rootPath.equals("")){
                        result = recursiveSearch(assets[i], fileName);
                    }else{
                        result = recursiveSearch(rootPath + "/" + assets[i], fileName);
                    }

                    if(!result.equals("")){
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            Log.e("tag", "I/O Exception", ex);
        }

        return result;
    }

    private TestCase createTestCase(String appPackage, List<Action> testActions, String predicate){
        List<String> initialLabels = new ArrayList<>();
        List<String> finalLabels = new ArrayList<>();
        List<Action> beforeActions = new ArrayList<>();
        List<Action> afterActions = new ArrayList<>();

        beforeActions.add(new StartAppAction(appPackage));
        afterActions.add(new CloseAppAction(appPackage));

        TestCase testCase = new TestCase(appPackage, Collections.EMPTY_SET, beforeActions, testActions, afterActions, initialLabels, finalLabels);
        testCase.setPredicate(predicate);
        return testCase;
    }

    private UiObject objectSelector(UiDevice device, String actionType, String selectorType, String selectorValue){

        UiObject object = null;

        switch(selectorType){

            case "RESOURCE_ID":
                object = device.findObject(new UiSelector().resourceId(selectorValue));
                break;

            case "DESCRIPTION":
                object = device.findObject(new UiSelector().description(selectorValue));
                break;

            case "TEXT":
                object = device.findObject(new UiSelector().text(selectorValue));
                break;

            case "SCROLLABLE":
                object = device.findObject(new UiSelector().scrollable(!actionType.equals("SCROLL_TO")));
                break;

            case "POSITION":
                object = device.findObject(new UiSelector().className("android.widget.CheckedTextView").index(Integer.parseInt(selectorValue)));
                break;

            case "CLASS":
                int index = Integer.parseInt(
                        selectorValue.substring(selectorValue.indexOf("(")+1, selectorValue.indexOf(")"))
                );
                String className = selectorValue.substring(0, selectorValue.indexOf("("));

                object = device.findObject(new UiSelector().className(className).index(index));

                break;
        }

        return object;

    }

    private Action parseAction(String type, UiDevice device, UiObject object, Long seed, String value, String generatorType, String firstGeneratorParameter, String secondGeneratorParameter){

        Action generatedAction = null;

        switch (type) {
            case "BUTTON":
                generatedAction = new ButtonAction(object);
                break;
            case "TEXT":
                TextInputGenerator textInputGenerator = new TextInputGenerator(seed, value, generatorType, firstGeneratorParameter, secondGeneratorParameter);
                generatedAction = new TextInputAction(object, textInputGenerator);
                break;
            case "CHECKBOX":
                generatedAction = new CheckBoxAction(object);
                break;
            case "RADIO_BUTTON":
                RadioButtonInputGenerator radioButtonInputGenerator = new RadioButtonInputGenerator(seed);
                generatedAction = new RadioButtonAction(object, radioButtonInputGenerator);
                break;
            case "SCROLL_TO":
                generatedAction = new ScrollToAction(object);
                break;
            case "COUNT_DOWN":
                generatedAction = new CountDownAction(object);
                break;
            case "SPINNER":
                generatedAction = new SpinnerAction(object);
                break;
            case "CHECKED_TEXT":
                generatedAction = new CheckedTextAction(object);
                break;
            case "SWITCH":
                generatedAction = new SwitchAction(object);
                break;
            case "GO_BACK":
                generatedAction = new GoBackAction(device);
                break;
            case "SCREENSHOT":
                generatedAction = new ScreenshotAction(device);
                break;
            case "ENTER":
                generatedAction = new EnterAction(device);
                break;

        }

        return generatedAction;
    }
}
