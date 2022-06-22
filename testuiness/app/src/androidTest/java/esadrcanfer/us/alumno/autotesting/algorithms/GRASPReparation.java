package esadrcanfer.us.alumno.autotesting.algorithms;

import android.util.Log;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.TestPredicate;
import esadrcanfer.us.alumno.autotesting.inagraph.CloseAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.StartAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.ObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.PredicateMeetingObjectiveFunction;
import esadrcanfer.us.alumno.autotesting.objectivefunctions.PredicateMeetingObjectiveFunctionWithReparison;
import esadrcanfer.us.alumno.autotesting.util.WriterUtil;

public class GRASPReparation extends BaseReparationAlgorithm {

    int maxIterations;
    int maxCreationIterations;
    int maxImprovementIterations;
    int objectiveFunctionEvaluations;
    Long executionTime;
    long startInstant;
    List<Action> beforeActions;
    List<Action> afterActions;
    List<Action> testActions;
    List<Action> previousValidTestActions;
    List<Action> actionsAfterBreakingPoint;
    TestCase bugTestCase;
    int breakingPoint;
    Random random;
    TestCase optimum;
    Double currentEvaluation;

    ObjectiveFunction objectiveFunction;

    public GRASPReparation(int maxIterations) {
        this(maxIterations,10,10);
    }
    public GRASPReparation(int maxIterations, int maxCreationIterations, int maxImprovementIterations) {
        this.maxIterations = maxIterations;
        this.maxCreationIterations=maxCreationIterations;
        this.maxImprovementIterations=maxImprovementIterations;
    }


    @Override
    public TestCase repair(UiDevice device, TestCase buggyTestCase, int breakingPoint) throws UiObjectNotFoundException {
        startInstant=System.currentTimeMillis();
        String appPackage=buggyTestCase.getAppPackage();
        beforeActions = new ArrayList<>();
        beforeActions.add(new StartAppAction(appPackage));
        afterActions = new ArrayList<>();
        afterActions.add(new CloseAppAction(appPackage));
        testActions = new ArrayList<>();
        objectiveFunctionEvaluations=0;

        previousValidTestActions=new ArrayList<>();
        for(int i=0;i<breakingPoint;i++)
            previousValidTestActions.add(buggyTestCase.getTestActions().get(i));

        actionsAfterBreakingPoint=new ArrayList<>();
        for(int i=breakingPoint+1;i<buggyTestCase.getTestActions().size();i++)
            actionsAfterBreakingPoint.add(buggyTestCase.getTestActions().get(i));

        if(random==null)
            random=new Random();
        if(objectiveFunction==null)
            objectiveFunction=new PredicateMeetingObjectiveFunctionWithReparison();
        this.breakingPoint=breakingPoint;
        this.bugTestCase=buggyTestCase;
        return run(device,appPackage);
    }

