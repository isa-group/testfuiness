package com.example.testingandroid;

import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static java.lang.System.out;
import static java.lang.Thread.sleep;
import static junit.framework.Assert.assertTrue;

/**
 * Created by adrca on 01/03/2018.
 */

@RunWith(AndroidJUnit4.class)
public class TestingAndroidTest {

    @Before
    public void setUp(){
        UiDevice mDevice;
        mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

    }

    private static final String tag = "Testing";
    @Test
    public void test1() throws UiObjectNotFoundException, InterruptedException {
        UiDevice mDevice;
        mDevice = UiDevice.getInstance(getInstrumentation());
        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps"));
        allAppsButton.click();
        UiScrollable appViews = new UiScrollable(
                new UiSelector().scrollable(true));
        appViews.scrollIntoView(new UiSelector().text("TestingAndroid"));
        UiObject testingApp = mDevice.findObject(new UiSelector().text("TestingAndroid"));
        testingApp.clickAndWaitForNewWindow();
        BySelector sel = By.clazz("android.widget.Button");
        List<UiObject2> botones = mDevice.findObjects(sel);
        for (UiObject2 boton: botones) {
            boton.click();
            String message = "Se ha pulsado: " + boton.getText();
            Log.i(tag, message);
            sleep(2500);

        }



    }


}
