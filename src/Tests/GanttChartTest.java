//package Tests;
//
//import Model.GanttChart;
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.collections.FXCollections;
//import javafx.embed.swing.JFXPanel;
//import javafx.scene.Scene;
//import javafx.scene.chart.CategoryAxis;
//import javafx.scene.chart.NumberAxis;
//import javafx.scene.chart.XYChart;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.paint.Color;
//import javafx.stage.Stage;
//import org.junit.Test;
//
//import java.util.Arrays;
//import java.util.Date;
//
///**
// * Created by Dinara Adilova on 05/05/2017.
// *
// */
//public class GanttChartTest {
//    @Test
//    public void start() throws Exception {
//        Thread thread = new Thread(() -> {
//            new JFXPanel(); // Initializes the JavaFx Platform
//            Platform.runLater(() -> {
//                Stage stage = new Stage();
//                stage.setTitle("Gantt Chart");
//
//                String[] tasks = new String[]{"Task1", "Task2"};
//
//                NumberAxis xAxis = new NumberAxis();
//                CategoryAxis yAxis = new CategoryAxis();
//                GanttChart chart = new GanttChart<>(xAxis, yAxis);
//
//
//                xAxis.setLabel("Time");
//                xAxis.setTickLabelFill(Color.CHOCOLATE);
//                //xAxis.setMinorTickCount(1);
//                //xAxis.setTickLabelGap(1);
//                xAxis.setTickLabelGap(3);
//                //xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(dates)));
//
//
//                yAxis.setLabel("");
//                yAxis.setTickLabelFill(Color.CHOCOLATE);
//                yAxis.setTickLabelGap(10);
//                yAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(tasks)));
//
//                chart.setTitle("Study Planner");
//                chart.setLegendVisible(false);
//                chart.setFrameHeight(25); //sets depth of block
//
//
//                XYChart.Series series1 = new XYChart.Series();
//                series1.getData().add(new XYChart.Data(new Date().getTime(), "Task1",
//                        new GanttChart.MetaData(new Date(2017,05,26).getTime() / 3600000, "status-green")));
//                series1.getData().add(new XYChart.Data(new Date(2017,05,27).getTime(),"Task1",
//                        new GanttChart.MetaData(new Date(2017,05,28).getTime() / 3600000,"status-purple")));
//
//                XYChart.Series series2 = new XYChart.Series();
//                series2.getData().add(new XYChart.Data(2, "Task2",
//                        new GanttChart.MetaData(50, "status-green")));
//
//                chart.getData().addAll(series1, series2);
//
//                //System.out.println(getClass().getResource("."));
//                chart.getStylesheets().add(getClass().getResource("/View/ganttchart.css").toExternalForm());
//
//                ScrollPane pane = new ScrollPane();
//                pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//                pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
//
//                Scene chartScene = new Scene(pane,620,350);
//                pane.setContent(chart);
//
//                stage.setScene(chartScene);
//                stage.show();
//            });
//        });
//        thread.start();// Initialize the thread
//        Thread.sleep(1000000);
//    }
//    private static void showChart(){
//
//    }
//    public static void main(String[] args) {
//    }
//}
