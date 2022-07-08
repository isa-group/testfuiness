package esadrcanfer.us.alumno.autotesting.testUIAutomator;

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
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    public void test01CreateFolder() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/extended_fab"));
        button.click();

        UiObject folder = mDevice.findObject(new UiSelector().description("Folder"));
        folder.click();

        UiObject text = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/edit_text"));
        text.setText("UI Automator Folder");

        UiObject create = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/positive_button"));
        create.click();
    }

    @Test
    public void test02DeleteFolder() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(true));
        scroll.scrollIntoView(new UiSelector().text("UI Automator"));

        UiObject folder = mDevice.findObject(new UiSelector().description("More actions for UI Automator Folder"));
        folder.click();

        UiScrollable scroll2 = new UiScrollable(new UiSelector().scrollable(false));
        scroll2.scrollToEnd(10);

        UiObject delete = mDevice.findObject(new UiSelector().text("Remove"));
        delete.click();

        UiObject confirm = mDevice.findObject(new UiSelector().text("Move to trash"));
        confirm.click();
    }

    @Test
    public void test03CreateGoogleDoc() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiScrollable app = new UiScrollable(new UiSelector().scrollable(true));
        app.scrollIntoView(new UiSelector().text("UI Automator Tests"));

        UiObject testing = mDevice.findObject(new UiSelector().text("UI Automator Tests"));
        testing.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/extended_fab"));
        button.click();

        UiObject document = mDevice.findObject(new UiSelector().description("Google Docs"));
        document.clickAndWaitForNewWindow();

        UiObject save = mDevice.findObject(new UiSelector().description("Done"));
        save.click();

        UiObject close = mDevice.findObject(new UiSelector().description("Close"));
        close.click();

    }

    @Test
    public void test04EditGoogleDoc() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiObject option = mDevice.findObject(new UiSelector().description("More actions for Untitled document"));
        option.click();

        UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(false));
        scroll.scrollToEnd(5);

        UiObject document = mDevice.findObject(new UiSelector().text("Rename"));
        document.clickAndWaitForNewWindow();

        UiObject text = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/edit_text"));
        text.setText("UI Automator New Doc");

        UiObject rename = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/positive_button"));
        rename.click();

    }

    @Test
    public void test05DeleteGoogleDoc() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiObject option = mDevice.findObject(new UiSelector().description("More actions for UI Automator New Doc"));
        option.click();

        UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(false));
        scroll.scrollToEnd(10);

        UiObject document = mDevice.findObject(new UiSelector().text("Remove"));
        document.clickAndWaitForNewWindow();

        UiObject confirm = mDevice.findObject(new UiSelector().text("Move to trash"));
        confirm.click();

    }

    @Test
    public void test06CreateGoogleSheet() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/extended_fab"));
        button.click();

        UiObject sheet = mDevice.findObject(new UiSelector().description("Google Sheets"));
        sheet.clickAndWaitForNewWindow();

        UiObject close = mDevice.findObject(new UiSelector().description("Close"));
        close.click();

    }

    @Test
    public void test07EditGoogleSheet() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiObject option = mDevice.findObject(new UiSelector().description("More actions for Untitled spreadsheet"));
        option.click();

        UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(false));
        scroll.scrollToEnd(5);

        UiObject document = mDevice.findObject(new UiSelector().text("Rename"));
        document.clickAndWaitForNewWindow();

        UiObject text = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/edit_text"));
        text.setText("UI Automator Sheet");

        UiObject rename = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/positive_button"));
        rename.click();

    }

    @Test
    public void test08DeleteGoogleSheet() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiObject option = mDevice.findObject(new UiSelector().description("More actions for UI Automator Sheet"));
        option.click();

        UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(true));
        scroll.scrollToEnd(10);

        UiObject remove = mDevice.findObject(new UiSelector().text("Remove"));
        remove.clickAndWaitForNewWindow();

        UiObject confirm = mDevice.findObject(new UiSelector().text("Move to trash"));
        confirm.click();

    }

    @Test
    public void test09CreateGoogleSlide() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/extended_fab"));
        button.click();

        UiObject document = mDevice.findObject(new UiSelector().description("Google Slides"));
        document.clickAndWaitForNewWindow();

        UiObject close = mDevice.findObject(new UiSelector().description("Close"));
        close.click();

    }

    @Test
    public void test10EditGoogleSlide() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiObject option = mDevice.findObject(new UiSelector().description("More actions for Untitled presentation"));
        option.click();

        UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(true));
        scroll.scrollToEnd(5);

        UiObject document = mDevice.findObject(new UiSelector().text("Rename"));
        document.clickAndWaitForNewWindow();

        UiObject text = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/edit_text"));
        text.setText("UI Automator Slide");

        UiObject rename = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs:id/positive_button"));
        rename.click();

    }

    @Test
    public void test11DeleteGoogleSlide() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Drive"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Drive"));
        testingApp.clickAndWaitForNewWindow();

        UiObject option = mDevice.findObject(new UiSelector().description("More actions for UI Automator Slide"));
        option.click();

        UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(true));
        scroll.scrollToEnd(10);

        UiObject remove = mDevice.findObject(new UiSelector().text("Remove"));
        remove.clickAndWaitForNewWindow();

        UiObject confirm = mDevice.findObject(new UiSelector().text("Move to trash"));
        confirm.click();
    }
}