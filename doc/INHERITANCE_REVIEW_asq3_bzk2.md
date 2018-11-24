model.Cell Society: Inheritance Review
===

Allen Qiu (asq3), Brooke Keene (bzk2)

Part 1
===

1. What is an implementation decision that your design is encapsulating (i.e., hiding) for other areas of the program?
    * By creating a main/runner class, the initial setup methods are hidden from the runner class, which adds flexibility to the runner in case more different types of setups are needed in the future (eg. for the first lab, there were several different Hangman types/classes and by changing just one line in the runner, you could easily change them all.)
2. What inheritance hierarchies are you intending to build within your area and what behavior are they based around?
    * One example of inheritance I plan on implementing surrounds the use of messages. There are multiple types of messages that may need to be communicated to the user and these can take on various forms: error, warning, or success. For example, an error message for an invalid XML file or a success message that the XML file was loaded. A superclass could be created for the message and subclasses can be used to display different icons and titles depending on the message. Another similar area where the same logic can be applied is for buttons (confirm vs. cancel, etc.).
3. What parts within your area are you trying to make closed and what parts open to take advantage of this polymorphism you are creating?
    * The initial GUI setup should be closed since that is the code that runs at first and does not need to be run again or interacted with by any other class.
4. What exceptions (error cases) might occur in your area and how will you handle them (or not, by throwing)?
    * The GUI prompts the user to load in the XML code and then uses the XML parser class to check this XML code. If the parser detects anything wrong with the XML code, it will throw an error at the user (using the inheritance described in question 2) until they load a proper XML file. 
5. Why do you think your design is good (also define what your measure of good is)?
    * It uses the principles we have learned in class including but not limited to separation of classes, keeping code dry, shy, and telling the other guy, and inheritance. Good is code that uses the concepts we have learned in class.

Part 2
===
1. How is your area linked to/dependent on other areas of the project?
    * The GUI must update the display of the cells based off of our model.CellManager, which updates the status of the cells based on the simulation that is being run. It must also interact with the XML parser during initialization so that it can check if the XML code that the user has loaded is valid.
2. Are these dependencies based on the other class's behavior or implementation?
    * These dependencies are based off of the other class's behavior. At the end of the day, for the GUI, no matter how the XML parser or model.CellManager is implemented, as long as it is functional, the GUI will also be able to functional.
3. How can you minimize these dependencies?
    * The GUI is the link between the backend code and the user so these dependencies are necessary for it to serve its purpose.
4. Go over one pair of super/sub classes in detail to see if there is room for improvement. Focus on what things they have in common (these go in the superclass) and what about them varies (these go in the subclass).
    * For the messages super class that I am planning on creating, all of the messages will share a similar behavior (popping up over the current GUI window), some sort of image on the left side (an X or check or exclamation mark), some text message, and one or more buttons. In the subclasses, specific implementation will determine what the text is, what kind of image will appear, and what the button options are.

Part 3
===
1. Come up with at least five use cases for your part (most likely these will be useful for both teams).
    * The user will input a local XML file and the GUI will call the XML parser, which will check it for validity and return an error if it is not valid
    * The user will be able to press pause and the GUI will interact with model.CellManager to pause the simulation
    * The user will be able to press play and the GUI will interact with model.CellManager to resume the simulation after it has been paused
    * The user will be able to press the next frame button after the game has been paused and the GUI will interact with the model.CellManager to move on to the next frame but keep the simulation pused
    * The user will be able to press the reset button and this will interact with the model.CellManager to restart the simulation
2. What feature/design problem are you most excited to work on?
    * I am excited to use CSS to style the GUI. I have worked with CSS in the past for webpages and would like to use it to create an aesthetically pleasing GUI, and my familiarity with CSS will certainly help.
3. What feature/design problem are you most worried about working on?
    * I am worried that the differences in JavaFX between this project and the game will be large enough to throw me off. I entirely learned all of my JavaFX concepts with the context of the game and trying to use it in a different context and project may throw me off.