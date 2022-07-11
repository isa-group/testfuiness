package esadrcanfer.us.alumno.autotesting.testUIAutomator;

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
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class TestMessage {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "Messages";
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
    public void testSendMessage() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Messages"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Messages"));
        testingApp.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.messaging:id/start_new_conversation_button"));
        button.click();

        UiObject number = mDevice.findObject(new UiSelector().text("Type a name, phone number, or email"));
        number.setText("654123987");

        UiObject contact = mDevice.findObject(new UiSelector().text("654987123 (+1 more)"));
        contact.click();

        UiObject select = mDevice.findObject(new UiSelector().text("Home - 654987123"));
        select.click();

        UiObject message = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.messaging:id/compose_message_text"));
        message.setText("UI Automator");

        // UiObject send = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.messaging:id/self_send_icon"));
        UiObject send = mDevice.findObject(new UiSelector().text("SMS"));
        send.clickAndWaitForNewWindow();

    }

    @Test
    public void testSendIcon() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Messages"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Messages"));
        testingApp.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.messaging:id/start_new_conversation_button"));
        button.click();

        UiObject number = mDevice.findObject(new UiSelector().text("Type a name, phone number, or email"));
        number.setText("654123987");

        UiObject contact = mDevice.findObject(new UiSelector().text("654987123 (+1 more)"));
        contact.click();

        UiObject select = mDevice.findObject(new UiSelector().text("Home - 654987123"));
        select.click();

        UiObject icon = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.messaging:id/emoji_gallery_button"));
        // UiObject icon = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.messaging:id/emoji_keyboard_button"));
        // UiObject icon = mDevice.findObject(new UiSelector().description("Explore emoji"));
        icon.click();

        // UiObject emoji = mDevice.findObject(new UiSelector().className("android.widget.ImageView").index(0));
        UiObject emoji = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.messaging:id/emoji_image"));
        emoji.click();

        UiObject send = mDevice.findObject(new UiSelector().text("SMS"));
        send.clickAndWaitForNewWindow();

    }

    @Test
    public void testDeleteMessage() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollIntoView(new UiSelector().text("Messages"));

        UiObject testingApp = mDevice.findObject(new UiSelector().text("Messages"));
        testingApp.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().text("Gonzalo Aguilar Hermoso"));
        button.click();

        UiObject options = mDevice.findObject(new UiSelector().description("More options"));
        options.click();

        UiObject delete = mDevice.findObject(new UiSelector().text("Delete"));
        delete.click();

        UiObject send = mDevice.findObject(new UiSelector().resourceId("android:id/button1"));
        send.clickAndWaitForNewWindow();
    }

}