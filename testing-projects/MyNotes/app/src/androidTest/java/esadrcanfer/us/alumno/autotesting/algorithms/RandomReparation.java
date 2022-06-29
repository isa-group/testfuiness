package esadrcanfer.us.alumno.autotesting.algorithms;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.inagraph.CloseAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.StartAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.ActionFactory;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.dynamic.DynamicObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.util.WriterUtil;

import static android.os.SystemClock.sleep;
import static esadrcanfer.us.alumno.autotesting.tests.AutomaticRepairTests.labelsDetection;

public class RandomReparation extends BaseReparationAlgorithm{

    long maxIterations;
    List<Action> beforeActions;
    List<Action> afterActions;
    List<Action> testActions;
    TestCase bugTestCase;

    public RandomReparation(long maxIterations, TestCase bugTestCase, String appPackage) {
        this.maxIterations = maxIterations;
        this.bugTestCase = bugTestCase;
        beforeActions = new ArrayList<>();
        beforeActions.add(new StartAppAction(appPackage));
        afterActions = new ArrayList<>();
        afterActions.add(new CloseAppAction(appPackage));
        testActions = new ArrayList<>();
    }

    public TestCase run(UiDevice device, String appPackage) throws UiObjectNotFoundException {
        startInstant=System.currentTimeMillis();
        objectiveFunctionEvaluations=0;
        currentOptimum=0.0;
        int i = 0;
        List<Action> testCaseActions;
        List<Action> availableActions;
        WriterUtil writerUtil = null;
        Action chosenAction;
        TestCase res = null;
        Boolean eval = false;
        while (i < maxIterations) {
            Log.d("ISA", "Running iteration " + (i + 1));
            Random chosenSeed = new Random();
            Long seed = chosenSeed.nextLong();
            Random seeds = new Random(seed);
            startApp(appPackage);
            testCaseActions = new ArrayList<>();
            availableActions = createAction(device, seeds.nextLong());
            List<String> initialState = labelsDetection();
            while (testCaseActions.size() < bugTestCase.getTestActions().size()*2 && availableActions.size() > 0) {
                chosenAction = availableActions.get(getRandom().nextInt(availableActions.size()));
                testCaseActions.add(chosenAction);
                Log.d("ISA", "Executing action: " + chosenAction);
                chosenAction.perform();
                String appName = UiDevice.getInstance().getCurrentPackageName();
                if (!appName.equals(appPackage)) {
                    break;
                }
                if(testCaseActions.size()>=bugTestCase.getTestActions().size()){
                    List<String> finalState = labelsDetection();
                    res = new TestCase(bugTestCase.getAppPackage(), Collections.EMPTY_SET, beforeActions, testCaseActions, afterActions, initialState, finalState);
                    res.setPredicate(bugTestCase.getPredicate());
                    eval = res.evaluate();
                    objectiveFunctionEvaluations++;
                    if (eval == true) {
                        currentOptimum=(double)bugTestCase.getPredicate().getNClauses();
                        executionTime=System.currentTimeMillis()-startInstant;
                        return res;
                    }
                }
                availableActions = createAction(device, seeds.nextLong());
            }
            closeApp(bugTestCase.getAppPackage());
            i++;
        }
        //TODO Escribir el testCase en un fichero
        /*    writerUtil = new WriterUtil();
            writerUtil.write(appPackage);
            writerUtil.write(selectedSeed.toString());
            for (Action a : testActions) {
                writerUtil.write(a.toString());
            }
        */
        executionTime=System.currentTimeMillis()-startInstant;
        return res;
    }

    @Override
    public TestCase repair(UiDevice device, TestCase buggyTestCase, int breakingPoint) throws UiObjectNotFoundException {
        return run(device,buggyTestCase.getAppPackage());
    }


    /**public boolean isSameNode(UiDevice device, List<Action> availableActions, Random random) {
     boolean result = true;
     List<Action> actions = new ArrayList<>(ActionFactory.createActions(device, random).values());
     for (int i = 0; i < actions.size() && result; i++)
     result = (result && availableActions.contains(actions.get(i)));
     result = (result && availableActions.size() == (actions.size()));
     return result;
     }**/
}
