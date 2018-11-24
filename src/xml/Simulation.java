package xml;

import model.rule.Rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Simple immutable value object representing Simulation product data.
 *
 * @author Robert C. Duvall
 * @author Scott McConnell
 */
public class Simulation {
    // name in data file that will indicate it represents data for this type of object
    public static final String DATA_TYPE = "Simulation";
    // field names expected to appear in data file holding values for this object
    // NOTE: simple way to create an immutable list
    public static final List<String> DATA_FIELDS = List.of(
            "simulationName",
            "title",
            "author",
            "shape",
            "edgeType",
            "gridLines",
            "cols",
            "rows",
            "configs",
            "neighbors",
            "colors",
            "description"
    );
    static private final int SIM_NAME = 0;
    static private final int SIM_TITLE = 1;
    static private final int SIM_AUTHOR = 2;
    static private final int SHAPE = 3;
    static private final int EDGE_TYPE = 4;
    static private final int GRID_LINES = 5;
    static private final int COLS = 6;
    static private final int ROWS = 7;
    static private final int CONFIGS = 8;
    static private final int NEIGHBORS = 9;
    static private final int COLORS = 10;
    static private final int DESCRIPTION = 11;
    static private final int NEIGHBOR_COORDINATES_SIZE = 3;
    // specific data values for this instance
    private String mySimulationName;
    private String myTitle;
    private String myAuthor;
    private String myShape;
    private String myEdgeType;
    private int myGridLines;
    private int myRows;
    private int myCols;
    private String myConfigs;
    private String myNeighbors;
    private String myColors;
    private Rule myRule;
    private String myDescription;
    private static final String DEFAULT_RESOURCE_PACKAGE = "English";
    private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
    // NOTE: keep just as an example for converting toString(), otherwise not used
    private Map<String, String> myDataValues;


    /**
     * Create game data from given data.
     */
    public Simulation(String simulationName, String title, String author, String shape, String edgeType, int gridLines, GeneralConfigurations configs) {
        mySimulationName = simulationName;
        myTitle = title;
        myAuthor = author;
        myShape = shape;
        myEdgeType = edgeType;
        myGridLines = gridLines;
        myRows = configs.getRows();
        myCols = configs.getCols();
        myNeighbors = configs.getNeighbors();
        myConfigs = configs.getConfigs();
        myColors = configs.getColors();
        myDescription = configs.getDescription();
        myRule = UI.ConfigurationManager.findSimulationType(mySimulationName);

        // NOTE: this is useful so our code does not fail due to a NullPointerException
        myDataValues = new HashMap<>();
    }

    /**
     * Create game data from a data structure of Strings.
     *
     * @param dataValues map of field names to their values
     */
    public Simulation(Map<String, String> dataValues) {
        this(dataValues.get(DATA_FIELDS.get(SIM_NAME)),
                dataValues.get(DATA_FIELDS.get(SIM_TITLE)),
                dataValues.get(DATA_FIELDS.get(SIM_AUTHOR)),
                dataValues.get(DATA_FIELDS.get(SHAPE)),
                dataValues.get(DATA_FIELDS.get(EDGE_TYPE)),
                Integer.parseInt(dataValues.get(DATA_FIELDS.get(GRID_LINES))),
                new GeneralConfigurations(Integer.parseInt(dataValues.get(DATA_FIELDS.get(COLS))),
                Integer.parseInt(dataValues.get(DATA_FIELDS.get(ROWS))),
                dataValues.get(DATA_FIELDS.get(CONFIGS)),
                dataValues.get(DATA_FIELDS.get(NEIGHBORS)),
                dataValues.get(DATA_FIELDS.get(COLORS)),
                dataValues.get(DATA_FIELDS.get(DESCRIPTION))));
        myDataValues = dataValues;
    }

