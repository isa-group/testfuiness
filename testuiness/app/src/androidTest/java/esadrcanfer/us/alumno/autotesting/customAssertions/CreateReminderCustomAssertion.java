package esadrcanfer.us.alumno.autotesting.customAssertions;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertNotNull;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;

import esadrcanfer.us.alumno.autotesting.tests.AssertionChecker;

public class CreateReminderCustomAssertion extends AssertionChecker {
    @Override
    public void assertionCheck() {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject object = device.findObject(new UiSelector().descriptionContains("Prueba ficheros test"));
        assertNotNull(object);
    }
}
