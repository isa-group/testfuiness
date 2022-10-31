package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;

public class CountDownAction extends Action {

    public CountDownAction(UiObject target) {
        super(target, ActionType.COUNT_DOWN);
    }

    @Override
    public void perform() throws UiObjectNotFoundException, InterruptedException {
        this.target.waitUntilGone(Integer.parseInt(value));
        Thread.sleep(timeout);
    }

}