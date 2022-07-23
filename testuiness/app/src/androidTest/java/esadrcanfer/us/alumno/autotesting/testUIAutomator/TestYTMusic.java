package esadrcanfer.us.alumno.autotesting.testUIAutomator;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

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

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestYTMusic {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "YT Music";
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
    public void test1SearchAndPlaySong() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("YT Music"));
        testingApp.clickAndWaitForNewWindow();

        UiObject search = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/action_search_button"));
        search.click();

        UiObject search2 = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/search_edit_text"));
        search2.setText("Radioactive");

        UiObject song = mDevice.findObject(new UiSelector().className("android.widget.LinearLayout").index(1));
        song.click();

        UiObject play = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/first_entity_button"));
        play.click();

        UiObject pause = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/player_control_play_pause_replay_button"));
        pause.waitUntilGone(30000);
        pause.click();
    }

    @Test
    public void test2AddSongToLibrary() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));   // API 28 y 29
        appViews.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("YT Music"));
        testingApp.clickAndWaitForNewWindow();

        UiObject options = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/contextual_menu"));
        options.click();

        UiObject addToLibrary = mDevice.findObject(new UiSelector().text("Add to library"));
        addToLibrary.click();

    }

    @Test
    public void test3DeleteSongFromLibrary() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("YT Music"));
        testingApp.clickAndWaitForNewWindow();

        UiObject back = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/player_collapse_button"));
        back.click();

        UiObject library = mDevice.findObject(new UiSelector().text("Library"));
        library.click();

        UiObject songs = mDevice.findObject((new UiSelector().text("Songs")));
        songs.click();

        UiObject menu = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/contextual_menu_anchor"));
        menu.click();

        UiObject delete = mDevice.findObject(new UiSelector().text("Remove from library"));
        delete.click();

    }

    @Test
    public void test4Explore() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("YT Music"));
        testingApp.clickAndWaitForNewWindow();

        UiObject explore = mDevice.findObject(new UiSelector().text("Explore"));
        explore.click();

        UiObject topMusic = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(1));
        topMusic.click();

        UiObject song = mDevice.findObject((new UiSelector().resourceId("com.google.android.apps.youtube.music:id/two_row_item").index(0)));
        song.click();

        UiObject pause = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/player_control_play_pause_replay_button"));
        pause.waitUntilGone(30000);
        pause.click();

    }

    @Test
    public void test5Subscribe() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("YT Music"));
        testingApp.clickAndWaitForNewWindow();

        UiObject back = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/player_collapse_button"));
        back.click();

        UiObject options = mDevice.findObject(new UiSelector().resourceId("com.google.android.apps.youtube.music:id/contextual_menu_anchor"));
        options.click();

        UiObject subscribe = mDevice.findObject((new UiSelector().text("Subscribe")));
        subscribe.click();

    }

    @Test
    public void test6Unsubscribe() throws UiObjectNotFoundException {

        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps list"));
        allAppsButton.click();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));
        appViews.scrollForward();

        UiObject testingApp = mDevice.findObject(new UiSelector().text("YT Music"));
        testingApp.clickAndWaitForNewWindow();

        UiObject library = mDevice.findObject(new UiSelector().text("Library"));
        library.click();

        UiObject subscriptions = mDevice.findObject(new UiSelector().text("Subscriptions"));
        subscriptions.click();

        UiObject options = mDevice.findObject((new UiSelector().resourceId("com.google.android.apps.youtube.music:id/contextual_menu_anchor")));
        options.click();

        UiObject unsubscribe = mDevice.findObject(new UiSelector().text("Unsubscribe"));
        unsubscribe.click();

    }

}
