package View;

import Controller.SemesterController;
import Model.User;
import Utils.ControlledScene;
import Utils.StageHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

/**
 * Created by Didac on 07/05/2017.
 */
public class SemesterView extends SemesterController implements ControlledScene{

    private StageHandler stageHandler;

    @FXML
    private Menu userMenu;
    @FXML
    private MenuBar menuBar;
    @FXML
    private TabPane tabPane;
    @FXML
    private VBox semesterView;


    // Inherits the FXML from DashboardView, needs to have Controller as suffix.
    // No need to assign it, since FXMLLoader assigns it when SemesterView is loaded.
    @FXML
    private DashBoardView dashboardController;

    @FXML
    private MainView mainViewController;

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
        // DECORATE STAGE ••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••

        userMenu.setText(user.getFirstname());
        stageHandler.getStage().setTitle("Welcome back, " + user.getFirstname() + "!");

        // BIND DIMENSIONS •••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••

        semesterView.prefWidthProperty().bind(stageHandler.getStage().widthProperty());
        semesterView.prefHeightProperty().bind(stageHandler.getStage().heightProperty());

        dashboardController.dashboardGrid.prefWidthProperty().bind(
                semesterView.widthProperty()
        );

        dashboardController.dashboardGrid.prefHeightProperty().bind(
                semesterView.heightProperty()
        );

        mainViewController.mainViewPane.prefHeightProperty().bind(
                semesterView.widthProperty()
        );

        mainViewController.mainViewPane.prefWidthProperty().bind(
                semesterView.heightProperty()
        );



        // POPULATE DASHBOARD TAB PANE •••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••

        dashboardController.load();
        //dashboardController.assignments.setText(assessments.get(0).getTitle());

        mainViewController.load();

        System.out.println(user.getEmail());
    }

    /** Function used to log user out, delete
     *  current session and change scene back to login
     *
     */
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
    @FXML
    public void updateSemesterProfile() {
        // TODO: CALL UPLOAD A FILE HERE, UPDATE USING THIS USER's ID

    }

}
