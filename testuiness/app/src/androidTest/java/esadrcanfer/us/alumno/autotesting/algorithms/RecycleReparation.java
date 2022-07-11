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
import esadrcanfer.us.alumno.autotesting.util.WriterUtil;

import static android.os.SystemClock.sleep;
import static esadrcanfer.us.alumno.autotesting.tests.AutomaticRepairTests.labelsDetection;

public class RecycleReparation extends BaseReparationAlgorithm{

    long maxIterations;
    List<Action> beforeActions;
    List<Action> afterActions;
    List<Action> testActions;
    TestCase bugTestCase;
    Random random;
    int breakingPoint;

    public RecycleReparation(long maxIterations, TestCase bugTestCase, int breakingPoint) {
        this.maxIterations = maxIterations;
        this.bugTestCase = bugTestCase;
        beforeActions = new ArrayList<>();
        beforeActions.add(new StartAppAction(bugTestCase.getAppPackage()));
        afterActions = new ArrayList<>();
        afterActions.add(new CloseAppAction(bugTestCase.getAppPackage()));
        testActions = new ArrayList<>();
        this.breakingPoint = breakingPoint;
    }

    public TestCase run(UiDevice device) throws UiObjectNotFoundException {

        startInstant=System.currentTimeMillis();
        objectiveFunctionEvaluations=0;
        currentOptimum=0.0;
        List<Action> testCaseActions;
        List<Action> availableActions;
        WriterUtil writerUtil = null;
        Action chosenAction;
        TestCase res = null;
        Boolean eval = false;
        List<Action> validActions = new ArrayList<>();
        for(int i=0;i<breakingPoint;i++)
            validActions.add(bugTestCase.getTestActions().get(i));
        int i = 0;

        while (i < maxIterations) {
            Log.d("ISA", "Running iteration " + (i + 1));
            Random chosenSeed = new Random();
            Long seed = chosenSeed.nextLong();
            Random seeds = new Random(seed);
            startApp(bugTestCase.getAppPackage());
            List<String> initialState = labelsDetection();
            testCaseActions = new ArrayList<>();
            try {

                for (Action a : testCaseActions) {
                    a.perform();
                    testCaseActions.add(a);
                }
            }catch(UiObjectNotFoundException ex){
                ex.printStackTrace();
            }
            try{

                availableActions = createAction(device, seeds.nextLong());
                while (testCaseActions.size() < bugTestCase.getTestActions().size()*2 && availableActions.size() > 0) {
                    chosenAction = availableActions.get(getRandom().nextInt(availableActions.size()));
                    testCaseActions.add(chosenAction);
                    Log.d("ISA", "Executing action: " + chosenAction);
                    chosenAction.perform();
                    String appName = UiDevice.getInstance().getCurrentPackageName();
                    if (!appName.equals(bugTestCase.getAppPackage())) {
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
            }catch(UiObjectNotFoundException ex){
                ex.printStackTrace();
            }
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
        return run(device);
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
