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
public class TestPlayBooks{

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Play Books";
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
    public void test1SearchBook() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Play Books"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Play Books"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("Search Play Books")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.books:id/open_search_view_edit_text")).setText("el archivo de las tormentas");

        mDevice.pressEnter();

        new UiScrollable(new UiSelector().resourceId("com.google.android.apps.books:id/search_results_page_swipe_refresh")).scrollIntoView(new UiSelector().text("El Archivo de las Tormentas (Flash Relatos): Una guía de bolsillo para El camino de los reyes y Palabras radiantes"));

        mDevice.findObject(new UiSelector().text("El Archivo de las Tormentas (Flash Relatos): Una guía de bolsillo para El camino de los reyes y Palabras radiantes")).click();

        mDevice.findObject(new UiSelector().text("Get for €0")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Read"));

    }

    @Test
    public void test2SearchGender() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Play Books"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Play Books"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        new UiScrollable(new UiSelector().resourceId("com.google.android.apps.books:id/open_search_view")).scrollIntoView(new UiSelector().text("Genres"));

        mDevice.findObject(new UiSelector().text("Genres")).click();

        mDevice.findObject(new UiSelector().text("Science")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.books:id/card_image_body_button_list_item_root")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.size() > initialState.size());

    }

    @Test
    public void test3DeleteBook() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Play Books"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Play Books"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("Library")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.books:id/overflow_icon_view")).click();

        mDevice.findObject(new UiSelector().text("Remove download")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.books:id/primary_button")).click();

        mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.books:id/overflow_icon_view")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.contains("Download"));

    }
}