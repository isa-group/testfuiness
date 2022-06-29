package esadrcanfer.us.diversityapp;

import com.ivasandom.parserlibrary.TextualUITestGenerator;

import org.junit.Test;

import java.io.IOException;

public class ParserLibraryUnitTests {
    @Test
    public void assertExistsTest() throws IOException {
        TextualUITestGenerator parse = new TextualUITestGenerator();
        parse.textualUITestGenerator("src\\androidTest\\java\\esadrcanfer\\us\\diversityapp\\MainActivityTest.java", BuildConfig.APPLICATION_ID);
    }

    @Test
    public void changeActivityTest() throws IOException {
        TextualUITestGenerator parse = new TextualUITestGenerator();
        parse.textualUITestGenerator("src\\androidTest\\java\\esadrcanfer\\us\\diversityapp\\MainActivityTest2.java", BuildConfig.APPLICATION_ID);
    }

    @Test
    public void changeActivityTwoTimesTest() throws IOException {
        TextualUITestGenerator parse = new TextualUITestGenerator();
        parse.textualUITestGenerator("src\\androidTest\\java\\esadrcanfer\\us\\diversityapp\\MainActivityTest3.java", BuildConfig.APPLICATION_ID);
    }
}
