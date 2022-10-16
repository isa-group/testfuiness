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

import esadrcanfer.us.alumno.autotesting.BrokenTestCaseException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.algorithms.BaseReparationAlgorithm;
import esadrcanfer.us.alumno.autotesting.algorithms.GRASPReparation;
import esadrcanfer.us.alumno.autotesting.algorithms.RandomReparation;
import esadrcanfer.us.alumno.autotesting.algorithms.WATERReparation;
import esadrcanfer.us.alumno.autotesting.util.CheckpointUtil;
import esadrcanfer.us.alumno.autotesting.util.ObjectsMapParser;
import esadrcanfer.us.alumno.autotesting.util.ReadUtil;
import esadrcanfer.us.alumno.autotesting.util.WriterUtil;

@RunWith(Parameterized.class)
@SdkSuppress(minSdkVersion = 18)
public class RunExperiment {

    private static final List<String> TESTS = Arrays.asList("Test Google Maps/Old/TestJourneyGoogleMaps.txt",
                                                            "Test Google Maps/Old/TestSearchGoogleMaps.txt",
                                                            "Test Google Maps/Old/TestShareLocationGoogleMaps.txt");
    private static final int NUMBER_OF_EXEC = 4;
    private static final List<String> ALGORITHMS = Arrays.asList("WATER Algorithm",
                                                                "Random Algorithm");

    private String id;
    private String path;
    private String algorithm;

    @Parameterized.Parameters
    public static Collection<String> data(){

        List<String> testSuite = new ArrayList<>();
        String downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();

        for(int i = 0; i< TESTS.size()*NUMBER_OF_EXEC; i++){
            testSuite.add(TESTS.get(i%TESTS.size()));
        }

        CheckpointUtil checkpoints = new CheckpointUtil(downloadsPath+"/reparation_experiment", testSuite, ALGORITHMS);

        return checkpoints.getTestSuite();
    }

    public RunExperiment(String testcase) {

        String[] testCaseSplit = testcase.split(";");

        this.id = testCaseSplit[0];
        this.path = testCaseSplit[1];
        this.algorithm = testCaseSplit[2];
    }

    @Test
    public void runTxtTests() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        String downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        CheckpointUtil checkpoints = new CheckpointUtil(downloadsPath+"/reparation_experiment");

        ReadUtil readUtil = new ReadUtil(path, true);
        TestCase testCase = readUtil.generateTestCase();

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

        } catch (BrokenTestCaseException ex) {

            try {

                BaseReparationAlgorithm isaReparationAlgorithm = null;
                WATERReparation waterReparation = null;

                switch (algorithm){
                    case "Random Algorithm":
                        isaReparationAlgorithm = new RandomReparation(500, testCase, testCase.getAppPackage());
                        break;

                    case "GRASP Algorithm":
                        isaReparationAlgorithm = new GRASPReparation(10, 3, 5);
                        break;
                }

                Log.i("ISA", "The reparation algorithm used to repair this test is: " + algorithm);

                if(isaReparationAlgorithm == null){
                    waterReparation = new WATERReparation(checkpoints, id);
                    executeWATER(device, testCase, waterReparation, checkpoints);
                }else{
                    executeIsaAlgorithm(device, testCase, ex.getBreakingIndex(), isaReparationAlgorithm);
                }

            }catch(Exception e){
                String[] pathSplitted = path.split("/");
                String name = pathSplitted[pathSplitted.length-1];

                WriterUtil dataMetrics = new WriterUtil(downloadsPath+"/reparation_experiment", "dataMetrics.csv");
                dataMetrics.write(name+";"+algorithm+";ReparationFailed");
            }

        }

    }

    private void executeIsaAlgorithm(UiDevice device, TestCase testCase, long breakingPoint, BaseReparationAlgorithm reparationAlgorithm) throws UiObjectNotFoundException{

        String downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();

        long startTime = System.currentTimeMillis();
        testCase = reparationAlgorithm.repair(device, testCase, (int) breakingPoint);
        long endTime = System.currentTimeMillis();

        long reparationTime = endTime - startTime;

        String[] pathSplitted = path.split("/");
        String name = pathSplitted[pathSplitted.length-1].split("\\.")[0];

        if(testCase == null){
            WriterUtil dataMetrics = new WriterUtil(downloadsPath+"/reparation_experiment", "dataMetrics.csv");
            dataMetrics.write(name+";"+algorithm+";ReparationFailed");
        }else{
            WriterUtil.saveInDevice(testCase, (long) -1, name, reparationTime, id, algorithm);
        }
    }

    private void executeWATER(UiDevice device, TestCase testCase, WATERReparation waterReparation, CheckpointUtil checkpoints) throws UiObjectNotFoundException{

        String downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        List<TestCase> repairs;

        long startTime = System.currentTimeMillis();
        repairs = waterReparation.repair(device, testCase);
        long endTime = System.currentTimeMillis();

        long reparationTime = endTime - startTime;

        String name = checkpoints.getFileName(id).split("\\.")[0].trim();

        if(repairs.size() == 0){
            WriterUtil dataMetrics = new WriterUtil(downloadsPath+"/reparation_experiment", "dataMetrics.csv");
            dataMetrics.write(name+";"+algorithm+";ReparationFailed");
        }else{
            WriterUtil.saveInDeviceWATER(repairs, (long) -1, name, reparationTime, id, algorithm);
        }
    }

}
