package model.rule.rps;

import model.Cell;

public class RPSCell extends Cell {
    public double getRock_level() {
        return rock_level;
    }

    public void setRock_level(double rock_level) {
        this.rock_level = rock_level;
    }

    public double getPaper_level() {
        return paper_level;
    }

    public void setPaper_level(double paper_level) {
        this.paper_level = paper_level;
    }

    public double getScissors_level() {
        return scissors_level;
    }

    public void setScissors_level(double scissors_level) {
        this.scissors_level = scissors_level;
    }

    public Bacteria getBacteria() {
        return myBacteria;
    }

    public void setBacteria(Bacteria myBacteria) {
        this.myBacteria = myBacteria;
    }

    double rock_level;
    double paper_level;
    double scissors_level;
    Bacteria myBacteria;
    public RPSCell(int row, int col, int initState) {
        super(row, col, initState);
        myBacteria = null;
        rock_level = 0;
        paper_level = 0;
        scissors_level = 0;
    }


}
