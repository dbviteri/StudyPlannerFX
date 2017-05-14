package View;

import Controller.DatabaseHandler;
import Controller.UserController;
import Model.User;
import Utils.ControlledScene;
import Utils.StageHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Created by Didac on 07/05/2017.
 */
public class LoginView extends UserController implements ControlledScene{
    private StageHandler stageHandler;

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    /** Function used to log a user in
     *  on button press, if credentials are valid.
     *  it also creates a new session based on the user
     *  and sets the scene to semester
     *
     */
    @FXML
    public void loginUser() {
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

        String username = usernameField.getText();//"xdn15mcu";
        String password = passwordField.getText();//"test";

        User user = find(username, password);

        // If user is not null, show the main panel
        if(user == null) {
            new AlertDialog(Alert.AlertType.INFORMATION, "User doesn't exist. Try again.");
            return;
        }

        //databaseHandler.createSession(user);
        DatabaseHandler.getInstance().createSession(user);

        // Reload scene after creating a session
        stageHandler.reloadScene(StageHandler.SCENE.SEMESTER);
        stageHandler.setScene(StageHandler.SCENE.SEMESTER, true);

    }

    /** Basic FXML function, on button press
     *  sets scene to register
     *
     */
    @FXML
    private void showRegister() {
        stageHandler.setScene(StageHandler.SCENE.REGISTER, false);
    }
    @Override
    public void setParentScene(StageHandler stageHandler) {
        this.stageHandler = stageHandler;
    }
}
