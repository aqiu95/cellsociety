package UI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.rule.Rule;
import xml.Simulation;
import xml.XMLException;
import xml.XMLParser;

import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class manages the UI and interacts with the XML parser and the cell manager.
 *
 * @author Allen Qiu (asq3)
 */
public class UIManager extends Application {
    private static final double MILLISECOND_DELAY = 300;
    private static final int WINDOW_SIZE = 600;
    private static final int PADDING_SIZE = 10;
    private static final int HORIZONTAL_GUI_GAP = 5;
    private static final int VERTICAL_GUI_GAP = 5;
    private static final String DEFAULT_RESOURCE_PACKAGE = "English";

    private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
    private File chosen;
    private ConfigurationManager myConfigurationManager;
    private Timeline currentAnimation;
    private ArrayList<Timeline> myAnimations = new ArrayList<>();
    private ArrayList<Stage> myStages = new ArrayList<>();
    private GraphManager myGraph;
    private GridUI myGridUI;
    private Rule myRule;

    public static void main(String args[]) {
        launch(args);
    }

    public void start(Stage stage) {
        myStages.add(stage);

        Text fileName = new Text(myResources.getString("NoFile"));

        Button loadButton = new Button(myResources.getString("SelectButton"));
        loadButton.setOnAction(event -> {
            chooseFile(myStages.get(0));
            if (chosen != null) {
                fileName.setText(chosen.getName());
            }
        });

        Timeline animation = new Timeline();
        currentAnimation = animation;
        myAnimations.add(animation);

        Button startButton = new Button(myResources.getString("StartButton"));
        startButton.setOnAction(event -> createSimulator(myStages.get(0), animation));

        GridPane myGridPane = new GridPane();
        myGridPane.setMinSize(WINDOW_SIZE, WINDOW_SIZE);
        myGridPane.setPadding(new Insets(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));
        myGridPane.setVgap(VERTICAL_GUI_GAP);
        myGridPane.setHgap(HORIZONTAL_GUI_GAP);
        myGridPane.setAlignment(Pos.CENTER);

        myGridPane.add(new Text(myResources.getString("ChooseFileLabel")), 0, 0);
        myGridPane.add(fileName, 0, 1);
        myGridPane.add(loadButton, 1, 1);
        myGridPane.add(startButton, 0, 2);

        Scene myScene = new Scene(myGridPane);

        myStages.get(0).setTitle(myResources.getString("WindowTitle"));
        myStages.get(0).setScene(myScene);
        myStages.get(0).show();
    }

    private void createSimulator(Stage myStage, Timeline animation) {
        initializeWindow(myStage);

        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.playFromStart();

    }

