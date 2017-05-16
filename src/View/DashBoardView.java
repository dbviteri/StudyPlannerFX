package View;

import Controller.SemesterController;
import Model.*;
import Utils.StageHandler;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;

/**
 * Created by Didac on 08/05/2017.
 */
public class DashBoardView extends SemesterController {

    private static final int COMPLETED_COLUMN = 2;
    private static final int UPCOMING_COLUMN = 1;
    private static final int MISSED_COLUMN = 0;

    private static int ROW_INDEX_COMPLETED = 0;
    private static int ROW_INDEX_UPCOMING = 0;
    private static int ROW_INDEX_MISSED = 0;

    @FXML
    GridPane dashboardGrid;
    @FXML
    GridPane completedDeadlineGrid;
    @FXML
    GridPane upcomingDeadlineGrid;
    @FXML
    GridPane missedDeadlineGrid;

    //private DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

    private DoubleProperty doubleProperty = new SimpleDoubleProperty();

    public void load() {
        User user = dbhandler.getUserSession();
        //SemesterProfile semesterProfile = MasterController.getSemester(user.getId());
        loadSemester(user.getId());

        Map<Module, Module> modules = dbhandler.getSemesterSession().getModules();

        dashboardGrid.setId("dashboardGrid");
        upcomingDeadlineGrid.setId("upcomingGrid");

        // Decide what pane should the assignments go based on deadline
        Date date = new Date();
        // For each module
        for (Map.Entry<Module, Module> moduleEntry : modules.entrySet()) {
            // Find the related assessments for that module

            Map<Assessment, Assessment> assessments = moduleEntry.getValue().getAssessments();
            for (Map.Entry<Assessment, Assessment> assessmentEntry : assessments.entrySet()) {
                Label moduleInfo = new Label("MODULE: " + moduleEntry.getValue().getTitle() + ": \n\n");
                // For each module create a label with the module name
                Assessment assessment = assessmentEntry.getValue();

                // Create a list of labels of the size of assessments
                // If compareTo is more than 0, date is after the deadline
                // Should go to missed deadlines

                // If compareTo is less than 0, date is before the deadline
                // Should go to upcoming deadlines
                moduleInfo.setText(
                        moduleInfo.getText() +
                                "ASSESSMENT: " + assessment.toString() + "\n" + "Deadline : "
                                + assessment.getDeadLine() + "\n" +
                                "Progress for this assessment: \n"
                );

                if (date.compareTo(assessment.getDeadLine()) < 0) {
                    Button button = new Button(moduleEntry.getValue().getTitle() + "'s \n ganttchart");
                    button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    button.setWrapText(true);
                    button.setOnAction(event -> openChart(moduleEntry.getValue()));

                    ProgressBar progressBar = new ProgressBar(0);
                    progressBar.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    System.out.println(assessment.getCompletion());
                    progressBar.setProgress(assessment.getCompletion() / 100);

                    assessment.completionProperty().addListener((observable, oldValue, newValue) -> {
                        progressBar.setProgress(assessment.getCompletion()/100);
                    });
//                    Bounds upcomingDeadlinesLblBounds = upcomingDeadlinesLbl.getBoundsInLocal();
//                    Point2D labelLocation = upcomingDeadlinesLbl.localToScreen()
                    upcomingDeadlineGrid.addColumn(0, moduleInfo);
                    //upcomingDeadlineGrid.addColumn(UPCOMING_COLUMN, assessmentInfo);
                    upcomingDeadlineGrid.addColumn(0, progressBar);
                    upcomingDeadlineGrid.addRow(ROW_INDEX_UPCOMING, button);
                    //upcomingDeadlineGrid.getChildren().get(ROW_INDEX_UPCOMING).setStyle("-fx-background-color: cornsilk; -fx-alignment: center;");
                    //upcomingDeadlineGrid.setVgap(20);
                    //upcomingDeadlinesLbl.setText(assessment.getTitle());

                    ROW_INDEX_UPCOMING += upcomingDeadlineGrid.impl_getColumnCount();
                } else if (date.compareTo(assessment.getDeadLine()) > 0) {
                    missedDeadlineGrid.add(moduleInfo, MISSED_COLUMN, ROW_INDEX_MISSED);
                    //missedDeadlineGrid.setVgap(20);
                    //missedDeadlinesLbl.setText(assessment.getTitle());
                    ROW_INDEX_MISSED++;
                }

                //assessmentInfo.setPadding(new Insets(0, 0, 20, 0));
                //System.out.println(date.compareTo(assessment.getDeadLine()));
            }


        }

//        // Re-fit the gridpane after adding the new rows based on the largest row index
        int rowsAdded = (ROW_INDEX_COMPLETED > ROW_INDEX_MISSED) ? ROW_INDEX_COMPLETED : ROW_INDEX_MISSED;
        rowsAdded = (rowsAdded > ROW_INDEX_UPCOMING) ? rowsAdded : ROW_INDEX_UPCOMING;

//        ColumnConstraints columnConstraints = new ColumnConstraints();
//        columnConstraints.setHalignment(HPos.RIGHT);
//        upcomingDeadlineGrid.getColumnConstraints().add(columnConstraints);
//
        completedDeadlineGrid.setVgap(20);
        upcomingDeadlineGrid.setVgap(20);
        missedDeadlineGrid.setVgap(20);
//
        completedDeadlineGrid.setHgap(20);
        upcomingDeadlineGrid.setHgap(20);
        missedDeadlineGrid.setHgap(20);
        //completedDeadlineGrid.setAlignment(Pos.CENTER);


        //System.out.println(SemesterController.find(user.getId()).getEndDate());
        //System.out.println("can call");
        ROW_INDEX_COMPLETED = 0;
        ROW_INDEX_UPCOMING = 0;
        ROW_INDEX_MISSED = 0;

    }
    public void openChart(Module module){

        Assessment[] assessments = new Assessment[module.getAssessments().values().size()];
        module.getAssessments().values().toArray(assessments);
//        HashMap<Task,Task> tasks = new HashMap<>();
//        HashMap<Activity, Activity> activities = new HashMap<>();
//        HashMap<Milestone,Milestone> milestones = new HashMap<>();
        ArrayList<String> assessmentTitles = new ArrayList<>();
        ArrayList<XYChart.Series> chartEntry = new ArrayList<>();
        XYChart.Series series = new XYChart.Series();
        int i = 0;
        for(Assessment entry : assessments) {
            Task[] tasks = new Task[entry.getTasks().size()];
            entry.getTasks().values().toArray(tasks);
            assessmentTitles.add(entry.getTitle());
            for(Task task : tasks){
                Activity [] activities = new Activity[task.getActivities().size()];
                task.getActivities().values().toArray(activities);
                series.getData().add(new XYChart.Data(0, task.getTitle(), new GanttChart.MetaData(2,"status-red")));
                for(Activity activity : activities){
                    series.getData().add(new XYChart.Data(2, activity.getTitle(), new GanttChart.MetaData(4,"status-purple")));
                }
            }
            series.getData().add(new XYChart.Data(4, entry.getTitle(), new GanttChart.MetaData(6,"status-blue")));
            chartEntry.add(series);
            series = new XYChart.Series();
        }

        NumberAxis xAxis = new NumberAxis();
        CategoryAxis yAxis = new CategoryAxis();
        GanttChart ganttChart = new GanttChart<>(xAxis,yAxis);

        ganttChart.setTitle(module.getTitle()+" chart");
        ganttChart.setLegendVisible(false);
        ganttChart.setFrameHeight( 50);

        xAxis.setLabel("");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        xAxis.setMinorTickCount(4);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);
        yAxis.setCategories(FXCollections.observableArrayList(assessmentTitles));

        for(XYChart.Series series1 : chartEntry){
            ganttChart.getData().add(series1);
        }
        ganttChart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());

        //series.getData().add(new XYChart.Data(tasks.get, assessmentTitles.get(0), new GanttChart.MetaData( 1, "status-red")));

        ScrollPane pane = new ScrollPane();
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        Scene chartScene = new Scene(pane,620,350);
        pane.setContent(ganttChart);

        Stage stage = new Stage();
        stage.setScene(chartScene);
        stage.show();
    }
}
