package esadrcanfer.us.alumno.autotesting.experiments;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

import static esadrcanfer.us.alumno.autotesting.tests.AutomaticRepairTests.labelsDetection;

import android.os.Environment;
import android.util.Log;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import esadrcanfer.us.alumno.autotesting.BrokenTestCaseException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.algorithms.BaseReparationAlgorithm;
import esadrcanfer.us.alumno.autotesting.algorithms.GRASPReparation;
import esadrcanfer.us.alumno.autotesting.algorithms.RandomReparation;
import esadrcanfer.us.alumno.autotesting.algorithms.WATERReparation;
import esadrcanfer.us.alumno.autotesting.util.CheckpointUtil;
import esadrcanfer.us.alumno.autotesting.util.ReadUtil;
import esadrcanfer.us.alumno.autotesting.util.WriterUtil;

public abstract class Experiment {

    private String id;
    private String path;
    private String algorithm;
    private long timeout;

    public Experiment(String id, String path, String algorithm) {
        this.id = id;
        this.path = path;
        this.algorithm = algorithm;
    }

    protected static Collection<String> createTestData(List<String> tests, int numberOfExec, List<String> algorithms){

        List<String> testSuite = new ArrayList<>();
        String downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();

        for(int i = 0; i< tests.size()*numberOfExec; i++){
            testSuite.add(tests.get(i%tests.size()));
        }

        CheckpointUtil checkpoints = new CheckpointUtil(downloadsPath+"/reparation_experiment", testSuite, algorithms);

        return checkpoints.getTestSuite();
    }

    protected void runTest(long timeout) throws UiObjectNotFoundException {

        this.timeout = timeout;

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

            BaseReparationAlgorithm isaReparationAlgorithm = null;
            WATERReparation waterReparation;

            switch (algorithm){
                case "Random Algorithm":
                    isaReparationAlgorithm = new RandomReparation(500, testCase, testCase.getAppPackage());
                    break;

                case "GRASP Algorithm":
                    isaReparationAlgorithm = new GRASPReparation(10, 3, 5);
                    break;
            }

            Log.i("ISA", "The reparation algorithm used to repair this test is: " + algorithm);


            Future future;
            ExecutorService executor = Executors.newSingleThreadExecutor();

            if(isaReparationAlgorithm == null){
                waterReparation = new WATERReparation(checkpoints, id);
                Runnable waterTask = () -> {
                    try {
                        executeWATER(device, testCase, waterReparation, checkpoints);
                    } catch (UiObjectNotFoundException e) {
                        handleCatch();
                    }
                };
                future = executor.submit(waterTask);
            }else{
                BaseReparationAlgorithm finalIsaReparationAlgorithm = isaReparationAlgorithm;
                Runnable isaAlgorithmTask = () -> {
                    try {
                        executeIsaAlgorithm(device, testCase, ex.getBreakingIndex(), finalIsaReparationAlgorithm);
                    } catch (UiObjectNotFoundException e) {
                        handleCatch();
                    }
                };
                future = executor.submit(isaAlgorithmTask);
            }

            try {
                future.get(timeout, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                future.cancel(true);
                handleCatch();
            } catch (Exception e) {
                Log.e("ISA", "An exception has occurred, here is its message: " + e.getMessage());
            } finally {
                executor.shutdownNow();
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

    private void handleCatch(){
        String downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();

        String[] pathSplitted = path.split("/");
        String name = pathSplitted[pathSplitted.length-1];

        WriterUtil dataMetrics = new WriterUtil(downloadsPath+"/reparation_experiment", "dataMetrics.csv");
        dataMetrics.write(name+";"+algorithm+";ReparationFailed");
    }

}
