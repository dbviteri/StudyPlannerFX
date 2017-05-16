package View;

import Model.Task;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Date;

/**
 * Created by Didac on 14/05/2017.
 */
public class TaskView {

    @FXML
    TextField titleField;

    @FXML
    ComboBox<Task.TaskType> typeField;

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

    private ObservableList<Task> tasks;

    TaskView(ObservableList<Task> tasks) {
        this.tasks = tasks;
    }

    public void initialize() {
        taskLbl.setText("Adding a task ");
        dependencyList.getItems().addAll(tasks);
        typeField.getItems().addAll(Task.TaskType.values());
    }

    public void addTask(ActionEvent actionEvent) {
        if (titleField.getText().isEmpty()) return;
        if (criteriaField.getText().isEmpty()) return;
        if (typeField.getValue() == null) return;
        if (criteriaValue.getValue() == null) return;

        String taskTitle = titleField.getText();
        String criteria = criteriaField.getText();
        Task.TaskType taskType = typeField.getValue();
        int critValue = criteriaValue.getValue();

        if (dependencyList.getValue() != null) {
            Task task = new Task(taskTitle, taskType, criteria, critValue, 0, new Date());
            task.addDependency(dependencyList.getValue());
            tasks.add(task);
        } else {
            tasks.add(new Task(taskTitle, taskType, criteria, critValue, 0, new Date()));
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        stage.hide();
    }
}
