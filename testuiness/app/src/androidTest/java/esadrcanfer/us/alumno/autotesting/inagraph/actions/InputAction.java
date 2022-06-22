package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;


public abstract class InputAction extends Action {
    InputGenerator inputGenerator;
    public InputAction(UiObject target, InputGenerator generator, ActionType actionType){
        super(target, actionType);
        this.inputGenerator=generator;
    }

    public void perform() throws UiObjectNotFoundException {
        value = inputGenerator.generateInput(target);
    }

}