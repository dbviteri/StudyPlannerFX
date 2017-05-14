package View;

import Model.Assessment;
import Model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Created by Didac on 14/05/2017.
 */
public class CreateTaskView {

    @FXML
    TextField titleField;

    @FXML
    ComboBox<Task.TaskType> typeField;

    @FXML
    ComboBox<String> timeField;

    @FXML
    TextField criteriaField;

    @FXML
    VBox createTaskView;

    @FXML
    Label taskLbl;

    @FXML
    ComboBox<Task> dependencyList;

    Assessment assessment;

    public CreateTaskView(Assessment assessment) {
        this.assessment = assessment;
    }

    public void initialize() {
        taskLbl.setText("Adding a task to: " + assessment.getTitle());
        dependencyList.getItems().addAll(assessment.getTasks().values());
        typeField.getItems().addAll(Task.TaskType.values());
    }
}
