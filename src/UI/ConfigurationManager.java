package UI;

import javafx.scene.control.Slider;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.rule.Rule;
import model.rule.fire.FireRule;
import model.rule.foragingants.ForagingAntsRule;
import model.rule.gameoflife.GameOfLifeRule;
import model.rule.langtonsloop.LangtonsLoopRule;
import model.rule.predatorprey.PredatorPreyRule;
import model.rule.rps.RPSRule;
import model.rule.segregation.SegregationRule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class manages the configuration of the animation.
 *
 * @author Allen Qiu (asq3)
 */
public class ConfigurationManager {
    private int[][] myInitialStates;
    private int myRows;
    private int myColumns;
    private String myTitle;
    private String mySimulationName;
    private String myDescription;
    private String myAuthor;
    private String[] myColors;
    private String myShape;
    private String myEdgeType;
    private int[][] myNeighbors;
    private boolean myGridlines;

    ConfigurationManager(int[][] initialStates, int rows, int columns, String title, String name, String author, String[] colors, String shape, String edgeType, int[][] neighbors, String description, boolean gridlines){
        myInitialStates = initialStates;
        myRows = rows;
        myColumns = columns;
        myTitle = title;
        mySimulationName = name;
        myAuthor = author;
        myColors = colors;
        myShape = shape;
        myEdgeType = edgeType;
        myNeighbors = neighbors;
        myDescription = description;
        myGridlines = gridlines;
    }

    public int[][] getInitialStates(){
        return myInitialStates;
    }

    public int getRows(){
        return myRows;
    }

    public int getColumns(){
        return myColumns;
    }

    public String getDescription(){
        return myDescription;
    }

    public String getTitle(){
        return myTitle;
    }

    public String getSimulationName(){
        return mySimulationName;
    }

    public String getAuthor(){
        return myAuthor;
    }

    public String[] getColors(){
        return myColors;
    }

    public String getShape(){
        return myShape;
    }

    public String getEdgeType(){
        return myEdgeType;
    }

    public int[][] getNeighbors(){
        return myNeighbors;
    }

    public static Rule findSimulationType(String name) {
        Rule rule;
        if (name.compareToIgnoreCase("Game of Life") == 0) {
            rule = new GameOfLifeRule();
        } else if (name.compareToIgnoreCase("Predator Prey") == 0) {
            rule = new PredatorPreyRule();
        } else if (name.compareToIgnoreCase("Fire") == 0) {
            rule = new FireRule();
        } else if (name.compareToIgnoreCase("Rock Paper Scissors") == 0) {
            rule = new RPSRule();
        } else if (name.compareToIgnoreCase("Foraging Ants") == 0) {
            rule = new ForagingAntsRule();
        } else if (name.compareToIgnoreCase("Langtons Loop") == 0) {
            rule = new LangtonsLoopRule();
        } else {
            rule = new SegregationRule();
        }
        return rule;
    }

    public ArrayList<Slider> getSliders(Rule myRule){
        ArrayList<Slider> mySliders = new ArrayList<>();
        if(mySimulationName.compareToIgnoreCase("Fire") == 0){
            Slider fireProb = new Slider(0, 1, ((FireRule)myRule).getProbability());
            fireProb.setShowTickLabels(true);
            fireProb.setShowTickMarks(true);
            fireProb.setBlockIncrement(.1);
            fireProb.valueProperty().addListener((observable, oldValue, newValue) -> ((FireRule) myRule).setProbability(newValue.doubleValue()));
            mySliders.add(fireProb);
        }
        else if(mySimulationName.compareToIgnoreCase("Segregation") == 0){
            Slider tolerance = new Slider(0, 1, ((SegregationRule)myRule).getTolerance());
            tolerance.setShowTickLabels(true);
            tolerance.setShowTickMarks(true);
            tolerance.setBlockIncrement(.1);
            tolerance.valueProperty().addListener((observable, oldValue, newValue) -> ((SegregationRule) myRule).setTolerance(newValue.doubleValue()));
            mySliders.add(tolerance);
        }
        else if(mySimulationName.compareToIgnoreCase("Predator Prey") == 0){
            Slider fishTime = new Slider(1, 30, ((PredatorPreyRule)myRule).getFishReproductionTime());
            Slider sharkTime = new Slider(1, 30, ((PredatorPreyRule)myRule).getSharkReproductionTime());
            fishTime.setShowTickLabels(true);
            sharkTime.setShowTickLabels(true);
            fishTime.setShowTickMarks(true);
            sharkTime.setShowTickLabels(true);
            sharkTime.setBlockIncrement(1);
            fishTime.setBlockIncrement(1);
            fishTime.valueProperty().addListener((observable, oldValue, newValue) -> ((PredatorPreyRule) myRule).setFishReproductionTime(newValue.intValue()));
            sharkTime.valueProperty().addListener((observable, oldValue, newValue) -> ((PredatorPreyRule)myRule).setSharkReproductionTime(newValue.intValue()));
            mySliders.add(sharkTime);
            mySliders.add(fishTime);
        }
        return mySliders;
    }

    public GridUI createGridUI(Rule myRule){
        if(myShape.compareToIgnoreCase("square") == 0){
            return new SquareGridUI(myInitialStates, myRows, myColumns, myColors, myRule, myNeighbors, myEdgeType, myGridlines);
        }
        return new TriangleGridUI(myInitialStates, myRows, myColumns, myColors, myRule, myNeighbors, myEdgeType, myGridlines);
    }

    public void saveConfiguration(ResourceBundle myResources, Stage myStage, GridUI myGridUI){
        FileChooser myFileChooser = new FileChooser();
        myFileChooser.setTitle(myResources.getString("SaveFileTitle"));
        myFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
        myFileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File chosen = null;
        while(chosen == null){
            chosen = myFileChooser.showSaveDialog(myStage);
        }
        try {
            FileWriter fileWriter = new FileWriter(chosen);
            fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<data media=\"Simulation\">\n" +
                    "    <simulationName>" + mySimulationName + "</simulationName>\n" +
                    "    <title>" + myTitle + "</title>\n" +
                    "    <author>" + myAuthor + "</author>\n" +
                    "    <shape>" + myShape + "</shape>\n" +
                    "    <edgeType>" + myEdgeType + "</edgeType>\n" +
                    "    <gridLines>" + getGridlines(myGridlines) + "</gridLines>\n" +
                    "    <rows>" + myRows + "</rows>\n" +
                    "    <cols>" + myColumns + "</cols>\n" +
                    "    <configs>" + myGridUI.getCellStates() + "</configs>\n" +
                    "    <neighbors>" + matrixToString(myNeighbors) + "</neighbors>\n" +
                    "    <colors>" + String.join(",", myColors) + "</colors>\n" +
                    "    <description>" + myDescription + "</description>\n" +
                    "</data>");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String matrixToString(int[][] matrix){
        String s = "";
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[i].length;j++){
                s += matrix[i][j] + ",";
            }
        }
        s = s.substring(0, s.length()-1);
        return s;
    }

    private int getGridlines(boolean gridlines){
        if(myGridlines){
            return 1;
        }
        return 0;
    }
}
