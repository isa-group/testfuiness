package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import android.widget.RadioButton;

import java.util.Random;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

public class RadioButtonInputGenerator extends InputGenerator {

    Long seed;
    public RadioButtonInputGenerator(Long seed){
        this.seed = seed;
    }

    public String generateInput(UiObject target) throws UiObjectNotFoundException {
        String value = null;
        if(target.getChildCount() != 0){
            Integer selectedRadioButton = new Random(seed).nextInt(target.getChildCount());
            UiObject dataValue = target.getChild(new UiSelector().className(RadioButton.class.getName()).index(selectedRadioButton));
            value = dataValue.getText();
            dataValue.click();
        }else{
            value = target.getText();
            target.click();
        }
        return value;
    }
}
