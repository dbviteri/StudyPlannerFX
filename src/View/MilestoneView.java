package View;

import Model.Assessment;
import Model.Milestone;
import Model.Task;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Didac on 16/05/2017.
 */
public class MilestoneView {

    @FXML
    private ComboBox<Task> taskPicker;
    @FXML
    private TextField titleField;
    @FXML
    private DatePicker deadlinePicker;

    private ObservableList<Milestone> milestones;
    private Assessment assessment;

    public MilestoneView(ObservableList<Milestone> milestones, Assessment assessment) {
        this.milestones = milestones;
        this.assessment = assessment;
    }

    public void initialize() {
        taskPicker.getItems().addAll(assessment.getTasks().values());
        deadlinePicker.valueProperty().addListener((Ob, oldV, newV) -> {
            if (newV == null) return;
            Date date = Date.from(newV.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date newD = new Date();
            if (date.compareTo(newD) < 0)
                new AlertDialog(Alert.AlertType.INFORMATION, "Wrong deadline");
        });
    }

    public void addMilestone(ActionEvent actionEvent) {
        if (deadlinePicker.getValue() == null) return;
        if (titleField.getText().isEmpty()) return;

        String title = titleField.getText();
        LocalDate localDate = deadlinePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date deadLineDate = Date.from(instant);

        milestones.add(new Milestone(title, new Date(), deadLineDate));

        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        stage.hide();
    }
}
