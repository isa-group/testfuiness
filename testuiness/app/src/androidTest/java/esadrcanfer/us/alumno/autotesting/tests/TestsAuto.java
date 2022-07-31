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

        /*return Arrays.asList("Test Calculator/API 25, 27, 28/TestDivisión.txt",
                            "Test Calculator/API 25, 27, 28/TestMultiplicación.txt",
                            "Test Calculator/API 25, 27, 28/TestResta.txt",
                            "Test Calculator/API 25, 27, 28/TestSuma.txt");*/

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

        return Arrays.asList(//"Test Google Calendar/TestCreateEvent.txt",
                            "Test Google Calendar/TestEditEvent.txt",
                            "Test Google Calendar/TestDeleteEvent.txt");

        // CHROME

        /*return Arrays.asList("Test Google Chrome/TestSearchGoogleChrome.txt"),
                            "Test Google Chrome/TestShareImageGoogleChrome.txt",
                            "Test Google Chrome/TestClearHistoryGoogleChrome.txt");*/

        // GOOGLE DOCS

        /*return Arrays.asList("Download/Test Google Docs/CreateGoogleDocs.txt",
                            "Download/Test Google Docs/EditGoogleDocs.txt",
                            "Download/Test Google Docs/EditGoogleDocs2.txt",
                            "Download/Test Google Docs/EditGoogleDocs3.txt",
                            "Download/Test Google Docs/SendCopyGoogleDocs.txt",
                            "Download/Test Google Docs/DeleteGoogleDocs.txt");*/

        // GOOGLE DRIVE

        /*return Arrays.asList("Download/Test Google Drive/CreateFolderGoogleDrive.txt",
                            "Download/Test Google Drive/CreateGoogleDoc.txt",
                            "Download/Test Google Drive/CreateGoogleSheet.txt",
                            "Download/Test Google Drive/CreateGoogleSlide.txt",
                            "Download/Test Google Drive/EditGoogleDoc.txt",
                            "Download/Test Google Drive/EditGoogleSheet.txt",
                            "Download/Test Google Drive/EditGoogleSlide.txt",
                            "Download/Test Google Drive/DeleteFolderGoogleDrive.txt",
                            "Download/Test Google Drive/DeleteGoogleDoc.txt",
                            "Download/Test Google Drive/DeleteGoogleSheet.txt",
                            "Download/Test Google Drive/DeleteGoogleSlide.txt");*/

        // GOOGLE EARTH

        /*return Arrays.asList("Test Google Earth/API 27, 28, 29/TestLuckGoogleEarth.txt",
                            "Test Google Earth/API 27, 28, 29/TestSearchGoogleEarth.txt",
                            "Test Google Earth/API 27, 28, 29/TestVoyagerGoogleEarth.txt");*/

        // GOOGLE MAPS

        /*return Arrays.asList("Download/Test Google Maps/TestJourneyGoogleMaps.txt",
                            "Download/Test Google Maps/TestSearchGoogleMaps.txt",
                            "Download/Test Google Maps/TestShareLocationGoogleMaps.txt");*/

        // GOOGLE NOTES

        /*return Arrays.asList("Download/Test Google Notes/TestCreateGoogleNotes.txt",
                            "Download/Test Google Notes/TestListGoogleNotes.txt",
                            "Download/Test Google Notes/TestEditGoogleNotes.txt",
                            "Download/Test Google Notes/TestReminderGoogleNotes.txt",
                            "Download/Test Google Notes/TestDeleteGoogleNotes.txt");*/

        // GOOGLE SHEETS

        /*return Arrays.asList("Download/Test Google Sheets/CreateGoogleSheets.txt",
                            "Download/Test Google Sheets/EditGoogleSheets.txt",
                            "Download/Test Google Sheets/SendCopyGoogleSheets.txt",
                            "Download/Test Google Sheets/DeleteGoogleSheets.txt");*/

        // GOOGLE SLIDES

        /*return Arrays.asList("Download/Test Google Slides/API 27, 28, 29/CreateGoogleSlides.txt",
                            "Download/Test Google Slides/API 27, 28, 29/EditGoogleSlides.txt",
                            "Download/Test Google Slides/API 27, 28, 29/SendCopyGoogleSlides.txt",
                            "Download/Test Google Slides/API 27, 28, 29/DeleteGoogleSlides.txt");*/

        // GOOGLE TRANSLATE

        /*return Arrays.asList("Download/Test Google Traductor/API 28, 29/TestTraductor.txt",
                            "Download/Test Google Traductor/API 28, 29/TestCambiarIdioma.txt");*/

        // MESSAGES

        /*return Arrays.asList("Download/Test Messages/TestSendMessage.txt",
                            "Download/Test Messages/TestSendIcon.txt",
                            "Download/Test Messages/TestDeleteMessage.txt");*/

        // PHONE

        /*return Arrays.asList("Download/Test Phone/API 28/TestCallContact.txt",
                            "Download/Test Phone/API 28/TestCallPhone.txt",
                            "Download/Test Phone/API 28/TestAddFavourites.txt",
                            "Download/Test Phone/API 28/TestDeleteFavourites.txt",
                            "Download/Test Phone/API 28/TestClearCallHistory.txt");*/

        // PLAY BOOKS

        /*return Arrays.asList("Download/Test Play Books/TestSearchBook.txt",
                            "Download/Test Play Books/TestSearchGender.txt",
                            "Download/Test Play Books/TestDeleteBook.txt");*/

        // PLAY GAMES

        /*return Arrays.asList("Download/Test Play Games/API 28, 29/TestPlayGame.txt",
                            "Download/Test Play Games/API 28, 29/TestSearchGame.txt");*/

        // PLAY MOVIES

        /*return Arrays.asList("Download/Test Play Movies/TestPlayMovies.txt",
                            "Download/Test Play Movies/TestSearchMovies.txt");*/

        // PLAY MUSIC

        /*return Arrays.asList("Download/Test Play Music/TestPlayMusic.txt",
                            "Download/Test Play Music/TestStopMusic.txt",
                            "Download/Test Play Music/TestNextSong.txt",
                            "Download/Test Play Music/TestPreviousSong.txt");*/

        // PLAY STORE

        /*return Arrays.asList("Download/Test Play Store/TestSearchApplication.txt",
                            "Download/Test Play Store/TestInstallApplication.txt",
                            "Download/Test Play Store/TestUpdateApplication.txt",
                            "Download/Test Play Store/TestRemoveApplication.txt",
                            "Download/Test Play Store/TestUninstallApplication.txt");*/

        // YOUTUBE

        /*return Arrays.asList("Download/Test YouTube/API 28, 29/TestSearchVideo.txt",
                            "Download/Test YouTube/API 28, 29/TestShareVideo.txt",
                            "Download/Test YouTube/API 28, 29/TestChangeVelocity.txt",
                            "Download/Test YouTube/API 28, 29/TestChangeQuality.txt");*/
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
