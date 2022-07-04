package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import net.sf.extjwnl.JWNLException;

import java.util.Random;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;

public abstract class InputGenerator {

    public abstract String generateInput(UiObject object) throws UiObjectNotFoundException;
}
