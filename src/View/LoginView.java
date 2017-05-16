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
    private UserController userController;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    public LoginView(){
        userController = new UserController();
    }
    /** Function used to log a user in
     *  on button press, if credentials are valid.
     *  it also creates a new session based on the user
     *  and sets the scene to semester
     *
     */
    @FXML
    public void directLogIn() {
        String username = usernameField.getText();//"xdn15mcu";
        String password = passwordField.getText();//"test";
        userController.logIn(stageHandler,username,password);
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
