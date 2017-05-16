package View;

import Model.Activity;
import Model.Task;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * Created by Didac on 14/05/2017.
 */
public class ActivityView {

    @FXML
    Label activityLbl;

    @FXML
    TextField titleField;

    @FXML
    ComboBox<Integer> quantitySelect;

    @FXML
    ComboBox<Integer> timeSelect;

    private ObservableList<Activity> activities;
    private Task task; // Reference task from main view. The task selected on the combo box.
    private double allowedQuantities = 0;

    ActivityView(ObservableList<Activity> activities, Task task) {
        this.activities = activities;
        this.task = task;
    }

    public void initialize(){
        activityLbl.setText("Adding an activity to: " + task.getTitle());
        double allowedQuantities = task.getCriterionValue() - task.getCriterionSoFar();
        for (int i = 0; i < allowedQuantities; i++) {
            quantitySelect.getItems().add(i + 1);
        }
    }

    public void addActivity(ActionEvent actionEvent) {
        if (titleField.getText().isEmpty()) return;
        if (quantitySelect.getValue() == null) return;
        if (timeSelect.getValue() == null) return;

        String activityTitle = titleField.getText();
        int quantity = quantitySelect.getValue();
        int time = timeSelect.getValue();

        //if (task.getCriterionSoFar() == allowedQuantities + )
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        stage.hide();
        activities.add(new Activity(activityTitle, quantity, time));
    }
}
