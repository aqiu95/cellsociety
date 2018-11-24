Design Overview
===
## Design Goals
* Easily create new simulations that are limited in as few ways as possible
* Easily extend the UI and configuration options

## Adding New Features
* The code that we wrote is very flexible in terms of creating new rules for new simulations, especially if the simulation is simple, such as Game of Life.
    * Adding simulations
        * The ConfigurationManager and Simulation classes need to be altered to add the name of the simulation
        * A new, valid XML configuration file needs to be added
        * The findSimulationType method in ConfigurationManager needs to be updated to include the new simulation type.
        * A new rule needs to be created in model and implemented to follow the rules of the simulation.
    * To add new tags:
        * Additional constructors need to be updated for SquareGridUI, TriangleGridUI, CellManager classes
        * The tag needs to be added as a variable and in the constructor for GeneralConfigurations in the XML packages, and a get method needs to be added
        * Simulation also needs the tag added as a variable, an index needs to be assigned to it, the value needs to be set in the constructor, and a getter method needs to be implemented
## Major Design Choices

* One design decision was made regarding which package should handle the saving of the current configuration
    * We debated over whether the xml package or the UI package should do this
    * We ultimately settled on having the ConfigurationManager do this
        * The Simulation class was already very long
        * The ConfigurationManager is the point of origin since it can get the current states from the CellManager directly and stores all of the other parameters so no passing of variables was needed
        * A UI interface was needed anyways to prompt the user where to save the file
    * I prefer it the way it is because currently the xml class serves only as an input device from the XML file and making that a two way street would be confusing
        * I don't thinking saving the state to a XML file is beyond the scope of the ConfigurationManager
* Another design decision that was made was to make the findSimulationType method in ConfigurationManager
    * This was because the ConfigurationManager needs to know what type of simulation it is so it can create a new rule of that type, but the XML parser also needs it so that it can reject invalid simulation names
    * The XML parser can't pass it as a String since the ConfigurationManager needs to instantiate a rule and creating the rule and passing it along would go against the scope of the XML parser
    * We ultimately settled upon making the method static so that the XML parser can also use it
        * This is to avoid duplicated code
        * Although this makes for confusing dependencies, we decided this was the most efficient way to do it and keep packages separate
    * I would prefer for it to be done in the XML parser since static methods can be confusing, but it is beyond the scope of the parser
* Another design decision was whether the rule subclass should edit cells' current state
    * This would require that the Grid object be passed into these rule subclasses
    * We concluded that the rule subclasses should only return the integer value of nextState for each cell, and the CellManager would take these nextState values and go on to actually change the state of the Cell objects
        * This made our Grid object much more encapsulated because only the UIManager and CellManager have access to the Grid

## Assumptions/Decisions

* There is a finite number of different simulation types that can be run since these are manually added in several classes using just a series of if statements
* If multiple windows are opened, the user would like to pause or play all of them at the same time
* If the initial configuration in the XML is left blank, the user would like randomly generated initial configurations
* If the gridType in the XML is left blank, then the user would like no grid lines to show
* If the shape in the XML is left blank, then the user would like square cell shapes
* The user would like the simulation to start immediately after pressing start
* The colors used for the simulation will be visible on the graph
* There is a set 1x, 0.5x, and 2x speed rather than letting users endlessly double or halve the speed every time these buttons are pressed 