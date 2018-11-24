package model.rule.predatorprey;

import model.Cell;

public class Shark {
    final static int STARTING_ENERGY = 3;
    Cell myCell;
    int myAge = 0;
    int myEnergy;


    Shark(Cell cell) {
        myCell = cell;
        myEnergy = STARTING_ENERGY;
    }

    public int getEnergy() {
        return myEnergy;
    }

    public void setEnergy(int myEnergy) {
        this.myEnergy = myEnergy;
    }

    public int getAge() {
        return myAge;
    }

    public void setAge(int age) {
        myAge = age;
    }


    public Cell getCell() {
        return myCell;
    }

    public void setCell(Cell cell) {
        this.myCell = cell;
    }
}
