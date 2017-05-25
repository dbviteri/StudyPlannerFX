package View;

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
public class ActivityMilestoneView extends ActivityView {

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
    private ComboBox<Milestone> milestoneSelect;
    @FXML
    private ComboBox<Task> taskSelect;

    private ObservableList<Activity> activities = FXCollections.observableArrayList();
    private ObservableList<Task> tasks = FXCollections.observableArrayList();
    private ObservableList<Milestone> milestones = FXCollections.observableArrayList();

    private ListChangeListener<Activity> activitiesListener;
    public void initialize() {

        activitiesListener = changedValue -> {
            changedValue.next();

            if (changedValue.wasAdded()) {
                refreshActivitiesTable();
            }
        };

        assessmentSelect.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                milestoneSelect.getItems().clear();
                Bindings.unbindContent(milestoneSelect.getItems(), milestones);
                return;
            }

            milestones = newValue.getObservableMilestoneList();
            Bindings.bindContent(milestoneSelect.getItems(), milestones);
        });

        milestoneSelect.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == null) {
                taskSelect.getItems().clear();
                Bindings.unbindContent(taskSelect.getItems(), tasks);
                return;
            }

            tasks = newValue.getObservableTaskList();
            Bindings.bindContent(taskSelect.getItems(), tasks);
        }));

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

    private void refreshTaskBox() {

    }

    private void refreshActivitiesTable() {
        clearView();

        milestoneSelect.getValue().updateProgress();

        activities.removeListener(activitiesListener);
        activities = taskSelect.getValue().getObservableActivityList();
        activities.addListener(activitiesListener);

        super.addActivitiesColumns(activitiesTable);

        Bindings.bindContent(activitiesTable.getItems(), activities);
        updateQuantityBox();
        updateProgressBar();
    }

    private void clearView() {
        quantityBox.getItems().clear();
        activitiesTable.getItems().clear();
        activitiesTable.getColumns().clear();
        taskProgress.setProgress(0);
    }

    private void updateQuantityBox() {
        for (int i = 0; i < taskSelect.getValue().getCriterionValue() - taskSelect.getValue().getCriterionSoFar(); i++) {
            quantityBox.getItems().add(i + 1);
        }
    }

    private void updateProgressBar() {
        System.out.println(taskSelect.getValue().getProgress());
        taskProgress.setProgress(taskSelect.getValue().getProgress() / 100);
    }

    public void addActivity() {
        if (titleField.getText().isEmpty()) return;
        if (timeSpentField.getText().isEmpty()) return;
        if (quantityBox.getValue() == null) return;

        Task selectedTask = taskSelect.getValue();
        Activity activity = new Activity(titleField.getText(), quantityBox.getValue(),
                Integer.valueOf(timeSpentField.getText()), new Date());

        if (super.addActivity(selectedTask, activity)) {
            refreshActivitiesTable();
        }
    }

    public void updateNotes(ActionEvent actionEvent) {
        if (notesField.getText().isEmpty()) return;
        if (taskSelect.getValue() == null) return;

        ActivityNote activityNote = new ActivityNote("test", notesField.getText(), new Date());

        super.updateNotes(activitiesTable.getSelectionModel().getSelectedItem(), activityNote);
    }
}
