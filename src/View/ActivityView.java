package View;

import Model.Activity;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
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

    ActivityView(ObservableList<Activity> activities) {
        this.activities = activities;
    }

    public void initialize(){
        activityLbl.setText("Adding an activity");
    }

    public void addActivity(ActionEvent actionEvent) {
        if (titleField.getText().isEmpty()) return;
        if (quantitySelect.getValue() == null) return;
        if (timeSelect.getValue() == null) return;

        String activityTitle = titleField.getText();
        int quantity = quantitySelect.getValue();
        int time = timeSelect.getValue();

        activities.add(new Activity(activityTitle, quantity, time));
    }
}
