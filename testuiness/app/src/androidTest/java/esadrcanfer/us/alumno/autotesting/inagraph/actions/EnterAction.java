package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import android.util.Log;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;

public class EnterAction extends Action{

    UiDevice device;

    public EnterAction(UiDevice device){
        super(null, ActionType.ENTER);
        this.device = device;
    }

    @Override
    public void perform() throws UiObjectNotFoundException {
        device.pressEnter();
        try{
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            Log.d("ISA", "Interrumpted exception: " + e.getMessage());
        }
    }

    @Override
    public String toString(){
        return actionType+", UiObject[enterButton], ";
    }
}
