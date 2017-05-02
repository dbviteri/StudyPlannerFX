package View;

import Utils.ControlledScene;
import Utils.StageHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

/**
 * Created by Didac on 01/05/2017.
 */
public class SemesterView implements ControlledScene {

    private StageHandler stageHandler;
    @FXML private TabPane tabPane;

    @Override
    public void setParentScene(StageHandler parentScene) {
        stageHandler = parentScene;
    }
}