    public TestCase run(UiDevice device, String appPackage) throws UiObjectNotFoundException {
        WriterUtil writerUtil = null;
        TestCase res = null;
        optimum=bugTestCase;
        currentOptimum=null;
        for (int iterations=0;iterations< maxIterations;iterations++) {
            Log.d("ISA", "Running iteration " + (iterations + 1));
            res = creationPhase(device, appPackage);
            Log.d("ISA", "Evaluation at creation: " + currentEvaluation);
            if (currentEvaluation!=null && (currentOptimum==null || currentEvaluation>=currentOptimum)){
                optimum=res;
                currentOptimum=currentEvaluation;
            }
            if(optimum.getPredicate().getNClauses()==currentOptimum)
                break;
            res = improvementPhase(device,res);
            Log.d("ISA", "Eval at improvement: " + currentEvaluation);
            if (currentEvaluation!=null && (currentOptimum==null || currentEvaluation>=currentOptimum)){
                    optimum=res;
                    currentOptimum=currentEvaluation;
            }
            if(optimum.getPredicate().getNClauses()==currentOptimum)
                break;
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

    public TestCase creationPhase(UiDevice device, String appPackage) throws UiObjectNotFoundException {
        // Initialization
        TestCase result=null;
        startApp(appPackage);
        List<String> initialState = labelsDetection();
        testActions=new ArrayList<>();
        for(Action a:previousValidTestActions) {
            try {
                testActions.add(a);
                a.perform();
            }catch(UiObjectNotFoundException ex){
                testActions.remove(a);
            }
        }
        List<Action> availableActions;
        List<Action> RCL;
        Action chosenAction;
        // Main Loop:
        int creationIterations=0;
        while(testActions.size()<bugTestCase.getTestActions().size() && creationIterations<maxCreationIterations) {
            availableActions = identifyAvailableActions(device);
            RCL = chooseRestrictedCandidatesList(device,availableActions,testActions);
            if(!RCL.isEmpty()) {
                chosenAction = chooseRandomAction(RCL);
                testActions.add(chosenAction);
                chosenAction.perform();
            }
            creationIterations++;
        }
        List<String> finalState = labelsDetection();
        closeApp(appPackage);
        /*
        while (testCaseActions.size() < bugTestCase.getTestActions().size() && availableActions.size() > 0) {
            chosenAction = availableActions.get(getRandom().nextInt(availableActions.size()));
            testCaseActions.add(chosenAction);
            Log.d("ISA", "Executing action: " + chosenAction);
            chosenAction.perform();
            String appName = UiDevice.getInstance().getCurrentPackageName();
            if (!appName.equals(appPackage)) {
                break;
            }
            availableActions = createAction(device, seeds.nextInt());
        }*/
        // Reporting and results creation:
        result = new TestCase(appPackage, Collections.EMPTY_SET, beforeActions, testActions, afterActions, initialState, finalState);
        result.setPredicate(bugTestCase.getPredicate());
        currentEvaluation=Double.valueOf(result.getPredicate().nClausesMeet(result));
        objectiveFunctionEvaluations++;
        return result;
    }

    public List<Action> identifyAvailableActions(UiDevice device){
        return createAction(device, random.nextLong());
    }

    public List<Action> chooseRestrictedCandidatesList(UiDevice device,List<Action> availableActions,List<Action> testCaseActions) {
        List<Action> RCL=new ArrayList<>();
        if(availableActions.isEmpty()) {
            System.out.println("The set of available actions is empty");
            return RCL;
        }
        Action originalAction=null;
        if(bugTestCase.getTestActions().size()>testCaseActions.size())
            originalAction=bugTestCase.getTestActions().get( testCaseActions.size());
        for(Action candidate:availableActions){
            if((containsActionOnObject(actionsAfterBreakingPoint,candidate) ||
                    (originalAction!=null && originalAction.getClass().equals(candidate.getClass())))
                    && !containsActionOnObject(testCaseActions,candidate))
                RCL.add(candidate);
        }
        if(RCL.isEmpty())
            RCL.addAll(availableActions);
        return RCL;
    }

    public boolean containsActionOnObject(List<Action> actions, Action candidate){
        String target=candidate.getTarget().getSelector().toString();
        boolean result=false;
        if(target!=null) {
            for (Action action : actions) {
                if (target.equals(action.getTarget().getSelector().toString())) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }



    public TestCase improvementPhase(UiDevice device,TestCase inputTestCase) throws UiObjectNotFoundException {
        TestCase result=inputTestCase;
        TestCase previousCandidate=inputTestCase;
        List<TestCase> candidates;
        List<TestCase> feasibleCandidates=new ArrayList<>();
        Map<TestCase,Double> evaluations=new HashMap<>();
        Double localOptimum=currentEvaluation;
        Double candidateEvaluation;
        int index;
        for(int i=0;i<maxImprovementIterations;i++) {
            if(result.getTestActions().size()>breakingPoint)
                index = breakingPoint+random.nextInt(result.getTestActions().size()-breakingPoint);
            else
                index = random.nextInt(result.getTestActions().size());
            candidates = additionImprovementCandidates(device, index, result);
            if(index<result.getTestActions().size() && result.getTestActions().size()>1)
                candidates.add(removalImprovement(device, index, result));
            for (TestCase candidate : candidates) {
                candidateEvaluation = objectiveFunction.evaluate(candidate, candidate.getAppPackage());
                objectiveFunctionEvaluations++;
                if (candidateEvaluation != null && candidateEvaluation > localOptimum) {
                    result = candidate;
                    localOptimum = candidateEvaluation;
                    if(candidateEvaluation==candidate.getPredicate().getNClauses()) {
                        currentEvaluation=localOptimum;
                        return result;
                    }
                }
                if(candidateEvaluation != null) {
                    feasibleCandidates.add(candidate);
                    evaluations.put(candidate,candidateEvaluation);
                }
            }
            if(result!=previousCandidate) {
                currentEvaluation = localOptimum;
            }else{
                if(!feasibleCandidates.isEmpty()) {
                    result = feasibleCandidates.get(random.nextInt(feasibleCandidates.size()));
                    currentEvaluation = evaluations.get(result);
                    feasibleCandidates.clear();
                }
            }
            previousCandidate=result;
        }

        return result;
    }

    public List<TestCase> additionImprovementCandidates(UiDevice device, int index,TestCase inputTestCase) throws UiObjectNotFoundException {
        TestCase result=inputTestCase;
        Double localOptimum=objectiveFunction.evaluate(result,result.getAppPackage());
        Double candidateEvaluation;
        index=executeUntil(device,index,inputTestCase);
        List<Action> availableActions=identifyAvailableActions(device);
        closeApp(inputTestCase.getAppPackage());
        List<TestCase> feasibleAdditions=findFeasibleAdditons(device,index, inputTestCase,availableActions);
        return feasibleAdditions;
    }

    public int executeUntil(UiDevice device, int index,TestCase inputTestCase) throws UiObjectNotFoundException {
        inputTestCase.executeBefore();
        int i=0;
        try {
            for (i = 0; i < index; i++) {
                inputTestCase.getTestActions().get(i).perform();
            }
        }catch(UiObjectNotFoundException uiex){
            List<Action> actions=new ArrayList<>();
            for(int j=0;j<i;j++)
                actions.add(inputTestCase.getTestActions().get(j));
            inputTestCase.setTestActions(actions);
            return i;
        }
        return index;
    }

    public List<TestCase> findFeasibleAdditons(UiDevice device,int index,TestCase inputTestCase, List<Action> availableActions){
        List<TestCase> result=new ArrayList<>();
        TestPredicate adaptedPredicate=adaptPredicateAdditionAt(inputTestCase,index);
        TestCase testCase;
        for(Action action:availableActions){
            testCase=new TestCase(inputTestCase);
            testCase.getTestActions().add(index,action);
            testCase.setPredicate(adaptedPredicate);
            result.add(testCase);
        }
        return result;
    }

    public TestPredicate adaptPredicateAdditionAt(TestCase originaltest, int index){
        String [] clauses=originaltest.getPredicate().toString().split("&&");
        List<String> adaptedClauses=new ArrayList<>(clauses.length);
        for(String clause:clauses){
            adaptedClauses.add(adaptClauseAdditionAt(clause,index,originaltest.getTestActions().size()));
        }
        return new TestPredicate(adaptedClauses);
    }

    public String adaptClauseAdditionAt(String clause, int index,int totalActions){
        String result=clause;
        for(int i=totalActions-1;i>=index;i--){
            result=result.replace("testActions["+i+"]","testActions["+(i+1)+"]");
        }
        return result;
    }

    public TestPredicate adaptPredicateRemovalAt(TestCase originaltest, int index){
        String [] clauses=originaltest.getPredicate().toString().split("&&");
        List<String> adaptedClauses=new ArrayList<>(clauses.length);
        for(String clause:clauses){
            adaptedClauses.add(adaptClauseRemovalAt(clause,index,originaltest.getTestActions().size()));
        }
        return new TestPredicate(adaptedClauses);
    }
    public String adaptClauseRemovalAt(String clause, int index,int totalActions){
        String result=clause;
        for(int i=Math.max(index,1);i<totalActions;i++){
            result=result.replace("testActions["+i+"]","testActions["+(i-1)+"]");
        }
        return result;
    }

    public TestCase removalImprovement(UiDevice device,int index,TestCase inputTestCase) throws UiObjectNotFoundException {
        TestCase newTestCase=new TestCase(inputTestCase);
        newTestCase.getTestActions().remove(index);
        newTestCase.setPredicate(adaptPredicateRemovalAt(inputTestCase,index));
        return  newTestCase;
    }



    public Action chooseRandomAction(List<Action> actions){

       return actions.get(random.nextInt(actions.size()));
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public Double getCurrentOptimum() {
        return currentOptimum;
    }

    public int getObjectiveFunctionEvaluations() {
        return objectiveFunctionEvaluations;
    }
}

