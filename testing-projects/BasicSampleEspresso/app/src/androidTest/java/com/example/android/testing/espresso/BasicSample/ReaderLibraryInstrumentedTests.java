package com.example.android.testing.espresso.BasicSample;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static esadrcanfer.us.alumno.autotesting.tests.AutomaticRepairTests.labelsDetection;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import esadrcanfer.us.alumno.autotesting.BrokenTestCaseException;
import esadrcanfer.us.alumno.autotesting.TestCase;
import esadrcanfer.us.alumno.autotesting.algorithms.RandomReparation;
import esadrcanfer.us.alumno.autotesting.tests.ReadTestCase;
import esadrcanfer.us.alumno.autotesting.util.ReadUtil;

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

    @Test
    public void changeTextActivityTest() throws UiObjectNotFoundException, IOException {

        ReadTestCase read = new ReadTestCase();
        read.read("TestCase-20220702_115920", true);
    }

    @Test
    public void changeTextActivityTestPruebaWithRepair() throws UiObjectNotFoundException, IOException{

        UiDevice device = UiDevice.getInstance(getInstrumentation());
        ReadUtil readUtil = new ReadUtil("TestCase-20220702_115920_broken.txt", true);

        TestCase testCase = readUtil.generateTestCase();
        Log.d("ISA", "Loaded test case from file!");
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
            RandomReparation randomReparation = new RandomReparation(5, testCase, testCase.getAppPackage());
            testCase = randomReparation.run(device, testCase.getAppPackage());
        }
        Log.d("ISA", "TestCase found: " + testCase);

    }
}
