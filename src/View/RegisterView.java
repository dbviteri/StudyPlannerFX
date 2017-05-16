package View;

import Controller.UserController;
import Model.SemesterProfile;
import Model.User;
import Utils.ControlledScene;
import Utils.FileParser;
import Utils.StageHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Created by Didac on 05/05/2017.
 */
public class RegisterView implements ControlledScene{
    // Form Fields
    @FXML private TextField nameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    private StageHandler stageHandler;
    private UserController userController;
    public RegisterView(){
        userController = new UserController();
    }

    /** Function used to register and add a new user
     *  + the semester profile to the DB
     *
     */
    @FXML
    public void registerUser(){
        SemesterProfile semesterProfile = null;
        semesterProfile = parseProfile();
        if (semesterProfile == null) {
            new AlertDialog(Alert.AlertType.ERROR, "Wrong file.");
            return;
        }

        if (userController.userExists(usernameField.getText())) {
            new AlertDialog(Alert.AlertType.INFORMATION, "Username is taken!");
            return;
        }

        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String firstname = nameField.getText();
        String lastname = lastNameField.getText();
        boolean isStaff = false; // TODO: HANDLE THIS IN REGISTER VIEW

        User user = new User(email, username, password, firstname, lastname, isStaff);

        userController.create(user);
        userController.insertProfile(semesterProfile);
        showLoginScreen();
    }

    // TODO: Change this so it doesn't query the database everytime
//    public String generateUserName(){
//        Random r = new Random();
//        StringBuilder sb = new StringBuilder();
//        DateFormat df = new SimpleDateFormat("yy");
//        char[] randomChars = new char[6];
//        //create user name
//        for (int i = 0; i < randomChars.length; i++) {
//            randomChars[i] = (char) (r.nextInt((122 - 97) + 1) + 97);
//            sb.append(randomChars[i]);
//            if (i == 2) sb.append(df.format(Calendar.getInstance().getTime()));
//        }
//        return sb.toString();
//    }

    /** Function opens up a fileChooser, so the user can
     *  select the json file then parses it and
     *  returns a semesterprofile object
     *
     * @return semesterProfile
     */
    @FXML
    private SemesterProfile parseProfile() {
        File file = null;
        final FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(stageHandler.getStage());

        SemesterProfile semesterProfile = null;
        if(file != null){
            semesterProfile = FileParser.parseFile(file);
            return semesterProfile;
        }
        return null;
    }

    private File directRegistry(){
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog((stageHandler.getStage()));
        return file;
    }

    /** Basic FXML function on button
     *  press changes scene to login
     *
     */
    @FXML
    public void showLoginScreen() {
        stageHandler.setScene(StageHandler.SCENE.LOGIN, false);
    }

    @Override
    public void setParentScene(StageHandler parentScene) {
        this.stageHandler = parentScene;
    }
}
