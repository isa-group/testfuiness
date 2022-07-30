package esadrcanfer.us.alumno.autotesting.inagraph.actions;

import android.os.Environment;
import android.util.Log;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;

import net.sf.extjwnl.JWNLException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

import esadrcanfer.us.alumno.autotesting.generators.DictionaryBasedValueGenerator;
import esadrcanfer.us.alumno.autotesting.generators.GivenValueGenerator;
import esadrcanfer.us.alumno.autotesting.generators.IncrementDoubleGenerator;
import esadrcanfer.us.alumno.autotesting.generators.IntegerListGenerator;
import esadrcanfer.us.alumno.autotesting.generators.ProbabilityGenerator;
import esadrcanfer.us.alumno.autotesting.generators.RandomIntegerGenerator;
import esadrcanfer.us.alumno.autotesting.generators.RandomRegexGenerator;
import esadrcanfer.us.alumno.autotesting.generators.ReflectionGenerator;
import esadrcanfer.us.alumno.autotesting.util.ReadUtil;

public class TextInputGenerator extends InputGenerator {

    private Long seed;
    private String defaultValue;
    private String type;
    private String firstGeneratorParameter;
    private String secondGeneratorParameter;


    public TextInputGenerator(Long seed, String defaultValue, String type, String generatorParameters){
        this.seed = seed;
        if(defaultValue == null) defaultValue = "";
        this.setDefaultValue(defaultValue);
        this.type=type;

        String[] parameters = parseParameters(generatorParameters);

        this.firstGeneratorParameter=parameters[0];
        this.secondGeneratorParameter=parameters[1];
    }

    public String generateInput(UiObject object) throws UiObjectNotFoundException{
        String res = getDefaultValue();
        List<Integer> integerList = new ArrayList<>();

        if(type.equals("numberFromList") || type.equals("numberFromProbabilityList")){
            String[] numbers = firstGeneratorParameter.split(",");
            for(String number: numbers){
                integerList.add(Integer.parseInt(number));
            }
        }
        try {

            if (getSeed() > 0 || defaultValue.isEmpty()) {

                switch (type) {
                    case "numberFromList":
                        IntegerListGenerator integerRes = new IntegerListGenerator(integerList);
                        res = integerRes.generate().toString();
                        break;
                    case "numberFromProbabilityList":
                        ProbabilityGenerator integerProbabilityRes = new ProbabilityGenerator(integerList);
                        res = integerProbabilityRes.generate().toString();
                        break;
                    case "number":
                        int min=Integer.parseInt(firstGeneratorParameter);
                        int max=Integer.parseInt(secondGeneratorParameter);
                        RandomIntegerGenerator numberRes = new RandomIntegerGenerator(min, max);
                        res = numberRes.generate().toString();
                        break;
                    case "regex":
                        RandomRegexGenerator regexRes = new RandomRegexGenerator(firstGeneratorParameter);
                        res = regexRes.generate();
                        break;
                    case "word":
                        int numberOfWords = Integer.parseInt(firstGeneratorParameter);
                        DictionaryBasedValueGenerator dictionaryRes = new DictionaryBasedValueGenerator(numberOfWords, Math.abs(new Random().nextLong()));
                        res = dictionaryRes.generate();
                        break;
                    case "given":
                        String givenWord = firstGeneratorParameter;
                        GivenValueGenerator givenRes = new GivenValueGenerator(givenWord);
                        res = givenRes.generate();
                        break;
                    case "increment":
                        int givenNumber = Integer.parseInt(firstGeneratorParameter);
                        IncrementDoubleGenerator incrementRes = new IncrementDoubleGenerator(givenNumber);
                        res = incrementRes.generate().toString();
                        break;
                    case "reflection":
                        ReflectionGenerator reflectionRes = new ReflectionGenerator(firstGeneratorParameter);
                        res = reflectionRes.generate();
                        break;
                }
            }
        }catch (JWNLException e) {
            e.printStackTrace();
        }

        if(object != null) object.setText(res);

        return res;
    }

    public Long getSeed() {
        return seed;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    private String[] parseParameters(String generatorParameters){

        String[] parameters = new String[2];

        if(generatorParameters.startsWith("(") && generatorParameters.endsWith(")")){

            parameters = generatorParameters.replace("(", "")
                    .replace(")", "")
                    .split(",");

        }else if(generatorParameters.contains("[") && generatorParameters.contains("]")){

            parameters[0] = generatorParameters.replace("[", "").replace("]", "");
            parameters[1] = "";
        }else{
            parameters[0] = generatorParameters;
            parameters[1] = "";
        }

        return parameters;
    }
}
