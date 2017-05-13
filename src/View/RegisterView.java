package View;

import Controller.SemesterController;
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
import java.io.IOException;

/**
 * Created by Didac on 05/05/2017.
 */
public class RegisterView extends UserController implements ControlledScene{
    @FXML private TextField nameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    private StageHandler stageHandler;

    public RegisterView(){
        super();
    }

    @FXML
    public void registerUser(){
        SemesterProfile semesterProfile = parseProfile();
        if (semesterProfile == null) {
            new AlertDialog(Alert.AlertType.ERROR, "Wrong file.");
            return;
        }

        if (userExists(usernameField.getText())) {
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

        UserController.create(user);
        //semesterProfile.setUserId(user.getId());


        SemesterController.insertSemester(semesterProfile);
//        for (Module module : semesterProfile.getModules()) {
//            ModuleController.insertModule(module);
////            for (Assessment assessment : module.getAssessments()) {
////                AssessmentController.insertAssessment(assessment);
////            }
//        }
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

    @FXML
    private SemesterProfile parseProfile() {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stageHandler.getStage());

        SemesterProfile semesterProfile = null;

        try {
            semesterProfile = FileParser.parseFile(file);
        } catch (IOException e) {
            // TODO: Display a message saying the file is not .json
            //new AlertDialog(Alert.AlertType.ERROR, e.getMessage());
        }

        return semesterProfile;
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
