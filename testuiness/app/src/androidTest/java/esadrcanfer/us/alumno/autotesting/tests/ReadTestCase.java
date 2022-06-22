package esadrcanfer.us.alumno.autotesting.tests;

import android.os.health.UidHealthStats;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.util.ReadUtil;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class ReadTestCase {

    @Test
    public void read() throws UiObjectNotFoundException {
        UiDevice.getInstance(getInstrumentation());
        ReadUtil readUtil = new ReadUtil("Download/TestCase-20210112_135747.txt", true);
        TestCase testCase = readUtil.generateTestCase();
        Log.d("TFG","Test case found: "+testCase);
        Log.d("TFG","Runnig it...");
        testCase.executeBefore();
        testCase.executeTest();
        testCase.executeAfter();
        Log.d("TFG","Done!");
    }
}
