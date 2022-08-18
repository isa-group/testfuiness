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
public class TestPlayStore{

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Play Store";
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
    public void test1SearchApplication() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Play Store"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Play Store"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("Search for apps & games")).click();

        mDevice.findObject(new UiSelector().text("Search for apps & games")).setText("Google Classroom");

        mDevice.pressEnter();

        mDevice.findObject(new UiSelector().text("Install")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Cancel"));

    }

    @Test
    public void test2InstallApplication() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Play Store"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Play Store"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("Apps")).click();

        mDevice.findObject(new UiSelector().text("Based on your recent activity")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.vending:id/mini_blurb")).click();

        mDevice.findObject(new UiSelector().text("Install")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Verified by Play Protect"));

    }

    @Test
    public void test3UpdateApplication() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Play Store"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Play Store"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().className("android.widget.ImageView").index(1)).click();

        mDevice.findObject(new UiSelector().text("Manage apps & device")).click();

        mDevice.findObject(new UiSelector().text("Manage")).click();

        mDevice.findObject(new UiSelector().text("Updates available")).click();

        new UiScrollable(new UiSelector().className("android.widget.FrameLayout").index(1)).scrollIntoView(new UiSelector().text("Google Duo"));

        mDevice.findObject(new UiSelector().text("Google Duo")).click();

        mDevice.findObject(new UiSelector().text("Update")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Wallpapers") && finalState.contains("Pending..."));

    }

    @Test
    public void test4UninstallApplication() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Play Store"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Play Store"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().className("android.widget.ImageView").index(1)).click();

        mDevice.findObject(new UiSelector().text("Manage apps & device")).click();

        mDevice.findObject(new UiSelector().text("Manage")).click();

        mDevice.findObject(new UiSelector().text("Google Classroom")).click();

        mDevice.findObject(new UiSelector().text("Uninstall")).click();

        mDevice.findObject(new UiSelector().text("Uninstall")).click();

        mDevice.findObject(new UiSelector().description("Navigate up")).click();

        mDevice.findObject(new UiSelector().description("Navigate up")).click();

        mDevice.findObject(new UiSelector().className("android.widget.ImageView").index(0)).click();

        mDevice.findObject(new UiSelector().text("Manage apps & device")).click();

        mDevice.findObject(new UiSelector().text("Manage")).click();

        List<String> finalState = labelsDetection();

        assertTrue(!finalState.contains("Google classroom"));

    }

}