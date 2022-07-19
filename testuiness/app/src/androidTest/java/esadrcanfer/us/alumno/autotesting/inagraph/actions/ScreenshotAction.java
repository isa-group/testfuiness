package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import static esadrcanfer.us.alumno.autotesting.tests.AutomaticRepairTests.labelsDetection;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;

import java.io.File;
import java.util.List;

import esadrcanfer.us.alumno.autotesting.tests.AssertionChecker;
import esadrcanfer.us.alumno.autotesting.util.WriterUtil;

public class ScreenshotAction extends Action{
    UiDevice device;


    public ScreenshotAction(UiDevice device){
        super(null, Action.ActionType.SCREENSHOT);
        this.device=device;
    }

    @Override
    public void perform() throws UiObjectNotFoundException {
        List<String> currentState = labelsDetection();
        AssertionChecker.setScreenshotLabels(currentState);
        File dir = new File("storage/emulated/0/Download/Screenshots");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        WriterUtil writerUtil = new WriterUtil("Screenshot", "storage/emulated/0/Download/Screenshots");
        File screenShot = writerUtil.getLogFile();
        device.takeScreenshot(screenShot);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScreenshotAction)) return false;
        if (!super.equals(o)) return false;

        ScreenshotAction that = (ScreenshotAction) o;

        return device != null ? device.equals(that.device) : that.device == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (device != null ? device.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Take Screenshot ";
    }
}
