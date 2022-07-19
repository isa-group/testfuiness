package esadrcanfer.us.alumno.autotesting.generators;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;

import net.sf.extjwnl.JWNLException;

import java.util.ArrayList;
import java.util.List;

public class IntegerListGenerator extends AbstractGenerator{

    List<Integer> valueList;

    public IntegerListGenerator(List<Integer> valueList){
        this.valueList=valueList;
    }


    public Integer generate() throws JWNLException{

        Integer min = 0;
        Integer max = valueList.size()-1;
        Integer chosenIndex = (int)(Math.random()*(max-min+1)+min);

        Integer randomValue = valueList.get(chosenIndex);

        return randomValue;
    }
}
