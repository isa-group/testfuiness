package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;

public class CheckedTextAction extends Action {

    public CheckedTextAction(UiObject checkedText) {
        super(checkedText, ActionType.CHECKED_TEXT);
    }

    public void perform() throws UiObjectNotFoundException, InterruptedException {
        value = target.getText();
        this.target.click();
        Thread.sleep(timeout);
    }
}
