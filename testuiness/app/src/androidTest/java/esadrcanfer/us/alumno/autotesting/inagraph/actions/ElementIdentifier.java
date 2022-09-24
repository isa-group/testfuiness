package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

public class ElementIdentifier {

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

            try{
                if(button != null && button.isClickable()) {
                    result.add(button);
                }
            }catch(UiObjectNotFoundException e){
                Log.d("ISA", "The element with id '"+elementId+"' and text '"+elementText+"'i not clickable or does not exit.");
            }
        }
        return result;
    }

    private static BySelector resolveSelector(String finder) {
        BySelector result = null;
        finder = finder.substring(finder.lastIndexOf(".") + 1);
        if (finder.equalsIgnoreCase("button")) {
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
        }
        return result;
    }
}
