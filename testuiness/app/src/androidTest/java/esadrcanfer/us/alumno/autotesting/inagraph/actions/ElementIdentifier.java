package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

public class ElementIdentifier {

    private static final List<String> UI_SELECTORS_LIST = Arrays.asList("android.widget.Button",
                                                                        "android.widget.EditText",
                                                                        "android.widget.RadioGroup",
                                                                        "android.widget.CheckBox",
                                                                        "android.widget.Spinner",
                                                                        "android.widget.DatePicker",
                                                                        "android.widget.TextView",
                                                                        "android.widget.View",
                                                                        "android.widget.ImageButton");

    public static List<UiObject> findElements(UiDevice device, String finder) {
        List<UiObject> result = new ArrayList<>();
        BySelector sel = resolveSelector(finder);
        List<UiObject2> elements = device.findObjects(sel);
        UiSelector selector = null;
        UiObject button = null;
        String elementId = null;
        String elementText = null;
        for (UiObject2 btn : elements) {

            elementId = btn.getResourceName();
            elementText = btn.getText();

            if(elementId != null) {
                selector = new UiSelector().resourceId(elementId);
                button = device.findObject(selector);
            }else if(elementText != null){
                selector = new UiSelector().text(elementText);
                button = device.findObject(selector);
            }


            if(button != null && btn.isClickable()) {
                result.add(button);
            }Log.d("ISA", "The element with id '"+elementId+"' and text '"+elementText+"'i not clickable or does not exit.");

        }
        return result;
    }

    public static List<UiObject2> findAll(UiDevice device){
        List<UiObject2> result = new ArrayList<>();
        for(String finder: UI_SELECTORS_LIST){
            BySelector sel = resolveSelector(finder);
            List<UiObject2> elements = device.findObjects(sel);

            result.addAll(elements);
        }

        return result;
    }

    private static BySelector resolveSelector(String finder) {
        BySelector result = null;
        finder = finder.substring(finder.lastIndexOf(".") + 1);
        if (finder.equalsIgnoreCase("Button")) {
            result = By.clazz(Button.class);
        } else if (finder.equalsIgnoreCase("EditText")) {
            result = By.clazz(EditText.class);
        } else if (finder.equalsIgnoreCase("RadioGroup")) {
            result = By.clazz(RadioGroup.class);
        } else if (finder.equalsIgnoreCase("CheckBox")) {
            result = By.clazz(CheckBox.class);
        } else if (finder.equalsIgnoreCase("Spinner")) {
            result = By.clazz(Spinner.class);
        } else if (finder.equalsIgnoreCase("DatePicker")) {
            result = By.clazz(DatePicker.class);
        }else if (finder.equalsIgnoreCase("TextView")){
            result = By.clazz(TextView.class);
        }else if (finder.equalsIgnoreCase("View")){
            result = By.clazz(View.class);
        }else if (finder.equalsIgnoreCase("ImageButton")){
            result = By.clazz(ImageButton.class);
        }
        return result;
    }
}
