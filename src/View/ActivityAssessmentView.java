package View;

import Model.Activity;
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

/**
 * Created by Didac on 17/05/2017.
 */
public class ActivityAssessmentView {

    @FXML
    private ProgressBar taskProgress;
    @FXML
    private TextField timeSpentField;
    @FXML
    private TextField titleField;
    @FXML
    private ComboBox<Integer> quantityBox;
    @FXML
    private TableView<Activity> activitiesTable;
    @FXML
    ComboBox<Module> moduleSelect;
    @FXML
    ComboBox<Assessment> assessmentSelect;
    @FXML
    private ComboBox<Task> taskSelect;

    private ObservableList<Activity> activities = FXCollections.observableArrayList();
    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    // Needs to have a reference to assessments and modules
    public void initialize() {
        assessmentSelect.valueProperty().addListener((observable, oldValue, newValue) -> {
            taskSelect.getItems().clear();
            if (newValue == null) return;

            tasks = newValue.getObservableTaskList();
            Bindings.bindContent(taskSelect.getItems(), tasks);
        });

        // Tasks dropdown box listener:
        taskSelect.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                clearView();
                return;
            }
            activities = taskSelect.getValue().getObservableActivityList();

            activities.addListener((ListChangeListener<Activity>) changedValue -> {
                changedValue.next();

                if (changedValue.wasAdded()) {
                    // Update the completion on the selected assessment
                    assessmentSelect.getValue().updateCompletion();
                    refreshActivitiesTable();
                }
            });

            // populate activities table
            refreshActivitiesTable();
        });

        timeSpentField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                timeSpentField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void addActivitiesColumns() {
        TableColumn<Activity, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Activity, Integer> quantityCol = new TableColumn<>("Type");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Activity, Integer> timeCol = new TableColumn<>("Weight");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("weight"));

        activitiesTable.getColumns().addAll(titleCol, quantityCol, timeCol);
    }

    private void refreshActivitiesTable() {
        clearView();

        taskProgress.setProgress(taskSelect.getValue().getProgress() / 100);

        for (int i = 0; i < taskSelect.getValue().getCriterionValue() - taskSelect.getValue().getCriterionSoFar(); i++) {
            quantityBox.getItems().add(i + 1);
        }

        addActivitiesColumns();
        Bindings.bindContent(activitiesTable.getItems(), activities);
    }

    private void clearView() {
        quantityBox.getItems().clear();
        activitiesTable.getItems().clear();
        activitiesTable.getColumns().clear();
        taskProgress.setProgress(0);
    }

    public void addActivity() {
        if (titleField.getText().isEmpty()) return;
        if (timeSpentField.getText().isEmpty()) return;
        if (quantityBox.getValue() == null) return;

        Activity activity = new Activity(titleField.getText(), quantityBox.getValue(),
                Integer.parseInt(timeSpentField.getText()),
                new Date());

        if (taskSelect.getValue().addActivity(activity)) {
            activities.add(activity);
        } else {
            new AlertDialog(Alert.AlertType.ERROR, "Can't start this task until dependencies are completed.");
        }
    }
}
