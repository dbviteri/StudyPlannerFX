package Controller;

import Model.User;
import Utils.ControlledScene;
import Utils.StageHandler;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Didac on 05/05/2017.
 */
public class RegisterController extends UserController implements ControlledScene{
    @FXML private TextField nameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    private StageHandler stageHandler;

    public RegisterController(){
        super();
    }

    @FXML
    public void registerUser(){
        User user = null;

        String username = generateUserName();

        // While there's a username, keep creating a new one
        while (userExists(username)){
            username = generateUserName();
        }

        user.setEmail(emailField.getText());
        user.setUsername(username);
        user.setPassword(passwordField.getText());
        user.setFirstname(nameField.getText());
        user.setLastname(lastNameField.getText());
        user.setStaff(false);
        // file chooser called in ... button too
        File userF = fileChooser();

        // TODO: Fix commented code below
//        try {
//            if (semC.checkFile(userF)) {
//                create(user);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    // TODO: Change this so it doesn't query the database everytime
    public String generateUserName(){
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        DateFormat df = new SimpleDateFormat("yy");
        char[] randomChars = new char[6];
        //create user name
        for (int i = 0; i < randomChars.length; i++) {
            randomChars[i] = (char) (r.nextInt((122 - 97) + 1) + 97);
            sb.append(randomChars[i]);
            if (i == 2) sb.append(df.format(Calendar.getInstance().getTime()));
        }
        return sb.toString();
    }

    @FXML
    private File fileChooser(){
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stageHandler.getStage());

        return file;
    }

    @FXML
    public void showLoginScreen() {
        stageHandler.setScene(StageHandler.SCENE.LOGIN, false);
    }


    @Override
    public void setParentScene(StageHandler parentScene) {
        this.stageHandler = parentScene;
    }
}
