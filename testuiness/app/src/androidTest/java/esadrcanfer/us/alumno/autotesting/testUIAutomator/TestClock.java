package esadrcanfer.us.alumno.autotesting.testUIAutomator;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

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

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestClock{

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Clock";
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {

        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(getInstrumentation());

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
    public void testAlarm() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Clock"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Clock"));
        testingApp.clickAndWaitForNewWindow();

        mDevice.findObject(new UiSelector().text("Alarm")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/fab")).click();

        mDevice.findObject(new UiSelector().text("8")).click();

        mDevice.findObject(new UiSelector().text("30")).click();

        mDevice.findObject(new UiSelector().text("AM")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/material_timepicker_ok_button")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/choose_ringtone")).click();

        new UiScrollable(new UiSelector().resourceId("com.google.android.deskclock:id/ringtone_content")).scrollIntoView(new UiSelector().text("Krypton"));

        mDevice.findObject(new UiSelector().text("Krypton")).click();

        mDevice.findObject(new UiSelector().description("Navigate up")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/edit_label")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/label_input_field")).setText("Test alarma");

        mDevice.findObject(new UiSelector().resourceId("android:id/button1")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/day_button_1")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/day_button_2")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/day_button_3")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/day_button_4")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/day_button_5")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/arrow")).click();

        // WRITE ASSERTIONS HERE

    }

    @Test
    public void testOtherAlarm() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Clock"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Clock"));
        testingApp.clickAndWaitForNewWindow();

        mDevice.findObject(new UiSelector().text("Alarm")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/arrow")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/day_button_1")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/day_button_2")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/day_button_3")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/day_button_4")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/day_button_6")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/day_button_0")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/digital_clock")).click();

        mDevice.findObject(new UiSelector().text("10")).click();

        mDevice.findObject(new UiSelector().text("30")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/material_timepicker_ok_button")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/arrow")).click();

        // WRITE ASSERTIONS HERE

    }

    @Test
    public void testStopWatch() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Clock"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Clock"));
        testingApp.clickAndWaitForNewWindow();

        mDevice.findObject(new UiSelector().text("Stopwatch")).click();

        mDevice.findObject(new UiSelector().description("Start")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/fab")).waitUntilGone(10);

        mDevice.findObject(new UiSelector().description("Pause")).click();

        mDevice.findObject(new UiSelector().description("Reset")).click();

        // WRITE ASSERTIONS HERE

    }

    @Test
    public void testTimer() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Clock"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Clock"));
        testingApp.clickAndWaitForNewWindow();

        mDevice.findObject(new UiSelector().text("Timer")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/timer_setup_digit_3")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/timer_setup_digit_7")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/timer_setup_digit_2")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/timer_setup_digit_8")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/timer_setup_digit_6")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/timer_setup_digit_1")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/fab")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/fab")).waitUntilGone(10);

        mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/fab")).click();

        mDevice.findObject(new UiSelector().description("Delete")).click();

        // WRITE ASSERTIONS HERE

    }

}