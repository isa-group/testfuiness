package esadrcanfer.us.alumno.autotesting.testUIAutomator;

import android.content.Context;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
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
    public void testTranslate() throws UiObjectNotFoundException {

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

        UiObject text = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.translate:id/lyt_home"));
        text.clickAndWaitForNewWindow();

        UiObject translate = mDevice.findObject(new UiSelector().textContains("English"));
        translate.setText("Can i go to the toilet please?");

        UiObject button = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.translate:id/result_selector"));
        button.clickAndWaitForNewWindow();
    }

    @Test
    public void testChangeLanguage() throws UiObjectNotFoundException {

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

        UiObject click = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.translate:id/picker1"));
        click.click();

        UiObject language = mDevice.findObject(new UiSelector().text("Bosnian"));
        language.click();

        UiObject change = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.translate:id/btn_lang_picker_swap"));
        change.click();

        UiObject option = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.translate:id/touch_to_type_text"));
        option.click();

        UiObject translate = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.translate:id/edit_input"));
        translate.setText("En verano hace calor, en invierno hace frío");

        UiObject button = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.translate:id/result_selector"));
        button.clickAndWaitForNewWindow();
    }

}
