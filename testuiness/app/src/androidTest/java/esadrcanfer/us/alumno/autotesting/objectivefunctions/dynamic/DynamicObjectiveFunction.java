package esadrcanfer.us.alumno.autotesting.objectivefunctions.dynamic;

import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;

public interface DynamicObjectiveFunction {
    public double evaluate(Action action, String appPackage);
}
