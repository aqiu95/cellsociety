package model.rule.rps;

public class Bacteria {
    public int getType() {
        return myType;
    }

    int myType;
    int myAge;

    public void incAge(){
        myAge++;
    }

    public int getAge() {
        return myAge;
    }

    public static final int MAX_AGE = 10;
    Bacteria(int type){
        myType = type;
        myAge = 0;
    }
}
