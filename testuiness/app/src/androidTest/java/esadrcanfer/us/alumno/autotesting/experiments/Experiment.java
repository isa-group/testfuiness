package esadrcanfer.us.alumno.autotesting.experiments;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

import static esadrcanfer.us.alumno.autotesting.tests.AutomaticRepairTests.labelsDetection;

import android.os.Environment;
import android.util.Log;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;

import org.junit.Assert;
import org.junit.runners.Parameterized;

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


/**
 * <p>
 * This class is the base for all the experiments. It contains the methods needed
 * to configure and run a experiment.
 *<p>
 * Its constructor receive inside each test the test ID, its path and the algorithm to use.
 */
public abstract class Experiment {

    private String id;
    private String path;
    private String algorithm;
    private long timeout; // In seconds

    public Experiment(String id, String path, String algorithm) {
        this.id = id;
        this.path = path;
        this.algorithm = algorithm;
    }

    /**
     * <p>
     *     This static method is used to create the test data for the experiments in the {@link Parameterized.Parameters} section of a JUnit4 test class.
     * @param tests List of tests' path to execute
     * @param numberOfExec Number of executions for each test
     * @param algorithms List of algorithms to use
     * @return A prepared test suite that will be received by the constructor.
     */
    protected static Collection<String> createTestData(List<String> tests, int numberOfExec, List<String> algorithms){

        List<String> testSuite = new ArrayList<>();
        String downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();

        for(int i = 0; i< tests.size()*numberOfExec; i++){
            testSuite.add(tests.get(i%tests.size()));
        }

        CheckpointUtil checkpoints = new CheckpointUtil(downloadsPath+"/reparation_experiment", testSuite, algorithms);

        return checkpoints.getTestSuite();
    }

    /**
     * This method runs the experiment with the algorithm configured.
     * @param timeout Timeout for the test execution (in seconds)
     * @throws UiObjectNotFoundException
     */
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

            String[] pathSplitted = path.split("/");
            String name = pathSplitted[pathSplitted.length-1].split("\\.")[0];

            WriterUtil dataMetrics = new WriterUtil(downloadsPath+"/reparation_experiment", "dataMetrics.csv");
            dataMetrics.write(name+";"+algorithm+";No Reparation Needed");

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

    /* -------------------------------- PRIVATE METHODS SECTION -------------------------------- */

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
            dataMetrics.write(name+";"+algorithm+";Reparation Failed");
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
            dataMetrics.write(name+";"+algorithm+";Reparation Failed");
        }else{
            WriterUtil.saveInDeviceWATER(repairs, (long) -1, name, reparationTime, id, algorithm);
        }
    }

    private void handleCatch(){
        String downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();

        String[] pathSplitted = path.split("/");
        String name = pathSplitted[pathSplitted.length-1];

        WriterUtil dataMetrics = new WriterUtil(downloadsPath+"/reparation_experiment", "dataMetrics.csv");
        dataMetrics.write(name+";"+algorithm+";Reparation Failed");
    }

}
