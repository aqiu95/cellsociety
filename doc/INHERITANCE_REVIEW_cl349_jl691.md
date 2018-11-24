Part 1
===
1. What is an implementation decision that your design is encapsulating (i.e., hiding) for other areas of the program?
    - model.CellManager is solely responsible for editing cell states; it passes the Rule object a cell and a list of its neighbors, and recieves
    the next state as an output, which it then passes to the cell.
2. What inheritance hierarchies are you intending to build within your area and what behavior are they based around?
    - Rule objects implement the model.Rule, since they all have the same method of taking in a cell and its neighbors
    and producing a next state. 
3. What parts within your area are you trying to make closed and what parts open to take advantage of this polymorphism you are creating?
    - By implementing rules as an interface, the simulation rules are open for extension, but the interaction with
    model.CellManager remain the same.
4. What exceptions (error cases) might occur in your area and how will you handle them (or not, by throwing)?
    - There is the isEdge() function in model.CellManager, which detects when a cell's neighborhood runs off
    the edge of the grid. Before applying rules, isEdge() is checked so that these edge cases can be
    easily handled. 
5. Why do you think your design is good (also define what your measure of good is)?
    - I think that my design is good because our components are well defined and are minimally dependent on each other.
    This means that we can work independently very easily, and when one part breaks, the other parts do not break. 

Part 2
===
1. How is your area linked to/dependent on other areas of the project?
    - The model depends on XML parser to get initial conditions, and it depends on the UI to call its methods. 
2. Are these dependencies based on the other class's behavior or implementation?
    - They're dependent on other classes passing in the right parameters. Otherwise, no. 
3. How can you minimize these dependencies?
    - The messiest interaction is with the implementation of each specific simulation, and these interactions 
    are simplified by model.CellManager interacting with an interface. 
4. Go over one pair of super/sub classes in detail to see if there is room for improvement. 
    - model.GameOfLifeRule implements model.Rule. The biggest weakness of this design is that the rule is only aware of 
    its local neighborhood, making rulesets like Segregation inelegant to implement. 
5. Focus on what things they have in common (these go in the superclass) and what about them varies (these go in the subclass).
    - Rules basically have nothing in common except that they generate a nextState for each cell. 
    This is why our group chose to use an interface instead of an abstract class. 

Part 3
===
1. Come up with at least five use cases for your part (most likely these will be useful for both teams).
    - Game of Life, Segregation, Predator Prey, Spreading Fire, Wator Life, any other CA simulation. 
2. What feature/design problem are you most excited to work on?
    - I'm excited to implement all the different rulesets. 
3. What feature/design problem are you most worried about working on?
    - I'm dreading having to contribute to the UI. UI design is always weird and fiddly. We also
realized that we may have to create an XML file editor in order to create test simulations. 