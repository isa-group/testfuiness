package esadrcanfer.us.alumno.autotesting.inagraph;

import android.content.Context;
import android.content.Intent;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;

public class StartAppAction extends Action {

    String appPackage;

    public StartAppAction(String appPackage) {
        super(null, ActionType.START);
        this.appPackage=appPackage;
    }

    @Override
    public void perform() throws UiObjectNotFoundException {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(appPackage);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }
    }

    @Override
    public String toString() {
        return "Start App "+this.appPackage;
    }
}
