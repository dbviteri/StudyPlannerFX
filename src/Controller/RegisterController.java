package Controller;

import Model.*;
import Utils.ControlledScene;
import Utils.FileParser;
import Utils.StageHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Tooltip usernameTooltip;

    private StageHandler stageHandler;

    public RegisterController(){
        super();
    }

    @FXML
    public void registerUser(){

        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String firstname = nameField.getText();
        String lastname = lastNameField.getText();
        boolean isStaff = false; // TODO: HANDLE THIS IN REGISTER VIEW
        User user = new User(email, username, password, firstname, lastname, isStaff);

        // While there's a username, keep creating a new one
        if (userExists(username)){
            displayTooltip("Username is taken, please choose another one.");
            return;
        }

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
    private File fileChooser(){
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stageHandler.getStage());
        try {
            SemesterProfile semesterProfile = FileParser.parseFile(file);

            SemesterController.insertSemester(semesterProfile);
            for (Module module : semesterProfile.getModules()){
                ModuleController.insertModule(module);
                for (Assessment assessment : module.getAssessments()){
                    AssessmentController.insertAssessment(assessment);
                }
            }

        } catch (IOException e) {
            // Display a message saying the file is not .json
        }
        return file;
    }

    @FXML
    public void showLoginScreen() {
        stageHandler.setScene(StageHandler.SCENE.LOGIN, false);
    }

    //TODO: GET RID OF DUPLICATE CODE
    // This works for now
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

    @Override
    public void setParentScene(StageHandler parentScene) {
        this.stageHandler = parentScene;
    }
}
