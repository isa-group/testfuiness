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
import java.util.Map;

import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.algorithms.RandomReparation;
import esadrcanfer.us.alumno.autotesting.algorithms.WATERReparation;
import esadrcanfer.us.alumno.autotesting.util.CheckpointUtil;
import esadrcanfer.us.alumno.autotesting.util.ObjectsMapParser;
import esadrcanfer.us.alumno.autotesting.util.ReadUtil;
import esadrcanfer.us.alumno.autotesting.util.WriterUtil;

@RunWith(Parameterized.class)
@SdkSuppress(minSdkVersion = 18)
public class TestAutoWithMetricsWATER {

    private String id;
    private String path;

    @Parameterized.Parameters
    public static Collection<Pair<String, String>> data(){

        List<String> testSuite = new ArrayList<>();
        String downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();

        List<String> tests = Arrays.asList("Test Clock/API 25/TestAlarm.txt",
                "Test Clock/API 25/TestOtherAlarm.txt",
                "Test Clock/API 25/TestStopWatch.txt",
                "Test Clock/API 25/TestTimer.txt");

        for(int i = 0; i< tests.size()*4; i++){
            testSuite.add(tests.get(i%4));
        }

        CheckpointUtil checkpoints = new CheckpointUtil(downloadsPath+"/repairedTests", testSuite, "WATER Algorithm");

        return checkpoints.getTestSuite();
    }

    public TestAutoWithMetricsWATER(Pair<String, String> testcase) {
        this.id = testcase.first;
        this.path = testcase.second;
    }

    @Test
    public void runTxtTests() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        String downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        CheckpointUtil checkpoints = new CheckpointUtil(downloadsPath+"/repairedTests");

        ReadUtil readUtil = new ReadUtil(path, true);
        TestCase testCase = readUtil.generateTestCase();
        List<TestCase> repairs = new ArrayList<>();

        Log.d("ISA", "Loaded test case from file!");
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
                WATERReparation waterReparation = new WATERReparation(checkpoints, id);
                long startTime = System.currentTimeMillis();
                repairs = waterReparation.repair(device, testCase);
                long endTime = System.currentTimeMillis();

                long reparationTime = endTime - startTime;

                String name = checkpoints.getFileName(id).split("\\.")[0].trim();

                if(repairs.size() == 0){
                    WriterUtil dataMetrics = new WriterUtil(downloadsPath+"/repairedTests", "dataMetrics.csv");
                    dataMetrics.write(name+";ReparationFailed");
                }else{
                    WriterUtil.saveInDeviceWATER(repairs, (long) -1, name, reparationTime, id, "WATER Algorithm");
                }
            }catch(Exception e){
                String[] pathSplitted = path.split("/");
                String name = pathSplitted[pathSplitted.length-1];

                WriterUtil dataMetrics = new WriterUtil(downloadsPath+"/repairedTests", "dataMetrics.csv");
                dataMetrics.write(name+";ReparationFailed");
            }

        }

    }

}