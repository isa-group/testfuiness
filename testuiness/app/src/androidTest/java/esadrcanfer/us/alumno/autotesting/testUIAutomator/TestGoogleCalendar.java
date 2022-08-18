package esadrcanfer.us.alumno.autotesting.testUIAutomator;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.KeyEvent;

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
public class TestGoogleCalendar{

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Calendar";
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {

        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();
        try{
            mDevice.pressKeyCode(KeyEvent.KEYCODE_APP_SWITCH);
            UiScrollable appScroll = new UiScrollable(new UiSelector().resourceId("com.google.android.apps.nexuslauncher:id/workspace"));
            appScroll.swipeRight(5);
            mDevice.findObject(new UiSelector().text("CLEAR ALL")).click();
        }catch(UiObjectNotFoundException e){
            Log.d("ISA", "There are no open apps");
        }

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
    public void test1CreateEvent() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Calendar"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Calendar"));
        testingApp.clickAndWaitForNewWindow();

        mDevice.findObject(new UiSelector().description("Create new event and more")).click();

        mDevice.findObject(new UiSelector().text("Event")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.calendar:id/input")).setText("Prueba ficheros test");

        mDevice.findObject(new UiSelector().resourceId("com.google.android.calendar:id/start_date")).click();

        mDevice.findObject(new UiSelector().text("30")).click();

        mDevice.findObject(new UiSelector().resourceId("android:id/button1")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.calendar:id/end_date")).click();

        mDevice.findObject(new UiSelector().text("30")).click();

        mDevice.findObject(new UiSelector().resourceId("android:id/button1")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.calendar:id/start_time")).click();

        mDevice.findObject(new UiSelector().description("11")).click();

        mDevice.findObject(new UiSelector().description("15")).click();

        mDevice.findObject(new UiSelector().text("AM")).click();

        mDevice.findObject(new UiSelector().resourceId("android:id/button1")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.calendar:id/end_time")).click();

        mDevice.findObject(new UiSelector().description("12")).click();

        mDevice.findObject(new UiSelector().description("30")).click();

        mDevice.findObject(new UiSelector().text("PM")).click();

        mDevice.findObject(new UiSelector().resourceId("android:id/button1")).click();

        mDevice.findObject(new UiSelector().text("SAVE")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.calendar:id/date_picker_button")).click();

        mDevice.findObject(new UiSelector().className("android.view.View").index(29)).click();

        mDevice.findObject(new UiSelector().description("11:15 AM – 12:30 PM: Prueba ficheros test")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Prueba ficheros test"));

    }

    @Test
    public void test2EditEvent() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Calendar"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Calendar"));
        testingApp.clickAndWaitForNewWindow();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.calendar:id/date_picker_button")).click();

        mDevice.findObject(new UiSelector().className("android.view.View").index(29)).click();

        mDevice.findObject(new UiSelector().description("11:15 AM – 12:30 PM: Prueba ficheros test")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.calendar:id/info_action_edit_hit")).click();

        new UiScrollable(new UiSelector().resourceId("com.google.android.calendar:id/editor_scroll_view")).scrollIntoView(new UiSelector().text("Add note"));

        mDevice.findObject(new UiSelector().text("Add note")).setText("Evento importante");

        mDevice.findObject(new UiSelector().text("SAVE")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Evento importante"));

    }

    @Test
    public void test3DeleteEvent() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Calendar"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Calendar"));
        testingApp.clickAndWaitForNewWindow();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.calendar:id/date_picker_button")).click();

        mDevice.findObject(new UiSelector().className("android.view.View").index(29)).click();

        mDevice.findObject(new UiSelector().description("11:15 AM – 12:30 PM: Prueba ficheros test")).click();

        mDevice.findObject(new UiSelector().description("More options")).click();

        mDevice.findObject(new UiSelector().text("Delete")).click();

        mDevice.findObject(new UiSelector().resourceId("android:id/button1")).click();

    }

}