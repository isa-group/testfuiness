package esadrcanfer.us.alumno.autotesting.generators;

import net.sf.extjwnl.JWNLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProbabilityGenerator extends IntegerListGenerator{

    List<Integer> integerList;
    Map<Integer, Integer> probabilityMap = new TreeMap<>();

    public ProbabilityGenerator(List<Integer> integerList){
        super(integerList);
        this.integerList = integerList;
    }

    public Integer generate() throws JWNLException {

        List<Integer> probabilities = new ArrayList<>();
        Integer index = 0;

        for (Integer i: integerList){
            Integer prob = (int)(Math.random()*(100-1+1)+1); //Creamos una lista de probabilidades y la rellenamos con tantos numeros random
            probabilities.add(prob);                         //entre 1 y 100 como elementos haya en integerList. Luego añadimos al mapa las parejas de valores
            probabilityMap.put(i, probabilities.get(index));
            index++;
        }


        Integer min = 0;
        Integer max = 0;
        Integer randomValue = 0;

        for(int value: probabilityMap.values()) //Este for recorre el mapa, y va sumando los valores de probabilidad asociados a cada valor en el mapa.
            max = max+value;

        Integer chosenNumber = (int)(Math.random()*(max-min+1)+min); //Esto coge un número aleatorio entre 0 y el max;

        Integer acc = 0; //Inicializamos un valor acumulativo que será igual a la suma en cada iteración de los valores del mapa en el siguiente for.

        for (Map.Entry<Integer, Integer> entry : probabilityMap.entrySet()) {

            if(chosenNumber >= acc && chosenNumber < acc+entry.getValue()){
                randomValue = entry.getKey();
            }
            acc = acc+entry.getValue(); //Sumamos el valor actual a lo que ya llevamos

        }
        return randomValue;
    }

}
