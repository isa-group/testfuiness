package esadrcanfer.us.alumno.autotesting.tests;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static esadrcanfer.us.alumno.autotesting.tests.AutomaticRepairTests.labelsDetection;

import android.os.Environment;
import android.support.test.filters.SdkSuppress;
import android.util.Log;
import android.util.Pair;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.algorithms.RandomReparation;
import esadrcanfer.us.alumno.autotesting.algorithms.WATERReparation;
import esadrcanfer.us.alumno.autotesting.util.CheckpointUtil;
import esadrcanfer.us.alumno.autotesting.util.ReadUtil;
import esadrcanfer.us.alumno.autotesting.util.WriterUtil;

@RunWith(Parameterized.class)
@SdkSuppress(minSdkVersion = 18)
public class OldTestWATERDataExtractor {

    private String path;

    @Parameterized.Parameters
    public static Collection<String> data() {

        List<String> tests = Arrays.asList(
                                            "Test Google Slides/Old/SendCopyGoogleSlides.txt");

        return tests;
    }

    public OldTestWATERDataExtractor(String testPath) {
        this.path = testPath;
    }

    @Test
    public void runTxtTests() {
        UiDevice device = UiDevice.getInstance(getInstrumentation());

        ReadUtil readUtil = new ReadUtil(path, true);
        TestCase testCase = readUtil.generateTestCase();

        Log.d("ISA", "Loaded test case from file!");
        Log.d("ISA", "Extracting data...");
        try{

            testCase.executeBefore();
            WATERReparation.getTestWATERData(device, testCase, path.substring(path.lastIndexOf("/")+1).split("\\.")[0]+"-old");
            testCase.executeAfter();

        } catch (Exception ex) {

            Log.e("ISA", "An error has occurred while extracting data from test: " + path);

        }

    }

}
