package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import android.util.Log;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;

public abstract class InputAction extends Action {
    InputGenerator inputGenerator;

    public InputAction(UiObject target, InputGenerator generator, ActionType actionType){
        super(target, actionType);
        this.inputGenerator=generator;
    }

    public void perform() throws UiObjectNotFoundException {
        value = inputGenerator.generateInput(target);
        try{
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            Log.d("ISA", "Interrumpted exception: " + e.getMessage());
        }
    }

}