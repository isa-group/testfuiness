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
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGoogleTranslate {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Translate";
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

        assertEquals("com.example.translate", appContext.getPackageName());
    }

    @Test
    public void test1Translate() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        // UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true)); // API 25 y 27
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));   // API 28 y 29
        // appViews.scrollIntoView(new UiSelector().text("Translate"));
        // appViews.scrollToEnd(10);
        appViews.scrollForward();

        // UiScrollable scroll = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));   // API 28
        UiScrollable scroll = new UiScrollable(new UiSelector().resourceId("com.google.android.apps.nexuslauncher:id/apps_list_view"));
        scroll.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Translate"));
        testingApp.clickAndWaitForNewWindow();

        UiObject text = mDevice.findObject(new UiSelector().text("Enter text"));
        text.clickAndWaitForNewWindow();
        text.setText("Can i go to the toilet please?");

        mDevice.pressEnter();

        UiObject back = mDevice.findObject(new UiSelector().className("android.widget.ImageButton").index(0));
        back.click();
    }

    @Test
    public void test2ChangeLanguage() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        // UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true)); // API 25 y 27
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));   // API 28 y 29
        // appViews.scrollIntoView(new UiSelector().text("Translate"));
        // appViews.scrollToEnd(10);
        appViews.scrollForward();

        // UiScrollable scroll = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));   // API 28
        UiScrollable scroll = new UiScrollable(new UiSelector().resourceId("com.google.android.apps.nexuslauncher:id/apps_list_view"));
        scroll.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Translate"));
        testingApp.clickAndWaitForNewWindow();

        UiObject click = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.translate:id/language_button_a"));
        click.click();

        UiObject language = mDevice.findObject(new UiSelector().text("Armenian"));
        language.click();

        UiObject change = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.translate:id/swap_languages_button"));
        change.click();

        UiObject text = mDevice.findObject(new UiSelector().text("Enter text"));
        text.clickAndWaitForNewWindow();
        text.setText("En verano hace calor, en invierno hace frío");

        mDevice.pressEnter();
    }

}
