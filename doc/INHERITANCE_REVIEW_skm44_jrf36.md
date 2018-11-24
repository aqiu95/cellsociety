# model.Cell Society: Inheritance Review Questions/Answers (skm44, jrf36)

Part 1
=======
1. What is an implementation decision that your design is encapsulating (i.e., hiding) for other areas of the program?
    * The behavior of each cell is hidden from the grid, so the grid just exists as a data structure that houses each cell and gives them their respective locations. It doesn't actually interact with the cells behavior in any way. The simulator code is uniform, so regardless of what class of cell it's dealing with, it's all run with the same simulator. All the behavior is hidden from the simulator, but when the simulator is running, it can change the states of the cells.
2. What inheritance hierarchies are you intending to build within your area and what behavior are they based around?
    * We have a cell class, a parent rule class, and subclasses for each individual rule (Game of Life, etc.).
3. What parts within your area are you trying to make closed and what parts open to take advantage of this polymorphism you are creating?
    * The simulation updates are going to be open, but the methods that run each subclass of the cell will be closed.
4. What exceptions (error cases) might occur in your area and how will you handle them (or not, by throwing)?
    * When there is a collision (two cells trying to go to the same place), the simulator decides which one will go to which place using a randomization.
5. Why do you think your design is good (also define what your measure of good is)?
    * Our code is very shy, and it does a good job of telling the other guy, especially between the cells and the simulator.

Part 2
=======
1. How is your area linked to/dependent on other areas of the project?
    * I'm writing a superclass for the cells, so each simulation has a different kind of cell that is a subclass of that superclass.
2. Are these dependencies based on the other class's behavior or implementation?
    * No, the subclasses' behavior is predetermined within the class itself (e.g. there will be a separate FireCell).
3. How can you minimize these dependencies?
    * By trying to encompass as much of the behavior into the superclass as possible.
4. Go over one pair of super/sub classes in detail to see if there is room for improvement.
    * My cell superclass has updateState(), getNeighbors(), etc. that will be passed into each of the cell subclasses. The subclasses either override the previous methods or define exactly how the methods should work.
5. Focus on what things they have in common (these go in the superclass) and what about them varies (these go in the subclass).
    * The updateNeighbors() may vary so I might take that out of the superclass, but getNeighbors, position, etc. will remain constant.

Part 3
=======

1. Come up with at least five use cases for your part (most likely these will be useful for both teams).
    * The simulator can update the grid. Implementing the subclasses. Adding a new simulation with a different type of cell.
2. What feature/design problem are you most excited to work on?
    * Excited to get into the subclass behaviors and flesh those out, since that's the most interesting part of the project to me.
3. What feature/design problem are you most worried about working on?
    * Worried about the simulator class encompassing too much, I don't want it to become cluttered. May need to split it up. Won't know how until we start to implement it.