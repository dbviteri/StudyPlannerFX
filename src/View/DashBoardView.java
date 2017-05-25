package View;

import Controller.DatabaseHandler;
import Model.Assessment;
import Model.Module;
import Model.SemesterProfile;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * Created by Didac on 08/05/2017.
 */
public class DashBoardView {

    @FXML
    GridPane dashboardGrid;
    @FXML
    VBox completedDeadlineBox;
    @FXML
    VBox upcomingDeadlineBox;
    @FXML
    VBox missedDeadlineBox;

    @FXML
    ScrollPane completeScrollPane;
    @FXML
    ScrollPane upcomingScrollPane;
    @FXML
    ScrollPane missedScrollPane;
    private DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

    public void load() {
        completedDeadlineBox.prefWidthProperty().bind(completeScrollPane.widthProperty());
        upcomingDeadlineBox.prefWidthProperty().bind(completeScrollPane.widthProperty());
        missedDeadlineBox.prefWidthProperty().bind(completeScrollPane.widthProperty());

        completedDeadlineBox.setPadding(new Insets(16, 16, 16, 16));
        upcomingDeadlineBox.setPadding(new Insets(16, 16, 16, 16));
        missedDeadlineBox.setPadding(new Insets(16, 16, 16, 16));

        //DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
        //SemesterProfile semesterProfile = MasterController.getSemester(user.getId());

        Map<Module, Module> modules = databaseHandler.getSemesterSession().getModules();
        // Decide what pane should the assignments go based on deadline
        Date date = new Date();
        // For each module
        for (Module module : modules.values()) {
            // Find the related assessments for that module

            Map<Assessment, Assessment> assessments = module.getAssessments();
            for (Map.Entry<Assessment, Assessment> assessmentEntry : assessments.entrySet()) {
                Label moduleInfo = new Label("MODULE: " + module.getTitle() + ": \n\n");
                // For each module create a label with the module name
                Assessment assessment = assessmentEntry.getValue();

                moduleInfo.setText(
                        moduleInfo.getText() +
                                "ASSESSMENT: " + assessment.toString() + "\n" + "Deadline : "
                                + assessment.getDeadLine() + "\n" +
                                "Progress for this assessment: \n"
                );
                moduleInfo.setMaxWidth(Double.MAX_VALUE);

                Button button = new Button(module.getTitle() + "'s \n ganttchart");
                button.setMaxWidth(Double.MAX_VALUE);
                button.setWrapText(true);
                button.setOnAction(event -> openChart(module));

                ProgressBar progressBar = new ProgressBar(0);
                progressBar.setMaxWidth(Double.MAX_VALUE);
                progressBar.progressProperty().bind(assessment.completionProperty().divide(100));

                assessment.completionProperty().addListener(((observable, oldValue, newValue) -> {
                    if (oldValue.doubleValue() < 100) {
                        if (newValue.doubleValue() == 100) {
                            removeElements(upcomingDeadlineBox, moduleInfo, progressBar, button);
                            addElements(completedDeadlineBox, moduleInfo, progressBar, button);
                        }
                    } else if (oldValue.doubleValue() == 100) {
                        if (newValue.doubleValue() < 100 && date.compareTo(assessment.getDeadLine()) < 0) {
                            removeElements(completedDeadlineBox, moduleInfo, progressBar, button);
                            addElements(upcomingDeadlineBox, moduleInfo, progressBar, button);
                        } else {
                            removeElements(completedDeadlineBox, moduleInfo, progressBar, button);
                            addElements(missedDeadlineBox, moduleInfo, progressBar, button);
                        }
                    }
                }));

                // If compareTo is more than 0, date is after the deadline
                // Should go to missed deadlines

                // If compareTo is less than 0, date is before the deadline
                // Should go to upcoming deadlines
                if (date.compareTo(assessment.getDeadLine()) < 0 && assessment.getCompletion() < 100) {
                    addElements(upcomingDeadlineBox, moduleInfo, progressBar, button);
                } else if (date.compareTo(assessment.getDeadLine()) > 0 && assessment.getCompletion() < 100) {
                    addElements(missedDeadlineBox, moduleInfo, progressBar, button);
                } else {
                    addElements(completedDeadlineBox, moduleInfo, progressBar, button);
                }
            }
        }
    }

    private void addElements(VBox box, Node... nodes) {
        for (Node node : nodes) {
            box.getChildren().add(node);
        }
    }

