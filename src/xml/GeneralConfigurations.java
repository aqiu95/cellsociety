package xml;


/**
 * Placeholder class for Simulation parameters from XML file
 *
 * @author Scott McConnell
 */
public class GeneralConfigurations {
    private int myRows;
    private int myCols;
    private String myConfigs;
    private String myNeighbors;
    private String myColors;
    private String myDescription;

    public GeneralConfigurations(int rows, int cols, String configs, String neighbors, String colors, String description) {
        myRows = rows;
        myCols = cols;
        myConfigs = configs;
        myNeighbors = neighbors;
        myColors = colors;
        myDescription = description;
    }

    public int getRows() {
        return myRows;
    }

    public int getCols() {
        return myCols;
    }

    public String getConfigs() {
        return myConfigs;
    }

    public String getNeighbors() {
        return myNeighbors;
    }

    public String getColors() {
        return myColors;
    }

    public String getDescription() {
        return myDescription;
    }
}

