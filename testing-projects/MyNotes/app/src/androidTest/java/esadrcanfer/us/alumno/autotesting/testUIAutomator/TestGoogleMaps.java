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
public class TestGoogleMaps {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Maps";
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
    public void testSearchGoogleMaps() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Maps"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Maps"));
        testingApp.clickAndWaitForNewWindow();

        UiObject search = mDevice.findObject(new UiSelector().text("Search here"));
        search.clickAndWaitForNewWindow();
        search.setText("Reina Mercedes");

        UiObject location = mDevice.findObject(new UiSelector().text("Avenida de la Reina Mercedes"));
        location.clickAndWaitForNewWindow();
    }

    @Test
    public void testShareLocation() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Maps"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Maps"));
        testingApp.clickAndWaitForNewWindow();

        UiObject search = mDevice.findObject(new UiSelector().text("Search here"));
        search.clickAndWaitForNewWindow();
        search.setText("Reina Mercedes");

        UiObject location = mDevice.findObject(new UiSelector().text("Avenida de la Reina Mercedes"));
        location.clickAndWaitForNewWindow();

        UiObject share = mDevice.findObject(new UiSelector().text("Share"));
        share.clickAndWaitForNewWindow();

        UiObject message = mDevice.findObject(new UiSelector().text("Messages"));
        message.clickAndWaitForNewWindow();

        UiObject share2 = mDevice.findObject(new UiSelector().text("Continue"));
        share2.clickAndWaitForNewWindow();

        // UiObject contact = mDevice.findObject(new UiSelector().text("Gonzalo Aguilar Hermoso"));
        UiObject contact = mDevice.findObject(new UiSelector().text("654123987"));
        contact.clickAndWaitForNewWindow();

        UiObject send = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.messaging:id/send_message_button_icon"));
        send.clickAndWaitForNewWindow();

    }

    @Test
    public void testJourney() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Maps"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Maps"));
        testingApp.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().text("GO"));
        button.clickAndWaitForNewWindow();

        UiObject location = mDevice.findObject(new UiSelector().text("Your location"));
        location.clickAndWaitForNewWindow();

        UiObject startingPoint = mDevice.findObject(new UiSelector().text("Choose starting point"));
        startingPoint.setText("Centro Comercial Lagoh Sevilla");

        UiObject start = mDevice.findObject(new UiSelector().text("Avenida de Palmas Altas, Seville, Spain"));
        start.clickAndWaitForNewWindow();

        UiObject destination = mDevice.findObject(new UiSelector().text("Choose destination"));
        destination.clickAndWaitForNewWindow();
        destination.setText("Plaza Colón");

        UiObject end = mDevice.findObject(new UiSelector().text("Plaza de Colón, Madrid, Spain"));
        end.clickAndWaitForNewWindow();
    }

    @Test
    public void testHotels() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Maps"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Maps"));
        testingApp.clickAndWaitForNewWindow();

        UiScrollable filter = new UiScrollable(new UiSelector().scrollable(false));
        filter.scrollForward();

        UiObject hotels = mDevice.findObject(new UiSelector().text("Hotels"));
        hotels.clickAndWaitForNewWindow();

        UiObject configuration = mDevice.findObject(new UiSelector().description("More filters"));
        configuration.clickAndWaitForNewWindow();

        UiObject checkIn = mDevice.findObject(new UiSelector().text("Check in"));
        checkIn.clickAndWaitForNewWindow();

        UiObject startDate = mDevice.findObject(new UiSelector().text("20"));
        UiObject endDate = mDevice.findObject(new UiSelector().text("25"));

        UiObject done = mDevice.findObject(new UiSelector().text("Done"));
        UiObject cancel = mDevice.findObject(new UiSelector().text("Cancel"));

        if (startDate.isEnabled()) {
            startDate.click();
            done.click();
        } else {
            cancel.click();
        }

        UiObject checkOut = mDevice.findObject(new UiSelector().text("Check out"));
        checkOut.clickAndWaitForNewWindow();

        if (endDate.isEnabled()) {
            endDate.click();
            done.click();
        } else {
            cancel.click();
        }

        UiObject price = mDevice.findObject(new UiSelector().text("Lowest price"));
        price.click();

        UiObject rating = mDevice.findObject(new UiSelector().text("4.5"));
        rating.click();

        UiObject star = mDevice.findObject(new UiSelector().text("4-star"));
        star.click();

        UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(true));
        scroll.scrollToEnd(10);

        UiObject pool = mDevice.findObject(new UiSelector().text("Pool"));
        pool.click();

        UiObject bar = mDevice.findObject(new UiSelector().text("Bar"));
        bar.click();

        UiObject restaurant = mDevice.findObject(new UiSelector().text("Restaurant"));
        restaurant.click();

        UiObject button = mDevice.findObject(new UiSelector().text("Apply"));
        button.clickAndWaitForNewWindow();

    }

    @Test
    public void testRestaurants() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));
        appViews.scrollIntoView(new UiSelector().text("Maps"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Maps"));
        testingApp.clickAndWaitForNewWindow();

        UiObject restaurants = mDevice.findObject(new UiSelector().text("Restaurants"));
        restaurants.clickAndWaitForNewWindow();

        UiObject configuration = mDevice.findObject(new UiSelector().description("More filters"));
        configuration.clickAndWaitForNewWindow();

        UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(true));
        scroll.scrollToBeginning(1);

        UiObject money = mDevice.findObject(new UiSelector().text("$$$$"));
        money.click();

        UiObject rating = mDevice.findObject(new UiSelector().text("4.5"));
        rating.click();

        UiObject hours = mDevice.findObject(new UiSelector().text("Custom"));
        hours.click();

        // UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(false));
        scroll.scrollToEnd(1);

        UiObject cuisine = mDevice.findObject(new UiSelector().text("Hamburger"));
        cuisine.click();

        UiObject button = mDevice.findObject(new UiSelector().text("Apply"));
        button.clickAndWaitForNewWindow();

    }

}
