package View;

import Controller.ActivityController;
import Controller.NoteController;
import Model.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Date;

/**
 * Created by Didac on 17/05/2017.
 */
public class ActivityAssessmentView extends ActivityView {

    @FXML
    private TextArea notesField;
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

    private ActivityController activityController;
    private NoteController noteController;

    private ListChangeListener<Activity> activitiesListener;
    // Needs to have a reference to assessments and modules
    public void initialize() {
        noteController = new NoteController();
        activityController = new ActivityController();

        activitiesListener = changedValue -> {
            changedValue.next();

            if (changedValue.wasAdded()) {
                // Update the completion on the selected assessment
                assessmentSelect.getValue().updateCompletion();
            }
        };

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
            // populate activities table
            refreshActivitiesTable();
        });

        timeSpentField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                timeSpentField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void refreshActivitiesTable() {
        clearView();

        taskProgress.setProgress(taskSelect.getValue().getProgress() / 100);

        activities.removeListener(activitiesListener);
        activities = taskSelect.getSelectionModel().getSelectedItem().getObservableActivityList();
        activities.addListener(activitiesListener);

        for (int i = 0; i < taskSelect.getValue().getCriterionValue() - taskSelect.getValue().getCriterionSoFar(); i++) {
            quantityBox.getItems().add(i + 1);
        }

        super.addActivitiesColumns(activitiesTable);
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

        // update the dependency before checking
        Task selectedTask = taskSelect.getValue();
        Activity activity = new Activity(titleField.getText(), quantityBox.getValue(),
                Integer.valueOf(timeSpentField.getText()), new Date());

        if (super.addActivity(selectedTask, activity)) {
            refreshActivitiesTable();
        }
    }

    public void updateNote(ActionEvent actionEvent) {

        if (notesField.getText().isEmpty()) return;
        if (taskSelect.getValue() == null) return;

        ActivityNote activityNote = new ActivityNote("test", notesField.getText(), new Date());

        super.updateNotes(activitiesTable.getSelectionModel().getSelectedItem(), activityNote);
    }
}
