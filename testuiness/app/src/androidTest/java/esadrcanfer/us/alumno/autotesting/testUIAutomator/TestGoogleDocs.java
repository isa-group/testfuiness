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
public class TestGoogleDocs{

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Docs";
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
    public void test1CreateGoogleDocs() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Docs"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Docs"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs.editors.docs:id/fab_base_button")).click();

        mDevice.findObject(new UiSelector().text("New document")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs.editors.docs:id/action_mode_close_button")).click();

        mDevice.findObject(new UiSelector().description("Back")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Untitled document"));

    }

    @Test
    public void test2EditGoogleDocs() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Docs"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Docs"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs.editors.docs:id/more_actions_button")).click();

        new UiScrollable(new UiSelector().resourceId("com.google.android.apps.docs.editors.docs:id/menu_recycler_view")).scrollToEnd(10);

        mDevice.findObject(new UiSelector().text("Rename")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs.editors.docs:id/edit_text")).setText("Prueba ficheros");

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs.editors.docs:id/positive_button")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Prueba ficheros"));

    }

    @Test
    public void test3EditGoogleDocs2() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Docs"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Docs"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs.editors.docs:id/more_actions_button")).click();

        new UiScrollable(new UiSelector().resourceId("com.google.android.apps.docs.editors.docs:id/menu_recycler_view")).scrollIntoView(new UiSelector().text("Rename"));

        mDevice.findObject(new UiSelector().text("Rename")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs.editors.docs:id/edit_text")).setText("Prueba");

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs.editors.docs:id/positive_button")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Prueba"));

    }

    @Test
    public void test4EditGoogleDocs3() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Docs"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Docs"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs.editors.docs:id/more_actions_button")).click();

        new UiScrollable(new UiSelector().resourceId("com.google.android.apps.docs.editors.docs:id/menu_recycler_view")).scrollToEnd(Integer.MAX_VALUE);

        mDevice.findObject(new UiSelector().text("Rename")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs.editors.docs:id/edit_text")).setText("Prueba");

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs.editors.docs:id/positive_button")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Prueba"));

    }

    @Test
    public void test5SendCopyGoogleDocs() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Docs"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Docs"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs.editors.docs:id/more_actions_button")).click();

        mDevice.findObject(new UiSelector().text("Send a copy")).click();

        mDevice.findObject(new UiSelector().text("PDF Document (.pdf)")).click();

        mDevice.findObject(new UiSelector().resourceId("android:id/button1")).click();

        mDevice.findObject(new UiSelector().text("JUST ONCE")).clickAndWaitForNewWindow();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Compose"));

    }

    @Test
    public void test6DeleteGoogleDocs() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Docs"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Docs"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs.editors.docs:id/more_actions_button")).click();

        new UiScrollable(new UiSelector().resourceId("com.google.android.apps.docs.editors.docs:id/menu_recycler_view")).scrollToEnd(10);

        mDevice.findObject(new UiSelector().text("Remove")).click();

        mDevice.findObject(new UiSelector().text("Move to trash")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("File moved to trash. "));

    }

}