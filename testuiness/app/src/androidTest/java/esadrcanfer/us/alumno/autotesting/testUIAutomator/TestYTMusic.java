package esadrcanfer.us.alumno.autotesting.testUIAutomator;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static esadrcanfer.us.alumno.autotesting.tests.AutomaticRepairTests.labelsDetection;

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

import java.util.List;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestYTMusic{

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "YT Music";
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
    public void test1SearchAndPlaySong() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("YT Music"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("YT Music"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/action_search_button")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/search_edit_text")).setText("Radioactive");

        mDevice.findObject(new UiSelector().className("android.widget.LinearLayout").index(1)).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/first_entity_button")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/player_control_play_pause_replay_button")).waitUntilGone(30000);

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Radioactive"));

    }

    @Test
    public void test2AddSongToLibrary() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("YT Music"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("YT Music"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/action_search_button")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/search_edit_text")).setText("Radioactive");

        mDevice.findObject(new UiSelector().className("android.widget.LinearLayout").index(1)).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/contextual_menu_anchor")).click();

        mDevice.findObject(new UiSelector().text("Add to library")).clickAndWaitForNewWindow();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Added to library"));

    }

    @Test
    public void test3DeleteFromLibrary() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("YT Music"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("YT Music"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("Library")).click();

        mDevice.findObject(new UiSelector().text("Songs")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/contextual_menu_anchor")).click();

        mDevice.findObject(new UiSelector().text("Remove from library")).click();

        List<String> finalState = labelsDetection();

        assertTrue(!finalState.contains("Radioactive"));

    }

    @Test
    public void test4Explore() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("YT Music"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("YT Music"));
        testingApp.clickAndWaitForNewWindow();

        mDevice.findObject(new UiSelector().text("Explore")).click();

        mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(1)).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/two_row_item")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/player_control_play_pause_replay_button")).waitUntilGone(30000);

    }

    @Test
    public void test5Subscribe() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("YT Music"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("YT Music"));
        testingApp.clickAndWaitForNewWindow();

        mDevice.findObject(new UiSelector().text("Explore")).click();

        mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(1)).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/contextual_menu_anchor")).click();

        mDevice.findObject(new UiSelector().text("Subscribe")).click();

    }

    @Test
    public void test6Unsubscribe() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("YT Music"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("YT Music"));
        testingApp.clickAndWaitForNewWindow();

        mDevice.findObject(new UiSelector().text("Library")).click();

        mDevice.findObject(new UiSelector().text("Subscriptions")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/contextual_menu_anchor")).clickAndWaitForNewWindow();

        mDevice.findObject(new UiSelector().text("Unsubscribe")).clickAndWaitForNewWindow();

    }

}
