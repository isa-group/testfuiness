package esadrcanfer.us.alumno.autotesting.tests;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import esadrcanfer.us.alumno.autotesting.util.TestParser;

public class TestParserTest {

    @Test
    public void testTextPlainParser(){

        String path = "";
        String name = "";
        String packageName = "";

        List<String> paths = Arrays.asList("Test Calculator/API 25, 27, 28",
                "Test Clock/API 27, 28, 29",
                "Test Contacts/API 27, 28, 29",
                "Test Gmail/API 27, 28, 29",
                "Test Google Calendar",
                "Test Google Chrome",
                "Test Google Docs",
                "Test Google Drive",
                "Test Google Earth/API 27, 28, 29",
                "Test Google Maps",
                "Test Google Notes",
                "Test Google Sheets",
                "Test Google Slides/API 27, 28, 29",
                "Test Google Traductor/API 28, 29",
                "Test Messages",
                "Test Phone/API 28",
                "Test Play Books",
                "Test Play Games/API 28, 29",
                "Test Play Movies",
                "Test Play Store",
                //"Test YouTube/API 28, 29",
                "Test YT Music");

        List<String> names = Arrays.asList("TestCalculator",
                "TestClock",
                "TestContacts",
                "TestGmail",
                "TestGoogleCalendar",
                "TestChrome",
                "TestGoogleDocs",
                "TestGoogleDrive",
                "TestGoogleEarth",
                "TestGoogleMaps",
                "TestGoogleNotes",
                "TestGoogleSheets",
                "TestGoogleSlides",
                "TestGoogleTranslate",
                "TestMessage",
                "TestPhone",
                "TestPlayBooks",
                "TestPlayGames",
                "TestPlayMovies",
                "TestPlayStore",
                //"TestYouTube",
                "TestYTMusic");

        List<String> packages = Arrays.asList("Calculator",
                "Clock",
                "Contacts",
                "Gmail",
                "Calendar",
                "Chrome",
                "Docs",
                "Drive",
                "Earth",
                "Maps",
                "Keep Notes",
                "Sheets",
                "Slides",
                "Translate",
                "Messages",
                "Phone",
                "Play Books",
                "Play Games",
                "Play Movies",
                "Play Store",
                //"YouTube",
                "YT Music");

        for(int i = 0; i<paths.size(); i++){

            path = paths.get(i);
            name = names.get(i);
            packageName = packages.get(i);

            TestParser.parseTextPlainTest(path, name, packageName);
        }

    }

}
