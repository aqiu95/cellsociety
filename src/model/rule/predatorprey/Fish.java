package model.rule.predatorprey;

import model.Cell;

public class Fish {

    public static final int ENERGY_YIELD = 2;
    Cell myCell;
    int myAge = 0;

    Fish(Cell cell) {
        myCell = cell;
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
