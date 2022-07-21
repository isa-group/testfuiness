package esadrcanfer.us.alumno.autotesting.generators;

import androidx.test.uiautomator.UiObjectNotFoundException;

public class GivenValueGenerator extends AbstractGenerator{

    private String defaultValue;

    public GivenValueGenerator(String defaultValue){
        this.setDefaultValue(defaultValue);
    }

    public String generate() throws UiObjectNotFoundException {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

}
