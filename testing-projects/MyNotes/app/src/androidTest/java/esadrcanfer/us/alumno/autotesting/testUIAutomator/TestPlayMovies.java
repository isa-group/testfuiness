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
public class TestPlayMovies {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Play Movies";
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
    public void testPlayVideo() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        // UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true)); // API 25 y 27
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));   // API 28 y 29
        appViews.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Play Movies & TV"));
        testingApp.clickAndWaitForNewWindow();

        UiObject release = mDevice.findObject(new UiSelector().text("New release movies"));
        release.click();

        UiObject movie = mDevice.findObject(new UiSelector().resourceId("com.google.android.videos:id/thumbnail_frame"));
        movie.click();

        UiObject play = mDevice.findObject(new UiSelector().text("Trailer"));
        play.click();

    }

    @Test
    public void testSearchVideo() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        // UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true)); // API 25 y 27
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));   // API 28 y 29
        appViews.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Play Movies & TV"));
        testingApp.clickAndWaitForNewWindow();

        UiObject search = mDevice.findObject(new UiSelector().textContains("Search"));
        search.click();

        UiObject movie = mDevice.findObject(new UiSelector().text("Search for movies & TV shows"));
        movie.setText("Megalodon");

        UiObject movie2 = mDevice.findObject(new UiSelector().text("megalodon"));
        movie2.clickAndWaitForNewWindow();

        UiObject select = mDevice.findObject(new UiSelector().text("The Meg"));
        select.clickAndWaitForNewWindow();

    }

}
