package model.rule.segregation;


import model.Cell;
import model.rule.Rule;

import java.util.List;

/**
 * Extension of main.model.rule.Rule to apply rules specifically for SegregationLife
 * Returns nextState for myCell
 *
 * @author Scott McConnell skm44
 **/

public class SegregationRule extends Rule {
    static final int NUM_PASSES = 3;
    static final int VACANT = 0;
    static final int BLUE = 1;
    static final int RED = 2;
    static final int UNSATISFIED = 3;
    static double PERCENT_SIMILAR_TOLERANCE = 0.3;

    private int unallocated_blue = 0;
    private int unallocated_red = 0;

    public SegregationRule(){
        myNumStates = 3;
    }

    public int getPasses() {
        return NUM_PASSES;
    }
    // percent of neighbors that must be similar for happiness

    public double getTolerance(){
        return PERCENT_SIMILAR_TOLERANCE;
    }

    public void setTolerance(double tolerance){
        PERCENT_SIMILAR_TOLERANCE = tolerance;
    }

    @Override
    public Class getCellType() {
        return Cell.class;
    }


    public void applyRule(Cell cell, List<Cell> neighborsArray, int passNum) {

        // find next state of occupied cells
        if (passNum == 0) {
            int similarNeighborsCount = 0;
            for (Cell neighbor : neighborsArray) {
                if (cell.getCurrentState() == neighbor.getCurrentState()) {
                    similarNeighborsCount += neighbor.getCurrentState();
                }
            }

            // percent of neighbors that are similar
            double percentSimilarNeighbors = similarNeighborsCount / neighborsArray.size();

            markUnsatisfied(cell, percentSimilarNeighbors);
        } else if (passNum == 1){
            allocateUnsatisfiedCells(cell);
        }
        else{
            resetToVacant(cell);
        }
    }

    private void resetToVacant(Cell cell) {
        if(cell.getNextState() == UNSATISFIED){
            if (unallocated_blue > 0) {
                unallocated_blue--;
                cell.setNextState(BLUE);
            } else if (unallocated_red > 0) {
                unallocated_red--;
                cell.setNextState(RED);
            }else{
                cell.setNextState(VACANT);
            }
        }
    }

    private void allocateUnsatisfiedCells(Cell cell) {
        if (cell.getNextState() == VACANT) {
            if (unallocated_blue > 0) {
                unallocated_blue--;
                cell.setNextState(BLUE);
            } else if (unallocated_red > 0) {
                unallocated_red--;
                cell.setNextState(RED);
            }
        }
    }

    private void markUnsatisfied(Cell cell, double percentSimilarNeighbors) {
        if (cell.getCurrentState() == RED || cell.getCurrentState() == BLUE) {
            if (percentSimilarNeighbors < PERCENT_SIMILAR_TOLERANCE) {
                if (cell.getCurrentState() == RED) {
                    unallocated_red++;
                    cell.setNextState(UNSATISFIED);
                } else if(cell.getCurrentState() == BLUE){
                    unallocated_blue++;
                    cell.setNextState(UNSATISFIED);
                }
            }
        }
    }
}
