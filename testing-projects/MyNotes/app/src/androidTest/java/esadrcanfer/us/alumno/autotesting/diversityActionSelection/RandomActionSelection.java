package esadrcanfer.us.alumno.autotesting.diversityActionSelection;


import java.util.List;
import java.util.Random;

import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;

public class RandomActionSelection implements ActionSelection {
    private Random random;

    @Override
    public Action selectAction(List<TestCase> testCases, List<Action> testCaseActions, List<Action> availableActions) {
        return availableActions.get(getRandom().nextInt(availableActions.size()));
    }

    @Override
    public Random getRandom() {
        if(random == null){
            random = new Random();
        }
        return random;
    }

    @Override
    public void setRandom(Random random) {
        this.random = random;
    }
}
