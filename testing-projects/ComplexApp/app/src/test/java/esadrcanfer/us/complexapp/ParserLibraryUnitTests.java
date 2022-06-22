package esadrcanfer.us.complexapp;

import com.ivasandom.parserlibrary.TextualUITestGenerator;

import org.junit.Test;

import java.io.IOException;

public class ParserLibraryUnitTests {
    @Test
    public void pressButtonOneTest() throws IOException {
        TextualUITestGenerator parse = new TextualUITestGenerator();
        parse.textualUITestGenerator("src\\androidTest\\java\\esadrcanfer\\us\\complexapp\\MainActivityTest.java", BuildConfig.APPLICATION_ID);
    }

    @Test
    public void changeActivityTest() throws IOException {
        TextualUITestGenerator parse = new TextualUITestGenerator();
        parse.textualUITestGenerator("src\\androidTest\\java\\esadrcanfer\\us\\complexapp\\MainActivityTest2.java", BuildConfig.APPLICATION_ID);
    }

    @Test
    public void backToActivityTest() throws IOException {
        TextualUITestGenerator parse = new TextualUITestGenerator();
        parse.textualUITestGenerator("src\\androidTest\\java\\esadrcanfer\\us\\complexapp\\MainActivityTest3.java", BuildConfig.APPLICATION_ID);
    }

    @Test
    public void assertExistsTest() throws IOException {
        TextualUITestGenerator parse = new TextualUITestGenerator();
        parse.textualUITestGenerator("src\\androidTest\\java\\esadrcanfer\\us\\complexapp\\MainActivityTest4.java", BuildConfig.APPLICATION_ID);
    }
}
