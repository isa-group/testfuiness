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
public class TestPlayBooks {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Play Books";
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
    public void testSearchGender() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        // UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true)); // API 25 y 27
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));   // API 28 y 29
        appViews.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Play Books"));
        testingApp.clickAndWaitForNewWindow();

        UiObject genres = mDevice.findObject(new UiSelector().text("Genres"));
        genres.click();

        // UiObject science = mDevice.findObject(new UiSelector().text("Science & technology"));
        UiObject science = mDevice.findObject(new UiSelector().textContains("Science"));
        science.click();

        UiObject select = mDevice.findObject(new UiSelector().className("android.widget.RelativeLayout").index(2));
        select.click();

        // UiObject book = mDevice.findObject(new UiSelector().text("Preview"));
        // UiObject book = mDevice.findObject(new UiSelector().text("Free sample"));
        UiObject book = mDevice.findObject(new UiSelector().resourceId("com.android.vending:id/left_button"));
        book.click();

    }

    @Test
    public void testSearchBook() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        // UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true)); // API 25 y 27
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));   // API 28 y 29
        appViews.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Play Books"));
        testingApp.clickAndWaitForNewWindow();

        UiObject search = mDevice.findObject(new UiSelector().text("Search Play Books"));
        search.click();

        UiObject search2 = mDevice.findObject(new UiSelector().text("Search Play Books"));
        search2.setText("el archivo de las tormentas");

        UiObject book = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.books:id/replay__listitem__body"));
        book.click();

        UiObject book2 = mDevice.findObject(new UiSelector().textContains("El Archivo de las Tormentas"));
        book2.click();

    }

    @Test
    public void testDeleteBook() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        // UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true)); // API 25 y 27
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));   // API 28 y 29
        appViews.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Play Books"));
        testingApp.clickAndWaitForNewWindow();

        UiObject options = mDevice.findObject(new UiSelector().descriptionContains("Options for"));
        options.click();

        UiObject remove = mDevice.findObject(new UiSelector().text("Remove download"));
        remove.click();

        UiObject confirm = mDevice.findObject(new UiSelector().text("Remove"));
        confirm.click();

    }

}