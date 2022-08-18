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
public class TestGmail{

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Gmail";
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
    public void test1SendEmail() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Gmail"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Gmail"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.gm:id/compose_button")).click();

        mDevice.findObject(new UiSelector().className("android.widget.EditText").index(0)).setText("alegarfer4@alum.us.es");

        mDevice.pressEnter();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.gm:id/subject")).setText("Test ficheros");

        mDevice.findObject(new UiSelector().text("Compose email")).setText("Test probando aplicacion de Gmail");

        mDevice.findObject(new UiSelector().resourceId("com.google.android.gm:id/send")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Sent"));

    }

    @Test
    public void test2EditDraft() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Gmail"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Gmail"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().description("Open navigation drawer")).click();

        mDevice.findObject(new UiSelector().text("Drafts")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.gm:id/viewified_conversation_item_view")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.gm:id/edit_draft")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.gm:id/add_attachment")).click();

        mDevice.findObject(new UiSelector().text("Insert from Drive")).click();

        mDevice.findObject(new UiSelector().text("My Drive")).click();

        new UiScrollable(new UiSelector().resourceId("com.google.android.apps.docs:id/doclist_recycler_view")).scrollIntoView(new UiSelector().text(" SCRUM 2.0"));

        mDevice.findObject(new UiSelector().text("SCRUM 2.0")).click();

        mDevice.findObject(new UiSelector().text("Select")).click();

        mDevice.findObject(new UiSelector().description("Navigate up")).click();

        List<String> finalState = labelsDetection();

        assertTrue(!finalState.contains("Compose"));

    }

    @Test
    public void test3DeleteEmail() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Gmail"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Gmail"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().description("Open navigation drawer")).click();

        mDevice.findObject(new UiSelector().text("Sent")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.gm:id/viewified_conversation_item_view")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.gm:id/delete")).click();

        List<String> finalState = labelsDetection();

        finalState.contains("1 deleted");

    }

    @Test
    public void test4EmptyTrash() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Gmail"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Gmail"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().description("Open navigation drawer")).click();

        new UiScrollable(new UiSelector().resourceId("android:id/list")).scrollIntoView(new UiSelector().text("Trash"));

        mDevice.findObject(new UiSelector().text("Trash")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.gm:id/empty_trash_spam_action")).click();

        mDevice.findObject(new UiSelector().resourceId("android:id/button1")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Nothing in Trash"));

    }

}
