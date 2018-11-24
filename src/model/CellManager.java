package model;

import model.rule.Rule;

import java.util.List;

/**
 * This class is responsible for managing the myGrid and editing cell states
 *
 * @author Christopher Lin cl349
 **/
public class CellManager {
    //Constants
    public static final int SQUARE_GRID = 0;
    public static final int TRIANGLE_GRID = 1;
    Rule myActiveRule;
    private Grid myGrid;
    private int myRowSize;
    private int myColSize;
    private int[][] myNeighbors;


    //Initializes the myGrid of cells
    public CellManager(int rows, int cols, int[][] initConditions, Rule activeRule, int gridType, int[][] neighbors, String edgeType) {
        myRowSize = rows;
        myColSize = cols;
        if (gridType == SQUARE_GRID) {
            if(edgeType.compareToIgnoreCase("toroidal") == 0){
                myGrid = new SquareGrid(rows, cols, initConditions, Grid.TOROIDAL, activeRule.getNumStates(), activeRule.getCellType());
            }
            else {
                myGrid = new SquareGrid(rows, cols, initConditions, Grid.FINITE, activeRule.getNumStates(), activeRule.getCellType());
            }
        }
        else if (gridType == TRIANGLE_GRID){
            if(edgeType.compareToIgnoreCase("toroidal") == 0){
                myGrid = new TriangleGrid(rows, cols, initConditions, Grid.TOROIDAL, activeRule.getNumStates(), activeRule.getCellType());
            }
            else {
                myGrid = new TriangleGrid(rows, cols, initConditions, Grid.FINITE, activeRule.getNumStates(), activeRule.getCellType());
            }
        }
        myActiveRule = activeRule;
        myNeighbors = neighbors;
    }

    public Grid getGrid() {
        return myGrid;
    }

    //Second pass sets currentState to nextState
    public void updateCells() {
        for (int i = 0; i < myRowSize; i++) {
            for (int j = 0; j < myColSize; j++) {
                Cell myCurrentCell = myGrid.getCell(i, j);
                myGrid.myStateList[myCurrentCell.getCurrentState()]--;
                myCurrentCell.setCurrentState(myCurrentCell.getNextState());
                myGrid.myStateList[myCurrentCell.getCurrentState()]++;
            }
        }
    }

    public static void throwOutDiagonals(Cell cell, List<Cell> neighborsArray) {
        for (int i = 0; i < neighborsArray.size(); i++) {
            if (neighborsArray.get(i).getCol() != cell.getCol() && neighborsArray.get(i).getRow() != cell.getRow()) {
                neighborsArray.remove(neighborsArray.get(i));
            }
        }
    }

    public void nextGeneration() {
        int numPasses = myActiveRule.getPasses();
        //default all nextState to currentState to make some simulations easier
        for (int i = 0; i < myRowSize; i++) {
            for (int j = 0; j < myColSize; j++) {
                myGrid.getCell(i, j).setNextState(myGrid.getCell(i, j).getCurrentState());
            }
        }

        //apply rules
        for (int k = 0; k < numPasses; k++) {
            for (int i = 0; i < myRowSize; i++) {
                for (int j = 0; j < myColSize; j++) {
                    Cell currentCell = myGrid.getCell(i, j);
                    List neighborList = myGrid.getNeighbors(currentCell, myNeighbors);
                    myActiveRule.applyRule(currentCell, neighborList, k);
                }
            }
        }
        updateCells();
    }


}
