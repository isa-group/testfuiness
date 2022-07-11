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
public class TestGoogleDrive {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Drive";
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
    public void testCreateFolder() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/branded_fab"));
        button.click();

        UiObject folder = mDevice.findObject(new UiSelector().description("Folder"));
        folder.click();

        UiObject text = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/edit_text"));
        text.setText("Trabajo Fin de Grado 3.0");

        UiObject create = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/positive_button"));
        create.click();
    }

    @Test
    public void testDeleteFolder() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(true));
        scroll.scrollIntoView(new UiSelector().text("Trabajo Fin de Grado 3.0"));

        UiObject folder = mDevice.findObject(new UiSelector().description("More actions for Trabajo Fin de Grado 3.0"));
        folder.click();

        UiScrollable scroll2 = new UiScrollable(new UiSelector().scrollable(false));
        scroll2.scrollToEnd(10);

        UiObject delete = mDevice.findObject(new UiSelector().text("Remove"));
        delete.click();
    }

    @Test
    public void testCreateGoogleDoc() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiScrollable app = new UiScrollable(new UiSelector().scrollable(true));
        app.scrollIntoView(new UiSelector().text("Trabajo Fin de Grado 2.0"));

        UiObject testing = mDevice.findObject(new UiSelector().text("Trabajo Fin de Grado 2.0"));
        testing.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/branded_fab"));
        button.click();

        UiObject document = mDevice.findObject(new UiSelector().description("Google Docs"));
        document.clickAndWaitForNewWindow();

        UiObject save = mDevice.findObject(new UiSelector().description("Done"));
        save.click();

        UiObject close = mDevice.findObject(new UiSelector().description("Navigate up"));
        close.click();

    }

    @Test
    public void testEditGoogleDoc() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiScrollable app = new UiScrollable(new UiSelector().scrollable(true));
        app.scrollIntoView(new UiSelector().text("Trabajo Fin de Grado 2.0"));

        UiObject testing = mDevice.findObject(new UiSelector().text("Trabajo Fin de Grado 2.0"));
        testing.clickAndWaitForNewWindow();

        UiObject option = mDevice.findObject(new UiSelector().description("More actions for Untitled document"));
        option.click();

        UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(false));
        scroll.scrollToEnd(5);

        UiObject document = mDevice.findObject(new UiSelector().text("Rename"));
        document.clickAndWaitForNewWindow();

        UiObject text = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/edit_text"));
        text.setText("UI Automator");

        UiObject rename = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/positive_button"));
        rename.click();

    }

    @Test
    public void testDeleteGoogleDoc() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiScrollable app = new UiScrollable(new UiSelector().scrollable(true));
        app.scrollIntoView(new UiSelector().text("Trabajo Fin de Grado 2.0"));

        UiObject testing = mDevice.findObject(new UiSelector().text("Trabajo Fin de Grado 2.0"));
        testing.clickAndWaitForNewWindow();

        UiObject option = mDevice.findObject(new UiSelector().description("More actions for UI Automator"));
        option.click();

        UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(false));
        scroll.scrollToEnd(10);

        UiObject document = mDevice.findObject(new UiSelector().text("Remove"));
        document.clickAndWaitForNewWindow();

        // UiObject option = mDevice.findObject(new UiSelector().description("UI Automator, Google Docs"));
        // option.longClick();

        // UiObject remove = mDevice.findObject(new UiSelector().description("Remove"));
        //remove.clickAndWaitForNewWindow();

    }

    @Test
    public void testCreateGoogleSheet() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiScrollable app = new UiScrollable(new UiSelector().scrollable(true));
        app.scrollIntoView(new UiSelector().text("Trabajo Fin de Grado 2.0"));

        UiObject testing = mDevice.findObject(new UiSelector().text("Trabajo Fin de Grado 2.0"));
        testing.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/branded_fab"));
        button.click();

        UiObject sheet = mDevice.findObject(new UiSelector().description("Google Sheets"));
        sheet.click();

        UiObject close = mDevice.findObject(new UiSelector().description("Navigate up"));
        close.waitUntilGone(20000);
        close.click();

    }

    @Test
    public void testEditGoogleSheet() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiScrollable app = new UiScrollable(new UiSelector().scrollable(true));
        app.scrollIntoView(new UiSelector().text("Trabajo Fin de Grado 2.0"));

        UiObject testing = mDevice.findObject(new UiSelector().text("Trabajo Fin de Grado 2.0"));
        testing.clickAndWaitForNewWindow();

        UiObject option = mDevice.findObject(new UiSelector().description("More actions for Untitled spreadsheet"));
        option.click();

        UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(false));
        scroll.scrollToEnd(5);

        UiObject document = mDevice.findObject(new UiSelector().text("Rename"));
        document.clickAndWaitForNewWindow();

        UiObject text = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/edit_text"));
        text.setText("UI Automator");

        UiObject rename = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/positive_button"));
        rename.click();

    }

    @Test
    public void testDeleteGoogleSheet() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiScrollable app = new UiScrollable(new UiSelector().scrollable(true));
        app.scrollIntoView(new UiSelector().text("Trabajo Fin de Grado"));

        UiObject testing = mDevice.findObject(new UiSelector().text("Trabajo Fin de Grado 2.0"));
        testing.clickAndWaitForNewWindow();

        UiObject option = mDevice.findObject(new UiSelector().description("UI Automator, Google Sheets"));
        option.longClick();

        UiObject remove = mDevice.findObject(new UiSelector().description("Remove"));
        remove.clickAndWaitForNewWindow();

    }

    @Test
    public void testCreateGoogleSlide() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiScrollable app = new UiScrollable(new UiSelector().scrollable(true));
        app.scrollIntoView(new UiSelector().text("Trabajo Fin de Grado 2.0"));

        UiObject testing = mDevice.findObject(new UiSelector().text("Trabajo Fin de Grado 2.0"));
        testing.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/branded_fab"));
        button.click();

        UiObject document = mDevice.findObject(new UiSelector().description("Google Slides"));
        document.clickAndWaitForNewWindow();

        UiObject close = mDevice.findObject(new UiSelector().description("Navigate up"));
        close.click();

    }

    @Test
    public void testEditGoogleSlide() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiScrollable app = new UiScrollable(new UiSelector().scrollable(true));
        app.scrollIntoView(new UiSelector().text("Trabajo Fin de Grado 2.0"));

        UiObject testing = mDevice.findObject(new UiSelector().text("Trabajo Fin de Grado 2.0"));
        testing.clickAndWaitForNewWindow();

        UiObject option = mDevice.findObject(new UiSelector().description("More actions for Untitled presentation"));
        option.click();

        UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(false));
        scroll.scrollToEnd(5);

        UiObject document = mDevice.findObject(new UiSelector().text("Rename"));
        document.clickAndWaitForNewWindow();

        UiObject text = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/edit_text"));
        text.setText("UI Automator");

        UiObject rename = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/positive_button"));
        rename.click();

    }

    @Test
    public void testDeleteGoogleSlide() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiScrollable app = new UiScrollable(new UiSelector().scrollable(true));
        app.scrollIntoView(new UiSelector().text("Trabajo Fin de Grado 2.0"));

        UiObject testing = mDevice.findObject(new UiSelector().text("Trabajo Fin de Grado 2.0"));
        testing.clickAndWaitForNewWindow();

        UiObject option = mDevice.findObject(new UiSelector().description("UI Automator, Google Slides"));
        option.longClick();

        UiObject remove = mDevice.findObject(new UiSelector().description("Remove"));
        remove.clickAndWaitForNewWindow();

    }
}