package UI;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Cell;
import model.CellManager;
import model.rule.Rule;

/**
 * This class manages the square UI grid.
 *
 * @author Allen Qiu (asq3)
 */
public class SquareGridUI extends GridUI {
    private static final int USABLE_WINDOW_SIZE = 500;
    private GridPane simulatorGridPane;
    private String[] myColors;
    private int myRows;
    private int myColumns;
    private CellManager myCellManager;
    private boolean myEdgelines;

    SquareGridUI(int[][] initialStates, int rows, int columns, String[] colors, Rule myRule, int[][] neighbors, String edgeType, boolean edgelines){
        myColors = colors;
        myRows = rows;
        myColumns = columns;
        myEdgelines = edgelines;
        myCellManager = new CellManager(rows, columns, initialStates, myRule, CellManager.SQUARE_GRID, neighbors, edgeType);
        simulatorGridPane = new GridPane();
        simulatorGridPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myColumns; j++) {
                simulatorGridPane.add(createCell(myColors[initialStates[i][j]]), i, j);
            }
        }
    }

    public void step(){
        myCellManager.nextGeneration();
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myColumns; j++) {
                BorderPane thisCellPane = (BorderPane) getNodeFromGridPane(i, j);
                Cell thisCell = myCellManager.getGrid().getCell(i, j);
                if (thisCellPane != null) {
                    updateCellAppearance(myColors[thisCell.getCurrentState()], thisCellPane);
                }
            }
        }
    }

    private BorderPane createCell(String color){
        BorderPane cell = new BorderPane();
        int cellSize = USABLE_WINDOW_SIZE / Math.max(myRows, myColumns);
        cell.setMinSize(cellSize, cellSize);
        if(myEdgelines){
            cell.setStyle("-fx-border-color: #000000;" +
                    "-fx-background-color: #" + color + ";" +
                    "-fx-border-width: 1;");
        }
        else{
            cell.setStyle("-fx-background-color: #" + color + ";");
        }
        cell.setOnMouseClicked(event -> toggleNextState(cell));
        return cell;

    }

    private void updateCellAppearance(String color, BorderPane myCell){
        if(myEdgelines){
            myCell.setStyle("-fx-border-color: #000000;" +
                    "-fx-background-color: #" + color + ";" +
                    "-fx-border-width: 1;");
        }
        else{
            myCell.setStyle("-fx-background-color: #" + color + ";");
        }
    }

    private void toggleNextState(BorderPane cell){
        int numStates = myColors.length;
        Cell thisCell = myCellManager.getGrid().getCell(GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell));
        this.setNextStates(thisCell, numStates);
        updateCellAppearance(myColors[thisCell.getNextState()], cell);
    }

    private Node getNodeFromGridPane(int col, int row) {
        for (Node node : simulatorGridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    public GridPane getGridPane(){
        return simulatorGridPane;
    }

    public int[] getCellStateList(){
        return myCellManager.getGrid().getStateList();
    }

    public String getCellStates(){
        return myCellManager.getGrid().getCellStates();
    }
}
