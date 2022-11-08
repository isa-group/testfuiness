package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import android.util.Log;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;

public class CountDownAction extends Action {

    public CountDownAction(UiObject target) {
        super(target, ActionType.COUNT_DOWN);
    }

    @Override
    public void perform() throws UiObjectNotFoundException {
        this.target.waitUntilGone(Integer.parseInt(value));
        try{
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            Log.d("ISA", "Interrumpted exception: " + e.getMessage());
        }
    }

}