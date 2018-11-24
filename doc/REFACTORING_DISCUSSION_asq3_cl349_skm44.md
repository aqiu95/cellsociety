Refactoring Discussion
===

Duplication Refactoring 
---
No duplicate code was found 

Checklist Refactoring
---
- Minor coding style changes were made
- applyRule() was refactored in SegregationRule and PredatorPreyRule by pulling out sections of code into seperate methods
    - This makes the code less complex and more readable
- In UI.UIManager, the dimensions of the window and the padding/inset were changed to constants to avoid magic numbers
- Removed unused imports in UI.UIManager and imported specific classes from inside the model folder instead of all
- Created a new readConfiguration method in UI.UIManager to shorten the initializeWindow method
- Removed magic values in:
    - Simulation.java
    - GameOfLifeRule.java
    - PredatorPrey.java
- Changed initializeGrid to CellManager constructor
    - There is no case where you would want to create a CellManager without creating a grid
- Changed ArrayList return types to List
    - Code is now more general and easier to modify

General Refactoring
---
- Ran the IntelliJ formatting tool
    - This should take care of all the Java Notes issues
- The code smells should have been taken care of by the ApplyRule refactors.

Longest Methods
---
- The longest method was the ApplyRule for Segregation, which was taken care of in the refactor where chunks were pulled out into methods.