package View;

import Controller.SemesterController;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView extends SemesterController {

    @FXML
    GridPane mainViewPane;
    @FXML
    ProgressBar taskProgressBar;
    @FXML
    ComboBox<Module> moduleSelect;
    @FXML
    ComboBox<Assessment> assessmentSelect;
    @FXML
    ComboBox<Task> taskSelect;
    @FXML
    TextArea moduleDetails;
    @FXML
    TextArea taskDetails;
    @FXML
    TableView<Task> taskTable;
    @FXML
    TableView<Milestone> milestoneActivitiesTable;
    @FXML
    TableView<Activity> activitiesTable;
    @FXML
    ComboBox<Milestone> milestoneSelect;

    private ObservableList<Task> taskObservableList; // All the tasks inside an assessment
    private ObservableList<Activity> activityObservableList; // All the activities inside an activity
    private ObservableList<Milestone> milestoneObservableList; // All the activities inside an activity

    /**
     * Adds the modules from semester profile to the combo box
     */
    public void load() {
        moduleSelect.getItems().addAll(dbhandler.getSemesterSession().getModules().values());
        addListeners();
    }

    /**
     * Deletes a task from the observable list
     * If unsuccessful, it will show an alert.
     */
    public void deleteTask() {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
        System.out.println("BEFORE: " + assessmentSelect.getValue().getTasks().size());
        if (!assessmentSelect.getValue().deleteTask(selectedTask)) {
            new AlertDialog(Alert.AlertType.ERROR, "Can't delete task, it has dependencies");
            return;
        }
        //assessmentSelect.getValue().deleteTask(selectedTask);
        taskObservableList.remove(selectedTask);
        System.out.println("AFTER: " + assessmentSelect.getValue().getTasks().size());
    }

    /**
     * Displays the view to add a task.
     * @param actionEvent Action event needed to hide parent window
     */
    public void addTask(ActionEvent actionEvent) {
        if (assessmentSelect.getValue() == null) return;

        Stage parentStage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TaskView.fxml"));
        fxmlLoader.setControllerFactory((Class<?> CreateTaskView) -> new TaskView(taskObservableList));
        Parent root;
        try {
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add task");
            stage.setScene(new Scene(root, 450, 450));
            stage.setResizable(false);
            stage.show();
            stage.setOnCloseRequest(event -> parentStage.show());
            stage.setOnHidden(event -> parentStage.show());
            parentStage.hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds columns to the table view. It essentially populates the table with tasks.
     */
    private void addTaskColumns() {
        // Add tasks to table
        TableColumn<Task, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Task, Task.TaskType> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Task, Integer> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Task, Integer> criterionCol = new TableColumn<>("Criterion");
        criterionCol.setCellValueFactory(new PropertyValueFactory<>("criterion"));

        taskTable.getColumns().addAll(titleCol, typeCol, timeCol, criterionCol);
    }

    /**
     * Adds columns to the table view. It essentially populates the table with activities.
     */
    private void addActivityColumns() {
        TableColumn<Activity, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Activity, String> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Activity, String> timeCol = new TableColumn<>("Time Spent");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        activitiesTable.getColumns().addAll(titleCol, quantityCol, timeCol);
    }

    private void addMilestoneColumns() {
        TableColumn<Milestone, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Milestone, String> deadlineCol = new TableColumn<>("Deadline");
        deadlineCol.setCellValueFactory(new PropertyValueFactory<>("deadline"));

        milestoneActivitiesTable.getColumns().addAll(titleCol, deadlineCol);
    }

    /**
     * Function to handle all listeners in main view. Communicates table views between them.
     * It also updates them and combo boxes based on the observable lists listeners.
     */
    private void addListeners() {

        // Combo box listener:
        // It populates the assessment combo box with the selected module.
        moduleSelect.valueProperty().addListener((observable, oldValue, newValue) -> {
            assessmentSelect.getItems().clear();
            if (newValue != null) {
                // Populate assessment list
                assessmentSelect.getItems().addAll(
                        dbhandler.getSemesterSession().getModules().
                                get(moduleSelect.getValue()).getAssessments().values());


            }
        });

        // Assessment combo box listener:
        // Inside there's a observable list listener, which gets updated whenever there's a
        // change with the combo box
        assessmentSelect.valueProperty().addListener((observable, oldValue, newValue) -> {
            taskTable.getItems().clear();
            if (newValue == null) return;

            moduleDetails.setText(moduleSelect.getValue().toString() + "\n" + assessmentSelect.getValue().toString());

            milestoneActivitiesTable.getItems().clear();
            milestoneActivitiesTable.getColumns().clear();

            taskSelect.getItems().clear();
            taskTable.getColumns().clear();

            taskObservableList = FXCollections.observableArrayList(assessmentSelect.getValue().getTasks().values());
            taskSelect.getItems().addAll(taskObservableList);

            // Updates the task combo box and adds or removes from the
            // actual reference (SemesterProfile) in session.
            taskObservableList.addListener((ListChangeListener<Task>) pChange -> {
                pChange.next();
                System.out.println("ASDF");
                if (pChange.wasRemoved()) {
                    taskSelect.getItems().clear();
                    if (assessmentSelect.getValue() == null) return;
                    assessmentSelect.getValue().deleteTask(pChange.getRemoved().get(0));
                    taskSelect.getItems().clear();
                    taskSelect.getItems().addAll(taskObservableList);
                } else if (pChange.wasAdded()) {
                    System.out.println("BEFORE: " + dbhandler.getSemesterSession().getModules().get(moduleSelect.getValue()).getAssessments().get(assessmentSelect.getValue()).getTasks().size());
                    //int index = pChange.getTo() - pChange.getFrom();
                    assessmentSelect.getValue().addTask(pChange.getAddedSubList().get(pChange.getAddedSubList().size() - 1));
                    taskSelect.getItems().clear();
                    taskSelect.getItems().addAll(taskObservableList);
                    System.out.println("AFTER: " + dbhandler.getSemesterSession().getModules().get(moduleSelect.getValue()).getAssessments().get(assessmentSelect.getValue()).getTasks().size());
                }
            });

            addTaskColumns();
            taskTable.setItems(taskObservableList);

            milestoneObservableList = FXCollections.observableArrayList(assessmentSelect.getValue().getMilestones().values());

            addMilestoneColumns();
            milestoneSelect.getItems().addAll(milestoneObservableList);
            milestoneActivitiesTable.setItems(milestoneObservableList);
        });

        // Task combo box listener:
        taskSelect.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                taskProgressBar.setProgress(0);
                taskDetails.clear();
                activitiesTable.getColumns().clear();
                return;
            }

            taskProgressBar.setProgress(taskSelect.getValue().getProgress() / 100);

            taskDetails.setText(taskSelect.getValue().toString());

            activitiesTable.getColumns().clear();

            activityObservableList = FXCollections.observableArrayList(taskSelect.getValue().getActivities().values());

            // Updates the task combo box and adds or removes from the
            // actual reference (SemesterProfile) in session.
            activityObservableList.addListener((ListChangeListener<Activity>) pChange -> {
                pChange.next();
                System.out.println("ASDF");
                if (pChange.wasRemoved()) {
                    if (taskSelect.getValue() == null) return;
                    // Delete activity
                    taskSelect.getValue().deleteActivity(pChange.getRemoved().get(0));
                    // Update progress bar
                    taskProgressBar.setProgress(taskSelect.getValue().getProgress() / 100);
                    // Update completion for assessment
                    assessmentSelect.getValue().calculateCompletion();
                } else if (pChange.wasAdded()) {
                    System.out.println("BEFORE: " + taskSelect.getValue().getActivities().size());
                    //int index = pChange.getTo() - pChange.getFrom();
                    // Add activity to task
                    taskSelect.getValue().addActivity(pChange.getAddedSubList().get(pChange.getAddedSubList().size() - 1));
                    // Update progress bar for task
                    taskProgressBar.setProgress(taskSelect.getValue().getProgress() / 100);
                    // Update assessment completion based on the task
                    assessmentSelect.getValue().calculateCompletion();

                    taskDetails.setText(taskSelect.getValue().toString());
                    System.out.println("AFTER: " + taskSelect.getValue().getActivities().size());
                }
            });


            addActivityColumns();

            activitiesTable.setItems(activityObservableList);
        });
    }

    /**
     * Displays the activity view to be added to the activity observable list.
     * The list is passed as a parameter to the add activity view so it can be changed.
     *
     * @param actionEvent Used to hide the parent stage
     */
    public void addActivity(ActionEvent actionEvent) {
        if (taskSelect.getValue() == null) return;

        if (taskSelect.getValue().getProgress() == 100) {
            new AlertDialog(Alert.AlertType.INFORMATION, "You can't add more activities. The task is completed.");
            return;
        }

        Stage parentStage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ActivityView.fxml"));
        fxmlLoader.setControllerFactory((Class<?> CreateTaskView) -> new ActivityView(activityObservableList, taskSelect.getValue()));
        Parent root;
        try {
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add activity");
            stage.setScene(new Scene(root, 450, 450));
            stage.setResizable(false);
            stage.show();
            stage.setOnCloseRequest(event -> parentStage.show());
            stage.setOnHidden(event -> parentStage.show());
            parentStage.hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes an activity from the observable activity list
     */
    public void deleteActivity() {
        Activity selectedActivity = activitiesTable.getSelectionModel().getSelectedItem();
        System.out.println("BEFORE: " + taskSelect.getValue().getActivities().size());
        if (!taskSelect.getValue().deleteActivity(selectedActivity)) {
            new AlertDialog(Alert.AlertType.ERROR, "Can't delete activity");
            return;
        }
        //assessmentSelect.getValue().deleteTask(selectedTask);
        activityObservableList.remove(selectedActivity);
        System.out.println("AFTER: " + taskSelect.getValue().getActivities().size());
    }
}