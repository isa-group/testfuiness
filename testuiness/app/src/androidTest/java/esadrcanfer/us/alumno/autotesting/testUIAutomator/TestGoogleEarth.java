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
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class TestGoogleEarth {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Earth";
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
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = getInstrumentation().getTargetContext();

        assertEquals("com.example.earth", appContext.getPackageName());
    }

    // UiObject voyager = mDevice.findObject(new UiSelector().description("Voyager"));
    // UiObject lucky   = mDevice.findObject(new UiSelector().description("I'm Feeling Lucky"));
    // UiObject measure = mDevice.findObject(new UiSelector().description("Measure"));
    // UiObject options = mDevice.findObject(new UiSelector().description("More options"));
    // UiObject search  = mDevice.findObject(new UiSelector().description("Search"));
    // UiObject menu    = mDevice.findObject(new UiSelector().description("Open navigation drawer"));

    @Test
    public void testSearchLocation() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Earth"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Earth"));
        testingApp.clickAndWaitForNewWindow();

        UiObject menu = mDevice.findObject(new UiSelector().description("Open navigation drawer"));
        menu.click();

        // UiObject button = mDevice.findObject(new UiSelector().resourceId("com.google.earth:id/toolbar_search"));
        UiObject button = mDevice.findObject(new UiSelector().text("Search"));
        button.click();

        // UiObject location = mDevice.findObject(new UiSelector().text("Search Google Earth"));
        UiObject location = mDevice.findObject(new UiSelector().textContains("Search"));
        location.setText("Avenida Reina Mercedes");

        // UiObject option = mDevice.findObject(new UiSelector().text("Avenida Reina Mercedes, Seville, Spain"));
        UiObject option = mDevice.findObject(new UiSelector().text("Avenida Reina Mercedes, Seville"));
        option.click();

        UiObject select = mDevice.findObject(new UiSelector().description("Fly here"));
        select.click();
    }

    // UiObject editor = mDevice.findObject(new UiSelector().text("Editor's Picks"));
    // UiObject games  = mDevice.findObject(new UiSelector().text("Games"));
    // UiObject layers = mDevice.findObject(new UiSelector().text("Layers"));
    // UiObject nature = mDevice.findObject(new UiSelector().text("Nature"));
    // UiObject street = mDevice.findObject(new UiSelector().text("Street View"));

    // UiObject park1 = mDevice.findObject(new UiSelector().text("Visit Zion National Park"));
    // UiObject park2 = mDevice.findObject(new UiSelector().text("Visit Yosemite National Park"));
    // UiObject park3 = mDevice.findObject(new UiSelector().text("Visit Yellowstone National Park"));
    // UiObject park4 = mDevice.findObject(new UiSelector().text("Visit Sequoia National Park"));

    @Test
    public void testVoyager() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Earth"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Earth"));
        testingApp.click();

        UiObject menu = mDevice.findObject(new UiSelector().description("Open navigation drawer"));
        menu.click();

        // UiObject button = mDevice.findObject(new UiSelector().resourceId("com.google.earth:id/toolbar_feed"));
        UiObject button = mDevice.findObject(new UiSelector().text("Voyager"));
        button.click();

        UiObject option = mDevice.findObject(new UiSelector().text("Nature"));
        option.click();

        UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(false));
        scroll.scrollIntoView(new UiSelector().text("Visit Yellowstone National Park"));

        UiObject select = mDevice.findObject(new UiSelector().text("Visit Yellowstone National Park"));
        select.click();

        UiObject go = mDevice.findObject(new UiSelector().text("LET'S GO"));
        go.click();

    }

    @Test
    public void testLuck() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Earth"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Earth"));
        testingApp.clickAndWaitForNewWindow();

        // UiObject button = mDevice.findObject(new UiSelector().resourceId("com.google.earth:id/toolbar_feeling_lucky"));
        UiObject button = mDevice.findObject(new UiSelector().description("I'm Feeling Lucky"));
        button.click();

        UiObject options = mDevice.findObject(new UiSelector().description("More options"));
        options.click();

        UiObject share = mDevice.findObject(new UiSelector().text("Share link"));
        share.click();

        UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(false));
        scroll.scrollIntoView(new UiSelector().text("Keep Notes"));

        UiObject keep = mDevice.findObject(new UiSelector().text("Keep Notes"));
        keep.click();

        UiObject save = mDevice.findObject(new UiSelector().text("Save"));
        save.click();
    }

}