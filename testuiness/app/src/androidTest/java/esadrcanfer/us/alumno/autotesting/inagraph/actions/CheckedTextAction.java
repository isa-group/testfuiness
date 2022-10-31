package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import android.util.Log;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;

public class CheckedTextAction extends Action {

    public CheckedTextAction(UiObject checkedText) {
        super(checkedText, ActionType.CHECKED_TEXT);
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
