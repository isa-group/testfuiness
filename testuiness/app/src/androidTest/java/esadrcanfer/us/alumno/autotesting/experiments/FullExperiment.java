package esadrcanfer.us.alumno.autotesting.experiments;

import android.support.test.filters.SdkSuppress;

import androidx.test.uiautomator.UiObjectNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
@SdkSuppress(minSdkVersion = 18)
public class FullExperiment extends Experiment{

    private static final List<String> tests = Arrays.asList(
                                                                /* CLOCK */
                                                                "Test Clock/Old/TestAlarm.txt",
                                                                "Test Clock/Old/TestOtherAlarm.txt",
                                                                /* CONTACTS */
                                                                "Test Contacts/Old/TestCreateContact.txt",
                                                                "Test Contacts/Old/TestEditContact.txt",
                                                                "Test Contacts/Old/TestFavoriteContact.txt",
                                                                "Test Contacts/Old/TestDeleteContact.txt",
                                                                /* GMAIL */
                                                                "Test Gmail/Old/TestSendEmail.txt",
                                                                "Test Gmail/Old/TestDeleteEmail.txt",
                                                                "Test Gmail/Old/TestEmptyTrash.txt",
                                                                /* GOOGLE CALENDAR */
                                                                //"Test Google Calendar/Old/TestCreateEvent.txt",
                                                                //"Test Google Calendar/Old/TestEditEvent.txt",
                                                                //"Test Google Calendar/Old/TestDeleteEvent.txt",
                                                                /* GOOGLE CHROME */
                                                                "Test Google Chrome/Old/TestSearchGoogleChrome.txt",
                                                                "Test Google Chrome/Old/TestShareImageGoogleChrome.txt",
                                                                "Test Google Chrome/Old/TestClearHistoryGoogleChrome.txt",
                                                                /* GOOGLE DOCS */
                                                                "Test Google Docs/Old/CreateGoogleDocs.txt",
                                                                "Test Google Docs/Old/EditGoogleDocs.txt",
                                                                "Test Google Docs/Old/EditGoogleDocs2.txt",
                                                                "Test Google Docs/Old/SendCopyGoogleDocs.txt",
                                                                "Test Google Docs/Old/DeleteGoogleDocs.txt",
                                                                /* GOOGLE DRIVE */
                                                                "Test Google Drive/Old/CreateFolderGoogleDrive.txt",
                                                                "Test Google Drive/Old/CreateGoogleDoc.txt",
                                                                "Test Google Drive/Old/CreateGoogleSheet.txt",
                                                                "Test Google Drive/Old/CreateGoogleSlide.txt",
                                                                "Test Google Drive/Old/EditGoogleDoc.txt",
                                                                "Test Google Drive/Old/EditGoogleSheet.txt",
                                                                "Test Google Drive/Old/EditGoogleSlide.txt",
                                                                "Test Google Drive/Old/DeleteGoogleDoc.txt",
                                                                "Test Google Drive/Old/DeleteGoogleSheet.txt",
                                                                "Test Google Drive/Old/DeleteGoogleSlide.txt",
                                                                "Test Google Drive/Old/DeleteFolderGoogleDrive.txt",
                                                                /* GOOGLE EARTH */
                                                                "Test Google Earth/Old/TestSearchGoogleEarth.txt",
                                                                "Test Google Earth/Old/TestLuckGoogleEarth.txt",
                                                                "Test Google Earth/Old/TestVoyagerGoogleEarth.txt",
                                                                /* GOOGLE KEEP */
                                                                "Test Google Keep/Old/TestCreateGoogleKeep.txt",
                                                                "Test Google Keep/Old/TestEditGoogleKeep.txt",
                                                                "Test Google Keep/Old/TestListGoogleKeep.txt",
                                                                "Test Google Keep/Old/TestReminderGoogleKeep.txt",
                                                                "Test Google Keep/Old/TestDeleteGoogleKeep.txt",
                                                                /* GOOGLE MAPS */
                                                                "Test Google Maps/Old/TestJourneyGoogleMaps.txt",
                                                                "Test Google Maps/Old/TestSearchGoogleMaps.txt",
                                                                "Test Google Maps/Old/TestShareLocationGoogleMaps.txt",
                                                                /* GOOGLE SHEETS */
                                                                "Test Google Sheets/Old/CreateGoogleSheets.txt",
                                                                "Test Google Sheets/Old/EditGoogleSheets.txt",
                                                                "Test Google Sheets/Old/SendCopyGoogleSheets.txt",
                                                                "Test Google Sheets/Old/DeleteGoogleSheets.txt",
                                                                /* GOOGLE SLIDES */
                                                                "Test Google Slides/Old/CreateGoogleSlides.txt",
                                                                "Test Google Slides/Old/EditGoogleSlides.txt",
                                                                "Test Google Slides/Old/DeleteGoogleSlides.txt",
                                                                /* GOOGLE TRANSLATE */
                                                                "Test Google Translate/Old/TestTranslate.txt",
                                                                "Test Google Translate/Old/TestChangeLanguage.txt",
                                                                /* MESSAGES */
                                                                "Test Messages/Old/TestSendMessage.txt",
                                                                "Test Messages/Old/TestSendIcon.txt",
                                                                "Test Messages/Old/TestDeleteMessage.txt",
                                                                /* PHONE */
                                                                "Test Phone/Old/TestCallContact.txt",
                                                                "Test Phone/Old/TestCallPhone.txt",
                                                                "Test Phone/Old/TestAddFavorites.txt",
                                                                "Test Phone/Old/TestDeleteFavorites.txt",
                                                                "Test Phone/Old/TestClearCallHistory.txt",
                                                                /* PLAY GAMES */
                                                                "Test Play Games/Old/TestPlayGame.txt",
                                                                "Test Play Games/Old/TestSearchGame.txt");
    private static final int numberOfExec = 4;
    private static final List<String> algorithms = Arrays.asList("WATER Algorithm", "Random Algorithm");
    private static final int timeout = 3600;

    @Parameterized.Parameters
    public static Collection<String> getData(){

        return createTestData(tests, numberOfExec, algorithms);
    }

    public FullExperiment(String testcase) {

        super(testcase.split(";")[0], testcase.split(";")[1], testcase.split(";")[2]);

    }

    @Test
    public void runTest() throws UiObjectNotFoundException {
        try {
            runTest(timeout);
        }catch(UiObjectNotFoundException e){
            throw new UiObjectNotFoundException(e.getMessage());
        }
    }
}
