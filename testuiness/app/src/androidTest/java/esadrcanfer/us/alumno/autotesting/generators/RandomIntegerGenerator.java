package esadrcanfer.us.alumno.autotesting.generators;

import net.sf.extjwnl.JWNLException;

public class RandomIntegerGenerator extends AbstractGenerator{

    public static Integer min;
    public static Integer max;

    public RandomIntegerGenerator(Integer min, Integer max){
        this.min = min;
        this.max = max;
    }
    public Integer generate() throws JWNLException {
        Integer randomValue = (int)(Math.random()*(max-min+1)+min);
        return randomValue;
    }

}
