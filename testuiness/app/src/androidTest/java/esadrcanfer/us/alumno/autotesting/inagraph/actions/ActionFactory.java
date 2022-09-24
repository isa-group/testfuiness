package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import androidx.test.uiautomator.StaleObjectException;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;

import esadrcanfer.us.alumno.autotesting.util.ReadUtil;

public class ActionFactory {

    public static Map<UiObject, Action> createInputActions(UiDevice device, Long seed) {
        String value = null;

        String[] inputGenerator = ReadUtil.readConfigFile()[0].split(":");
        String generatorType = inputGenerator[0].trim();
        String generatorParameters = inputGenerator[1].trim();

        TextInputGenerator generator = new TextInputGenerator(seed, value, generatorType, generatorParameters);
        List<UiObject> inputTexts = ElementIdentifier.findElements(device, "android.widget.EditText");
        Map<UiObject, Action> result = new HashMap<>();
        for (UiObject input : inputTexts) {
            result.put(input, new TextInputAction(input, generator));
        }
        return result;
    }

    public static Map<UiObject, Action> createButtonActions(UiDevice device) {
        Map<UiObject, Action> result = new HashMap<>();
        try{
            List<UiObject> buttons = ElementIdentifier.findElements(device, "android.widget.Button");

            try {
                buttons.addAll(ElementIdentifier.findElements(device, "android.widget.CompoundButton"));
            }catch(Exception e){
                Log.d("ISA", "No elements found with class 'android.widget.CompoundButton'");
            }
            for (UiObject input : buttons) {
                result.put(input, new ButtonAction(input));
            }
        }catch(StaleObjectException ex){
            ex.printStackTrace();
        }
        return result;
    }

    public static Map<UiObject, Action> createRadioActions(UiDevice device, Long seed) {
        RadioButtonInputGenerator generator = new RadioButtonInputGenerator(seed);
        Map<UiObject, Action> result = new HashMap<>();
        List<UiObject> buttons = ElementIdentifier.findElements(device, "android.widget.RadioGroup");
        for (UiObject input : buttons) {
            result.put(input, new RadioButtonAction(input, generator));
        }
        return result;
    }

    public static Map<UiObject, Action> createCheckBoxActions(UiDevice device) {
        Map<UiObject, Action> result = new HashMap<>();
        List<UiObject> buttons = ElementIdentifier.findElements(device, "android.widget.CheckBox");
        for (UiObject input : buttons) {
            result.put(input, new CheckBoxAction(input));
        }
        return result;

    }

    public static Map<UiObject, Action> createClickableActions(UiDevice device) {
        Map<UiObject, Action> result = new HashMap<>();
        try{

            List<UiObject> clickable = new ArrayList<>();

            try {
                clickable.addAll(ElementIdentifier.findElements(device, "android.widget.TextView"));
            }catch(Exception e){
                Log.d("ISA", "No elements found with class 'android.widget.View'");
            }

            try {
                clickable.addAll(ElementIdentifier.findElements(device, "android.widget.<<View>>"));
            }catch(Exception e){
                Log.d("ISA", "No elements found with class 'android.widget.View'");
            }

            for (UiObject input : clickable) {
                result.put(input, new ButtonAction(input));
            }
        }catch(StaleObjectException ex){
            ex.printStackTrace();
        }

        return result;
    }

    public static Map<UiObject, Action> createActions(UiDevice device, Long seed) {
        Map<UiObject, Action> actions = new HashMap<>();
        actions.putAll(ActionFactory.createButtonActions(device));
        actions.putAll(ActionFactory.createInputActions(device, seed));
        actions.putAll(ActionFactory.createCheckBoxActions(device));
        actions.putAll(ActionFactory.createRadioActions(device, seed));
        actions.putAll(ActionFactory.createClickableActions(device));
        return actions;
    }
}
