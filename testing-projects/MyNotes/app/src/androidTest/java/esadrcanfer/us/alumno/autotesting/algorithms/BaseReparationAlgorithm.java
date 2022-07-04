package esadrcanfer.us.alumno.autotesting.algorithms;

import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.inagraph.CloseAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.StartAppAction;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.ActionFactory;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

public abstract class BaseReparationAlgorithm implements ReparationAlgorithm {

    protected Random random;
    protected Long executionTime;
    protected long startInstant;
    protected int objectiveFunctionEvaluations;
    protected Double currentOptimum;


    protected List<Action> createAction(UiDevice device, Long seed) {
        Map<UiObject, Action> actions;
        actions = ActionFactory.createActions(device, seed);
        return new ArrayList<>(actions.values());
    }

    public List<String> labelsDetection() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        List<String> finalState = new ArrayList<>();
        List<UiObject2> elements = device.findObjects(By.clazz(TextView.class));
        Log.d("ISA", "Size: " + elements.size());
        for (UiObject2 label : elements) {
            try {
                String text = label.getText();
                //Solución básica, hay que mejorarla
                if (!(text.contains(":") || text.contains("%"))) {
                    finalState.add(text);
                    Log.d("ISA", "Label: " + label.getText());
                }
            }catch(Exception e){
                System.out.println(e.getLocalizedMessage());
            }
        }
        return finalState;
    }

    protected void closeApp(String appPackage) {
        CloseAppAction action = new CloseAppAction(appPackage);
        action.perform();
    }

    protected void startApp(String appPackage) throws UiObjectNotFoundException {
        StartAppAction action = new StartAppAction(appPackage);
        action.perform();
    }

    public Random getRandom() {
        if (random == null) {
            random = new Random();
        }
        return random;
    }


    public void setRandom(Random random) {
        this.random = random;
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
