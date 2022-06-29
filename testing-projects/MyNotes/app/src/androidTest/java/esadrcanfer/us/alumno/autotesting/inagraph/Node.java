package esadrcanfer.us.alumno.autotesting.inagraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.test.uiautomator.UiObject;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.InputGenerator;

public class Node {
    Set<UiObject> controls;
    List<Action> availableActions;
    Map<Action,Node> outputVetex;
    Map<UiObject,InputGenerator> inputGenerators;

    public Node(){
        controls=new HashSet<UiObject>();
        availableActions=new ArrayList<Action>();
        outputVetex=new HashMap<>();
        inputGenerators=new HashMap<UiObject, InputGenerator>();
    }


    public boolean isOutboundAction(Action action){
        return outputVetex.keySet().contains(action);
    }

    public Map<Action,Node> getOutputVertex(){
        return outputVetex;
    }

    public List<Action> getAvailableActions()
    {
        return availableActions;
    }

    public Set<UiObject> getControls() {
        return controls;
    }

    @Override
    public String toString(){
        String res = "[";
        for (Action a: availableActions) {
            res += a.toString()+", ";
        }
        res = res.substring(0, res.length()-2);
        res +="]";
        return res;
    }
}
