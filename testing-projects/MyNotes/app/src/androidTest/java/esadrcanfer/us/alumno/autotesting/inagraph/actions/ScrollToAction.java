package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import android.util.Log;
import android.widget.ScrollView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.Direction;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;

import java.util.List;

public class ScrollToAction extends Action{

    public ScrollToAction(UiObject target) {
        super(target, ActionType.SCROLL_TO);
    }

    @Override
    public void perform() {

        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        UiObject2 scrollable = device.findObject(By.clazz(ScrollView.class));

        String valueReplaced = value.replace("toElementById=","");
        String id = device.getCurrentPackageName()+":id/"+valueReplaced;

        while(!device.hasObject(By.res(id))){
            scrollable.scroll(Direction.DOWN, (float) 0.5);
        }
    }
}
