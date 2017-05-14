package View;

import Controller.SemesterController;
import Model.Assessment;
import Model.Module;
import Model.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Didac on 14/05/2017.
 */
public class MainView extends SemesterController {

    @FXML
    VBox mainViewBox;

    @FXML
    HBox upperLeftBox;

    @FXML
    Label moduleDetails;

    @FXML
    Label assessmentDetails;

    @FXML
    Label taskDetails;

    @FXML
    ComboBox<Module> moduleSelect;

    @FXML
    ComboBox<Assessment> assessmentSelect;

    @FXML
    ComboBox<Task> taskSelect;

    /**
     * Loads the main view inside the semester view.
     * Populates the module details and semester details.
     * It also opulates the combo boxes and handles the buttons for
     * adding a new task and milestone.
     */
    public void load() {
        upperLeftBox.prefHeightProperty().bind(
                upperLeftBox.heightProperty()
        );

        upperLeftBox.prefWidthProperty().bind(
                upperLeftBox.widthProperty()
        );

        moduleSelect.getItems().addAll(dbhandler.getSemesterSession().getModules().values());

        moduleSelect.valueProperty().addListener((observable, oldValue, newValue) -> {
            assessmentSelect.getItems().clear();
            if (newValue != null) {
                // Populate assessment list
                assessmentSelect.getItems().addAll(
                        dbhandler.getSemesterSession().getModules().
                                get(moduleSelect.getValue()).getAssessments().values());


            }
        });

        assessmentSelect.valueProperty().addListener((observable, oldValue, newValue) -> {
            taskSelect.getItems().clear();
            if (newValue != null) {
                assessmentDetails.setText(assessmentSelect.getValue().toString());
                moduleDetails.setText(moduleSelect.getValue().toString());

                // Add tasks to combobox
                taskSelect.getItems().addAll(assessmentSelect.getValue().getTasks().values());
            }
        });

        taskSelect.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                taskDetails.setText(newValue.toString());
            }
        });

//        moduleSelect.getItems().addAll(dbhandler.getSemesterSession().getModules().values());

    }

    /**
     * Handles the button event to add a task. Selects the current semester chosen in combo box and
     * passess it to TaskCreateView so it knows where the task should be created.
     */
    public void displayAddTask(ActionEvent actionEvent) {
        if (assessmentSelect.getValue() == null) return;

        Stage parentStage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TaskView.fxml"));
        fxmlLoader.setControllerFactory((Class<?> CreateTaskView) -> new TaskView(assessmentSelect.getValue()));
        Parent root;
        try {
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add task");
            stage.setScene(new Scene(root, 450, 450));
            stage.setResizable(false);
            stage.show();
            stage.setOnCloseRequest(event -> parentStage.show());
            parentStage.hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayAddActivity(ActionEvent actionEvent) {
        if (taskSelect.getValue() == null) return;

        Stage parentStage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ActivityView.fxml"));
        fxmlLoader.setControllerFactory((Class<?> CreateActivityView) -> new ActivityView(taskSelect.getValue()));
        Parent root;
        try {
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add activity");
            stage.setScene(new Scene(root, 450, 450));
            stage.setResizable(false);
            stage.show();
            stage.setOnCloseRequest(event -> parentStage.show());
            parentStage.hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}