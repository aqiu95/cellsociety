package model.rule;


import model.Cell;

import java.util.List;

//TODO: change documentation

/**
 * * The Rule interface can be extended to create different simulations. Implementing rules as an interface
 * provides a flexible system for implementing different kinds of simulations. Rule does not edit cells, it only
 * generates the next state; editing cells is the sole responsibility of main.model.CellManager.
 * <p>
 * * @author Scott McConnell skm44
 * * @author Christopher Lin cl349
 **/
public abstract class Rule {
    protected int myNumStates;
    public int getNumStates(){
        return myNumStates;
    }

    public abstract void applyRule(Cell myCell, List<Cell> myNeighbors, int passNum);

    public abstract Class getCellType();

    public abstract int getPasses();
}
