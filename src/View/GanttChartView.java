package View;

import Model.Assessment;
import Model.GanttChart;
import Model.Module;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

// TODO: use date for x-axis
public class GanttChartView {

    public Scene load(Module module) {
        //Assessment[] assessments = new Assessment[module.getAssessments().values().size()];
        //module.getAssessments().values().toArray(assessments);
//        HashMap<Task,Task> tasks = new HashMap<>();
//        HashMap<Activity, Activity> activities = new HashMap<>();
//        HashMap<Milestone,Milestone> milestones = new HashMap<>();
        ArrayList<String> assessmentTitles = new ArrayList<>();
        ArrayList<XYChart.Series> chartEntry = new ArrayList<>();
        int i = 0;

        // For each assessment create a series
        for (Assessment assessment : module.getAssessments().values()) {
            XYChart.Series series = new XYChart.Series();
            assessmentTitles.add(assessment.getTitle());
            series.getData().add(new XYChart.Data(0, assessment.getDeadLine().toString(), new GanttChart.MetaData(1, "status-red")));

//            for(Task task : assessment.getTasks().values()){
//                Activity[] activities = new Activity[task.getActivities().size()];
//                task.getActivities().values().toArray(activities);
//                series.getData().add(new XYChart.Data(assessment, assessment.getDeadLine(), new GanttChart.MetaData((assessment.getCompletion() / 10),"status-red")));
//                for(Activity activity : activities){
//                    series.getData().add(new XYChart.Data(assessment, assessment.getDeadLine(), new GanttChart.MetaData((assessment.getCompletion() / 10),"status-purple")));
//                }
//            }
            //series.getData().add(new XYChart.Data(4, entry.getTitle(), new GanttChart.MetaData(6,"status-blue")));
            chartEntry.add(series);
            //series = new XYChart.Series();
            i++;
        }


        NumberAxis xAxis = new NumberAxis();
        CategoryAxis yAxis = new CategoryAxis();
        GanttChart ganttChart = new GanttChart<>(xAxis, yAxis);

        ganttChart.setTitle(module.getTitle() + " chart");
        ganttChart.setLegendVisible(false);
        ganttChart.setFrameHeight(50);

        xAxis.setLabel("");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        xAxis.setMinorTickCount(4);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);
        yAxis.setCategories(FXCollections.observableArrayList(assessmentTitles));

        for (XYChart.Series series1 : chartEntry) {
            ganttChart.getData().add(series1);
        }
        ganttChart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());

        //series.getData().add(new XYChart.Data(tasks.get, assessmentTitles.get(0), new GanttChart.MetaData( 1, "status-red")));

        ScrollPane pane = new ScrollPane();
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        Scene chartScene = new Scene(pane, 620, 350);
        pane.setContent(ganttChart);
        return chartScene;
    }
}