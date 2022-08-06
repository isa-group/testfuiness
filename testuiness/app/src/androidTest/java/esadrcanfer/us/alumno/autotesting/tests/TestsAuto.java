package esadrcanfer.us.alumno.autotesting.tests;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

import static esadrcanfer.us.alumno.autotesting.tests.AutomaticRepairTests.labelsDetection;

import android.os.Environment;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import androidx.test.uiautomator.UiDevice;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import esadrcanfer.us.alumno.autotesting.BrokenTestCaseException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.algorithms.RandomReparation;
import esadrcanfer.us.alumno.autotesting.util.ReadUtil;

@RunWith(Parameterized.class)
@SdkSuppress(minSdkVersion = 18)
public class TestsAuto {

    private String path;

    @Parameterized.Parameters
    public static Collection<String> data(){

        // CALCULATOR

        return Arrays.asList("Test Calculator/API 25, 27, 28/TestDivisión.txt",
                            "Test Calculator/API 25, 27, 28/TestMultiplicación.txt",
                            "Test Calculator/API 25, 27, 28/TestResta.txt",
                            "Test Calculator/API 25, 27, 28/TestSuma.txt");

        // CLOCK

        /*return Arrays.asList("Test Clock/API 27, 28, 29/TestAlarm.txt",
                            "Test Clock/API 27, 28, 29/TestOtherAlarm.txt",
                            "Test Clock/API 27, 28, 29/TestStopWatch.txt",
                            "Test Clock/API 27, 28, 29/TestTimer.txt");*/

        // CONTACTS

        /*return Arrays.asList("Test Contacts/API 27, 28, 29/TestCreateContact.txt",
                            "Test Contacts/API 27, 28, 29/TestEditContact.txt",
                            "Test Contacts/API 27, 28, 29/TestFavoriteContact.txt",
                            "Test Contacts/API 27, 28, 29/TestDeleteContact.txt");*/

        // GMAIL

        /*return Arrays.asList("Test Gmail/API 27, 28, 29/TestSendEmail.txt",
                            "Test Gmail/API 27, 28, 29/TestEditDraft.txt",
                            "Test Gmail/API 27, 28, 29/TestDeleteEmail.txt",
                            "Test Gmail/API 27, 28, 29/TestEmptyTrash.txt");*/

        // GOOGLE CALENDAR

        /*return Arrays.asList("Test Google Calendar/TestCreateEvent.txt",
                            "Test Google Calendar/TestEditEvent.txt",
                            "Test Google Calendar/TestDeleteEvent.txt");*/

        // CHROME

        /*return Arrays.asList("Test Google Chrome/TestSearchGoogleChrome.txt",
                            "Test Google Chrome/TestShareImageGoogleChrome.txt",
                            "Test Google Chrome/TestClearHistoryGoogleChrome.txt");*/

        // GOOGLE DOCS

        /*return Arrays.asList("Test Google Docs/CreateGoogleDocs.txt",
                            "Test Google Docs/EditGoogleDocs.txt",
                            "Test Google Docs/EditGoogleDocs2.txt",
                            "Test Google Docs/EditGoogleDocs3.txt",
                            "Test Google Docs/SendCopyGoogleDocs.txt",
                            "Test Google Docs/DeleteGoogleDocs.txt");*/

        // GOOGLE DRIVE

        /*return Arrays.asList("Test Google Drive/CreateFolderGoogleDrive.txt",
                            "Test Google Drive/CreateGoogleDoc.txt",
                            "Test Google Drive/CreateGoogleSheet.txt",
                            "Test Google Drive/CreateGoogleSlide.txt",
                            "Test Google Drive/EditGoogleDoc.txt",
                            "Test Google Drive/EditGoogleSheet.txt",
                            "Test Google Drive/EditGoogleSlide.txt",
                            "Test Google Drive/DeleteFolderGoogleDrive.txt",
                            "Test Google Drive/DeleteGoogleDoc.txt",
                            "Test Google Drive/DeleteGoogleSheet.txt",
                            "Test Google Drive/DeleteGoogleSlide.txt");*/

        // GOOGLE EARTH

        /*return Arrays.asList("Test Google Earth/API 27, 28, 29/TestSearchGoogleEarth.txt",
                            "Test Google Earth/API 27, 28, 29/TestLuckGoogleEarth.txt",
                            "Test Google Earth/API 27, 28, 29/TestVoyagerGoogleEarth.txt");*/

        // GOOGLE MAPS

        /*return Arrays.asList("Test Google Maps/TestSearchGoogleMaps.txt",
                            "Test Google Maps/TestShareLocationGoogleMaps.txt",
                            "Test Google Maps/TestJourneyGoogleMaps.txt");*/

        // GOOGLE NOTES

        /*return Arrays.asList("Test Google Notes/TestCreateGoogleNotes.txt",
                            "Test Google Notes/TestListGoogleNotes.txt",
                            "Test Google Notes/TestEditGoogleNotes.txt",
                            "Test Google Notes/TestReminderGoogleNotes.txt",
                            "Test Google Notes/TestDeleteGoogleNotes.txt");*/

        // GOOGLE SHEETS

        /*return Arrays.asList("Test Google Sheets/CreateGoogleSheets.txt",
                            "Test Google Sheets/EditGoogleSheets.txt",
                            "Test Google Sheets/SendCopyGoogleSheets.txt",
                            "Test Google Sheets/DeleteGoogleSheets.txt");*/

        // GOOGLE SLIDES

        /*return Arrays.asList("Test Google Slides/API 27, 28, 29/CreateGoogleSlides.txt",
                            "Test Google Slides/API 27, 28, 29/EditGoogleSlides.txt",
                            "Test Google Slides/API 27, 28, 29/SendCopyGoogleSlides.txt",
                            "Test Google Slides/API 27, 28, 29/DeleteGoogleSlides.txt");*/

        // GOOGLE TRANSLATE

        /*return Arrays.asList("Test Google Traductor/API 28, 29/TestTraductor.txt",
                            "Test Google Traductor/API 28, 29/TestCambiarIdioma.txt");*/

        // MESSAGES

        /*return Arrays.asList("Test Messages/TestSendMessage.txt",
                            "Test Messages/TestSendIcon.txt",
                            "Test Messages/TestDeleteMessage.txt");*/

        // PHONE

        /*return Arrays.asList("Test Phone/API 28/TestCallContact.txt",
                            "Test Phone/API 28/TestCallPhone.txt",
                            "Test Phone/API 28/TestAddFavourites.txt",
                            "Test Phone/API 28/TestDeleteFavourites.txt",
                            "Test Phone/API 28/TestClearCallHistory.txt");*/

        // PLAY BOOKS

        /*return Arrays.asList("Test Play Books/TestSearchBook.txt",
                            "Test Play Books/TestSearchGender.txt",
                            "Test Play Books/TestDeleteBook.txt");*/

        // PLAY GAMES

        /*return Arrays.asList("Test Play Games/API 28, 29/TestPlayGame.txt",
                            "Test Play Games/API 28, 29/TestSearchGame.txt");*/

        // PLAY MOVIES

        /*return Arrays.asList("Test Play Movies/TestPlayMovies.txt",
                            "Test Play Movies/TestSearchMovies.txt");*/

        // PLAY STORE

        /*return Arrays.asList("Test Play Store/TestSearchApplication.txt",
                            "Test Play Store/TestInstallApplication.txt",
                            "Test Play Store/TestUpdateApplication.txt",
                            "Test Play Store/TestUninstallApplication.txt");*/

        // YOUTUBE

        /*return Arrays.asList("Test YouTube/API 28, 29/TestSearchVideo.txt",
                            "Test YouTube/API 28, 29/TestShareVideo.txt",
                            "Test YouTube/API 28, 29/TestChangeVelocity.txt",
                            "Test YouTube/API 28, 29/TestChangeQuality.txt");*/

        // YT MUSIC

        /*return Arrays.asList("Test YT Music/TestSearchAndPlaySong.txt",
                            "Test YT Music/TestAddSongToLibrary.txt",
                            "Test YT Music/TestDeleteFromLibrary.txt",
                            "Test YT Music/TestExplore.txt",
                            "Test YT Music/TestSubscribe.txt",
                            "Test YT Music/TestUnsubscribe.txt");*/
    }

    public TestsAuto(String path) {

        this.path = path;
    }

    @Test
    public void runTxtTests(){
        ReadUtil readUtil = new ReadUtil(path, true);
        TestCase testCase = readUtil.generateTestCase();

        Log.d("ISA", "Loadded test case from file!");
        Log.d("ISA", "Executing it...");
        try{
            testCase.executeBefore();
            List<String> initialState = labelsDetection();
            testCase.executeTest();
            List<String> finalState = labelsDetection();
            testCase.setInitialState(initialState);
            testCase.setFinalState(finalState);
            Boolean eval = testCase.evaluate();
            Log.d("ISA", "Initial evaluation: " + eval.toString());
            testCase.executeAfter();

            Assert.assertTrue(eval);

        } catch (Exception ex) {
            Assert.assertFalse(true);
        }

        Log.d("ISA", "TestCase found: " + testCase);
    }
}
