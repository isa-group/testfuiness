package esadrcanfer.us.alumno.autotesting.experiments;

import android.support.test.filters.SdkSuppress;

import androidx.test.uiautomator.UiObjectNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
@SdkSuppress(minSdkVersion = 18)
public class ExperimentGoogleMaps extends Experiment {

    private static final List<String> tests = Arrays.asList("Test Google Maps/Old/TestJourneyGoogleMaps.txt",
                                                            "Test Google Maps/Old/TestSearchGoogleMaps.txt",
                                                            "Test Google Maps/Old/TestShareLocationGoogleMaps.txt");
    private static final int numberOfExec = 4;
    private static final List<String> algorithms = Arrays.asList("Random Algorithm",
                                                                "WATER Algorithm");
    private static final int timeout = 60;

    private String id;
    private String path;
    private String algorithm;

    @Parameterized.Parameters
    public static Collection<String> getData(){

        return createTestData(tests, numberOfExec, algorithms);
    }

    public ExperimentGoogleMaps(String testcase) {

        super();

        String[] testCaseSplit = testcase.split(";");

        this.id = testCaseSplit[0];
        this.path = testCaseSplit[1];
        this.algorithm = testCaseSplit[2];
    }

    @Test
    public void runTest() throws UiObjectNotFoundException {
        try {
            runTest(id, path, algorithm, timeout);
        }catch(UiObjectNotFoundException e){
            throw new UiObjectNotFoundException(e.getMessage());
        }
    }

}
