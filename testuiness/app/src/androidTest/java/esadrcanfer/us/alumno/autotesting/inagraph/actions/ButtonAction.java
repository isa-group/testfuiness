package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import android.util.Log;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;

public class ButtonAction extends Action {

    public ButtonAction(UiObject button) {
        super(button, ActionType.BUTTON);
    }

    public void perform() throws UiObjectNotFoundException {
        value = target.getText();
        this.target.click();
        try{
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            Log.d("ISA", "Interrumpted exception: " + e.getMessage());
        }
    }
}
