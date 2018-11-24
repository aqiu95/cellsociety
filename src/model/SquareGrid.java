package model;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SquareGrid extends Grid {

    SquareGrid(int rowSize, int colSize, int[][] initialConditions, int edgeType, int numStates, Class<Cell> cellType) {
        super(rowSize, colSize, edgeType, numStates, cellType);

        myGrid = new Cell[rowSize][colSize];
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                try {
                    myGrid[i][j] = cellType.getDeclaredConstructor(int.class, int.class, int.class).newInstance(i, j, initialConditions[i][j]);
                } catch (Exception e) {
                    System.out.println(e);
                }
                myStateList[initialConditions[i][j]] += 1;
            }
        }
    }

    @Override
    public List getNeighbors(Cell cell, int[][] neighborMask) {
        ArrayList<Cell> neighbors = new ArrayList<>();
        for (int i = cell.getRow() - 1; i <= cell.getRow() + 1; i++) {
            for (int j = cell.getCol() - 1; j <= cell.getCol() + 1; j++) {
                if (getNeighborCell(i, j) != null && neighborMask[i-(cell.getRow()-1)][j-(cell.getCol()-1)] == 1) {
                        neighbors.add(getNeighborCell(i, j));
                }
            }
        }
        return neighbors;
    }




    protected Cell getFiniteCell(int row, int col) {
        if (row <= 0 || col <= 0 || row >= myGrid.length - 1 || col >= myGrid[0].length - 1) {
            return null;
        } else {
            return myGrid[row][col];
        }
    }

    protected Cell getWrappingCell(int row, int col) {
        int adjustedRow;
        int adjustedCol;
        if (row < 0) {
            adjustedRow = getRowSize() + row;
        } else if (row > getRowSize() - 1) {
            adjustedRow = row - getRowSize();
        } else {
            adjustedRow = row;
        }
        if (col < 0) {
            adjustedCol = (getColSize()) + col;
        } else if (col > getColSize() - 1) {
            adjustedCol = col - getColSize();
        } else {
            adjustedCol = col;
        }

        return myGrid[adjustedRow][adjustedCol];
    }
}