    private void removeElements(VBox box, Node... nodes) {
        for (Node node : nodes) {
            box.getChildren().remove(node);
        }
    }

//    public void openChart(Module module){
//
//        Assessment[] assessments = new Assessment[module.getAssessments().values().size()];
//        module.getAssessments().values().toArray(assessments);
////        HashMap<Task,Task> tasks = new HashMap<>();
////        HashMap<Activity, Activity> activities = new HashMap<>();
////        HashMap<Milestone,Milestone> milestones = new HashMap<>();
//        ArrayList<String> assessmentTitles = new ArrayList<>();
//        ArrayList<XYChart.Series> chartEntry = new ArrayList<>();
//        XYChart.Series series = new XYChart.Series();
//        int i = 0;
//        for(Assessment entry : assessments) {
//            Task[] tasks = new Task[entry.getTasks().size()];
//            entry.getTasks().values().toArray(tasks);
//            assessmentTitles.add(entry.getTitle());
//            for(Task task : tasks){
//                Activity[] activities = new Activity[task.getActivities().size()];
//                task.getActivities().values().toArray(activities);
//                series.getData().add(new XYChart.Data(0, task.getTitle(), new GanttChart.MetaData(2,"status-red")));
//                for(Activity activity : activities){
//                    series.getData().add(new XYChart.Data(2, activity.getTitle(), new GanttChart.MetaData(4,"status-purple")));
//                }
//            }
//            series.getData().add(new XYChart.Data(4, entry.getTitle(), new GanttChart.MetaData(6,"status-blue")));
//            chartEntry.add(series);
//            series = new XYChart.Series();
//        }
//
//        NumberAxis xAxis = new NumberAxis();
//        CategoryAxis yAxis = new CategoryAxis();
//        GanttChart ganttChart = new GanttChart<>(xAxis,yAxis);
//
//        ganttChart.setTitle(module.getTitle()+" chart");
//        ganttChart.setLegendVisible(false);
//        ganttChart.setFrameHeight( 50);
//
//        xAxis.setLabel("");
//        xAxis.setTickLabelFill(Color.CHOCOLATE);
//        xAxis.setMinorTickCount(4);
//
//        yAxis.setLabel("");
//        yAxis.setTickLabelFill(Color.CHOCOLATE);
//        yAxis.setTickLabelGap(10);
//        yAxis.setCategories(FXCollections.observableArrayList(assessmentTitles));
//
//        for(XYChart.Series series1 : chartEntry){
//            ganttChart.getData().add(series1);
//        }
//        ganttChart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());
//
//        //series.getData().add(new XYChart.Data(tasks.get, assessmentTitles.get(0), new GanttChart.MetaData( 1, "status-red")));
//
//        ScrollPane pane = new ScrollPane();
//        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
//
//        Scene chartScene = new Scene(pane,620,350);
//        pane.setContent(ganttChart);
//
//        Stage stage = new Stage();
//        stage.setScene(chartScene);
//        stage.show();
//    }

    public void openChart(Module module){
        createJson();
        Stage stage = new Stage();
        Browser webView = new Browser(stage);
        //stage.setScene(new GanttChartView().load(module));
        stage.setScene(new Scene(webView));
        stage.show();
    }

    private void createJson() {
        SemesterProfile semesterProfile = databaseHandler.getSemesterSession();
        ArrayList<Module> moduleList = new ArrayList<>(semesterProfile.getModules().values());

        JSONObject semester = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<JSONObject> modules = new ArrayList<>();

        semester.put("start_date", String.valueOf(semesterProfile.getStartDate()));
        semester.put("end_date", String.valueOf(semesterProfile.getEndDate()));

        semesterProfile.getModules().values().forEach(module -> {
            JSONObject jsonModule = new JSONObject();
            jsonModule.put("title", module.getTitle());
            jsonModule.put("code", module.getCode());
            List<JSONObject> assessments = new ArrayList<>();
            module.getAssessments().values().forEach(assessment -> {
                JSONObject jsonAssessment = new JSONObject();
                jsonAssessment.put("title", assessment.getTitle());
                jsonAssessment.put("type", assessment.getType().toString());
                jsonAssessment.put("weight", assessment.getWeight());
                jsonAssessment.put("completion", assessment.getCompletion());
                jsonAssessment.put("deadline", String.valueOf(assessment.getDeadLine()));

                List<JSONObject> assessmentTasks = new ArrayList<>();
                assessment.getTasks().values().forEach(task -> {
                    JSONObject jsonTask = new JSONObject();
                    jsonTask.put("title", task.getTitle());
                    jsonTask.put("type", task.getType().toString());
                    jsonTask.put("criterion", task.getCriterion());
                    jsonTask.put("criterion value", task.getCriterionValue());
                    jsonTask.put("progress", task.getProgress());
                    jsonTask.put("date", String.valueOf(task.getDate()));
                    assessmentTasks.add(jsonTask);

                    List<JSONObject> activities = new ArrayList<>();
                    task.getActivities().values().forEach(activity -> {
                        JSONObject jsonActivitiy = new JSONObject();
                        jsonActivitiy.put("title", activity.getTitle());
                        jsonActivitiy.put("date", String.valueOf(activity.getDate()));
                        jsonActivitiy.put("time", activity.getTime());
                        activities.add(jsonActivitiy);
                    });
                });
                jsonAssessment.put("Tasks", assessmentTasks);

//                List<JSONObject> milestones = new ArrayList<>();
//                assessment.getMilestones().values().forEach(milestone -> {
//                    JSONObject jsonMilestone = new JSONObject();
//                    jsonMilestone.put("title", milestone.getTitle());
//                    jsonMilestone.put("")
//                });
//                jsonAssessment.put("Milestones", milestones);

                assessments.add(jsonAssessment);
            });

            jsonModule.put("assessments", assessments);
            modules.add(jsonModule);
        });

        semester.put("modules", modules);

        try (FileWriter file = new FileWriter("../View/ganttInclude/data.json")) {
            file.write(semester.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + semester);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(semester.toJSONString());
    }

    class Browser extends Region {
        private final String URL_LOCATION = "test.html";

        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();


        public Browser(Stage stage) {
            browser.prefWidthProperty().bind(stage.widthProperty());
            browser.prefHeightProperty().bind(stage.heightProperty());
            // load the web page
            URL url = getClass().getResource(URL_LOCATION);
            webEngine.load(url.toExternalForm());
            //add the web view to the scene
            getChildren().add(browser);

        }
    }
}
