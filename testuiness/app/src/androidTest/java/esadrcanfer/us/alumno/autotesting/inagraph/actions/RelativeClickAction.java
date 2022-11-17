package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import android.util.Log;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;

public class RelativeClickAction extends Action{

    UiDevice device;
    double x;
    double y;
    RelativeClickType type;

    public RelativeClickAction(UiDevice device, double x, double y, RelativeClickType type){
        super(null, ActionType.RELATIVE_CLICK);
        this.device = device;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void perform() throws UiObjectNotFoundException {

        if (type == RelativeClickType.ABSOLUTE){
            device.click((int) x, (int) y);
        }else{
            int width = device.getDisplayWidth();
            int height = device.getDisplayHeight();
            int resX = (int)(device.getDisplayWidth()*(x/100));
            int resY = (int)(device.getDisplayHeight()*(y/100));
            device.click((int)(device.getDisplayWidth()*(x/100)), (int)(device.getDisplayHeight()*(y/100)));
        }

        try{
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            Log.d("ISA", "Interrumpted exception: " + e.getMessage());
        }
    }

    public enum RelativeClickType{
        ABSOLUTE, RELATIVE
    }

    @Override
    public String toString(){
        return actionType+", UiObject[x=" + this.x + ", y=" + this.y + "], ";
    }

}