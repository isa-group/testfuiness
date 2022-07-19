package esadrcanfer.us.alumno.autotesting.generators;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;

import net.sf.extjwnl.JWNLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractGenerator<T> {

    public abstract T generate() throws UiObjectNotFoundException, JWNLException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, InstantiationException;

}
