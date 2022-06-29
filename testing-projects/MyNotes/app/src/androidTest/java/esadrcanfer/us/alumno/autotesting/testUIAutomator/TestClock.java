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
public class TestClock {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Clock";
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

    // UiObject sunday = mDevice.findObject(new UiSelector().description("Sunday"));

    // UiObject monday = mDevice.findObject(new UiSelector().description("Monday"));

    // UiObject tuesday = mDevice.findObject(new UiSelector().description("Tuesday"));

    // UiObject wednesday = mDevice.findObject(new UiSelector().description("Wednesday"));

    // UiObject thursday = mDevice.findObject(new UiSelector().description("Thursday"));

    // UiObject friday = mDevice.findObject(new UiSelector().description("Friday"));

    // UiObject saturday = mDevice.findObject(new UiSelector().description("Saturday"));

    @Test
    public void testAlarm() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

         UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
         allAppsButton.click();
         // allAppsButton.swipeUp(2);

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Clock"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Clock"));
        testingApp.clickAndWaitForNewWindow();

        // UiObject allApps = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.nexuslauncher:id/search_container_all_apps"));
        // UiObject allApps = mDevice.findObject(new UiSelector().description("Search"));
        // allApps.click();

        // UiObject searchApps = mDevice.findObject(new UiSelector().resourceId("com.google.android.googlequicksearchbox:id/search_box"));
        // searchApps.setText("Clock");

        // UiObject apps = mDevice.findObject(new UiSelector().className("android.widget.TextView").text("Clock"));
        // apps.click();

        // UiObject testingApp = mDevice.findObject(new UiSelector().text("Clock"));
        // testingApp.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().description("Alarm"));
        //UiObject button2 = mDevice.findObject(new UiSelector().text("ALARM"));
        button.clickAndWaitForNewWindow();

        //if (button1 != null){
        //    button1.clickAndWaitForNewWindow();
        //} else {
        //    button2.clickAndWaitForNewWindow();
        //}

        //UiObject expansion = mDevice.findObject(new UiSelector().resourceId("com.android.deskclock:id/arrow"));
        UiObject expansion = mDevice.findObject(new UiSelector().description("Expand alarm"));
        expansion.click();

        //UiObject ringtone = mDevice.findObject(new UiSelector().resourceId("com.android.deskclock:id/choose_ringtone"));
        // UiObject ringtone = mDevice.findObject(new UiSelector().description("Ringtone Default (Cesium)"));
        UiObject ringtone = mDevice.findObject(new UiSelector().descriptionContains("Ringtone "));
        ringtone.click();

        UiScrollable ringtoneViews = new UiScrollable(new UiSelector().scrollable(true));
        ringtoneViews.scrollIntoView(new UiSelector().text("Krypton"));

        UiObject tone = mDevice.findObject(new UiSelector().text("Krypton"));
        tone.clickAndWaitForNewWindow();

        UiObject out = mDevice.findObject(new UiSelector().description("Navigate up"));
        out.clickAndWaitForNewWindow();

        // API 28: no tiene botón de confirmación

        //UiObject select = mDevice.findObject(new UiSelector().resourceId("android:id/button1"));
        //select.clickAndWaitForNewWindow();

        //UiObject label = mDevice.findObject(new UiSelector().resourceId("com.android.deskclock:id/edit_label"));
        UiObject label = mDevice.findObject(new UiSelector().description("No label specified"));
        label.clickAndWaitForNewWindow();

        UiObject text = mDevice.findObject(new UiSelector().className("android.widget.EditText"));
        text.setText("UI Automator");

        UiObject select = mDevice.findObject(new UiSelector().text("OK"));
        select.clickAndWaitForNewWindow();

        //UiObject checkBox = mDevice.findObject(new UiSelector().resourceId("com.android.deskclock:id/repeat_onoff"));
        UiObject checkBox = mDevice.findObject(new UiSelector().text("Repeat"));
        if (!checkBox.isSelected()) checkBox.click();

    }

    @Test
    public void testOtherAlarm() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();
        // allAppsButton.swipeUp(2);

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Clock"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Clock"));
        testingApp.clickAndWaitForNewWindow();

        // UiObject allApps = mDevice.findObject(new UiSelector().description("Search"));
        // allApps.click();

        // UiObject searchApps = mDevice.findObject(new UiSelector().resourceId("com.google.android.googlequicksearchbox:id/search_box"));
        // searchApps.setText("Clock");

        // UiObject apps = mDevice.findObject(new UiSelector().className("android.widget.TextView").text("Clock"));
        // apps.click();

        UiObject button = mDevice.findObject(new UiSelector().description("Alarm"));
        //UiObject button = mDevice.findObject(new UiSelector().text("ALARM"));
        button.clickAndWaitForNewWindow();

        //UiObject expansion = mDevice.findObject(new UiSelector().resourceId("com.android.deskclock:id/arrow"));
        UiObject expansion = mDevice.findObject(new UiSelector().description("Expand alarm"));
        expansion.click();

        UiObject sunday = mDevice.findObject(new UiSelector().description("Sunday"));
        sunday.click();

        UiObject monday = mDevice.findObject(new UiSelector().description("Monday"));
        monday.click();

        UiObject tuesday = mDevice.findObject(new UiSelector().description("Tuesday"));
        tuesday.click();

        UiObject wednesday = mDevice.findObject(new UiSelector().description("Wednesday"));
        wednesday.click();

        UiObject thursday = mDevice.findObject(new UiSelector().description("Thursday"));
        thursday.click();

        UiObject friday = mDevice.findObject(new UiSelector().description("Friday"));
        friday.click();

        UiObject saturday = mDevice.findObject(new UiSelector().description("Saturday"));
        saturday.click();

        // UiObject checkBox = mDevice.findObject(new UiSelector().resourceId("com.android.deskclock:id/onoff"));
        UiObject checkBox = mDevice.findObject(new UiSelector().text("Repeat"));
        if (!checkBox.isSelected()) checkBox.click();

        UiObject time = mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/digital_clock"));
        time.click();

        UiObject hour = mDevice.findObject(new UiSelector().description("10"));
        hour.click();

        UiObject minute = mDevice.findObject(new UiSelector().description("30"));
        minute.click();

        UiObject confirm = mDevice.findObject(new UiSelector().resourceId("android:id/button1"));
        confirm.click();

        expansion.click();
    }


    @Test
    public void testTimer() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();
        // allAppsButton.swipeUp(2);

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Clock"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Clock"));
        testingApp.clickAndWaitForNewWindow();

        // UiObject allApps = mDevice.findObject(new UiSelector().description("Search"));
        // allApps.click();

        // UiObject searchApps = mDevice.findObject(new UiSelector().resourceId("com.google.android.googlequicksearchbox:id/search_box"));
        // searchApps.setText("Clock");

        // UiObject apps = mDevice.findObject(new UiSelector().className("android.widget.TextView").text("Clock"));
        // apps.click();

        UiObject button = mDevice.findObject(new UiSelector().description("Timer"));
        //UiObject button = mDevice.findObject(new UiSelector().text("TIMER"));
        button.clickAndWaitForNewWindow();

        UiObject number0 = mDevice.findObject(new UiSelector().text("0"));
        UiObject number1 = mDevice.findObject(new UiSelector().text("1"));
        UiObject number2 = mDevice.findObject(new UiSelector().text("2"));
        UiObject number3 = mDevice.findObject(new UiSelector().text("3"));
        UiObject number4 = mDevice.findObject(new UiSelector().text("4"));
        UiObject number5 = mDevice.findObject(new UiSelector().text("5"));
        UiObject number6 = mDevice.findObject(new UiSelector().text("6"));
        UiObject number7 = mDevice.findObject(new UiSelector().text("7"));
        UiObject number8 = mDevice.findObject(new UiSelector().text("8"));
        UiObject number9 = mDevice.findObject(new UiSelector().text("9"));

        number3.click();
        number7.click();

        number2.click();
        number8.click();

        number6.click();
        number1.click();

        // UiObject play = mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/fab"));
        UiObject play = mDevice.findObject(new UiSelector().description("Start"));
        play.click();

        UiObject stop = mDevice.findObject(new UiSelector().description("Stop"));
        stop.waitUntilGone(20000);
        stop.click();

        // UiObject delete = mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/left_button"));
        UiObject delete = mDevice.findObject(new UiSelector().description("Delete"));
        delete.click();

    }

    @Test
    public void testStopwatch() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();
        // allAppsButton.swipeUp(2);

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Clock"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Clock"));
        testingApp.clickAndWaitForNewWindow();

        // UiObject allApps = mDevice.findObject(new UiSelector().description("Search"));
        // allApps.click();

        // UiObject searchApps = mDevice.findObject(new UiSelector().resourceId("com.google.android.googlequicksearchbox:id/search_box"));
        // searchApps.setText("Clock");

        // UiObject apps = mDevice.findObject(new UiSelector().className("android.widget.TextView").text("Clock"));
        // apps.click();

        UiObject button = mDevice.findObject(new UiSelector().description("Stopwatch"));
        //UiObject button = mDevice.findObject(new UiSelector().text("STOPWATCH"));
        button.clickAndWaitForNewWindow();

        // UiObject play = mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/fab"));
        UiObject play = mDevice.findObject(new UiSelector().description("Start"));
        play.click();

        // play.waitUntilGone(20000);

        // play.click();

        // UiObject stop = mDevice.findObject(new UiSelector().resourceId("com.google.android.deskclock:id/left_button"));
        UiObject stop = mDevice.findObject(new UiSelector().description("Pause"));
        stop.waitUntilGone(20000);
        stop.click();

        UiObject reset = mDevice.findObject(new UiSelector().description("Reset"));
        reset.click();
    }
}