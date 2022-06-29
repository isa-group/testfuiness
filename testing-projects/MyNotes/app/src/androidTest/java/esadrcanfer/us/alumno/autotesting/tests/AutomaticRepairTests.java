package esadrcanfer.us.alumno.autotesting.tests;

import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.BrokenTestCaseException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.algorithms.GRASPReparation;
import esadrcanfer.us.alumno.autotesting.algorithms.RandomReparation;
import esadrcanfer.us.alumno.autotesting.algorithms.RecycleReparation;
import esadrcanfer.us.alumno.autotesting.inagraph.CloseAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.StartAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;
import esadrcanfer.us.alumno.autotesting.util.ReadUtil;
import esadrcanfer.us.alumno.autotesting.util.WriterUtil;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

public class AutomaticRepairTests {


    @Test
    public void testRandomReparation() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        ReadUtil readUtil = new ReadUtil("Download/Prueba/TestCase-20190227_183604.txt", false);
        TestCase testCase = readUtil.generateTestCase();
        Log.d("ISA", "Loadded test case from file!");
        Log.d("ISA", "Executing it...");
        try {
            testCase.executeBefore();
            List<String> initialState = labelsDetection();
            testCase.executeTest();
            List<String> finalState = labelsDetection();
            testCase.setInitialState(initialState);
            testCase.setFinalState(finalState);
            Boolean eval = testCase.evaluate();
            Log.d("ISA", "Initial evaluation: " + eval.toString());
            testCase.executeAfter();
        } catch (BrokenTestCaseException ex) {
            RandomReparation randomReparation = new RandomReparation(5, testCase, testCase.getAppPackage());
            testCase = randomReparation.run(device, testCase.getAppPackage());
        }
        Log.d("ISA", "TestCase found: " + testCase);
    }

    @Test
    public void testRecycleReparation() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        ReadUtil readUtil = new ReadUtil("TestCase-complex.txt", true);
        TestCase testCase = readUtil.generateTestCase();
        Log.d("ISA", "Loadded test case from file!");
        Log.d("ISA", "Executing it...");
        try {
            testCase.executeBefore();
            List<String> initialState = labelsDetection();
            testCase.executeTest();
            List<String> finalState = labelsDetection();
            testCase.setInitialState(initialState);
            testCase.setFinalState(finalState);
            Boolean eval = testCase.evaluate();
            Log.d("ISA", "Initial evaluation: " + eval.toString());
            testCase.executeAfter();
        } catch (BrokenTestCaseException ex) {
            testCase.executeAfter();
            RecycleReparation recycleReparation = new RecycleReparation(5, testCase, (int) ex.getBreakingIndex());
            testCase = recycleReparation.run(device);
        }
        Log.d("ISA", "TestCase found: " + testCase);
    }

    @Test
    public void TestMultiExecutionRandomReparation()  throws UiObjectNotFoundException {
        RandomReparation algorithm=null;
        String test="Broken2-DeletionTestCase";
        String path="Download/BrokenTest/"+test+".txt";
        int nRuns=10;
        boolean fixed;
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        ReadUtil readUtil = new ReadUtil(path, false);
        TestCase testCase = readUtil.generateTestCase();
        try {
            testCase.executeBefore();
            List<String> initialState = labelsDetection();
            testCase.executeTest();
            List<String> finalState = labelsDetection();
            testCase.setInitialState(initialState);
            testCase.setFinalState(finalState);
            Boolean eval = testCase.evaluate();
            testCase.executeAfter();
            Log.d("ISA", "The test case is OK!");
            Log.d("ISA", "It evaluates to: "+eval);
            Log.d("ISA","initialState: "+testCase.getInitialState());
            Log.d("ISA","finalState: "+testCase.getFinalState());

        } catch (BrokenTestCaseException ex) {
            testCase.executeAfter();
            Log.d("ISA", "The test case is broken at step " + ex.getBreakingIndex() + "with message '" + ex.getMessage() + "'");
            Log.d("ISA", "Repairing it...");
            TestCase solution;
            WriterUtil writerUtil = new WriterUtil("RandomScratchExecution-"+ test + "-GRASP.csv");
            writerUtil.write("Algorithm;Execution;Test;NClausesMeet;PredicateClauses;Fixed;ExecutionTime;ObjectiveFunctionEvaluations;Solution");
            for (int i = 0; i < nRuns; i++) {
                algorithm = new RandomReparation(50, testCase, testCase.getAppPackage());
                solution =  algorithm.repair(device, testCase, (int) ex.getBreakingIndex());
                fixed= algorithm.getCurrentOptimum()!=null &&  (solution.getPredicate().getNClauses()==algorithm.getCurrentOptimum().intValue());
                writerUtil.write("RandomFromScratch;"+i+";"+test+";"+algorithm.getCurrentOptimum()+";"+solution.getPredicate().getNClauses()+";"+fixed+";"+algorithm.getExecutionTime()+";"+algorithm.getObjectiveFunctionEvaluations()+";"+solution);
            }
        }
    }

    @Test
    public void TestMultiExecutionRecycleReparation()  throws UiObjectNotFoundException {
        RecycleReparation algorithm=null;
        String test="Broken-CancelCreationTestCase";
        String path="Download/BrokenTest/"+test+".txt";
        int nRuns=10;
        boolean fixed;
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        ReadUtil readUtil = new ReadUtil(path, false);
        TestCase testCase = readUtil.generateTestCase();
        try {
            testCase.executeBefore();
            List<String> initialState = labelsDetection();
            testCase.executeTest();
            List<String> finalState = labelsDetection();
            testCase.setInitialState(initialState);
            testCase.setFinalState(finalState);
            Boolean eval = testCase.evaluate();
            testCase.executeAfter();
            Log.d("ISA", "The test case is OK!");
            Log.d("ISA", "It evaluates to: "+eval);
            Log.d("ISA","initialState: "+testCase.getInitialState());
            Log.d("ISA","finalState: "+testCase.getFinalState());

        } catch (BrokenTestCaseException ex) {
            testCase.executeAfter();
            Log.d("ISA", "The test case is broken at step " + ex.getBreakingIndex() + "with message '" + ex.getMessage() + "'");
            Log.d("ISA", "Repairing it...");
            TestCase solution;
            WriterUtil writerUtil = new WriterUtil("RandomRecycleExecution-"+ test + "-GRASP.csv");
            writerUtil.write("Algorithm;Execution;Test;NClausesMeet;PredicateClauses;Fixed;ExecutionTime;ObjectiveFunctionEvaluations;Solution");
            for (int i = 0; i < nRuns; i++) {
                algorithm = new RecycleReparation(50, testCase, (int)ex.getBreakingIndex());
                solution = algorithm.repair(device, testCase, (int) ex.getBreakingIndex());
                fixed=algorithm.getCurrentOptimum()!=null && (solution.getPredicate().getNClauses()==algorithm.getCurrentOptimum().intValue());
                writerUtil.write("RandomRecycle;"+i+";"+test+";"+algorithm.getCurrentOptimum()+";"+solution.getPredicate().getNClauses()+";"+fixed+";"+algorithm.getExecutionTime()+";"+algorithm.getObjectiveFunctionEvaluations()+";"+solution);
            }
        }
    }


    @Test
    public void testMultiExecutionGRASP()  throws UiObjectNotFoundException {
        GRASPReparation grasp=null;
        String test="Broken-EditionTestCase";
        String path="Download/BrokenTest/"+test+".txt";
        int nRuns=10;
        boolean fixed;
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        ReadUtil readUtil = new ReadUtil(path, false);
        TestCase testCase = readUtil.generateTestCase();
        try {
            testCase.executeBefore();
            List<String> initialState = labelsDetection();
            testCase.executeTest();
            List<String> finalState = labelsDetection();
            testCase.setInitialState(initialState);
            testCase.setFinalState(finalState);
            Boolean eval = testCase.evaluate();
            testCase.executeAfter();
            Log.d("ISA", "The test case is OK!");
            Log.d("ISA", "It evaluates to: "+eval);
            Log.d("ISA","initialState: "+testCase.getInitialState());
            Log.d("ISA","finalState: "+testCase.getFinalState());

        } catch (BrokenTestCaseException ex) {
            testCase.executeAfter();
            Log.d("ISA", "The test case is broken at step " + ex.getBreakingIndex() + "with message '" + ex.getMessage() + "'");
            Log.d("ISA", "Repairing it...");
            TestCase solution;
            WriterUtil writerUtil = new WriterUtil("Execution-"+ test + "-GRASP.csv");
            writerUtil.write("Algorithm;Execution;Test;NClausesMeet;PredicateClauses;Fixed;ExecutionTime;ObjectiveFunctionEvaluations;Solution");
            for (int i = 0; i < nRuns; i++) {
                grasp = new GRASPReparation(10, 3, 5);
                solution = grasp.repair(device, testCase, (int) ex.getBreakingIndex());
                fixed=(solution.getPredicate().getNClauses()==grasp.getCurrentOptimum().intValue());
                writerUtil.write("GRASP;"+i+";"+test+";"+grasp.getCurrentOptimum()+";"+solution.getPredicate().getNClauses()+";"+fixed+";"+grasp.getExecutionTime()+";"+grasp.getObjectiveFunctionEvaluations()+";"+solution);
            }
        }
    }

    @Test
    public void testGRASPReparation() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        ReadUtil readUtil = new ReadUtil("Download/BrokenTest/Broken2-CreationTestCase.txt", false);
        TestCase testCase = readUtil.generateTestCase();
        Log.d("ISA", "Loadded test case from file!");
        Log.d("ISA", "Executing it...");
        try {
            testCase.executeBefore();
            List<String> initialState = labelsDetection();
            testCase.executeTest();
            List<String> finalState = labelsDetection();
            testCase.setInitialState(initialState);
            testCase.setFinalState(finalState);
            Boolean eval = testCase.evaluate();
            testCase.executeAfter();
        } catch (BrokenTestCaseException ex) {
            testCase.executeAfter();
            Log.d("ISA", "The test case is broken at step " + ex.getBreakingIndex() + "with message '" + ex.getMessage() + "'");
            Log.d("ISA", "Repairing it...");
            /*RandomReparationSearch randomReparationSearch = new RandomReparationSearch(5, testCase, testCase.getAppPackage());
            testCase = randomReparationSearch.run(device, testCase.getAppPackage());*/
            GRASPReparation grasp = new GRASPReparation(10, 3, 5);
            testCase = grasp.repair(device, testCase, (int) ex.getBreakingIndex());
            Log.d("ISA", "Repaired testCase found: " + testCase);
            Log.d("ISA", "Repaired testCase evaluated to: " + testCase.evaluate());
        }
    }

    @Test
    public void generateTestCases() throws UiObjectNotFoundException {
        UiDevice.getInstance(getInstrumentation());
        //createTestCase();
        //editTestCase();
        //deleteTestCase();
        complexTestCase();
    }

    public static TestCase createTestCase() throws UiObjectNotFoundException {
        Log.d("ISA", "Building Test case...");
        String appPackage = "esadrcanfer.us.mynotes";
        Random randomSeed = new Random();
        Long seed = randomSeed.nextLong();
        Random random = new Random(seed);
        List<Action> beforeActions = new ArrayList<>();
        List<Action> afterActions = new ArrayList<>();
        List<Action> testActions = new ArrayList<>();
        ;
        beforeActions.add(new StartAppAction(appPackage));
        afterActions.add(new CloseAppAction(appPackage));
        Action createNoteButton = ReadUtil.generateActionFromSimpleString("BUTTON, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/button]", random.nextLong());
        testActions.add(createNoteButton);
        Action addNoteText = ReadUtil.generateActionFromSimpleString("TEXT, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/editText1]", random.nextLong());
        testActions.add(addNoteText);
        Action saveNoteButton = ReadUtil.generateActionFromSimpleString("BUTTON, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/button]", random.nextLong());
        testActions.add(saveNoteButton);
        TestCase testCase = new TestCase(appPackage, Collections.EMPTY_SET, beforeActions, testActions, afterActions, new ArrayList<>(), new ArrayList<>());
        testCase.executeBefore();
        List<String> initialState = labelsDetection();
        testCase.executeTest();
        List<String> finalState = labelsDetection();
        testCase.executeAfter();
        testCase.setInitialState(initialState);
        testCase.setFinalState(finalState);
        testCase.setPredicate("finalState.contains(testActions[1].value)");
        WriterUtil writerUtil = new WriterUtil();
        writerUtil.write(testCase,seed);
        /*writerUtil.write(initialState.toString());
        writerUtil.write(finalState.toString());*/
        return testCase;
    }

    public static TestCase editTestCase() throws UiObjectNotFoundException {
        Log.d("ISA", "Building Test case...");
        String appPackage = "esadrcanfer.us.mynotes";
        Random randomSeed = new Random();
        Long seed = randomSeed.nextLong();
        Random random = new Random(seed);
        List<Action> beforeActions = new ArrayList<>();
        List<Action> afterActions = new ArrayList<>();
        List<Action> testActions = new ArrayList<>();
        ;
        beforeActions.add(new StartAppAction(appPackage));
        afterActions.add(new CloseAppAction(appPackage));
        Action createNoteButton = ReadUtil.generateActionFromSimpleString("BUTTON, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/editButton]", random.nextLong());
        testActions.add(createNoteButton);
        Action addNoteText = ReadUtil.generateActionFromSimpleString("TEXT, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/editText1]", random.nextLong());
        testActions.add(addNoteText);
        Action saveNoteButton = ReadUtil.generateActionFromSimpleString("BUTTON, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/button]", random.nextLong());
        testActions.add(saveNoteButton);
        TestCase testCase = new TestCase(appPackage, Collections.EMPTY_SET, beforeActions, testActions, afterActions, new ArrayList<>(), new ArrayList<>());
        testCase.executeBefore();
        List<String> initialState = labelsDetection();
        testCase.executeTest();
        List<String> finalState = labelsDetection();
        testCase.executeAfter();
        testCase.setFinalState(finalState);
        testCase.setPredicate("finalState.contains(testActions[1].value)");
        WriterUtil writerUtil = new WriterUtil();
        writerUtil.write(testCase,seed);
        /*writerUtil.write(initialState.toString());
        writerUtil.write(finalState.toString());*/
        return testCase;
    }

    public static TestCase deleteTestCase() throws UiObjectNotFoundException {
        Log.d("ISA", "Building Test case...");
        String appPackage = "esadrcanfer.us.mynotes";
        Random randomSeed = new Random();
        Long seed = randomSeed.nextLong();
        Random random = new Random(seed);
        List<Action> beforeActions = new ArrayList<>();
        List<Action> afterActions = new ArrayList<>();
        List<Action> testActions = new ArrayList<>();
        ;
        beforeActions.add(new StartAppAction(appPackage));
        afterActions.add(new CloseAppAction(appPackage));
        Action createNoteButton = ReadUtil.generateActionFromSimpleString("BUTTON, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/editButton]", random.nextLong());
        testActions.add(createNoteButton);
        Action saveNoteButton = ReadUtil.generateActionFromSimpleString("BUTTON, UiSelector[RESOURCE_ID=esadrcanfer.us.mynotes:id/button2]", random.nextLong());
        testActions.add(saveNoteButton);
        TestCase testCase = new TestCase(appPackage, Collections.EMPTY_SET, beforeActions, testActions, afterActions, new ArrayList<>(), new ArrayList<>());
        testCase.executeBefore();
        List<String> initialState = labelsDetection();
        testCase.executeTest();
        List<String> finalState = labelsDetection();
        testCase.executeAfter();
        testCase.setInitialState(initialState);
        testCase.setFinalState(finalState);
        testCase.setPredicate("finalState.size() < initialState.size()");
        WriterUtil writerUtil = new WriterUtil();
        writerUtil.write(testCase,seed);
        /*writerUtil.write(initialState.toString());
        writerUtil.write(finalState.toString());*/
        return testCase;
    }


    public static TestCase complexTestCase() throws UiObjectNotFoundException {
        Log.d("ISA", "Building Test case...");
        String appPackage = "esadrcanfer.us.complexapp";
        Random randomSeed = new Random();
        Long seed = randomSeed.nextLong();
        Random random = new Random(seed);
        List<Action> beforeActions = new ArrayList<>();
        List<Action> afterActions = new ArrayList<>();
        List<Action> testActions = new ArrayList<>();
        beforeActions.add(new StartAppAction(appPackage));
        afterActions.add(new CloseAppAction(appPackage));
        Action createNoteButton = ReadUtil.generateActionFromSimpleString("BUTTON, UiSelector[RESOURCE_ID=esadrcanfer.us.complexapp:id/button5]", random.nextLong());
        testActions.add(createNoteButton);
        Action addNoteText = ReadUtil.generateActionFromSimpleString("RADIO_BUTTON, UiSelector[RESOURCE_ID=esadrcanfer.us.complexapp:id/radio]", random.nextLong());
        testActions.add(addNoteText);
        TestCase testCase = new TestCase(appPackage, Collections.EMPTY_SET, beforeActions, testActions, afterActions, new ArrayList<>(), new ArrayList<>());
        testCase.executeBefore();
        List<String> initialState = labelsDetection();
        testCase.executeTest();
        List<String> finalState = labelsDetection();
        testCase.executeAfter();
        testCase.setInitialState(initialState);
        testCase.setFinalState(finalState);
        testCase.setPredicate("finalState.contains(testActions[1].value)");
        WriterUtil writerUtil = new WriterUtil();
        writerUtil.write(testCase,seed);
        /*writerUtil.write(initialState.toString());
        writerUtil.write(finalState.toString());*/
        return testCase;
    }

    public static List<String> labelsDetection() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        List<String> finalState = new ArrayList<>();
        List<UiObject2> elements = device.findObjects(By.clazz(TextView.class));
        elements.addAll(device.findObjects(By.clazz(CheckBox.class)));
        elements.addAll(device.findObjects(By.clazz(Button.class)));
        elements.addAll(device.findObjects(By.clazz(RadioButton.class)));
        elements.addAll(device.findObjects(By.clazz(ScrollView.class)));
        elements.addAll(device.findObjects(By.clazz(Spinner.class)));
        elements.addAll(device.findObjects(By.clazz(CheckedTextView.class)));
        elements.addAll(device.findObjects(By.clazz(Switch.class)));        for (UiObject2 label : elements) {
            String text = label.getText();
            //Solución básica, hay que mejorarla
            if (text!=null && (!(text.contains(":") || text.contains("%")))) {
                finalState.add(text);
            }
        }
        return finalState;
    }
}
