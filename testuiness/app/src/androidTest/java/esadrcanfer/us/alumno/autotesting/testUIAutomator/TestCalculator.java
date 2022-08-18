package esadrcanfer.us.alumno.autotesting.testUIAutomator;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static esadrcanfer.us.alumno.autotesting.tests.AutomaticRepairTests.labelsDetection;

import java.util.List;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCalculator{

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

    @Test
    public void testDivisión() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        List<String> initialState = labelsDetection();
        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Calculator"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Calculator"));
        testingApp.clickAndWaitForNewWindow();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_9")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/dec_point")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_4")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/op_div")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_3")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/dec_point")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_2")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/eq")).click();

        List<String> finalState = labelsDetection();

        finalState.contains("2.9375");

    }

    @Test
    public void testMultiplicación() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        List<String> initialState = labelsDetection();
        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Calculator"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Calculator"));
        testingApp.clickAndWaitForNewWindow();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_8")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/dec_point")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_1")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/op_mul")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_7")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/dec_point")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_5")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/eq")).click();

        List<String> finalState = labelsDetection();

        finalState.contains("60.75");

    }

    @Test
    public void testResta() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        List<String> initialState = labelsDetection();
        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Calculator"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Calculator"));
        testingApp.clickAndWaitForNewWindow();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_8")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/dec_point")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_5")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/op_sub")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_3")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/dec_point")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_2")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/eq")).click();

        List<String> finalState = labelsDetection();

        finalState.contains("5.3");

    }

    @Test
    public void testSuma() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        List<String> initialState = labelsDetection();
        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Calculator"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Calculator"));
        testingApp.clickAndWaitForNewWindow();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_4")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/dec_point")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_7")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/op_add")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_9")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/dec_point")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit_6")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/eq")).click();

        List<String> finalState = labelsDetection();

        finalState.contains("14.3");

    }

}