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
public class TestGoogleCalendar {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Calendar";
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

        assertEquals("com.example.calendar", appContext.getPackageName());
    }

    @Test
    public void testCreateEvent() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        // UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true)); // API 27
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false)); // API 28/29
        appViews.scrollIntoView(new UiSelector().text("Calendar"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Calendar"));
        testingApp.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().description("Create new event and more"));
        button.clickAndWaitForNewWindow();
        button.clickAndWaitForNewWindow();

        UiObject title = mDevice.findObject(new UiSelector().text("Add title"));
        title.setText("UI Automator");

        UiObject startDate = mDevice.findObject(new UiSelector().descriptionStartsWith("Start date:"));
        startDate.clickAndWaitForNewWindow();

        UiObject done = mDevice.findObject(new UiSelector().text("OK"));
        UiObject cancel = mDevice.findObject(new UiSelector().text("Cancel"));

        UiObject date1 = mDevice.findObject(new UiSelector().text("10"));
        date1.click();
        done.click();

        UiObject endDate = mDevice.findObject(new UiSelector().descriptionStartsWith("End date:"));
        endDate.clickAndWaitForNewWindow();

        UiObject date2 = mDevice.findObject(new UiSelector().text("10"));
        date2.click();
        done.click();

        UiObject startTime = mDevice.findObject(new UiSelector().descriptionStartsWith("Start time:"));
        startTime.clickAndWaitForNewWindow();

        UiObject time1 = mDevice.findObject(new UiSelector().description("12"));
        UiObject time2 = mDevice.findObject(new UiSelector().description("15"));
        time1.click();
        time2.click();
        done.click();

        UiObject endTime = mDevice.findObject(new UiSelector().descriptionStartsWith("End time:"));
        endTime.clickAndWaitForNewWindow();

        UiObject time3 = mDevice.findObject(new UiSelector().description("12"));
        UiObject time4 = mDevice.findObject(new UiSelector().description("45"));
        time3.click();
        time4.click();
        done.click();

        UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(true));
        scroll.scrollToEnd(10);

        UiObject color = mDevice.findObject(new UiSelector().text("Default color"));
        color.click();

        UiObject select = mDevice.findObject(new UiSelector().text("Blueberry"));
        select.click();

        UiObject description = mDevice.findObject(new UiSelector().text("Add description"));
        description.setText("UI Automator test");

        UiObject sheet = mDevice.findObject(new UiSelector().description("Collapse event sheet"));
        sheet.click();

        UiObject save = mDevice.findObject(new UiSelector().text("Save"));
        save.click();
    }

    @Test
    public void testEditEvent() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        // UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true)); // API 27
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false)); // API 28/29
        appViews.scrollIntoView(new UiSelector().text("Calendar"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Calendar"));
        testingApp.clickAndWaitForNewWindow();

        // UiObject calendar = mDevice.findObject(new UiSelector().resourceId("com.google.android.calendar:id/date_picker_text_view")); // API 27
        UiObject calendar = mDevice.findObject(new UiSelector().resourceId("com.google.android.calendar:id/date_picker_arrow")); // API 29
        calendar.click();

        UiObject day = mDevice.findObject(new UiSelector().className("android.view.View").index(9));
        day.click();

        UiObject event = mDevice.findObject(new UiSelector().descriptionContains("UI Automator"));
        event.click();

        UiObject edit = mDevice.findObject(new UiSelector().description("Edit"));
        edit.click();

        UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(true));
        scroll.scrollToEnd(10);

        UiObject notification = mDevice.findObject(new UiSelector().description("Remove notification"));
        notification.click();

        UiObject location = mDevice.findObject(new UiSelector().text("Add location"));
        location.click();
        location.setText("Avenida Reina Mercedes");

        UiObject select = mDevice.findObject(new UiSelector().text("Avenida Reina Mercedes, Seville"));
        select.click();

        scroll.scrollToEnd(10);

        UiObject attachment = mDevice.findObject(new UiSelector().text("Add attachment"));
        attachment.click();

        UiObject drive = mDevice.findObject(new UiSelector().text("My Drive"));
        drive.click();

        scroll.scrollIntoView(new UiSelector().text("UI Automator"));

        UiObject file = mDevice.findObject(new UiSelector().text("UI Automator"));
        file.click();

        UiObject button = mDevice.findObject(new UiSelector().text("Select"));
        button.click();

        UiObject sheet = mDevice.findObject(new UiSelector().description("Collapse event sheet"));
        sheet.click();

        UiObject save = mDevice.findObject(new UiSelector().text("Save"));
        save.click();
    }

    @Test
    public void testDeleteEvent() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        // UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true)); // API 27
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false)); // API 28/29
        appViews.scrollIntoView(new UiSelector().text("Calendar"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Calendar"));
        testingApp.clickAndWaitForNewWindow();

        UiObject calendar = mDevice.findObject(new UiSelector().resourceId("com.google.android.calendar:id/date_picker_text_view"));
        calendar.click();

        UiObject day = mDevice.findObject(new UiSelector().className("android.view.View").index(9));
        day.click();

        UiObject event = mDevice.findObject(new UiSelector().descriptionContains("UI Automator"));
        event.click();

        UiObject options = mDevice.findObject(new UiSelector().description("More options"));
        options.click();

        UiObject delete = mDevice.findObject(new UiSelector().text("Delete"));
        delete.click();

        UiObject confirm = mDevice.findObject(new UiSelector().text("Delete"));
        confirm.click();
    }

}