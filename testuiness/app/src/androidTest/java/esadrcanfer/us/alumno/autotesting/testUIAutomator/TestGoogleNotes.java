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
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static esadrcanfer.us.alumno.autotesting.tests.AutomaticRepairTests.labelsDetection;

import java.util.List;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGoogleNotes{

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Keep Notes";
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
    public void test1CreateGoogleNotes() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Keep Notes"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Keep Notes"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.keep:id/new_note_button")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.keep:id/editable_title")).setText("title");

        mDevice.findObject(new UiSelector().resourceId("com.google.android.keep:id/edit_note_text")).setText("description");

        mDevice.findObject(new UiSelector().description("Open navigation drawer")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("title"));

    }

    @Test
    public void test2ListGoogleNotes() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Keep Notes"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Keep Notes"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("title")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.keep:id/new_list_button")).click();

        mDevice.findObject(new UiSelector().text("Checkboxes")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.keep:id/description")).setText("List item 1");

        mDevice.findObject(new UiSelector().description("Navigate up")).click();

        List<String> finalState = labelsDetection();

        assertTrue(!finalState.contains("description"));

    }

    @Test
    public void test3EditGoogleNotes() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Keep Notes"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Keep Notes"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("title")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.keep:id/editable_title")).setText("modified title");

        mDevice.findObject(new UiSelector().text("List item 1")).setText("Modified List item 1");

        mDevice.findObject(new UiSelector().description("Navigate up")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("modified title"));

    }

    @Test
    public void test4ReminderGoogleNotes() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Keep Notes"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Keep Notes"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("modified title")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.keep:id/menu_reminder")).click();

        mDevice.findObject(new UiSelector().text("Pick a date & time")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.keep:id/date_spinner")).click();

        mDevice.findObject(new UiSelector().text("Tomorrow")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.keep:id/time_spinner")).click();

        mDevice.findObject(new UiSelector().text("Pick a time…")).click();

        mDevice.findObject(new UiSelector().text("12")).click();

        mDevice.findObject(new UiSelector().text("30")).click();

        mDevice.findObject(new UiSelector().text("OK")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.keep:id/recurrence_spinner")).click();

        mDevice.findObject(new UiSelector().text("Weekly")).click();

        mDevice.findObject(new UiSelector().text("Place")).click();

        mDevice.findObject(new UiSelector().text("Home")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.keep:id/save")).click();

        mDevice.findObject(new UiSelector().description("Navigate up")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Home"));

    }

    @Test
    public void test5DeleteGoogleNotes() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Keep Notes"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Keep Notes"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("modified title")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.keep:id/bs_action_button")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.keep:id/menu_text")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Note moved to trash"));

    }

}
