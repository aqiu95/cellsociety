Cell Society
====

This project implements a cellular automata simulator.

Names:
Allen Qiu (asq3)

Christopher Lin (cl349)

Scott McConnell (skm44)

### Timeline

Start Date: 09.07.18

Finish Date: 10.01.18

Hours Spent: 100 total

### Primary Roles
Christopher: Model/Simulation

Allen: UI

Scott: XML/Config


### Resources Used
http://necsi.org/postdocs/sayama/sdsr/java/loops.java

XMLException.java, XMLParser.java, and Game.java from spike_cellsociety

### Running the Program

Main class: UI.UIManager

Data files needed: Fire.xml, ForagingAnts.xml, GameOfLife.xml, PredatorPrey.xml, RPS.xml, Segregation.xml, LangtonsLoop.xml

To randomize the initial configuration, leave the configs tag blank in the XML file.

To show gridlines, enter 1, to hide gridlines, enter 0

Interesting data files: ForagingAnts.xml (play at 2x speed), RPS.xml

Features implemented:
- Differing shapes for square neighborhoods
- Square and triangular grids
- Finite and toroidal edges
- Simulations
    - Fire
    - Foraging Ants
    - Game of Life
    - Predator Prey (wator world)
    - Rock Paper Scissors (bacterial growth)
    - Segregation
    - Langton's Loop
- Config file error checking
- Specific and completely random initial conditions
- Configurable: 
    - Title
    - Author
    - Cell shapes
    - Edge types
    - Grid lines presence
    - Initial configuration or random configuration
    - Active neighbors
    - Cell colors
    - Simulation description
- Exceptions handled:
    - Empty string passed in (returns “0”)
    - Invalid or no simulation type given (throws exception)
    - Invalid cell states given (throws exception)
    - Cell locations given that are outside the bounds of the grid’s size (throws exception)
    - No initial configurations given (randomizes based on a total number of locations to occupy)
    - Negative value rows/columns integers (gets absolute valued)
    - Zero value rows/columns integers (throws exception)
    - Too many / not enough colors given (throws exception)
    - Invalid shape type string given (defaults to square)
    - Odd rows/columns parameter when triangle is chosen (throws exception)
    - Invalid edge type string given (defaults to finite)
    - Invalid gridlines integer given (default hides gridlines)
    - Null/Empty Title, Author, Description strings even (default shows “No Title” / “No Author” / “No Description”)
- Cell population graph
- Dynamic simulation parameter changing
- Interaction with cells through the UI
- Multiple windows
    - Graph only works for last graph opened

Assumptions or Simplifications:
- RPS uses simplified methods for autoinducer decay and release
- Foraging ants does not care about ant orientation
- Langton's loop is always run on a square grid

Known Bugs:
- Triangle grid does not allow selection of neighbors
- Langton's loop does not simulate properly

Extra credit:

### Notes

- For multiple windows, the graph only displays for the last window opened. Additionally, a previously implemented reset button no longer works and was commented out.
    - We chose to create new windows because we wanted to give the users the flexibility to arrange the simulations as they saw fit.
    - They do not need to be the same simulation type because we did not end getting the graph to work.
    - All the controls apply to all graphs because it was easier and we did not have enough time to implement some sort of mapping of thread IDs to specific Timelines.
        - We attempted this and thread ID conventions were very confusing and we ran out of time.
- For Foraging Ants, it is best to run it at 2x speed. The ants themselves our invisible, and the white cells represent cells where pheromones have built up.

### Impressions

- Many of the assumptions that were made during design week turned out to be untrue. 

- JavaFX and Java threads are super confusing because anything that needs to interact with the UI needs to run in the main JavaFX thread
    - If a background thread needs to modify the UI, you need to call Platform.runLater()
    - Threads in general were also confusing with threads, tasks, executors, etc.