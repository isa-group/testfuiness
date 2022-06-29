package esadrcanfer.us.alumno.autotesting.testUIAutomator;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.filters.SdkSuppress;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class TestCalculator {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Calculator";
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void checkPreconditions() {
        assertThat(mDevice, notNullValue());
    }

    // UiObject numero0 = mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_0"));
    // UiObject numero1 = mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_1"));
    // UiObject numero2 = mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_2"));
    // UiObject numero3 = mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_3"));
    // UiObject numero4 = mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_4"));
    // UiObject numero5 = mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_5"));
    // UiObject numero6 = mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_6"));
    // UiObject numero7 = mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_7"));
    // UiObject numero8 = mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_8"));
    // UiObject numero9 = mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_9"));
    // UiObject punto = mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/dec_point"));
    // UiObject igual = mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/eq"));
    // UiObject suma  = mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/op_add"));
    // UiObject resta = mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/op_sub"));
    // UiObject multiplicacion = mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/op_mul"));
    // UiObject division = mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/op_div"));
    // UiObject delete   = mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/del"));

    @Test
    public void testCalculadoraSuma() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Clock"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Calculator"));
        testingApp.clickAndWaitForNewWindow();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/digit_4")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_4")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("4")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/dec_point")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/dec_point")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text(".")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/digit_7")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_7")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("7")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/op_add")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/op_add")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("+")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/digit_9")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_9")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("9")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/dec_point")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/dec_point")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text(".")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/digit_6")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_6")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("6")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/eq")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/eq")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("=")).click();

        // UiObject result = mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/result_final")); // API 29
        // UiObject result = mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/result")); // API 25, 27, 28

        // assertThat(result.getText(), is(equalTo("14.3")));
    }

    @Test
    public void testCalculadoraResta() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Clock"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Calculator"));
        testingApp.clickAndWaitForNewWindow();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/digit_8")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_8")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("8")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/dec_point")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/dec_point")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text(".")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/digit_5")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_5")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("5")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/op_sub")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/op_sub")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("−")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/digit_3")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_3")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("3")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/dec_point")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/dec_point")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text(".")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/digit_2")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_2")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("2")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/eq")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/eq")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("=")).click();

        // UiObject result = mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/result_final")); // API 29
        // UiObject result = mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/result")); // API 25, 27, 28

        // assertThat(result.getText(), is(equalTo("5.3")));
    }

    @Test
    public void testCalculadoraMultiplicacion() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Clock"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Calculator"));
        testingApp.clickAndWaitForNewWindow();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/digit_8")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_8")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("8")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/dec_point")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/dec_point")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text(".")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/digit_1")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_1")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("1")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/op_mul")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/op_mul")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("×")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/digit_7")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_7")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("7")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/dec_point")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/dec_point")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text(".")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/digit_5")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_5")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("5")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/eq")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/eq")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("=")).click();

        // UiObject result = mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/result_final")); // API 29
        // UiObject result = mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/result")); // API 25, 27, 28

        // assertThat(result.getText(), is(equalTo("60.75")));
    }

    @Test
    public void testCalculadoraDivision() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Clock"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Calculator"));
        testingApp.clickAndWaitForNewWindow();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/digit_9")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_9")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("9")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/dec_point")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/dec_point")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text(".")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/digit_4")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_4")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("4")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/op_div")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/op_div")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("÷")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/digit_3")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_3")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("3")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/dec_point")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/dec_point")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text(".")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/digit_2")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_2")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("2")).click();

        // mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/eq")).click(); // API 29
        // mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/eq")).click(); // API 25, 27, 28

        mDevice.findObject(new UiSelector().text("=")).click();

        // UiObject result = mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/result_final")); // API 29
        // UiObject result = mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/result")); // API 25, 27, 28

        // assertThat(result.getText(), is(equalTo("2.9375")));
    }

}