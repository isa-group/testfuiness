package esadrcanfer.us.complexapp;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.UiObjectNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import esadrcanfer.us.alumno.autotesting.tests.ReadTestCase;

@RunWith(AndroidJUnit4.class)
public class ReaderLibraryInstrumentedTests {
    @Test
    public void pressButtonOneTest() throws UiObjectNotFoundException, IOException {
        ReadTestCase read = new ReadTestCase();
        read.read("TestCase-20210504_143218", true);
    }

    @Test
    public void changeActivityTest() throws UiObjectNotFoundException, IOException {
        ReadTestCase read = new ReadTestCase();
        read.read("TestCase-20210504_143221", true);
    }

    @Test
    public void backToActivityTest() throws UiObjectNotFoundException, IOException {
        ReadTestCase read = new ReadTestCase();
        read.read("TestCase-20210504_143225", true);
    }

    @Test
    public void assertExistsTest() throws UiObjectNotFoundException, IOException {
        ReadTestCase read = new ReadTestCase();
        read.read("TestCase-20210504_143228", true);
    }
}
