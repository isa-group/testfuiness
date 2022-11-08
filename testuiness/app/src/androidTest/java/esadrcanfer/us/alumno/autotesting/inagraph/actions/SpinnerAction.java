package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import android.util.Log;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;

public class SpinnerAction extends Action {

    public SpinnerAction(UiObject spinner) {
        super(spinner, ActionType.SPINNER);
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
