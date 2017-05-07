package View;

import Controller.SemesterController;
import Model.User;
import Utils.ControlledScene;
import Utils.StageHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;

/**
 * Created by Didac on 07/05/2017.
 */
public class SemesterView extends SemesterController implements ControlledScene{

    private StageHandler stageHandler;

    @FXML
    Menu userMenu;
    @FXML
    VBox vBox;

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
        userMenu.setText(user.getFirstname());

        stageHandler.getStage().setTitle("Welcome back, " + user.getFirstname() + "!");
        vBox.prefWidthProperty().bind(stageHandler.getStage().widthProperty());
        vBox.prefHeightProperty().bind(stageHandler.getStage().heightProperty().subtract(20));
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
