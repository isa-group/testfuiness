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
public class TestPlayStore {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Play Store";
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
    public void test1SearchApplication() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        // UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true)); // API 25 y 27
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));   // API 28 y 29
        // appViews.scrollIntoView(new UiSelector().text("Play Store"));
        appViews.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Play Store"));
        testingApp.clickAndWaitForNewWindow();

        UiObject search = mDevice.findObject(new UiSelector().text("Search for apps & games"));
        search.clickAndWaitForNewWindow();

        UiObject search2 = mDevice.findObject(new UiSelector().text("Search for apps & games"));
        search2.setText("Google classroom");

        UiObject app = mDevice.findObject(new UiSelector().text("google classroom"));
        app.clickAndWaitForNewWindow();

        UiObject install = mDevice.findObject(new UiSelector().description("Install"));
        install.clickAndWaitForNewWindow();

    }

    @Test
    public void test2UpdateApplication() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Play Store"));
        testingApp.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(3));
        button.clickAndWaitForNewWindow();

        UiObject option = mDevice.findObject(new UiSelector().text("Manage apps & device"));
        option.clickAndWaitForNewWindow();

        UiObject manage = mDevice.findObject(new UiSelector().text("Manage"));
        manage.clickAndWaitForNewWindow();

        UiObject updates = mDevice.findObject(new UiSelector().text("Updates available"));
        updates.click();

        UiObject application = mDevice.findObject(new UiSelector().className("android.widget.LinearLayout").index(0));
        application.clickAndWaitForNewWindow();

        UiObject update = mDevice.findObject(new UiSelector().text("Update"));
        update.click();

    }

    @Test
    public void test3InstallApplication() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        // UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true)); // API 25 y 27
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));   // API 28 y 29
        // appViews.scrollIntoView(new UiSelector().text("Play Store"));
        appViews.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Play Store"));
        testingApp.clickAndWaitForNewWindow();

        UiObject appsSection = mDevice.findObject(new UiSelector().text("Apps"));
        appsSection.clickAndWaitForNewWindow();

        UiObject recommended = mDevice.findObject(new UiSelector().text("Recommended for you"));
        recommended.clickAndWaitForNewWindow();

        UiObject application = mDevice.findObject(new UiSelector().resourceId("com.android.vending:id/mini_blurb").index(1));
        application.clickAndWaitForNewWindow();

        UiObject install = mDevice.findObject(new UiSelector().text("Install"));
        install.click();

    }

    @Test
    public void test4UninstallApplication() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));   // API 28 y 29
        appViews.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Play Store"));
        testingApp.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(3));
        button.clickAndWaitForNewWindow();

        UiObject option = mDevice.findObject(new UiSelector().text("Manage apps & device"));
        option.clickAndWaitForNewWindow();

        UiObject manage = mDevice.findObject(new UiSelector().text("Manage"));
        manage.clickAndWaitForNewWindow();

        UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(true)); // API 28/29
        scroll.scrollIntoView(new UiSelector().text("WhatsApp Messenger"));

        UiObject application = mDevice.findObject(new UiSelector().text("WhatsApp Messenger"));
        application.clickAndWaitForNewWindow();

        UiObject uninstall = mDevice.findObject(new UiSelector().text("Uninstall"));
        uninstall.clickAndWaitForNewWindow();

        UiObject confirm = mDevice.findObject(new UiSelector().className("android.widget.Button").index(1));
        confirm.clickAndWaitForNewWindow();

    }

    @Test
    public void test5SearchGender() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Play Store"));
        testingApp.clickAndWaitForNewWindow();

        UiObject books = mDevice.findObject(new UiSelector().text("Books"));
        books.clickAndWaitForNewWindow();

        UiObject genres = mDevice.findObject(new UiSelector().text("Genres"));
        genres.click();

        UiObject artAndEntertainment = mDevice.findObject(new UiSelector().text("Arts & entertainment"));
        artAndEntertainment.clickAndWaitForNewWindow();

        UiObject book = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(4));
        book.clickAndWaitForNewWindow();

    }

}