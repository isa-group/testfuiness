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
public class TestGoogleSlides {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Slides";
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
    public void test1CreateGoogleSlide() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

         UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
         appViews.scrollIntoView(new UiSelector().text("Slides"));

        // UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        // appViews.scrollForward();

        // UiScrollable scroll = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));     // API 28
        // UiScrollable scroll = new UiScrollable(new UiSelector().className("androidx.recyclerview.widget.RecyclerView"));  // API 29
        UiScrollable scroll = new UiScrollable(new UiSelector().resourceId("com.google.android.apps.nexuslauncher:id/apps_list_view"));
        scroll.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Slides"));
        testingApp.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().description("New presentation menu"));
        button.click();

        UiObject document = mDevice.findObject(new UiSelector().text("New presentation"));
        document.clickAndWaitForNewWindow();

        UiObject close = mDevice.findObject(new UiSelector().description("Back"));
        close.clickAndWaitForNewWindow(1000);
        close.click();

    }

    @Test
    public void test2RenameGoogleSlide() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        // UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        // appViews.scrollIntoView(new UiSelector().text("Slides"));

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollForward();

        // UiScrollable scroll = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));     // API 28
        // UiScrollable scroll = new UiScrollable(new UiSelector().className("androidx.recyclerview.widget.RecyclerView"));  // API 29
        UiScrollable scroll = new UiScrollable(new UiSelector().resourceId("com.google.android.apps.nexuslauncher:id/apps_list_view"));
        scroll.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Slides"));
        testingApp.clickAndWaitForNewWindow();

        UiObject document = mDevice.findObject(new UiSelector().description("More actions for Untitled presentation"));
        document.click();

        UiScrollable options = new UiScrollable(new UiSelector().scrollable(false));
        options.scrollToEnd(10);

        UiObject rename = mDevice.findObject(new UiSelector().text("Rename"));
        rename.clickAndWaitForNewWindow();

        UiObject name = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs.editors.slides:id/edit_text"));
        name.setText("UI Automator");

        UiObject button = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.docs.editors.slides:id/positive_button"));
        button.clickAndWaitForNewWindow();

    }

    @Test
    public void test4RemoveGoogleSlide() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        // UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        // appViews.scrollIntoView(new UiSelector().text("Slides"));

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollForward();

        // UiScrollable scroll = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));     // API 28
        // UiScrollable scroll = new UiScrollable(new UiSelector().className("androidx.recyclerview.widget.RecyclerView"));  // API 29
        UiScrollable scroll = new UiScrollable(new UiSelector().resourceId("com.google.android.apps.nexuslauncher:id/apps_list_view"));
        scroll.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Slides"));
        testingApp.clickAndWaitForNewWindow();

        UiObject document = mDevice.findObject(new UiSelector().description("More actions for UI Automator"));
        document.click();

        UiScrollable options = new UiScrollable(new UiSelector().scrollable(true));
        options.scrollToEnd(10);

        UiObject remove = mDevice.findObject(new UiSelector().text("Remove"));
        remove.clickAndWaitForNewWindow();

        UiObject confirm = mDevice.findObject(new UiSelector().text("Move to trash"));
        confirm.clickAndWaitForNewWindow();

    }

    @Test
    public void test3SendCopyGoogleSlide() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollForward();

        // UiScrollable scroll = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));     // API 28
        // UiScrollable scroll = new UiScrollable(new UiSelector().className("androidx.recyclerview.widget.RecyclerView"));  // API 29
        UiScrollable scroll = new UiScrollable(new UiSelector().resourceId("com.google.android.apps.nexuslauncher:id/apps_list_view"));
        scroll.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Slides"));
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
        user.setText("yalejandro9@gmail.com");

        UiObject subject = mDevice.findObject(new UiSelector().text("Subject"));
        subject.setText("UI Automator");

        UiObject body = mDevice.findObject(new UiSelector().text("Compose email"));
        body.setText("Test probando aplicación de Gmail");

        UiObject button = mDevice.findObject(new UiSelector().description("Send"));
        button.clickAndWaitForNewWindow();

    }
}
