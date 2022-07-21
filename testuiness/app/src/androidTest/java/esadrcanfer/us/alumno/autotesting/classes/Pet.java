package esadrcanfer.us.alumno.autotesting.classes;

public class Pet {

    private String name;
    private String breed;
    private Integer age;
    private Integer chipNumber;

    public Pet() {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.chipNumber = chipNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getChipNumber() {
        return chipNumber;
    }

    public void setChipNumber(Integer chipNumber) {
        this.chipNumber = chipNumber;
    }

    public String toString(){
        return name + ", "+ breed + ", chip: " + chipNumber;
    }
}
