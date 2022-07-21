package esadrcanfer.us.alumno.autotesting;

import androidx.test.uiautomator.UiObjectNotFoundException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;


public class TestCase {
    String app;
    Set<String> executionContext;
    List<Action> beforeActions;
    List<Action> testActions;
    List<Action> afterActions;
    TestPredicate predicate;

    List<String> initialState;

    List<String> finalState;

    public TestCase(TestCase baseCase){
        this.app=baseCase.app;

        this.executionContext=new HashSet<>();
        this.executionContext.addAll(baseCase.executionContext);

        this.beforeActions=new ArrayList<>();
        this.beforeActions.addAll(baseCase.beforeActions);

        this.testActions=new ArrayList<>();
        this.testActions.addAll(baseCase.testActions);

        this.afterActions=new ArrayList<>();
        this.afterActions.addAll(baseCase.afterActions);

        this.initialState = new ArrayList<>();
        this.finalState=new ArrayList<>();

        this.predicate=baseCase.predicate;
    }

    public TestCase(String app, Set<String> exceutionContext, List<Action> beforeActions, List<Action> testActions, List<Action> afterActions, List<String> initialState, List<String> finalState) {
        this.app = app;
        this.executionContext = exceutionContext;
        this.beforeActions = beforeActions;
        this.testActions = testActions;
        this.afterActions = afterActions;
        this.initialState = initialState;
        this.finalState = finalState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestCase)) return false;

        TestCase testCase = (TestCase) o;

        if (!app.equals(testCase.app)) return false;
        if (!executionContext.equals(testCase.executionContext)) return false;
        if (!beforeActions.equals(testCase.beforeActions)) return false;
        if (!testActions.equals(testCase.testActions)) return false;
        return afterActions.equals(testCase.afterActions);
    }

    @Override
    public int hashCode() {
        int result = app.hashCode();
        result = 31 * result + executionContext.hashCode();
        result = 31 * result + beforeActions.hashCode();
        result = 31 * result + testActions.hashCode();
        result = 31 * result + afterActions.hashCode();
        return result;
    }

    public void executeBefore() throws UiObjectNotFoundException {
        for(Action a:beforeActions)
            a.perform();
    }

    public void executeAfter() throws UiObjectNotFoundException {
        for(Action a:afterActions)
            a.perform();
    }

    public void executeTest() {
        int index=0;
        Action a;
        try{

            for(index=0;index<testActions.size();index++) {
                a=testActions.get(index);
                a.perform();
            }
        } catch (UiObjectNotFoundException e){
            throw new BrokenTestCaseException(e.getLocalizedMessage(),this,index);
        }
    }

    public boolean evaluate(){
        if(predicate!=null){
        return predicate.evaluate(this);
        }
        return true;
    }

    public void customEvaluate() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        String predicateString = predicate.toString();
        String target = predicateString.substring(predicateString.indexOf("=")+1,predicateString.indexOf("]"));
        Class<?> targetClass = null;
        try {
            targetClass = Class.forName("esadrcanfer.us.alumno.autotesting."+target);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object test = null;
        Constructor constructor = null;

        try {
            constructor = targetClass.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            test = constructor.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        if(predicate!=null) {
            String methodName = "assertionCheck";
            targetClass.getMethod(methodName).invoke(test, null);
        }
    }


    @Override
    public String toString(){
        StringBuilder builder=new StringBuilder("Test Case["+testActions.size()+"]:");
        for(Action a:testActions)
            builder.append(a.toString());
        return builder.toString();
    }

    public List<Action> getTestActions(){
        return testActions;
    }

    public List<String> getInitialState(){
        return initialState;
    }

    public List<String> getFinalState(){
        return finalState;
    }

    public void setInitialState(List<String> initialState){
        this.initialState = initialState;
    }

    public void setFinalState(List<String> finalState){
        this.finalState = finalState;
    }

    public TestPredicate getPredicate(){
        return predicate;
    }

    public String getAppPackage(){
        return app;
    }

    public void setPredicate(TestPredicate predicate){
        this.predicate = predicate;
    }

    public void setPredicate(String predicate){
        if(predicate!=null) //Si no hay predicate, no lo hace.
        this.predicate = new TestPredicate(predicate);
    }

    public double compareTestCase (TestCase testCase){
        double res = 0;
        for(Action a: this.testActions){
            for(Action a2: testCase.testActions){
                if(a.toString().compareTo(a2.toString()) != 0){
                    res++;
                }
            }
        }
        return res;
    }



    public Set<String> getExceutionContext() {
        return executionContext;
    }

    public void setTestActions(List<Action> testActions) {
        this.testActions=testActions;
    }
}
