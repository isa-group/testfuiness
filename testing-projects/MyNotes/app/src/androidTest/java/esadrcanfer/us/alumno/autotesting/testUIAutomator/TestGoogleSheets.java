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
public class TestGoogleSheets {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Sheets";
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
    public void testCreateGoogleSheet() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollForward();

        // UiScrollable scroll = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));     // API 28
        // UiScrollable scroll = new UiScrollable(new UiSelector().className("androidx.recyclerview.widget.RecyclerView"));  // API 29
        UiScrollable scroll = new UiScrollable(new UiSelector().resourceId("com.google.android.apps.nexuslauncher:id/apps_list_view"));
        scroll.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Sheets"));
        testingApp.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().description("New spreadsheet menu"));
        button.click();

        UiObject document = mDevice.findObject(new UiSelector().description("New spreadsheet"));
        document.clickAndWaitForNewWindow();

        UiObject close = mDevice.findObject(new UiSelector().description("Navigate up"));
        close.click();

    }

    @Test
    public void testRenameGoogleSheet() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollForward();

        // UiScrollable scroll = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));     // API 28
        // UiScrollable scroll = new UiScrollable(new UiSelector().className("androidx.recyclerview.widget.RecyclerView"));  // API 29
        UiScrollable scroll = new UiScrollable(new UiSelector().resourceId("com.google.android.apps.nexuslauncher:id/apps_list_view"));
        scroll.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Sheets"));
        testingApp.clickAndWaitForNewWindow();

        UiObject document = mDevice.findObject(new UiSelector().description("More actions for Untitled spreadsheet"));
        document.click();

        UiScrollable options = new UiScrollable(new UiSelector().scrollable(false));
        options.scrollToEnd(10);

        UiObject rename = mDevice.findObject(new UiSelector().text("Rename"));
        rename.clickAndWaitForNewWindow();

        UiObject name = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs.editors.sheets:id/edit_text"));
        name.setText("UI Automator");

        UiObject button = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs.editors.sheets:id/positive_button"));
        button.clickAndWaitForNewWindow();

    }

    @Test
    public void testRemoveGoogleSheet() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollForward();

        // UiScrollable scroll = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));     // API 28
        // UiScrollable scroll = new UiScrollable(new UiSelector().className("androidx.recyclerview.widget.RecyclerView"));  // API 29
        UiScrollable scroll = new UiScrollable(new UiSelector().resourceId("com.google.android.apps.nexuslauncher:id/apps_list_view"));
        scroll.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Sheets"));
        testingApp.clickAndWaitForNewWindow();

        UiObject document = mDevice.findObject(new UiSelector().description("More actions for UI Automator"));
        document.click();

        UiScrollable options = new UiScrollable(new UiSelector().scrollable(false));
        options.scrollToEnd(10);

        UiObject remove = mDevice.findObject(new UiSelector().text("Remove"));
        remove.clickAndWaitForNewWindow();

    }

    @Test
    public void testSendCopyGoogleSheet() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollForward();

        // UiScrollable scroll = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));     // API 28
        // UiScrollable scroll = new UiScrollable(new UiSelector().className("androidx.recyclerview.widget.RecyclerView"));  // API 29
        UiScrollable scroll = new UiScrollable(new UiSelector().resourceId("com.google.android.apps.nexuslauncher:id/apps_list_view"));
        scroll.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Sheets"));
        testingApp.clickAndWaitForNewWindow();

        UiObject document = mDevice.findObject(new UiSelector().description("More actions for UI Automator"));
        document.click();

        UiScrollable options = new UiScrollable(new UiSelector().scrollable(false));
        options.scrollIntoView(new UiSelector().text("Send a copy"));

        UiObject copy = mDevice.findObject(new UiSelector().text("Send a copy"));
        copy.clickAndWaitForNewWindow();

        UiObject pdf = mDevice.findObject(new UiSelector().text("PDF Document (.pdf)"));
        pdf.clickAndWaitForNewWindow();

        UiObject ok = mDevice.findObject(new UiSelector().text("OK"));
        ok.clickAndWaitForNewWindow();

        UiObject gmail = mDevice.findObject(new UiSelector().textContains("Gmail"));
        gmail.clickAndWaitForNewWindow();

        UiObject gmail2 = mDevice.findObject(new UiSelector().text("JUST ONCE"));
        gmail2.clickAndWaitForNewWindow();

        UiObject user = mDevice.findObject(new UiSelector().resourceId("com.google.android.gm:id/to"));
        user.setText("zalo.agui3@gmail.com");

        UiObject subject = mDevice.findObject(new UiSelector().text("Subject"));
        subject.setText("UI Automator");

        UiObject body = mDevice.findObject(new UiSelector().text("Compose email"));
        body.setText("Test probando aplicación de Gmail");

        UiObject button = mDevice.findObject(new UiSelector().description("Send"));
        button.clickAndWaitForNewWindow();

    }

}
