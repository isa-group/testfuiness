package com.example.android.testing.espresso.BasicSample;

import com.ivasandom.parserlibrary.TextualUITestGenerator;

import org.junit.Test;

import java.io.IOException;

public class ParserLibraryUnitTests {
    @Test
    public void changeTextAndPressButtonTest() throws IOException {
        TextualUITestGenerator parse = new TextualUITestGenerator();
        parse.textualUITestGenerator("src\\androidTest\\java\\com\\example\\android\\testing\\espresso\\BasicSample\\MainActivityTest.java", BuildConfig.APPLICATION_ID);
    }

    @Test
    public void changeTextAndChangeActivityTest() throws IOException {
        TextualUITestGenerator parse = new TextualUITestGenerator();
        parse.textualUITestGenerator("src\\androidTest\\java\\com\\example\\android\\testing\\espresso\\BasicSample\\MainActivityTest2.java", BuildConfig.APPLICATION_ID);
    }
}
