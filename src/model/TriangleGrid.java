package model;

import java.util.ArrayList;
import java.util.List;

public class TriangleGrid extends Grid {

    TriangleGrid(int rowSize, int colSize, int[][] initialConditions, int edgeType, int numStates, Class<Cell> cellType) {
        super(rowSize, colSize, edgeType, numStates, cellType);
        if(rowSize % 2 != 0 && colSize != 0){
            throw new IllegalArgumentException("Must have even rows!");
        }
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
    public List<Cell> getNeighbors(Cell cell, int[][] neighborMask) {
        ArrayList<Cell> neighbors = new ArrayList<>();
        //get neighbors on same row
        for(int i = cell.getCol()-2; i <= cell.getCol() + 2; i++){
            if(getNeighborCell(cell.getRow(), i) != null){
                neighbors.add(getNeighborCell(cell.getRow(), i));
            }
        }
        //get neighbors in row w/ 3 neighbors
        int rowDisplacement;
        //triangle faces up
        if(((cell.getCol()%2 == 0) && (cell.getRow()%2 == 0)) || ((cell.getCol()%2 == 1) && (cell.getRow()%2 == 1))){
            rowDisplacement = -1;
        }
        else{
            rowDisplacement = 1;
        }
        for(int i = cell.getCol()-1; i <= cell.getCol()+1; i++){
            if(getNeighborCell(cell.getRow()+ rowDisplacement, i)!= null){
                neighbors.add(getNeighborCell(cell.getRow()+ rowDisplacement, i));

            }
        }

        //get neighbors in row w/5 neighbors
        if(((cell.getCol()%2 == 0) && (cell.getRow()%2 == 1)) || ((cell.getCol()%2 == 1) && (cell.getRow()%2 == 0))){
            rowDisplacement = 1;
        }
        else{
            rowDisplacement = -1;
        }
        for(int i = cell.getCol()-2; i <= cell.getCol()+2; i++){
            if(getNeighborCell(cell.getRow()+ rowDisplacement, i) != null){
                neighbors.add(getNeighborCell(cell.getRow()+ rowDisplacement, i));
            }
        }
        return neighbors;
    }

    @Override
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

    @Override
    protected Cell getFiniteCell(int row, int col) {
        if (row <= 0 || col <= 0 || row >= myGrid.length - 1 || col >= myGrid[0].length - 1) {
            return null;
        }
        else{
            return myGrid[row][col];
        }
    }
}
