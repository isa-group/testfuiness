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

        /*return Arrays.asList("Download/Test Calculator/API 25, 27, 28/TestDivisión.txt",
                            "Download/Test Calculator/API 25, 27, 28/TestMultiplicación.txt",
                            "Download/Test Calculator/API 25, 27, 28/TestResta.txt",
                            "Download/Test Calculator/API 25, 27, 28/TestSuma.txt");*/

        // CLOCK

        /*return Arrays.asList("Download/Test Clock/API 27, 28, 29/TestAlarm.txt",
                            "Download/Test Clock/API 27, 28, 29/TestOtherAlarm.txt",
                            "Download/Test Clock/API 27, 28, 29/TestStopWatch.txt",
                            "Download/Test Clock/API 27, 28, 29/TestTimer.txt");*/

        // CONTACTS

        /*return Arrays.asList("Download/Test Contacts/API 27, 28, 29/TestCreateContact.txt",
                            "Download/Test Contacts/API 27, 28, 29/TestEditContact.txt",
                            "Download/Test Contacts/API 27, 28, 29/TestFavoriteContact.txt",
                            "Download/Test Contacts/API 27, 28, 29/TestDeleteContact.txt");*/

        // GMAIL

        /*return Arrays.asList("Download/Test Gmail/API 27, 28, 29/TestSendEmail.txt",
                            "Download/Test Gmail/API 27, 28, 29/TestEditDraft.txt",
                            "Download/Test Gmail/API 27, 28, 29/TestDeleteEmail.txt",
                            "Download/Test Gmail/API 27, 28, 29/TestEmptyTrash.txt");*/

        // GOOGLE CALENDAR

        /*return Arrays.asList("Download/Test Google Calendar/TestCreateEvent.txt",
                            "Download/Test Google Calendar/TestEditEvent.txt",
                            "Download/Test Google Calendar/TestDeleteEvent.txt");*/

        // CHROME

        /*return Arrays.asList("Download/Test Google Chrome/TestSearchGoogleChrome.txt",
                            "Download/Test Google Chrome/TestShareImageGoogleChrome.txt",
                            "Download/Test Google Chrome/TestClearHistoryGoogleChrome.txt");*/

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

        // GOOGLE MAPS

        return Arrays.asList("Download/Test Google Maps/TestJourneyGoogleMaps.txt",
                            "Download/Test Google Maps/TestSearchGoogleMaps.txt",
                            "Download/Test Google Maps/TestShareLocationGoogleMaps.txt");
    }

    public TestsAuto(String path) {

        this.path = path;
    }

    @Test
    public void runTxtTests(){
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        long iterations = 5;

        String aPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

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
        } catch (Exception ex) {
            Assert.assertFalse(true);
        }
        Log.d("ISA", "TestCase found: " + testCase);
    }
}
