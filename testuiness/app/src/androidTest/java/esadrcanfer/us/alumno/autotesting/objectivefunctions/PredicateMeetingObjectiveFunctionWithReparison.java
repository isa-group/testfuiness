package esadrcanfer.us.alumno.autotesting.objectivefunctions;

import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.BrokenTestCaseException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;

import java.util.List;
import java.util.ArrayList;

public class PredicateMeetingObjectiveFunctionWithReparison extends PredicateMeetingObjectiveFunction {

    protected Double brokenTestHandling(BrokenTestCaseException ex){
        return tryRepair(ex);
    }

    public Double tryRepair(BrokenTestCaseException ex) {
        Double result=null;
        TestCase testcase=ex.getBrokenTestCase();
        try {
            result = new Double(testcase.getPredicate().nClausesMeet(testcase));
            if (result == testcase.getPredicate().getNClauses()) {
                repair(testcase, (int)ex.getBreakingIndex());
            } else
                result = null;
        }catch(Exception exc){}
        return result;
    }

    public void repair(TestCase test,int index) {
        List<Action> testActions=new ArrayList<>(index);
        for(int i=0;i<index;i++) {
            testActions.add(test.getTestActions().get(i));
        }
        test.setTestActions(testActions);
    }

}
