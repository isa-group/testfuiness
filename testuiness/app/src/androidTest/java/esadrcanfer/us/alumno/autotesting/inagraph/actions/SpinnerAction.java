package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;

public class SpinnerAction extends Action {

    public SpinnerAction(UiObject spinner) {
        super(spinner, ActionType.SPINNER);
    }

    public void perform() throws UiObjectNotFoundException, InterruptedException {
        value = target.getText();
        this.target.click();
        Thread.sleep(timeout);
    }
}
