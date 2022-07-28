package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;

public class ScrollToAction extends Action{

    public ScrollToAction(UiObject target) {
        super(target, ActionType.SCROLL_TO);
    }

    @Override
    public void perform() throws UiObjectNotFoundException {

        UiScrollable scrollable =  new UiScrollable(target.getSelector());

        if (value.startsWith("Elements=")) {
            scrollable.scrollToEnd(Integer.valueOf(value.replace("Elements=","")));
        } else if (value.startsWith("toElementById=")) {
            scrollable.scrollIntoView(new UiSelector().resourceId(value.replace("toElementById=","")));
        } else if (value.startsWith("toElementByText=")) {
            scrollable.scrollIntoView(new UiSelector().text(value.replace("toElementByText=","")));
        } else if (value.startsWith("toElementByDescription=")) {
            scrollable.scrollIntoView(new UiSelector().description(value.replace("toElementByDescription=","")));
        } else
            scrollable.scrollToEnd(Integer.MAX_VALUE);
    }
}

