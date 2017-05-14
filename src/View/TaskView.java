package View;

import Model.Assessment;
import Model.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Created by Didac on 14/05/2017.
 */
public class TaskView {

    @FXML
    TextField titleField;

    @FXML
    ComboBox<Task.TaskType> typeField;

    @FXML
    ComboBox<Integer> timeField;

    @FXML
    TextField criteriaField;

    @FXML
    VBox createTaskView;

    @FXML
    Label taskLbl;

    @FXML
    ComboBox<Task> dependencyList;

    @FXML
    ComboBox<Integer> criteriaValue;

    private Assessment assessment;

    TaskView(Assessment assessment) {
        this.assessment = assessment;
    }

    public void initialize() {
        taskLbl.setText("Adding a task to: " + assessment.getTitle());
        dependencyList.getItems().addAll(assessment.getTasks().values());
        typeField.getItems().addAll(Task.TaskType.values());

    }

    public void addTask(ActionEvent actionEvent) {
        if (titleField.getText().isEmpty()) return;
        if (criteriaField.getText().isEmpty()) return;
        if (typeField.getValue() == null) return;
        if (timeField.getValue() == null) return;
        if (criteriaValue.getValue() == null) return;

        System.out.println("BEFORE ADDING: ");
        System.out.println(assessment.getTasks().size());

        String taskTitle = titleField.getText();
        String criteria = criteriaField.getText();
        Task.TaskType taskType = typeField.getValue();
        int time = timeField.getValue();
        int critValue = criteriaValue.getValue();

        if (dependencyList.getValue() != null) {
            Task task = new Task(taskTitle, taskType, criteria, critValue, 0);
            task.addDependency(dependencyList.getValue());
            assessment.addTask(task);
        } else {
            assessment.addTask(new Task(taskTitle, taskType, criteria, critValue, 0));
        }

        System.out.println("AFTER ADDING: ");
        System.out.println(assessment.getTasks().size());
        //((Node)(actionEvent.getSource())).getScene().getWindow();
    }
}
