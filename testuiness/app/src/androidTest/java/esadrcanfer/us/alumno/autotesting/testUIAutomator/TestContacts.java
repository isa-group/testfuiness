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
import static org.junit.Assert.assertTrue;

import static esadrcanfer.us.alumno.autotesting.tests.AutomaticRepairTests.labelsDetection;

import java.util.List;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestContacts{

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Contacts";
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
    public void test1CreateContact() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        List<String> initialState = labelsDetection();
        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Contacts"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Contacts"));
        testingApp.clickAndWaitForNewWindow();

        mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/floating_action_button")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/expansion_view")).click();

        mDevice.findObject(new UiSelector().text("First name")).setText("Alejandro");

        mDevice.findObject(new UiSelector().text("Middle name")).setText("Garcia");

        mDevice.pressBack();

        mDevice.findObject(new UiSelector().text("Last name")).setText("Fernandez");

        mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/expansion_view")).click();

        mDevice.findObject(new UiSelector().text("Phone")).setText("640936528");

        mDevice.findObject(new UiSelector().text("Email")).setText("yalejandro9@gmail.com");

        mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/editor_menu_save_button")).click();

        List<String> finalState = labelsDetection();

        finalState.contains("Alejandro Garcia Fernandez");

    }

    @Test
    public void test4DeleteContact() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        List<String> initialState = labelsDetection();
        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Contacts"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Contacts"));
        testingApp.clickAndWaitForNewWindow();

        mDevice.findObject(new UiSelector().text("Alejandro Garcia Fernandez")).click();

        mDevice.findObject(new UiSelector().description("More options")).click();

        mDevice.findObject(new UiSelector().text("Delete")).click();

        mDevice.findObject(new UiSelector().resourceId("android:id/button1")).click();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.size() < initialState.size());

    }

    @Test
    public void test2EditContact() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        List<String> initialState = labelsDetection();
        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Contacts"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Contacts"));
        testingApp.clickAndWaitForNewWindow();

        mDevice.findObject(new UiSelector().text("Alejandro Garcia Fernandez")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/menu_edit")).click();

        new UiScrollable(new UiSelector().resourceId("com.android.contacts:id/contact_editor_fragment")).scrollIntoView(new UiSelector().text("More fields"));

        mDevice.findObject(new UiSelector().text("More fields")).click();

        mDevice.findObject(new UiSelector().text("Company")).setText("Escuela Técnica Superior de Ingeniería Informática");

        new UiScrollable(new UiSelector().resourceId("com.android.contacts:id/contact_editor_fragment")).scrollIntoView(new UiSelector().text("Address"));

        mDevice.findObject(new UiSelector().text("Address")).setText("Avenida Reina Mercedes nº 4");

        new UiScrollable(new UiSelector().resourceId("com.android.contacts:id/contact_editor_fragment")).scrollIntoView(new UiSelector().text("Website"));

        mDevice.findObject(new UiSelector().text("Website")).setText("https://www.informatica.us.es/");

        new UiScrollable(new UiSelector().resourceId("com.android.contacts:id/contact_editor_fragment")).scrollIntoView(new UiSelector().text("Notes"));

        mDevice.findObject(new UiSelector().text("Notes")).setText("Prueba test ficheros");

        mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/editor_menu_save_button")).click();

        List<String> finalState = labelsDetection();

        finalState.contains("Avenida Reina Mercedes nº 4");

    }

    @Test
    public void test3FavoriteContact() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Contacts"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Contacts"));
        testingApp.clickAndWaitForNewWindow();

        List<String> initialState = labelsDetection();

        mDevice.findObject(new UiSelector().text("Alejandro Garcia Fernandez")).click();

        mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/menu_star")).click();

        mDevice.pressBack();

        List<String> finalState = labelsDetection();

        assertTrue(finalState.size() > initialState.size());

    }

}
