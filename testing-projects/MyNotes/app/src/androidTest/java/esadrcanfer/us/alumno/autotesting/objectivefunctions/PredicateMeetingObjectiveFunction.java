package esadrcanfer.us.alumno.autotesting.objectivefunctions;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.BrokenTestCaseException;
import esadrcanfer.us.alumno.autotesting.TestCase;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

public class PredicateMeetingObjectiveFunction implements ObjectiveFunction {

    @Override
    public Double evaluate(TestCase testcase, String appPackage) {
        Double result=null;
        try {
            testcase.executeBefore();
            testcase.setInitialState(labelsDetection());
            testcase.executeTest();
            testcase.setFinalState(labelsDetection());
            result = new Double(testcase.getPredicate().nClausesMeet(testcase));
        }catch(BrokenTestCaseException ex){
            try {
                testcase.setFinalState(labelsDetection());
                result=brokenTestHandling(ex);
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        } catch (UiObjectNotFoundException e) {
        }finally {
            try {
                testcase.executeAfter();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public List<String> labelsDetection() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        List<String> finalState = new ArrayList<>();
        List<UiObject2> elements = device.findObjects(By.clazz(TextView.class));
        for (UiObject2 label : elements) {
            try {
                String text = label.getText();
                //Solución básica, hay que mejorarla
                if (!(text.contains(":") || text.contains("%"))) {
                    finalState.add(text);
                }
            }catch(Exception e){
                System.out.println(e.getLocalizedMessage());
            }
        }
        return finalState;
    }

    protected Double brokenTestHandling(BrokenTestCaseException ex){
        return null;
    }
}
