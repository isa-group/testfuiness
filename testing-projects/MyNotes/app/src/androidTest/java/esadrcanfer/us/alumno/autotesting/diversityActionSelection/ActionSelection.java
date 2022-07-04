package esadrcanfer.us.alumno.autotesting.diversityActionSelection;

import java.util.List;
import java.util.Random;

import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;

public interface ActionSelection {
    public Action selectAction(List<TestCase> testCases, List<Action> testCaseActions, List<Action> availableActions);
    public Random getRandom();
    public void setRandom(Random random);
}
