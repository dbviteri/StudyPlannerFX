package View;

import Model.Activity;
import Model.Assessment;
import Model.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.lang.reflect.Array;
import java.util.ArrayList;

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

    private Task task;

    ActivityView(Task task){
        this.task = task;
    }

    public void initialize(){
        activityLbl.setText("Adding a activity to: " + task.getTitle());

        // quantity is based on criterion value
        ArrayList<Integer> quantityAllowed = new ArrayList<>();

        System.out.println("criterion value " + task.getCriterionValue());
        for (int i = 0; i < task.getCriterionValue(); i++){
            quantityAllowed.add(i + 1);
        }

        quantitySelect.getItems().addAll(quantityAllowed);
    }

    public void addActivity(ActionEvent actionEvent) {
        if (titleField.getText().isEmpty()) return;
        if (quantitySelect.getValue() == null) return;
        if (timeSelect.getValue() == null) return;

        System.out.println("BEFORE ADDING: ");
        System.out.println(task.getActivities().size());

        String activityTitle = titleField.getText();
        int quantity = quantitySelect.getValue();
        int time = timeSelect.getValue();

        task.addActivity(new Activity(activityTitle, quantity, time));

        System.out.println("AFTER ADDING: ");
        System.out.println(task.getActivities().size());
    }
}