    private int[][] stringToIntArray(String arrayString, int xSize, int ySize) {
        String[] integerStringArray = arrayString.split(",");
        int[] oneDimArray = new int[integerStringArray.length];
        int counter = 0;
        for (String s : integerStringArray) {
            oneDimArray[counter] = Integer.parseInt(s);
            counter++;
        }
        counter = 0;
        int[][] resultArray = new int[xSize][ySize];
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                resultArray[i][j] = oneDimArray[counter];
                counter++;
            }
        }
        return resultArray;
    }

    private int[][] generateRandomStates() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < (getCols() * getRows()); i++) {
            s.append(ThreadLocalRandom.current().nextInt(0, myRule.getNumStates())).append(",");
        }
        String resultString = s.toString();
        return stringToIntArray(resultString, myRows, myCols);
    }


    private boolean isValidSimName(String name) {
        String[] validSimulationNames = new String[]{"Game of Life", "Segregation", "Predator Prey", "Fire", "Rock Paper Scissors", "Foraging Ants", "Langtons Loop", "SugarScape"};

        for (String validName : validSimulationNames) {
            if (name.compareToIgnoreCase(validName) == 0) {
                return true;
            }
        }
        return false;
    }
    
    private boolean hasValidStates(int[][] cellStates) {
        for (int[] i : cellStates) {
            for (int j : i) {
                if (j < 0 || j > myRule.getNumStates()) {
                    return false;
                }
            }
        }
        return true;
    }

    // provide getters, not setters
    public String getSimulationName() throws XMLException {
        if (isValidSimName(mySimulationName)) {
            return mySimulationName;
        } else {
            throw new XMLException(myResources.getString("InvalidSimName"));
        }
    }

    public String getTitle() {
        if (myTitle !=  null && !myTitle.isEmpty()) {
            return myTitle;
        } else {
            return myResources.getString("NoTitle");
        }
    }

    public String getAuthor() {
        if (myAuthor !=  null && !myAuthor.isEmpty()) {
            return myAuthor;
        } else {
            return myResources.getString("NoAuthor");
        }
    }

    public String getShape() {
        if (myShape.compareToIgnoreCase("square") == 0 || myShape.compareToIgnoreCase("triangle") == 0) {
            if ((myShape.compareToIgnoreCase("triangle") == 0 && ((getRows() % 2) == 0) && ((getCols() % 2) == 0)) || myShape.compareToIgnoreCase("square") == 0) {
                return myShape;
            } else {
                throw new XMLException(myResources.getString("OddValuesWhenTriangle"));
            }
        } else {
            return "square";
        }
    }

    public String getEdgeType() {
        if (myEdgeType.compareToIgnoreCase("finite") == 0 || myEdgeType.compareToIgnoreCase("toroidal") == 0) {
            return myEdgeType;
        } else {
            return "finite";
        }
    }

    public boolean getGridLines() {
        if (myGridLines == 0 || myGridLines == 1) {
            return (myGridLines == 1);
        } else {
            return false;
        }
    }

    public int getCols() throws XMLException {
        if (myCols != 0) {
            return Math.abs(myCols);
        } else {
            throw new XMLException(myResources.getString("InvalidCols"));
        }
    }

    public int getRows() throws XMLException {
        if (myRows != 0) {
            return Math.abs(myRows);
        } else {
            throw new XMLException(myResources.getString("InvalidRows"));
        }
    }

    public int[][] getConfigs() throws XMLException {
        if (myConfigs.length() == 0) {
            return generateRandomStates();
        } else if (myConfigs.length() + 1 == 2 * getCols() * getRows()) {
            int[][] result = stringToIntArray(myConfigs, myRows, myCols);
            if (hasValidStates(result)) {
                return result;
            } else {
                throw new XMLException(myResources.getString("InvalidStates"));
            }
        } else {
            throw new XMLException(myResources.getString("InvalidCoordinates"));
        }
    }

    public int[][] getNeighborCoordinates() {
        return stringToIntArray(myNeighbors, NEIGHBOR_COORDINATES_SIZE, NEIGHBOR_COORDINATES_SIZE);
    }

    public String getColors() {
        if (myColors.split(",").length == myRule.getNumStates()) {
                return myColors;
            } else if (myColors.split(",").length > myRule.getNumStates()){
                throw new XMLException(myResources.getString("TooManyColors"));
            } else {
            throw new XMLException(myResources.getString("TooFewColors"));
        }
    }

    public String getDescription() {
        if (myDescription !=  null && !myDescription.isEmpty()) {
            return myDescription;
        } else {
            return myResources.getString("NoDescription");
        }
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        var result = new StringBuilder();
        result.append(DATA_TYPE + " {\n");
        for (var e : myDataValues.entrySet()) {
            result.append("  ").append(e.getKey()).append("='").append(e.getValue()).append("',\n");
        }
        result.append("}\n");
        return result.toString();
    }
}
