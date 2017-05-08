package View;

import Controller.AssessmentController;
import Controller.ModuleController;
import Controller.SemesterController;
import Model.Assessment;
import Model.Module;
import Model.SemesterProfile;
import Model.User;
import Utils.ControlledScene;
import Utils.StageHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Didac on 07/05/2017.
 */
public class SemesterView extends SemesterController implements ControlledScene{

    private StageHandler stageHandler;

    @FXML
    private Menu userMenu;
    @FXML
    private VBox semesterView;

    // Inherits the FXML from DashboardView, needs to have Controller as suffix.
    @FXML
    private DashBoardView dashboardController;


    /**
     * Constructs a SemesterProfile controller.
     */
    public SemesterView(StageHandler stageHandler){
        this.stageHandler = stageHandler;
    }

    // Methods ---------------------------------------------------------------------------------------------------------

    /**
     * Allows for post-processing of the FXML components. It is called after the constructor.
     */
    public void initialize() {
        //semesterLabel.setText("test");
        if (dbhandler.getUserSession() == null) return;

        User user = dbhandler.getUserSession();
        SemesterProfile semesterProfile = SemesterController.find(user.getId());
        ArrayList<Module> modules = ModuleController.findAll(semesterProfile.getSemesterId());
        ArrayList<Assessment> assessments = AssessmentController.findAll(modules.get(0).getCode());

        // DECORATE STAGE ••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••

        userMenu.setText(user.getFirstname());
        stageHandler.getStage().setTitle("Welcome back, " + user.getFirstname() + "!");

        // BIND DIMENSIONS •••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••

        semesterView.prefWidthProperty().bind(stageHandler.getStage().widthProperty());
        semesterView.prefHeightProperty().bind(stageHandler.getStage().heightProperty());

        dashboardController.scene.prefWidthProperty().bind(semesterView.widthProperty());
        dashboardController.scene.prefHeightProperty().bind(semesterView.heightProperty());

        // POPULATE DASHBOARD TAB PANE •••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••

        // Decide what pane should the assignments go based on deadline
        Date date = new Date();
        for (Assessment assessment : assessments) {

            // If compareTo is more than 0, date is after the deadline
            // Should go to missed deadlines

            // If compareTo is less than 0, date is before the deadline
            // Should go to upcoming deadlines
            if (date.compareTo(assessment.getDeadLine()) < 0) {
                dashboardController.upcomingDeadlinesLbl.setText(assessment.getTitle());
            } else if (date.compareTo(assessment.getDeadLine()) > 0) {
                dashboardController.missedDeadlinesLbl.setText(assessment.getTitle());
            }

            System.out.println(date.compareTo(assessment.getDeadLine()));
        }

        //dashboardController.assignments.setText(assessments.get(0).getTitle());

        System.out.println(user.getEmail());
    }
    @FXML
    public void logOut(){
        //dbhandler.closeConnection();
        dbhandler.deleteSession();
        stageHandler.reloadScene(StageHandler.SCENE.LOGIN);
        stageHandler.setScene(StageHandler.SCENE.LOGIN, false);
    }

    @Override
    public void setParentScene(StageHandler stageHandler) {
        this.stageHandler = stageHandler;
    }
}
