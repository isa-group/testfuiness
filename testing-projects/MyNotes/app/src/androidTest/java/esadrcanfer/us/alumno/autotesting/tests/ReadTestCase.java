package esadrcanfer.us.alumno.autotesting.tests;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.util.Log;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;

import java.util.List;

import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.util.ReadUtil;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static esadrcanfer.us.alumno.autotesting.tests.AutomaticRepairTests.labelsDetection;

@RunWith(AndroidJUnit4.class)
public class ReadTestCase {

    @Test
    public void read() throws UiObjectNotFoundException {
        UiDevice.getInstance(getInstrumentation());
        ReadUtil readUtil = new ReadUtil("Download/SinAssertion.txt", true);
        TestCase testCase = readUtil.generateTestCase();
        Log.d("TFG","Test case found: "+testCase);
        Log.d("TFG","Runnig it...");
        testCase.executeBefore();
        List<String> initialState = labelsDetection();
        testCase.executeTest();
        List<String> finalState = labelsDetection();
        testCase.setInitialState(initialState);
        testCase.setFinalState(finalState);
        Boolean eval = testCase.evaluate();
        Log.d("ISA", "Initial evaluation: " + eval.toString());
        testCase.executeAfter();
        if (eval.equals(false)) {
            Assert.fail("The assertion '" + testCase.getPredicate() + "' is not true");
        }
    }
}
