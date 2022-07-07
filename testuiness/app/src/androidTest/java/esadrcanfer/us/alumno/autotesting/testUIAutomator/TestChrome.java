package esadrcanfer.us.alumno.autotesting.testUIAutomator;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
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
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class TestChrome {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Chrome";
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

    @Test
    public void testSearchGoogleChrome() throws UiObjectNotFoundException {

        //useAppContext();

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Chrome"));
        testingApp.clickAndWaitForNewWindow();

        UiObject search = mDevice.findObject(new UiSelector().resourceId("com.android.chrome:id/search_box_text"));
        search.click();

        UiObject text = mDevice.findObject(new UiSelector().resourceId("com.android.chrome:id/url_bar"));
        text.setText("UI Automator");

        // UiObject option = mDevice.findObject(new UiSelector().text("UI Automator"));
        // UiObject option = mDevice.findObject(new UiSelector().className("android.widget.TextView").index(0));
        mDevice.pressEnter();

        UiObject select = mDevice.findObject(new UiSelector().index(10));
        select.click();
    }

    @Test
    public void testShareImageGoogleChrome() throws UiObjectNotFoundException {

        //useAppContext();

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Chrome"));
        testingApp.clickAndWaitForNewWindow();

        // NOTE: IF YOU WANT TO LAUNCH THIS TEST INDIVIDUALLY, PLEASE, REMOVE THE COMMENTS BELLOW

        // UiObject search = mDevice.findObject(new UiSelector().resourceId("com.android.chrome:id/search_box_text"));
        // search.click();

        UiObject text = mDevice.findObject(new UiSelector().resourceId("com.android.chrome:id/url_bar"));
        text.click();
        text.setText("SCRUM");

        mDevice.pressEnter();

        UiObject images = mDevice.findObject(new UiSelector().text("Imágenes"));
        images.clickAndWaitForNewWindow();

        // UiObject image = mDevice.findObject(new UiSelector().text("Image result for SCRUM"));
        UiObject image = mDevice.findObject(new UiSelector().text("What is Scrum?"));
        image.clickAndWaitForNewWindow();

        UiObject moreOptionsBtn = mDevice.findObject(new UiSelector().text("More actions for this image"));
        moreOptionsBtn.click();

        UiObject share = mDevice.findObject(new UiSelector().text("Share"));
        share.clickAndWaitForNewWindow();

        UiObject drive = mDevice.findObject(new UiSelector().text("Save to Drive"));
        drive.clickAndWaitForNewWindow();

        UiObject title = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/upload_edittext_document_title"));
        title.setText("Imagen SCRUM");

        UiObject save = mDevice.findObject(new UiSelector().text("SAVE"));
        save.clickAndWaitForNewWindow();
    }

    @Test
    public void testClearHistoryGoogleChrome() throws UiObjectNotFoundException {

        //useAppContext();

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Chrome"));
        testingApp.clickAndWaitForNewWindow();

        UiObject tabSwitcherButton = mDevice.findObject(new UiSelector().resourceId("com.android.chrome:id/tab_switcher_button"));
        tabSwitcherButton.click();

        UiObject newTabButton = mDevice.findObject(new UiSelector().resourceId("com.android.chrome:id/new_tab_button"));
        newTabButton.click();

        UiObject options = mDevice.findObject(new UiSelector().resourceId("com.android.chrome:id/menu_button"));
        options.click();

        UiObject history = mDevice.findObject(new UiSelector().text("History"));
        history.click();

        try {
            UiObject clear = mDevice.findObject(new UiSelector().text("CLEAR BROWSING DATA…"));
            clear.click();

            UiObject button = mDevice.findObject(new UiSelector().text("CLEAR DATA"));
            button.click();

            UiObject confirm = mDevice.findObject(new UiSelector().text("CLEAR"));
            confirm.click();
        }catch(Exception e){
            UiObject noHistory = mDevice.findObject(new UiSelector().text("No history here"));
            assertNotNull(noHistory);
        }
    }

    private static void useAppContext() {
        // Context of the app under test.
        Context appContext = getInstrumentation().getTargetContext();

        assertEquals("com.example.chrome", appContext.getPackageName());
    }
}
