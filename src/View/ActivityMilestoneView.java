package View;

import Model.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;

/**
 * Created by Didac on 17/05/2017.
 */
public class ActivityMilestoneView {
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

    public void initialize() {
        assessmentSelect.valueProperty().addListener((observable, oldValue, newValue) -> {
            milestoneSelect.getItems().clear();
            if (newValue == null) return;

            milestones = newValue.getObservableMilestoneList();
            Bindings.bindContent(milestoneSelect.getItems(), milestones);
        });

        milestoneSelect.valueProperty().addListener(((observable, oldValue, newValue) -> {
            taskSelect.getItems().clear();
            if (newValue == null) return;

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

        for (int i = 0; i < taskSelect.getValue().getCriterionValue() - taskSelect.getValue().getCriterionSoFar(); i++) {
            quantityBox.getItems().add(i + 1);
        }

        activities = taskSelect.getValue().getObservableActivityList();
        activities.addListener((ListChangeListener<Activity>) changedValue -> {
            changedValue.next();

            if (changedValue.wasAdded()) {
                // Update the completion on the selected milestone
                updateProgressBar();
                taskSelect.getValue().addActivity(changedValue.getAddedSubList().get(changedValue.getAddedSubList().size() - 1));
                milestoneSelect.getValue().updateProgress();
            }
        });

        addActivitiesColumns();
        Bindings.bindContent(activitiesTable.getItems(), activities);
        updateProgressBar();
    }

    private void clearView() {
        quantityBox.getItems().clear();
        activitiesTable.getItems().clear();
        activitiesTable.getColumns().clear();
        taskProgress.setProgress(0);
    }

    private void updateProgressBar() {
        System.out.println(taskSelect.getValue().getProgress());
        taskProgress.setProgress(taskSelect.getValue().getProgress() / 100);
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

    public void updateNotes(ActionEvent actionEvent) {}
}
