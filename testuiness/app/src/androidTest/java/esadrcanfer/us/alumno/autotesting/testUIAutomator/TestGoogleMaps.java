package esadrcanfer.us.alumno.autotesting.testUIAutomator;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.KeyEvent;

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
import static org.junit.Assert.assertTrue;

import static esadrcanfer.us.alumno.autotesting.tests.AutomaticRepairTests.labelsDetection;

import java.util.List;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGoogleMaps{

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Maps";
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {

        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();
        try{
            mDevice.pressKeyCode(KeyEvent.KEYCODE_APP_SWITCH);
            UiScrollable appScroll = new UiScrollable(new UiSelector().resourceId("com.google.android.apps.nexuslauncher:id/workspace"));
            appScroll.swipeRight(5);
            mDevice.findObject(new UiSelector().text("CLEAR ALL")).click();
        }catch(UiObjectNotFoundException e){
            Log.d("ISA", "There are no open apps");
        }

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
    public void test1SearchGoogleMaps() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Maps"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Maps"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.maps:id/search_omnibox_text_box")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.maps:id/search_omnibox_edit_text")).setText("Avenida de la Reina Mercedes");

        mDevice.pressEnter();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Av. de la Reina Mercedes"));

    }

    @Test
    public void test2ShareLocationGoogleMaps() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Maps"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Maps"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.maps:id/search_omnibox_text_box")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.maps:id/search_omnibox_edit_text")).setText("Avenida de la Reina Mercedes");

        mDevice.pressEnter();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.maps:id/place_page_view")).click();

        mDevice.findObject(new UiSelector().text("Share")).click();

        mDevice.findObject(new UiSelector().text("Messages")).click();

        mDevice.findObject(new UiSelector().text("NEW MESSAGE")).click();

        mDevice.findObject(new UiSelector().text("Alejandro Garcia Fernandez")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.messaging:id/send_message_button_icon")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Alejandro Garcia Fernandez"));

    }

    @Test
    public void test3JourneyGoogleMaps() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Maps"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Maps"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().description("Directions")).click();

        mDevice.findObject(new UiSelector().text("Your location")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.maps:id/search_omnibox_edit_text")).setText("Centro Comercial Lagoh Sevilla");

        mDevice.pressEnter();

        mDevice.findObject(new UiSelector().text("Choose destination")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.maps:id/search_omnibox_edit_text")).setText("Plaza Colón");

        mDevice.pressEnter();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.maps:id/start_button")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Route preview"));

    }

}
