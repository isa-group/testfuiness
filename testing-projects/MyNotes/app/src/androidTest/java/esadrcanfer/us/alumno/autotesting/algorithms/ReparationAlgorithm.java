package esadrcanfer.us.alumno.autotesting.algorithms;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import esadrcanfer.us.alumno.autotesting.TestCase;

public interface ReparationAlgorithm {
    public TestCase repair(UiDevice device, TestCase buggyTestCase,int breakingPoint) throws UiObjectNotFoundException;
}