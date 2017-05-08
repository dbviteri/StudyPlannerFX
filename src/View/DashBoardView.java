package View;

import Controller.AssessmentController;
import Controller.DatabaseHandler;
import Controller.ModuleController;
import Controller.SemesterController;
import Model.Assessment;
import Model.Module;
import Model.SemesterProfile;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Didac on 08/05/2017.
 */
public class DashBoardView {

    @FXML
    Label assignments;
    @FXML
    HBox scene;
    @FXML
    Pane completedDeadlines;
    @FXML
    Pane upcomingDeadlines;
    @FXML
    Pane missedDeadlines;
    @FXML
    Label completedDeadlinesLbl;
    @FXML
    Label upcomingDeadlinesLbl;
    @FXML
    Label missedDeadlinesLbl;

    DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

    public void load() {

        User user = databaseHandler.getUserSession();
        SemesterProfile semesterProfile = SemesterController.find(user.getId());
        ArrayList<Module> modules = ModuleController.findAll(semesterProfile.getSemesterId());
        ArrayList<Assessment> assessments = AssessmentController.findAll(modules.get(0).getCode());

        // Decide what pane should the assignments go based on deadline
        Date date = new Date();
        for (Assessment assessment : assessments) {

            // If compareTo is more than 0, date is after the deadline
            // Should go to missed deadlines

            // If compareTo is less than 0, date is before the deadline
            // Should go to upcoming deadlines
            if (date.compareTo(assessment.getDeadLine()) < 0) {
                upcomingDeadlinesLbl.setText(assessment.getTitle());
            } else if (date.compareTo(assessment.getDeadLine()) > 0) {
                missedDeadlinesLbl.setText(assessment.getTitle());
            }


            System.out.println(date.compareTo(assessment.getDeadLine()));
        }

        System.out.println(SemesterController.find(user.getId()).getEndDate());
        System.out.println("can call");
    }
}
