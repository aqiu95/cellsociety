package model.rule.gameoflife;

import model.Cell;
import model.rule.Rule;

import java.util.List;

/**
 * Extension of main.model.rule.Rule to apply rules specifically for Conway's Game Of Life
 * Returns nextState for myCell
 *
 * @author Scott McConnell skm44
 **/
public class GameOfLifeRule extends Rule {
    static final int NUM_PASSES = 1;
    static final int ALIVE = 1;
    static final int DEAD = 0;
    static final int POPULATION_MAX = 3;
    static final int POPULATION_MIN = 2;
    static final int PERFECT_FOR_BIRTH = 3;

    public int getPasses() {
        return NUM_PASSES;
    }

    public GameOfLifeRule(){
        myNumStates = 2;
    }

    @Override
    public Class<Cell> getCellType() {
        return Cell.class;
    }

    public void applyRule(Cell cell, List<Cell> neighborsArray, int passNum) {
        int liveNeighborsCount = 0;
        for (Cell neighbor : neighborsArray) {
            liveNeighborsCount += neighbor.getCurrentState();
        }

        // find next state of live cells
        if (cell.getCurrentState() == ALIVE) {
            if (liveNeighborsCount < POPULATION_MIN || liveNeighborsCount > POPULATION_MAX) {
                cell.setNextState(DEAD);
            } else {
                cell.setNextState(ALIVE);
            }
            // find next state of dead cells
        } else {
            if (liveNeighborsCount == PERFECT_FOR_BIRTH) {
                cell.setNextState(ALIVE);
            } else {
                cell.setNextState(DEAD);
            }
        }
    }
}
