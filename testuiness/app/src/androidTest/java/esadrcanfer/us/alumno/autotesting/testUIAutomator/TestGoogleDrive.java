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
public class TestGoogleDrive{

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Drive";
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
    public void test01CreateFolderGoogleDrive() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("Files")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/extended_fab")).click();

        mDevice.findObject(new UiSelector().text("Folder")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/edit_text")).setText("Test Folder");

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/positive_button")).click();

        new UiScrollable(new UiSelector().resourceId("com.google.android.apps.docs:id/doclist_container")).scrollIntoView(new UiSelector().text("Test Folder"));

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Test Folder"));

    }

    @Test
    public void test02DeleteFolderGoogleDrive() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("Files")).click();

        mDevice.findObject(new UiSelector().description("More actions for Test Folder")).click();

        new UiScrollable(new UiSelector().resourceId("com.google.android.apps.docs:id/menu_recycler_view")).scrollToEnd(10);

        mDevice.findObject(new UiSelector().text("Remove")).click();

        mDevice.findObject(new UiSelector().text("Move to trash")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Folder moved to trash. "));

    }

    @Test
    public void test03CreateGoogleDoc() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("Files")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/extended_fab")).click();

        mDevice.findObject(new UiSelector().text("Google Docs")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs.editors.docs:id/action_mode_close_button")).click();

        mDevice.findObject(new UiSelector().description("Close")).click();

        new UiScrollable(new UiSelector().resourceId("com.google.android.apps.docs:id/doclist_container")).scrollIntoView(new UiSelector().text("Untitled document"));

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Untitled document"));

    }

    @Test
    public void test04CreateGoogleSheet() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("Files")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/extended_fab")).click();

        mDevice.findObject(new UiSelector().text("Google Sheets")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs.editors.sheets:id/spreadsheet_view")).click();

        mDevice.findObject(new UiSelector().description("Done")).click();

        mDevice.findObject(new UiSelector().description("Close")).click();

        new UiScrollable(new UiSelector().resourceId("com.google.android.apps.docs:id/doclist_container")).scrollIntoView(new UiSelector().text("Untitled spreadsheet"));

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Untitled spreadsheet"));

    }

    @Test
    public void test05CreateGoogleSlide() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("Files")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/extended_fab")).click();

        mDevice.findObject(new UiSelector().text("Google Slides")).click();

        mDevice.findObject(new UiSelector().description("Close")).click();

        mDevice.findObject(new UiSelector().description("Close")).click();

        new UiScrollable(new UiSelector().resourceId("com.google.android.apps.docs:id/doclist_container")).scrollIntoView(new UiSelector().text("Untitled presentation"));

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Untitled presentation"));

    }

    @Test
    public void test06EditGoogleDoc() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("Files")).click();

        mDevice.findObject(new UiSelector().description("More actions for Untitled document")).click();

        new UiScrollable(new UiSelector().resourceId("com.google.android.apps.docs:id/menu_recycler_view")).scrollToEnd(10);

        mDevice.findObject(new UiSelector().text("Rename")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/edit_text")).setText("Docs");

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/positive_button")).click();

        new UiScrollable(new UiSelector().resourceId("com.google.android.apps.docs:id/doclist_container")).scrollIntoView(new UiSelector().text("Docs"));

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Docs"));

    }

    @Test
    public void test07EditGoogleSheet() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("Files")).click();

        mDevice.findObject(new UiSelector().description("More actions for Untitled spreadsheet")).click();

        new UiScrollable(new UiSelector().resourceId("com.google.android.apps.docs:id/menu_recycler_view")).scrollToEnd(10);

        mDevice.findObject(new UiSelector().text("Rename")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/edit_text")).setText("Sheets");

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/positive_button")).click();

        new UiScrollable(new UiSelector().resourceId("com.google.android.apps.docs:id/doclist_container")).scrollIntoView(new UiSelector().text("Sheets"));

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Sheets"));

    }

    @Test
    public void test08EditGoogleSlide() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("Files")).click();

        mDevice.findObject(new UiSelector().description("More actions for Untitled presentation")).click();

        new UiScrollable(new UiSelector().resourceId("com.google.android.apps.docs:id/menu_recycler_view")).scrollToEnd(10);

        mDevice.findObject(new UiSelector().text("Rename")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/edit_text")).setText("Slides");

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/positive_button")).click();

        new UiScrollable(new UiSelector().resourceId("com.google.android.apps.docs:id/doclist_container")).scrollIntoView(new UiSelector().text("Slides"));

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Slides"));

    }

    @Test
    public void test09DeleteGoogleDoc() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("Files")).click();

        mDevice.findObject(new UiSelector().description("More actions for Docs")).click();

        new UiScrollable(new UiSelector().resourceId("com.google.android.apps.docs:id/menu_recycler_view")).scrollToEnd(10);

        mDevice.findObject(new UiSelector().text("Remove")).click();

        mDevice.findObject(new UiSelector().text("Move to trash")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("File moved to trash. "));

    }

    @Test
    public void test10DeleteGoogleSheet() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("Files")).click();

        mDevice.findObject(new UiSelector().description("More actions for Sheets")).click();

        new UiScrollable(new UiSelector().resourceId("com.google.android.apps.docs:id/menu_recycler_view")).scrollToEnd(10);

        mDevice.findObject(new UiSelector().text("Remove")).click();

        mDevice.findObject(new UiSelector().text("Move to trash")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("File moved to trash. "));

    }

    @Test
    public void test11DeleteGoogleSlide() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("Files")).click();

        mDevice.findObject(new UiSelector().description("More actions for Slides")).click();

        new UiScrollable(new UiSelector().resourceId("com.google.android.apps.docs:id/menu_recycler_view")).scrollToEnd(10);

        mDevice.findObject(new UiSelector().text("Remove")).click();

        mDevice.findObject(new UiSelector().text("Move to trash")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("File moved to trash. "));

    }

}