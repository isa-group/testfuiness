package esadrcanfer.us.alumno.autotesting.util;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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

    public TestCase generateTestCase(){
        List<Action> beforeActions = new ArrayList<>();
        List<Action> afterActions = new ArrayList<>();
        List<Action> testActions = new ArrayList<>();
        String text = readText();
        String[] lines = text.split("\n");
        String appPackage = lines[0];
        Long seed;

        if(sameSeed) {
            seed = new Long(lines[1]);
        }else{
            seed = Math.abs(new Random().nextLong());
        }
        Integer actionsSize = new Integer(lines[2]);
        String action = "";
        String generatorType = "";
        String cond1 = "";
        String cond2 = "";
        Integer textInputCounter = 0;
        ReadUtil ru = new ReadUtil("config.txt");
        String configFile = ru.readText();
        String[] configLines = configFile.split("\n");
        String predicate = null; //Inicializo predicate a null porque no siempre tiene por qué haberlo
        for(int i = 3; i<= actionsSize + 2; i++){
            action = lines[i];
            if(action.startsWith("TEXT")) {
                String configLine = configLines[textInputCounter];
                textInputCounter++;
                String[] splitConfigLine = configLine.split("-");
                generatorType = splitConfigLine[0];

                String conditions = splitConfigLine[1];
                String[] splitConditions = conditions.split("/");
                cond1 = splitConditions[0];
                if(!conditions.endsWith("/")) {
                    cond2 = splitConditions[1];
                }
            }

            if(action.startsWith("CUSTOM ASSERTION")){
                predicate = action;
            }

            if (seed <  0) {
                if(!action.startsWith("CUSTOM ASSERTION"))
                testActions.add(generateActionFromString(action, seed, generatorType, cond1, cond2));
            }else {
                if(!action.startsWith("CUSTOM ASSERTION"))
                testActions.add(generateActionFromString(action, seed, generatorType, cond1, cond2)); //Antes, pasaba un número random que no sé por qué estaba ahí. Hay que pasarle el seed
            }
                if(i == actionsSize+2){
                break;
            }
        }
        long numberOfLines = Arrays.stream(lines).count(); //Cuento el número de líneas del archivo
        actionsSize = new Integer(lines[2]); //Vuelvo a setear actionsSize porque el método de arriba las cambia para acabar el for y las "arruina"
        if (numberOfLines != actionsSize+3) { //Esto cuenta las líneas y si no hay aserción, no habrá mas de actionsSize + 3
            predicate = lines[actionsSize + 3]; //Si hay aserción, se va a la línea donde debería estar. Cambié el +1 por un +3 porque tres líneas más arriba he cambiado el valor de actionsSize después del for
        }

        List<String> initialLabels = new ArrayList<>();
        /*
        String initialState = lines[actionsSize+2].replaceAll("\\[", "").replaceAll("\\]", "");
        String finalState = lines[actionsSize+3].replaceAll("\\[", "").replaceAll("\\]", "");
        for (String label: initialState.split(", ")) {
            initialLabels.add(label);
        }*/
        List<String> finalLabels = new ArrayList<>();
        /*for (String label: finalState.split(", ")) {
            finalLabels.add(label);
        }*/
        beforeActions.add(new StartAppAction(appPackage));
        afterActions.add(new CloseAppAction(appPackage));
        TestCase testCase = new TestCase(appPackage, Collections.EMPTY_SET,beforeActions,testActions,afterActions, initialLabels, finalLabels);
        testCase.setPredicate(predicate);
        return testCase;
    }

    public Action  generateActionFromString(String action, Long seed, String generatorType, String cond1, String cond2){
        String[] splitAction = action.split(","); // Dividir la cadena por comas
        String type = splitAction[0];// Seleccionar el tipo de objeto (botón, cuadro de texto, radio button, etc.)
        String resourceId = "";
        String value = "";
        if(splitAction.length>1) {
            resourceId = splitAction[1].trim(); // Selector del objeto sobre el que actuar
            value = splitAction.length == 2 ? "" : splitAction[2].trim();      // Valor a usar para realizar la acción
        }
        String selectorType = "";
        if(!(resourceId.equals("UiSelector[backButton]") || resourceId.equals("UiSelector[enterButton]"))) {
            if(!resourceId.startsWith("UiSelector[onClass=]")) {
                if (!resourceId.equals("")) {
                    selectorType = resourceId.substring(resourceId.indexOf("[") + 1, resourceId.indexOf("=")).trim();
                    resourceId = resourceId.substring(resourceId.indexOf("=") + 1, resourceId.length() - 1);
                }
            }
        }
        Action res = null;
        UiObject object = null;
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());;
        if(selectorType.equals("RESOURCE_ID"))
            object = new UiObject(new UiSelector().resourceId(resourceId));
        else if(selectorType.equals("DESCRIPTION"))
            object = new UiObject(new UiSelector().descriptionContains(resourceId));
        else if(selectorType.equals("TEXT"))
            object = new UiObject(new UiSelector().textContains(resourceId));
        else if (selectorType.equals("SCROLLABLE"))
            object = new UiObject(new UiSelector().scrollable(!type.equals("SCROLL_DOWN")));
        else if (selectorType.equals("POSITION"))
            object = new UiObject(new UiSelector().className("android.widget.CheckedTextView").index(Integer.parseInt(resourceId)));
        else if (selectorType.equals("CLASS")) {
            Integer index = Integer.parseInt(
                                    resourceId.substring(resourceId.indexOf("(")+1, resourceId.indexOf(")"))
                            );
            String className = resourceId.substring(0, resourceId.indexOf("("));
            object = new UiObject(new UiSelector().className(className).index(index));
        }
        switch (type) {
            case "BUTTON":
                res = new ButtonAction(object);
                break;
            case "TEXT":
                TextInputGenerator textInputGenerator = new TextInputGenerator(seed, value, generatorType, cond1, cond2);
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
                break;
            case "GO_BACK":
                res = new GoBackAction(device);
                break;
            case "SCREENSHOT":
                res = new ScreenshotAction(device);
                break;

            case "ENTER":
                res = new EnterAction(device);
                break;

        }
        Log.d("ISA", "Action: " + action);
        Log.d("ISA", "Value: " + value);
        res.setValue(value.trim());
        return res;
    }

    public static Action generateActionFromSimpleString(String action, Long seed){
        Log.d("ISA", action);
        String value = null;
        String[] splitAction = action.split(", ");
        String type = splitAction[0];
        String resourceId = splitAction[1];
        resourceId = splitAction[1].substring(resourceId.indexOf("=") + 1 ,resourceId.length()-1);
        Action res = null;
        UiObject object = new UiObject(new UiSelector().resourceId(resourceId));
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

    // ------------------- Auxiliar methods ------------------------

    private String searchFile(String fileName){

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

        String assets[] = null;

        try {
            assets = assetManager.list(rootPath);
            if (assets.length == 0) {
                String splitted[] = rootPath.split("/");

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
}
