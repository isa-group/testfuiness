package esadrcanfer.us.alumno.autotesting.objectivefunctions.dynamic;
import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;

import static android.os.SystemClock.sleep;

public class DynamicTestExecutionTimeObjectiveFunction implements DynamicObjectiveFunction {
    private long waitTime;

    public DynamicTestExecutionTimeObjectiveFunction(long waitTime){
        this.waitTime = waitTime;
    }
    @Override
    public double evaluate(Action action, String appPackage) {
        long duration=-1;
        try{
            long start=System.currentTimeMillis();
            action.perform();
            sleep(waitTime);
            duration=System.currentTimeMillis()-start- waitTime;
        }catch(Exception e){

        }
        return (double)duration;
    }
}
