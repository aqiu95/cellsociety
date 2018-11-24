package UI;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * This class manages the graphing of the UI.
 *
 * @author Allen Qiu (asq3)
 */
public class GraphManager {
    private static final int WINDOW_HEIGHT = 300;
    private static final int WINDOW_WIDTH = 1000;
    private ArrayList<XYChart.Series> mySeries = new ArrayList<>();
    private int time = 0;
    private Stage myStage;
    private boolean stageVisible = false;

    GraphManager(int numSeries, String[] colors) {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Steps");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Population");
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setCreateSymbols(false);
        lineChart.setLegendVisible(false);
        for(int i=0;i<numSeries;i++){
            mySeries.add(new XYChart.Series());
        }
        Scene scene  = new Scene(lineChart, WINDOW_WIDTH, WINDOW_HEIGHT);
        for(int i=0;i<mySeries.size();i++){
            XYChart.Series myCurrentSeries = mySeries.get(i);
            lineChart.getData().add(myCurrentSeries);
            myCurrentSeries.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: #" + colors[i] + ";");
        }
        myStage = new Stage();
        myStage.setTitle("CA Simulator");
        myStage.setX(0);
        myStage.setY(0);
        myStage.setScene(scene);
    }

    public void updateGraph(int[] values){
        for(int i=0;i<mySeries.size();i++){
            mySeries.get(i).getData().add(new XYChart.Data(time, values[i]));
        }
        time++;
    }

    public void toggleChart(){
        if(stageVisible){
            stageVisible = false;
            myStage.hide();
        }
        else {
            stageVisible = true;
            myStage.show();
        }
    }

    public void closeChart(){
        myStage.close();
    }
}
