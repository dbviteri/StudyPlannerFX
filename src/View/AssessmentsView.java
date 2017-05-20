package View;

import Controller.AssessmentController;
import Controller.DatabaseHandler;
import Model.Assessment;
import Model.Module;
import Model.Task;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;
import java.util.function.Predicate;

/**
 * Created by Didac on 17/05/2017.
 */
public class AssessmentsView {

    // FIELDS --------------------------------------------
    @FXML
    private TextField taskTitleField;
    @FXML
    private ComboBox<Task.TaskType> taskTypeBox;
    @FXML
    private TextField taskCritField;
    @FXML
    private TextField amountField;
    @FXML
    private ComboBox<Task> taskDependenciesBox;

    // VIEW ----------------------------------------------
    @FXML
    private ComboBox<Module> moduleSelect;
    @FXML
    private TableView<Assessment> assessmentTable;
    @FXML
    private TableView<Task> tasksTable;
    @FXML
    private ProgressBar assessmentProgress;

    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    // Needs to have a reference to assessments and modules
    public void initialize() {
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
        AssessmentController assessmentController = new AssessmentController();

        if (databaseHandler.getSemesterSession() == null) return;

        moduleSelect.getItems().addAll(databaseHandler.getSemesterSession().getModules().values());
        taskTypeBox.getItems().addAll(Task.TaskType.values());

        // Module dropdown box listener:
        moduleSelect.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;

            // populate assessment table
            refreshAssessmentTable();
            // but clear tasks table
            tasksTable.getItems().clear();
        });

        assessmentTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == null) return;

            assessmentProgress.progressProperty().bind(newValue.completionProperty().divide(100));

            refreshTaskTable();
        }));

        // Listener for the amount text field to only allow numbers
        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                amountField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void refreshTaskTable() {
        tasksTable.getColumns().clear();

        Assessment selectedAssessment = assessmentTable.getSelectionModel().getSelectedItem();

        tasks = selectedAssessment.getObservableTaskList();

        tasks.addListener((ListChangeListener<? super Task>) change -> {
            change.next();

            if (change.wasAdded()) {
                selectedAssessment.addTask(change.getAddedSubList().get(change.getAddedSubList().size() - 1));
                System.out.println(tasks.size());
            } else {
                //System.out.println("idk");
                selectedAssessment.deleteTask(change.getRemoved().get(0));
            }
        });

        addTasksColumns();
        Bindings.bindContent(tasksTable.getItems(), tasks);
        Bindings.bindContent(taskDependenciesBox.getItems(), tasks);
    }

    private void refreshAssessmentTable() {
        clearView();

        addAssessmentColumns();
        Date date = new Date();
        for (Assessment assessment : moduleSelect.getValue().getAssessments().values()) {
            if (date.compareTo(assessment.getDeadLine()) <= 0) {
                assessmentTable.getItems().add(assessment);
            }
        }

    }

    private void clearView() {
        assessmentTable.getColumns().clear();
        assessmentTable.getItems().clear();
        //assessmentProgress.setProgress(0);
    }

    private void addAssessmentColumns() {
        TableColumn<Assessment, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Assessment, Assessment.Type> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Assessment, Integer> weightCol = new TableColumn<>("Weight");
        weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));

        TableColumn<Assessment, Date> criterionCol = new TableColumn<>("Deadline");
        criterionCol.setCellValueFactory(new PropertyValueFactory<>("deadline"));

        assessmentTable.getColumns().addAll(titleCol, typeCol, weightCol, criterionCol);
    }

    private void addTasksColumns() {
        TableColumn<Task, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Task, Task.TaskType> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Task, Integer> weightCol = new TableColumn<>("Time spent");
        weightCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Task, Integer> criterionCol = new TableColumn<>("Criterion");
        criterionCol.setCellValueFactory(new PropertyValueFactory<>("criterion"));

        tasksTable.getColumns().addAll(titleCol, typeCol, weightCol, criterionCol);
    }

    /**
     * Called by the button event handler
     */
    public void addTask() {
        if (taskTitleField.getText().isEmpty()) return;
        if (taskTypeBox.getValue() == null) return;
        if (taskCritField.getText().isEmpty()) return;
        if (amountField.getText().isEmpty()) return;

        String taskTitle = taskTitleField.getText();
        String criteria = taskCritField.getText();
        Task.TaskType taskType = taskTypeBox.getValue();
        int critValue = Integer.parseInt(amountField.getText());

        if (taskDependenciesBox.getValue() != null) {
            Task task = new Task(taskTitle, taskType, criteria, critValue, 0, new Date());
            task.addDependency(taskDependenciesBox.getValue());
            tasks.add(task);
        } else {
            tasks.add(new Task(taskTitle, taskType, criteria, critValue, 0, new Date()));
        }
    }

    public void deleteTask() {
        if (tasksTable.getSelectionModel().getSelectedItem() == null) return;

        Task selectedTask = tasksTable.getSelectionModel().getSelectedItem();

        AlertDialog alertDialog = new AlertDialog();
        boolean confirmed = alertDialog.getConfirmation("Are you sure you want to delete " + selectedTask.getTitle() + "?");

        Predicate<Task> acceptedTask = task -> task.equals(selectedTask) &&
                assessmentTable.getSelectionModel().getSelectedItem().deleteTask(selectedTask);

        if (confirmed) {
            // Try to delete task from the original reference
            if (!tasks.removeIf(acceptedTask)) {
                new AlertDialog(Alert.AlertType.ERROR, "Can't delete task. It has dependencies.");
            }
        }
    }
}
