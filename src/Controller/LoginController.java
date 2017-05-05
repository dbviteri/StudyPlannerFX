package Controller;

import Model.User;
import Utils.ControlledScene;
import Utils.SPException;
import Utils.StageHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

/**
 * Created by Didac on 05/05/2017.
 */
public class LoginController extends UserController implements ControlledScene {
    public LoginController(){
        super();
    }

    private StageHandler stageHandler;

    @FXML
    private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Tooltip usernameTooltip;
    @FXML private ComboBox comboBox;

    @FXML
    public void loginUser() {
        //User user = dbhandler.getUserSession();
        String username = usernameField.getText();//"xdn15mcu";
        String password = passwordField.getText();//"test";
        User user = find(username, password);

        // If user is not null, show the main panel
        if(user == null) {
            displayTooltip("Username or password is wrong. Please try again.");
        } else {
            //databaseHandler.createSession(user);
            DatabaseHandler.getDatabaseHandler().createSession(user);

            // Reload scene after creating a session
            stageHandler.reloadScene(StageHandler.SCENE.SEMESTER);
            stageHandler.setScene(StageHandler.SCENE.SEMESTER, true);
        }
    }

    private void displayTooltip(String message){
        usernameField.getStyleClass().add("error");
        //final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
        //usernameField.pseudoClassStateChanged(errorClass, true);

        usernameTooltip.setText(message);

        Point2D point2D = usernameField.localToScene(0.0, 0.0);
        usernameTooltip.setAutoHide(true);
        usernameTooltip.show(stageHandler.getStage(),
                point2D.getX() + usernameField.getScene().getX() +
                        usernameField.getScene().getWindow().getX() + usernameField.getWidth(),
                point2D.getY() + usernameField.getScene().getY() + usernameField.getScene().getWindow().getY());
    }

    @FXML
    private void showRegister() {
        stageHandler.setScene(StageHandler.SCENE.REGISTER, false);
    }

    @Override
    public void setParentScene(StageHandler parentScene) {
        stageHandler = parentScene;
    }
}
