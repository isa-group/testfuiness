package esadrcanfer.us.alumno.autotesting.objectivefunctions.graph;

import android.util.Log;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.ObjectiveFunction;

public class ApplicationCrashObjectiveFunction implements ObjectiveFunction {
    @Override
    public Double evaluate(TestCase test, String appPackage) {
        Double result=0.0;
        UiDevice device = UiDevice.getInstance();
        try {
            test.executeBefore();
            test.executeTest();
        }catch(Exception e){
            Log.d("TFG", "Se ha cerrado la aplicación");
            result=1.0;
        } finally {
            try {

                if(!appPackage.equals(device.getCurrentPackageName())){
                    result = 1.0;
                }
                test.executeAfter();
            } catch (UiObjectNotFoundException e1) {

            }
        }
        return result;
    }
}
