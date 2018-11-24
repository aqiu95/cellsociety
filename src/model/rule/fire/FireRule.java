package model.rule.fire;

import model.Cell;
import model.rule.Rule;

import java.util.List;
import java.util.Random;

/**
 * Extension of main.model.rule.Rule to apply rules specifically for Conway's Game Of Life
 * Returns nextState for myCell
 *
 * @author Scott McConnell skm44
 **/
public class FireRule extends Rule {
    static final int NUM_PASSES = 1;
    static final int BURNING = 2;
    static final int TREE = 1;
    static final int EMPTY = 0;
    static double PROB_CATCH = 0.7;

    public FireRule(){
        myNumStates = 3;
    }

    public int getPasses() {
        return NUM_PASSES;
    }


    public void setProbability(double prob){
        PROB_CATCH = prob;
    }

    public double getProbability(){
        return PROB_CATCH;
    }

    @Override
    public Class<Cell> getCellType() {
        return Cell.class;
    }

    public void applyRule(Cell cell, List<Cell> neighborsArray, int passNum) {
        boolean nearFire = false;
        //throw out diagonals
        for (Cell c : neighborsArray) {
            if (c.getRow() != cell.getRow() && cell.getCol() != cell.getCol()) {
                neighborsArray.remove(c);
            }
        }
        for (Cell neighbor : neighborsArray) {
            if (neighbor.getCurrentState() == BURNING) {
                nearFire = true;
            }
        }
        // find next state of tree cells
        if (cell.getCurrentState() == TREE) {
            if (nearFire && new Random().nextDouble() <= PROB_CATCH) {
                cell.setNextState(BURNING);
            }
        }
        // if cell is empty or burning, make it empty next
        else {
            cell.setNextState(EMPTY);
        }
    }
}
