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
public class TestGmail {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Gmail";
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

        assertEquals("com.example.gmail", appContext.getPackageName());
    }

    @Test
    public void testSendEmail() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Gmail"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Gmail"));
        testingApp.clickAndWaitForNewWindow();

        UiObject email = mDevice.findObject(new UiSelector().description("Compose"));
        email.clickAndWaitForNewWindow();

        UiObject user = mDevice.findObject(new UiSelector().resourceId("com.google.android.gm:id/to"));
        user.setText("zalo.agui3@gmail.com");

        UiObject subject = mDevice.findObject(new UiSelector().text("Subject"));
        subject.setText("UI Automator");

        UiObject body = mDevice.findObject(new UiSelector().text("Compose email"));
        body.setText("Test probando aplicación de Gmail");

        UiObject button = mDevice.findObject(new UiSelector().description("Send"));
        button.clickAndWaitForNewWindow();

        mDevice.pressHome();
    }

    @Test
    public void testEditDraft() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Gmail"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Gmail"));
        testingApp.clickAndWaitForNewWindow();

        UiObject options = mDevice.findObject(new UiSelector().description("Open navigation drawer"));
        options.clickAndWaitForNewWindow();

        UiObject drafts = mDevice.findObject(new UiSelector().text("Drafts"));
        drafts.clickAndWaitForNewWindow();

        //UiObject email = mDevice.findObject(new UiSelector().descriptionStartsWith(" me, UI Automator, Test probando aplicación de Gmail"));
        UiObject email = mDevice.findObject(new UiSelector().text("Draft"));
        email.clickAndWaitForNewWindow();

        UiObject edit = mDevice.findObject(new UiSelector().description("Edit"));
        edit.clickAndWaitForNewWindow();

        UiObject text = mDevice.findObject(new UiSelector().description("Attach file"));
        text.clickAndWaitForNewWindow();

        UiObject attachment = mDevice.findObject(new UiSelector().text("Attach file"));
        attachment.clickAndWaitForNewWindow();

        UiObject root = mDevice.findObject(new UiSelector().description("Show roots"));
        root.clickAndWaitForNewWindow();

        UiObject drive = mDevice.findObject(new UiSelector().text("Drive"));
        drive.clickAndWaitForNewWindow();

        UiObject drive2 = mDevice.findObject(new UiSelector().text("My Drive"));
        drive2.clickAndWaitForNewWindow();

        UiScrollable app = new UiScrollable(new UiSelector().scrollable(false));
        app.scrollIntoView(new UiSelector().text("SCRUM.png"));

        UiObject file = mDevice.findObject(new UiSelector().text("SCRUM.png"));
        file.clickAndWaitForNewWindow();

        UiObject save = mDevice.findObject(new UiSelector().description("Navigate up"));
        save.clickAndWaitForNewWindow();
    }

    @Test
    public void testDeleteEmail() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Gmail"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Gmail"));
        testingApp.clickAndWaitForNewWindow();

        UiObject options = mDevice.findObject(new UiSelector().description("Open navigation drawer"));
        options.clickAndWaitForNewWindow();

        UiObject sent = mDevice.findObject(new UiSelector().text("Sent"));
        sent.clickAndWaitForNewWindow();

        // UiObject body = mDevice.findObject(new UiSelector().text("me"));
        // UiObject body = mDevice.findObject(new UiSelector().descriptionContains("me"));
        UiObject body = mDevice.findObject(new UiSelector().resourceId("com.google.android.gm:id/viewified_conversation_item_view").index(1));
        body.click();

        UiObject delete = mDevice.findObject(new UiSelector().description("Delete"));
        delete.clickAndWaitForNewWindow();
    }

    @Test
    public void testEmptyTrash() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Gmail"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Gmail"));
        testingApp.clickAndWaitForNewWindow();

        UiObject options = mDevice.findObject(new UiSelector().description("Open navigation drawer"));
        options.click();

        UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(false));
        scroll.scrollIntoView(new UiSelector().text("Trash"));

        UiObject sent = mDevice.findObject(new UiSelector().text("Trash"));
        sent.clickAndWaitForNewWindow();

        // UiObject trash = mDevice.findObject(new UiSelector().resourceId("com.google.android.gm:id/empty_trash_spam_action")); // API 25
        UiObject trash = mDevice.findObject(new UiSelector().text("Empty trash now"));
        trash.clickAndWaitForNewWindow();

        // UiObject confirm = mDevice.findObject(new UiSelector().resourceId("android:id/button1")); // API 25
        UiObject confirm = mDevice.findObject(new UiSelector().text("Empty"));
        confirm.clickAndWaitForNewWindow();
    }

}
