package esadrcanfer.us.alumno.autotesting.tests;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

import static esadrcanfer.us.alumno.autotesting.tests.AutomaticRepairTests.labelsDetection;

import android.os.Environment;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import esadrcanfer.us.alumno.autotesting.BrokenTestCaseException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.algorithms.RandomReparation;
import esadrcanfer.us.alumno.autotesting.util.ReadUtil;
import esadrcanfer.us.alumno.autotesting.util.WriterUtil;

@RunWith(Parameterized.class)
@SdkSuppress(minSdkVersion = 18)
public class TestsAutoWithMetricsRandom {

    private String path;
    private static Long reparationTime = (long) 0;

    @Parameterized.Parameters
    public static Collection<String> data(){

        List<String> tests = Arrays.asList("Test Clock/API 25/TestAlarm.txt",
                "Test Clock/API 25/TestOtherAlarm.txt",
                "Test Clock/API 25/TestStopWatch.txt",
                "Test Clock/API 25/TestTimer.txt");

        List<String> res = new ArrayList<>();

        for(int i = 0; i< tests.size(); i++){
            res.add(tests.get(i%4));
        }


        return res;
    }

    public TestsAutoWithMetricsRandom(String path) {
        this.path = path;
    }

    @Test
    public void runTxtTests() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        ReadUtil readUtil = new ReadUtil(path, true);
        TestCase testCase = readUtil.generateTestCase();

        Log.d("ISA", "Loadded test case from file!");
        Log.d("ISA", "Executing it...");
        try{
            testCase.executeBefore();
            List<String> initialState = labelsDetection();
            testCase.executeTest();
            List<String> finalState = labelsDetection();
            testCase.setInitialState(initialState);
            testCase.setFinalState(finalState);
            Boolean eval = testCase.evaluate();
            Log.d("ISA", "Initial evaluation: " + eval.toString());
            testCase.executeAfter();

            Assert.assertTrue(eval);

        } catch (Exception ex) {

            try {
                RandomReparation randomReparation = new RandomReparation(500, testCase, testCase.getAppPackage());
                long startTime = System.currentTimeMillis();
                testCase = randomReparation.run(device, testCase.getAppPackage());
                long endTime = System.currentTimeMillis();

                reparationTime += endTime - startTime;

                String[] pathSplitted = path.split("/");
                String name = pathSplitted[pathSplitted.length-1];

                if(testCase == null){
                    WriterUtil dataMetrics = new WriterUtil("/repairedTests", "dataMetrics.csv");
                    dataMetrics.write(name+";ReparationFailed");
                }else{
                    WriterUtil.saveInDevice(testCase, (long) -1, name, reparationTime);
                }
            }catch(Exception e){
                String[] pathSplitted = path.split("/");
                String name = pathSplitted[pathSplitted.length-1];

                WriterUtil dataMetrics = new WriterUtil("/repairedTests", "dataMetrics.csv");
                dataMetrics.write(name+";ReparationFailed");
            }

        }

        // Log.d("ISA", "TestCase found: " + testCase);
    }

    @AfterClass
    public static void writeMetrics(){

        WriterUtil writer = new WriterUtil("repairedTests", "testMetrics.txt");

        int seconds = (int) (reparationTime / 1000) % 60 ;
        int minutes = (int) ((reparationTime / (1000*60)) % 60);
        int hours   = (int) ((reparationTime / (1000*60*60)) % 24);

        writer.write("\n-------------- METRICS ------------------\n");
        writer.write(String.format("Reparation time: %d h %d min %d sec", hours, minutes, seconds));
    }
}