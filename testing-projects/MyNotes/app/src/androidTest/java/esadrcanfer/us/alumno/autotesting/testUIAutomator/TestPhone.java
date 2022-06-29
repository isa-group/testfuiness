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
public class TestPhone {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Phone";
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

    // UiObject numero0 = mDevice.findObject(new UiSelector().text("0"));
    // UiObject numero1 = mDevice.findObject(new UiSelector().text("1"));
    // UiObject numero2 = mDevice.findObject(new UiSelector().text("2"));
    // UiObject numero3 = mDevice.findObject(new UiSelector().text("3"));
    // UiObject numero4 = mDevice.findObject(new UiSelector().text("4"));
    // UiObject numero5 = mDevice.findObject(new UiSelector().text("5"));
    // UiObject numero6 = mDevice.findObject(new UiSelector().text("6"));
    // UiObject numero7 = mDevice.findObject(new UiSelector().text("7"));
    // UiObject numero8 = mDevice.findObject(new UiSelector().text("8"));
    // UiObject numero9 = mDevice.findObject(new UiSelector().text("9"));

    @Test
    public void testCallPhone() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Phone"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Phone"));
        testingApp.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().description("key pad")); // API 27, 28, 29
        //UiObject button = mDevice.findObject(new UiSelector().description("dial pad")); // API 25
        button.click();

        UiObject numero2 = mDevice.findObject(new UiSelector().text("2"));
        UiObject numero3 = mDevice.findObject(new UiSelector().text("3"));
        UiObject numero5 = mDevice.findObject(new UiSelector().text("5"));
        UiObject numero6 = mDevice.findObject(new UiSelector().text("6"));
        UiObject numero9 = mDevice.findObject(new UiSelector().text("9"));

        numero6.click();
        numero9.click();
        numero2.click();

        numero5.click();
        numero2.click();

        numero5.click();
        numero3.click();

        numero5.click();
        numero5.click();

        UiObject call = mDevice.findObject(new UiSelector().description("dial"));
        call.clickAndWaitForNewWindow();

        UiObject end = mDevice.findObject(new UiSelector().description("End call")); // API 28, 29
        end.waitUntilGone(20000);
        end.clickAndWaitForNewWindow();

    }

    @Test
    public void testCallContact() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Phone"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Phone"));
        testingApp.clickAndWaitForNewWindow();

        // UiObject button = mDevice.findObject(new UiSelector().descriptionStartsWith("Contacts")); // API 25, 27
        UiObject button = mDevice.findObject(new UiSelector().text("Contacts")); // API 28, 29
        button.click();

        UiObject contact = mDevice.findObject(new UiSelector().text("Gonzalo Aguilar Hermoso"));
        contact.click();

        UiObject call = mDevice.findObject(new UiSelector().text("(654) 123-987"));
        call.click();

        UiObject end = mDevice.findObject(new UiSelector().description("End call"));
        end.waitUntilGone(20000);
        end.click();
    }

    @Test
    public void testClearHistory() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Phone"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Phone"));
        testingApp.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().description("More options"));
        button.click();

        UiObject option = mDevice.findObject(new UiSelector().textStartsWith("Call"));
        option.click();

        button.click();

        UiObject clear = mDevice.findObject(new UiSelector().text("Clear call history"));
        clear.click();

        UiObject confirm = mDevice.findObject(new UiSelector().text("OK"));
        confirm.click();
    }

    @Test
    public void testAddFavorite() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Phone"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Phone"));
        testingApp.clickAndWaitForNewWindow();

        // UiObject button = mDevice.findObject(new UiSelector().descriptionStartsWith("Contacts")); // API 25, 27
        UiObject button = mDevice.findObject(new UiSelector().text("Contacts")); // API 28, 29
        button.click();

        UiObject contact = mDevice.findObject(new UiSelector().text("Gonzalo Aguilar Hermoso"));
        contact.click();

        UiObject favorite = mDevice.findObject(new UiSelector().description("Add to favorites"));
        favorite.click();
    }

    @Test
    public void testDeleteFavorite() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Phone"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Phone"));
        testingApp.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().text("Favorites"));
        button.click();

        UiObject option = mDevice.findObject(new UiSelector().description("View contact"));
        option.click();

        UiObject favorite = mDevice.findObject(new UiSelector().description("Remove from favorites"));
        favorite.click();
    }
}
