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
public class TestYouTube {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "YouTube";
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
    public void testSearchVideo() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        // appViews.scrollIntoView(new UiSelector().text("YouTube"));
        appViews.scrollForward();

        // UiScrollable scroll = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));     // API 28
        // UiScrollable scroll = new UiScrollable(new UiSelector().className("androidx.recyclerview.widget.RecyclerView"));  // API 29
        UiScrollable scroll = new UiScrollable(new UiSelector().resourceId("com.google.android.apps.nexuslauncher:id/apps_list_view"));
        scroll.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("YouTube"));
        testingApp.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().description("Search"));
        button.click();

        UiObject text = mDevice.findObject(new UiSelector().resourceId("com.google.android.youtube:id/search_edit_text"));
        text.setText("UI Automator");

        UiObject option = mDevice.findObject(new UiSelector().resourceId("com.google.android.youtube:id/text").index(1));
        option.click();

        UiObject select = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(3));
        select.click();

    }

    /*
    @Test
    public void testPlayVideo() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        // appViews.scrollIntoView(new UiSelector().text("YouTube"));
        appViews.scrollForward();

        // UiScrollable scroll = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));     // API 28
        // UiScrollable scroll = new UiScrollable(new UiSelector().className("androidx.recyclerview.widget.RecyclerView"));  // API 29
        UiScrollable scroll = new UiScrollable(new UiSelector().resourceId("com.google.android.apps.nexuslauncher:id/apps_list_view"));
        scroll.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("YouTube"));
        testingApp.clickAndWaitForNewWindow();

        UiObject button = mDevice.findObject(new UiSelector().className("android.widget.Button").index(1));
        button.click();

        UiObject option = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(0));
        option.click();

    }*/

    @Test
    public void testStopVideo() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        // appViews.scrollIntoView(new UiSelector().text("YouTube"));
        appViews.scrollForward();

        // UiScrollable scroll = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));     // API 28
        // UiScrollable scroll = new UiScrollable(new UiSelector().className("androidx.recyclerview.widget.RecyclerView"));  // API 29
        UiScrollable scroll = new UiScrollable(new UiSelector().resourceId("com.google.android.apps.nexuslauncher:id/apps_list_view"));
        scroll.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("YouTube"));
        testingApp.clickAndWaitForNewWindow();

        // UiObject button = mDevice.findObject(new UiSelector().className("android.widget.Button").index(1));
        UiObject button = mDevice.findObject(new UiSelector().description("Explore"));
        button.click();

        // UiObject option = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(0));
        UiObject option = mDevice.findObject(new UiSelector().description("Trending"));
        option.click();

        UiObject option2 = mDevice.findObject(new UiSelector().description("Gaming"));
        option2.click();

        UiObject start = mDevice.findObject(new UiSelector().text("PRESS START"));
        start.click();

        UiObject watch = mDevice.findObject(new UiSelector().textContains("WATCH"));
        watch.click();

        UiObject play = mDevice.findObject(new UiSelector().resourceId("com.google.android.youtube:id/watch_player"));
        play.click();

        play.waitUntilGone(20000);

        UiObject stop = mDevice.findObject(new UiSelector().resourceId("com.google.android.youtube:id/player_control_play_pause_replay_button"));
        stop.click();

    }

    @Test
    public void testChangeQuality() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        // appViews.scrollIntoView(new UiSelector().text("YouTube"));
        appViews.scrollForward();

        // UiScrollable scroll = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));     // API 28
        // UiScrollable scroll = new UiScrollable(new UiSelector().className("androidx.recyclerview.widget.RecyclerView"));  // API 29
        UiScrollable scroll = new UiScrollable(new UiSelector().resourceId("com.google.android.apps.nexuslauncher:id/apps_list_view"));
        scroll.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("YouTube"));
        testingApp.clickAndWaitForNewWindow();

        // UiObject button = mDevice.findObject(new UiSelector().className("android.widget.Button").index(1));
        UiObject button = mDevice.findObject(new UiSelector().description("Explore"));
        button.click();

        // UiObject option = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(0));
        UiObject option = mDevice.findObject(new UiSelector().description("Trending"));
        option.click();

        UiObject option2 = mDevice.findObject(new UiSelector().description("Gaming"));
        option2.click();

        UiObject start = mDevice.findObject(new UiSelector().text("PRESS START"));
        start.click();

        UiObject watch = mDevice.findObject(new UiSelector().textContains("WATCH"));
        watch.click();

        UiObject play = mDevice.findObject(new UiSelector().resourceId("com.google.android.youtube:id/player_view"));
        play.click();

        // play.waitUntilGone(20000);

        // UiObject select = mDevice.findObject(new UiSelector().resourceId("com.google.android.youtube:id/player_overflow_button"));
        UiObject select = mDevice.findObject(new UiSelector().description("More options"));
        select.click();

        // UiObject quality = mDevice.findObject(new UiSelector().className("android.widget.RelativeLayout").index(2));
        UiObject quality = mDevice.findObject(new UiSelector().text("Quality"));
        quality.click();

        // UiObject level = mDevice.findObject(new UiSelector().className("android.widget.RelativeLayout").index(7));
        UiObject level = mDevice.findObject(new UiSelector().textContains("1080p"));
        level.click();

    }

    @Test
    public void testChangeVelocity() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        // appViews.scrollIntoView(new UiSelector().text("YouTube"));
        appViews.scrollForward();

        // UiScrollable scroll = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));     // API 28
        // UiScrollable scroll = new UiScrollable(new UiSelector().className("androidx.recyclerview.widget.RecyclerView"));  // API 29
        UiScrollable scroll = new UiScrollable(new UiSelector().resourceId("com.google.android.apps.nexuslauncher:id/apps_list_view"));
        scroll.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("YouTube"));
        testingApp.clickAndWaitForNewWindow();

        // UiObject button = mDevice.findObject(new UiSelector().className("android.widget.Button").index(1));
        UiObject button = mDevice.findObject(new UiSelector().description("Explore"));
        button.click();

        // UiObject option = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(0));
        UiObject option = mDevice.findObject(new UiSelector().description("Trending"));
        option.click();

        UiObject option2 = mDevice.findObject(new UiSelector().description("Gaming"));
        option2.click();

        UiObject start = mDevice.findObject(new UiSelector().text("PRESS START"));
        start.click();

        UiObject watch = mDevice.findObject(new UiSelector().textContains("WATCH"));
        watch.click();

        UiObject play = mDevice.findObject(new UiSelector().resourceId("com.google.android.youtube:id/player_view"));
        play.click();

        // play.waitUntilGone(20000);

        // UiObject select = mDevice.findObject(new UiSelector().resourceId("com.google.android.youtube:id/player_overflow_button"));
        UiObject select = mDevice.findObject(new UiSelector().description("More options"));
        select.click();

        // UiObject velocity = mDevice.findObject(new UiSelector().className("android.widget.RelativeLayout").index(4));
        UiObject velocity = mDevice.findObject(new UiSelector().text("Playback speed"));
        velocity.click();

        // UiObject level = mDevice.findObject(new UiSelector().className("android.widget.RelativeLayout").index(8));
        UiObject level = mDevice.findObject(new UiSelector().text("2x"));
        level.click();

    }

    @Test
    public void testShareVideo() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        //appViews.scrollIntoView(new UiSelector().text("YouTube"));
        appViews.scrollForward();

        // UiScrollable scroll = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));     // API 28
        // UiScrollable scroll = new UiScrollable(new UiSelector().className("androidx.recyclerview.widget.RecyclerView"));  // API 29
        UiScrollable scroll = new UiScrollable(new UiSelector().resourceId("com.google.android.apps.nexuslauncher:id/apps_list_view"));
        scroll.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("YouTube"));
        testingApp.clickAndWaitForNewWindow();

        // UiObject button = mDevice.findObject(new UiSelector().className("android.widget.Button").index(1));
        UiObject button = mDevice.findObject(new UiSelector().description("Explore"));
        button.click();

        // UiObject option = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(0));
        UiObject option = mDevice.findObject(new UiSelector().description("Trending"));
        option.click();

        UiObject option2 = mDevice.findObject(new UiSelector().description("Gaming"));
        option2.click();

        UiObject start = mDevice.findObject(new UiSelector().text("PRESS START"));
        start.click();

        UiObject watch = mDevice.findObject(new UiSelector().textContains("WATCH"));
        watch.click();

        UiObject play = mDevice.findObject(new UiSelector().resourceId("com.google.android.youtube:id/player_view"));
        play.click();

        // play.waitUntilGone(30000);

        UiObject share = mDevice.findObject(new UiSelector().resourceId("com.google.android.youtube:id/player_share_button"));
        share.click();

        UiObject messages = mDevice.findObject(new UiSelector().text("Messages"));
        messages.click();

        UiObject contact = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.messaging:id/details_container").index(1));
        contact.click();

        UiObject send = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.messaging:id/send_message_button_icon"));
        send.click();

    }

}