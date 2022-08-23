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
public class TestPhone{

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Phone";
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
    public void test1AddFavourites() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        /*UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Phone"));*/

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Phone"));
        testingApp.clickAndWaitForNewWindow();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.dialer:id/tab_contacts")).click();

        mDevice.findObject(new UiSelector().text("Alejandro Garcia Fernandez")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/menu_star")).click();

    }

    @Test
    public void test2CallContact() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        /*UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Phone"));*/

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Phone"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.dialer:id/tab_contacts")).click();

        mDevice.findObject(new UiSelector().text("Alejandro Garcia Fernandez")).click();

        mDevice.findObject(new UiSelector().text("(640) 936-528")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.dialer:id/incall_end_call")).waitUntilGone(10000);

        mDevice.findObject(new UiSelector().resourceId("com.google.android.dialer:id/incall_end_call")).click();

        mDevice.pressBack();

        mDevice.findObject(new UiSelector().text("Recents")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Alejandro Garcia Fernandez"));

    }

    @Test
    public void test3CallPhone() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        /*UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Phone"));*/

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Phone"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.dialer:id/dialpad_fab")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.dialer:id/six")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.dialer:id/nine")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.dialer:id/two")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.dialer:id/five")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.dialer:id/two")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.dialer:id/five")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.dialer:id/three")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.dialer:id/five")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.dialer:id/five")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.dialer:id/dialpad_voice_call_button")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.dialer:id/incall_end_call")).waitUntilGone(10000);

        mDevice.findObject(new UiSelector().resourceId("com.google.android.dialer:id/incall_end_call")).click();

        mDevice.findObject(new UiSelector().text("Recents")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("692525355"));

    }

    @Test
    public void test4ClearCallHistory() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        /*UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Phone"));*/

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Phone"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().description("More options")).click();

        mDevice.findObject(new UiSelector().text("Call history")).click();

        mDevice.findObject(new UiSelector().description("More options")).click();

        mDevice.findObject(new UiSelector().text("Clear call history")).click();

        mDevice.findObject(new UiSelector().resourceId("android:id/button1")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Calls you make will show up here"));

    }

    @Test
    public void test5DeleteFavourites() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        /*UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Phone"));*/

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Phone"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("Contacts")).click();

        mDevice.findObject(new UiSelector().text("Alejandro Garcia Fernandez")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/menu_star")).click();

        List<String> finalState = labelsDetection();

    }

}