package com.example.android.testing.espresso.BasicSample;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.UiObjectNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import esadrcanfer.us.alumno.autotesting.tests.ReadTestCase;

@RunWith(AndroidJUnit4.class)
public class ReaderLibraryInstrumentedTests {
    @Test
    public void changeTextAndPressButtonTest() throws UiObjectNotFoundException, IOException {
        ReadTestCase read = new ReadTestCase();
        read.read("TestCase-20210504_142935", true);
    }

    @Test
    public void changeTextAndChangeActivityTest() throws UiObjectNotFoundException, IOException {
        ReadTestCase read = new ReadTestCase();
        read.read("TestCase-20210504_142939", true);
    }
}