    private void initializeWindow(Stage myStage) {
        try {
            readConfiguration();
            myRule = ConfigurationManager.findSimulationType(myConfigurationManager.getSimulationName());
            myGraph = new GraphManager(myConfigurationManager.getColors().length, myConfigurationManager.getColors());
            myGridUI = myConfigurationManager.createGridUI(myRule);

            GridPane rootPane = new GridPane();
            rootPane.setPadding(new Insets(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));
            rootPane.setMinSize(WINDOW_SIZE, WINDOW_SIZE);
            rootPane.setAlignment(Pos.CENTER);

            rootPane.add(createTitleBlock(), 0, 0);
            rootPane.add(myGridUI.getGridPane(), 0, 1);
            rootPane.add(createControlsBlock(myStage), 0, 2);

            myStage.setScene(new Scene(rootPane));
        } catch (XMLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, e.getMessage());
            alert.setTitle(myResources.getString("XMLAlertTitle"));
            alert.showAndWait();
            chooseFile(myStage);
            initializeWindow(myStage);
        }
    }

    private void chooseFile(Stage myStage) {
        FileChooser myFileChooser = new FileChooser();
        myFileChooser.setTitle(myResources.getString("ChooserWindowTitle"));
        myFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
        myFileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chosen = null;
        while(chosen == null){
            chosen = myFileChooser.showOpenDialog(myStage);
        }
    }

    private void readConfiguration() {
        Simulation configs = new XMLParser("media").getSimulation(chosen);
        myConfigurationManager = new ConfigurationManager(configs.getConfigs(), configs.getRows(), configs.getCols(), configs.getTitle(), configs.getSimulationName(), configs.getAuthor(), configs.getColors().split(","), configs.getShape(), configs.getEdgeType(), configs.getNeighborCoordinates(), configs.getDescription(), configs.getGridLines());
    }

    private GridPane createTitleBlock() {
        Label myTitle = new Label(myConfigurationManager.getTitle());
        Label myAuthor = new Label(myConfigurationManager.getAuthor());
        Label mySimulationName = new Label(myConfigurationManager.getSimulationName());
        Label myDescription = new Label(myConfigurationManager.getDescription());

        GridPane displayInfo = new GridPane();
        displayInfo.setAlignment(Pos.CENTER);
        displayInfo.setPadding(new Insets(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));
        displayInfo.add(myTitle, 0, 0);
        GridPane.setHalignment(myTitle, HPos.CENTER);
        displayInfo.add(myAuthor, 0, 1);
        GridPane.setHalignment(myAuthor, HPos.CENTER);
        displayInfo.add(mySimulationName, 0, 2);
        GridPane.setHalignment(mySimulationName, HPos.CENTER);
        displayInfo.add(myDescription, 0, 3);
        GridPane.setHalignment(myDescription, HPos.CENTER);

        return displayInfo;
    }

    private FlowPane createControlsBlock(Stage myStage) {
        FlowPane controls = new FlowPane();
        controls.setPadding(new Insets(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));
        controls.setAlignment(Pos.CENTER);

        Button play = new Button(myResources.getString("PlayButton"));
        play.setOnAction(event -> {
            for(Timeline thisAnimation:myAnimations){
                thisAnimation.play();
            }
        });

        Button pause = new Button(myResources.getString("PauseButton"));
        pause.setOnAction(event -> {
            for(Timeline thisAnimation:myAnimations){
                thisAnimation.pause();
            }
        });

        Button step = new Button(myResources.getString("StepButton"));
        step.setOnAction(event -> step());

        Button halfSpeed = new Button(myResources.getString("HalfSpeedButton"));
        halfSpeed.setOnAction(event -> {
            for(Timeline thisAnimation:myAnimations){
                thisAnimation.setRate(.5);
            }
        });

        Button normalSpeed = new Button(myResources.getString("NormalSpeedButton"));
        normalSpeed.setOnAction(event -> {
            for(Timeline thisAnimation:myAnimations){
                thisAnimation.setRate(1);
            }
        });

        Button doubleSpeed = new Button(myResources.getString("DoubleSpeedButton"));
        doubleSpeed.setOnAction(event -> {
            for(Timeline thisAnimation:myAnimations){
                thisAnimation.setRate(2);
            }
        });

        Button newSimulation = new Button(myResources.getString("NewSimulation"));
        newSimulation.setOnAction(event -> {
            currentAnimation.stop();
            GridUI testGridUI = myGridUI;
            Timeline animation = new Timeline();
            myAnimations.add(animation);
            new Thread(() -> {
                System.out.println(Thread.currentThread().getId());
                Platform.runLater(() -> {
                    Task<Void> stepInBackground = new Task<>() {
                        @Override
                        public Void call() {
                            var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> bgStep());
                            animation.setCycleCount(Timeline.INDEFINITE);
                            animation.getKeyFrames().add(frame);
                            animation.playFromStart();
                            return null;
                        }

                        private void bgStep() {
                            testGridUI.step();
                            myGraph.updateGraph(myGridUI.getCellStateList());
                        }
                    };
                    new Thread(stepInBackground).start();
                    newSimulation();
                });
            }).start();
        });

        Button toggleChart = new Button(myResources.getString("ToggleChart"));
        toggleChart.setOnAction(event -> myGraph.toggleChart());

        /*
          Didn't figure out threads in time to figure this out :(((
        Button reset = new Button(myResources.getString("Reset"));
        reset.setOnAction(event -> {
            myGraph.closeChart();
            createSimulator(myStage, myAnimationMap.get(Thread.currentThread().getId()));
        });
         */

        Button save = new Button(myResources.getString("Save"));
        save.setOnAction(event -> myConfigurationManager.saveConfiguration(myResources, myStage, myGridUI));

        controls.getChildren().addAll(play, pause, step, halfSpeed, normalSpeed, doubleSpeed, newSimulation, toggleChart, save);
        controls.getChildren().addAll(myConfigurationManager.getSliders(myRule));

        return controls;
    }

    private void newSimulation(){
        Timeline animation = new Timeline();
        currentAnimation = animation;
        myAnimations.add(animation);

        Stage newStage = new Stage();
        chooseFile(newStage);
        newStage.show();
        myStages.add(newStage);

        myGraph.closeChart();
        createSimulator(newStage, animation);
    }

    private void step() {
        myGridUI.step();
        myGraph.updateGraph(myGridUI.getCellStateList());
    }
}
