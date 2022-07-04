package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;

public class SwitchAction extends Action{
    public SwitchAction(UiObject aSwitch) {
        super(aSwitch, ActionType.SWITCH);
    }

    public void perform() throws UiObjectNotFoundException {
        value = target.getText();
        this.target.click();
    }
}
