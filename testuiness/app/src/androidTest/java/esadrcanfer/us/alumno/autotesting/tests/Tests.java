package esadrcanfer.us.alumno.autotesting.tests;

import android.util.Log;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;

import org.junit.Test;

import java.util.List;

import esadrcanfer.us.alumno.autotesting.BrokenTestCaseException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.algorithms.RandomReparation;
import esadrcanfer.us.alumno.autotesting.algorithms.RecycleReparation;
import esadrcanfer.us.alumno.autotesting.util.ReadUtil;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static esadrcanfer.us.alumno.autotesting.tests.AutomaticRepairTests.labelsDetection;

public class Tests {

    @Test
    public void testRandomReparation() throws UiObjectNotFoundException {

        UiDevice device = UiDevice.getInstance(getInstrumentation());
        long iterations = 5;


        // TEST GOOGLE NOTES Funciona API 25, 27, 28, 29

        // ReadUtil readUtil = new ReadUtil("Download/Google Notes/TestCreateGoogleNotes.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Notes/TestEditGoogleNotes.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Notes/TestDeleteGoogleNotes.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Notes/TestListGoogleNotes.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Notes/TestReminderGoogleNotes.txt", true);

        // TEST GOOGLE MAPS Funciona API 25, 27, 28, 29

        // ReadUtil readUtil = new ReadUtil("Download/Google Maps/TestJourneyGoogleMaps.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Maps/TestSearchGoogleMaps.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Maps/TestShareLocationGoogleMaps.txt", true);

        // TEST GOOGLE DOCS Funciona API 25, 27, 28, 29

        // ReadUtil readUtil = new ReadUtil("Download/Google Docs/CreateGoogleDocs.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Docs/EditGoogleDocs.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Docs/DeleteGoogleDocs.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Docs/SendCopyGoogleDocs.txt", true);

        // TEST GOOGLE SLIDE Funciona API 25, 27, 28, 29

        // ReadUtil readUtil = new ReadUtil("Download/Google Slides/CreateGoogleSlides.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Slides/EditGoogleSlides.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Slides/DeleteGoogleSlides.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Slides/SendCopyGoogleSlides.txt", true);

        // TEST GOOGLE SHEETS Funciona API 25, 27, 28, 29

        // ReadUtil readUtil = new ReadUtil("Download/Google Sheets/CreateGoogleSheets.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Sheets/EditGoogleSheets.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Sheets/DeleteGoogleSheets.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Sheets/SendCopyGoogleSheets.txt", true);

        // TEST GOOGLE DRIVE Funciona API 25, 27, 28, 29

        // ReadUtil readUtil = new ReadUtil("Download/Google Drive/CreateFolderGoogleDrive.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Drive/DeleteFolderGoogleDrive.txt", true);

        // ReadUtil readUtil = new ReadUtil("Download/Google Drive/CreateGoogleDoc.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Drive/CreateGoogleSlide.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Drive/CreateGoogleSheet.txt", true);

        // ReadUtil readUtil = new ReadUtil("Download/Google Drive/EditGoogleDoc.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Drive/EditGoogleSlide.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Drive/EditGoogleSheet.txt", true);

        // ReadUtil readUtil = new ReadUtil("Download/Google Drive/DeleteGoogleDoc.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Drive/DeleteGoogleSlide.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Drive/DeleteGoogleSheet.txt", true);

        // TEST GOOGLE CALENDAR // Funciona en API 25, 27, 28, 29

        // ReadUtil readUtil = new ReadUtil("Download/Google Calendar/TestCreateEvent.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Calendar/TestEditEvent.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Calendar/TestDeleteEvent.txt", true);

        // TEST GOOGLE EARTH // Funciona en API 25(a veces falla), 27, 28, 29

        // ReadUtil readUtil = new ReadUtil("Download/Google Earth/TestLuckGoogleEarth.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Earth/TestSearchGoogleEarth.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Earth/TestVoyagerGoogleEarth.txt", true);

        // TEST GOOGLE CHROME // Funciona API 25, 27, 28, 29

        // ReadUtil readUtil = new ReadUtil("Download/Google Chrome/TestSearchGoogleChrome.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Chrome/TestClearHistoryGoogleChrome.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Chrome/TestShareImageGoogleChrome.txt", true);

        // TEST GMAIL // Funciona 25, 27, 28, 29

        // ReadUtil readUtil = new ReadUtil("Download/Gmail/TestSendEmail.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Gmail/TestEditDraft.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Gmail/TestDeleteEmail.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Gmail/TestEmptyTrash.txt", true);

        // TEST GOOGLE PLAY MOVIES // Funciona 25, 27, 28, 29

        // ReadUtil readUtil = new ReadUtil("Download/Play Movies/TestPlayMovies.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Play Movies/TestSearchMovies.txt", true);

        // TEST GOOGLE PLAY MUSIC // Funciona API 25, 27, 28, 29

        // ReadUtil readUtil = new ReadUtil("Download/Play Music/TestNextSong.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Play Music/TestPlayMusic.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Play Music/TestStopMusic.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Play Music/TestPreviousSong.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Play Music/TestSearchSong.txt", true);

        // TEST GOOGLE PLAY BOOKS // Funciona API 25, 27, 28, 29

        // ReadUtil readUtil = new ReadUtil("Download/Play Books/TestSearchBook.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Play Books/TestDeleteBook.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Play Books/TestSearchGender.txt", true);

        // TEST GOOGLE PLAY GAMES // Funciona API 25, 27, 28, 29

        // ReadUtil readUtil = new ReadUtil("Download/Play Games/TestPlayGame.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Play Games/TestSearchGame.txt", true);

        // TEST GOOGLE PLAY STORE // Funciona API 25, 27, 28, 29

        // ReadUtil readUtil = new ReadUtil("Download/Play Store/TestInstallApplication.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Play Store/TestUninstallApplication.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Play Store/TestUpdateApplication.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Play Store/TestRemoveApplication.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Play Store/TestSearchApplication.txt", true);

        // TEST GOOGLE TRANSLATE // Funciona API 25, 27, 28, 29

         ReadUtil readUtil = new ReadUtil("Download/Google Translate/TestTraductor.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Google Translate/TestCambiarIdioma.txt", true); // NO

        // TEST YOUTUBE // Funciona API 25, 27, 28, 29

        // ReadUtil readUtil = new ReadUtil("Download/YouTube/TestChangeQuality.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/YouTube/TestChangeVelocity.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/YouTube/TestShareVideo.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/YouTube/TestSearchVideo.txt", true);

        // TEST CALCULATOR // Funciona API 25, 27, 28, 29

        // ReadUtil readUtil = new ReadUtil("Download/Calculator/TestSuma.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Calculator/TestResta.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Calculator/TestMultiplicación.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Calculator/TestDivisión.txt", true);

        // TEST CONTACT // Funciona 25, 27, 28, 29

        // ReadUtil readUtil = new ReadUtil("Download/Contacts/TestCreateContact.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Contacts/TestEditContact.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Contacts/TestDeleteContact.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Contacts/TestFavoriteContact.txt", true);

        // TEST CLOCK Funciona API 25, 27, 28, 29

        // ReadUtil readUtil = new ReadUtil("Download/Clock/TestAlarm.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Clock/TestOtherAlarm.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Clock/TestStopWatch.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Clock/TestTimer.txt", true);

        // TEST PHONE Funciona 25, 27, 28, 29

        // ReadUtil readUtil = new ReadUtil("Download/Phone/TestCallContact.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Phone/TestCallPhone.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Phone/TestAddFavorites.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Phone/TestDeleteFavorites.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Phone/TestClearCallHistory.txt", true);

        // TEST MESSAGES Funciona 25, 27, 28, 29

        // ReadUtil readUtil = new ReadUtil("Download/Messages/TestSendIcon.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Messages/TestSendMessage.txt", true);
        // ReadUtil readUtil = new ReadUtil("Download/Messages/TestDeleteMessage.txt", true);

        TestCase testCase = readUtil.generateTestCase();
        Log.d("ISA", "Loadded test case from file!");
        Log.d("ISA", "Executing it...");
        try {
            testCase.executeBefore();
            List<String> initialState = labelsDetection();
            testCase.executeTest();
            List<String> finalState = labelsDetection();
            testCase.setInitialState(initialState);
            testCase.setFinalState(finalState);
            Boolean eval = testCase.evaluate();
            Log.d("ISA", "Initial evaluation: " + eval.toString());
            testCase.executeAfter();
        } catch (BrokenTestCaseException ex) {
            RandomReparation randomReparation = new RandomReparation(iterations, testCase, testCase.getAppPackage());
            testCase = randomReparation.run(device, testCase.getAppPackage());
        }
        Log.d("ISA", "TestCase found: " + testCase);
    }

    @Test
    public void testRecycleReparation() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        // String path = "Download/CreationTestNote.txt";
        long iterations = 5;
        // ReadUtil readUtil = new ReadUtil("Download/CreationTestNote.txt", true);
        ReadUtil readUtil = new ReadUtil("Download/Google Notes/TestCreateGoogleNotes.txt", true);
        TestCase testCase = readUtil.generateTestCase();
        Log.d("ISA", "Loadded test case from file!");
        Log.d("ISA", "Executing it...");
        try {
            testCase.executeBefore();
            List<String> initialState = labelsDetection();
            testCase.executeTest();
            List<String> finalState = labelsDetection();
            testCase.setInitialState(initialState);
            testCase.setFinalState(finalState);
            Boolean eval = testCase.evaluate();
            Log.d("ISA", "Initial evaluation: " + eval.toString());
            testCase.executeAfter();
        } catch (BrokenTestCaseException ex) {
            testCase.executeAfter();
            RecycleReparation recycleReparation = new RecycleReparation(iterations, testCase, (int) ex.getBreakingIndex());
            testCase = recycleReparation.run(device);
        }
        Log.d("ISA", "TestCase found: " + testCase);
    }
}