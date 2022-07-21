package esadrcanfer.us.alumno.autotesting.generators;

import androidx.test.uiautomator.UiObjectNotFoundException;

import net.sf.extjwnl.JWNLException;

import nl.flotsam.xeger.Xeger;

public class RandomRegexGenerator extends AbstractGenerator{

    protected String type;
    public RandomRegexGenerator(String type){
        this.type=type;
    }

    public String generate() throws JWNLException, UiObjectNotFoundException {

        String randomValue = null;

        if(type.equals("email")) {
            String regex = "[bcdfghjklmnpqrstvwxy][aeiou][bcdfghjklmnpqrstvwxy][aeiou][bcdfghjklmnpqrstvwxy][aeiou][bcdfghjklmnpqrstvwxy][aeiou][123456789][123456789](\\@)(gmail|hotmail|yahoo)(\\.)(com)";
            Xeger generator = new Xeger(regex);
            randomValue = generator.generate();
            assert randomValue.matches(regex);
        }else if(type.equals("phone")){
            String regex = "(95)[0123456789]( )[0123456789][0123456789][0123456789]( )[0123456789][0123456789][0123456789]";
            Xeger generator = new Xeger(regex);
            randomValue = generator.generate();
            assert randomValue.matches(regex);
        }
        return randomValue;
    }

}
