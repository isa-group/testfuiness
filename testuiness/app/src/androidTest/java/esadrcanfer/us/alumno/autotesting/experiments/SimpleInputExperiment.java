package esadrcanfer.us.alumno.autotesting.experiments;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import android.support.test.filters.SdkSuppress;

import androidx.test.uiautomator.UiObjectNotFoundException;

@RunWith(Parameterized.class)
@SdkSuppress(minSdkVersion = 18)
public class SimpleInputExperiment extends Experiment {

    private static final List<String> tests = Arrays.asList("Test Simple Input App/WriteInputTest.txt");
    private static final int numberOfExec = 4;
    private static final List<String> algorithms = Arrays.asList("GRASP Algorithm",
                                                                "Random Algorithm",
                                                                "WATER Algorithm");
    private static final int timeout = 3600;
    private static final String experimentPath = "Experiments/ExperimentSimpleInput";

    @Parameterized.Parameters
    public static Collection<String> getData(){

        return createTestData(tests, numberOfExec, algorithms, experimentPath);
    }

    public SimpleInputExperiment(String testcase) {

        super(testcase.split(";")[0], testcase.split(";")[1], testcase.split(";")[2], experimentPath);
    }

    @Test
    public void runTest() throws UiObjectNotFoundException {
        try {
            runTest(timeout);
        }catch(UiObjectNotFoundException e){
            throw new UiObjectNotFoundException(e.getMessage());
        }
    }
}
