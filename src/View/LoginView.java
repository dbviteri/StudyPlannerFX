package View;

import Controller.DatabaseHandler;
import Controller.SemesterController;
import Controller.UserController;
import Model.Semester;
import Model.User;
import Utils.ControlledScene;
import Utils.StageHandler;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

import java.util.Stack;


public class LoginView implements ControlledScene {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Tooltip usernameTooltip;
    @FXML private ComboBox comboBox;

    private StageHandler stageHandler;
    //DatabaseHandler databaseHandler = DatabaseHandler.getInstance("xdn15mcu_studyplanner.jdbc");
    //UserController userController = databaseHandler.getUserController();
    UserController userController = new UserController();

    @FXML
    public void loginUser() {
        String username = usernameField.getText();//"xdn15mcu";
        String password = passwordField.getText();//"test";
        User user = userController.find(username, password);

        // If user is not null, show the main panel
        if(user == null) {
            displayTooltip("Username or password is wrong. Please try again.");
        } else {
            //databaseHandler.createSession(user);

            // Reload scene after creating a session
            stageHandler.reloadScene(StageHandler.SCENE.SEMESTER);
            stageHandler.setScene(StageHandler.SCENE.SEMESTER, true);
        }
    }

    /*
    @FXML
    private void displaySemesters(){
        String username = usernameField.getText();//"xdn15mcu";
        String password = passwordField.getText();//"test";

        SemesterController semesterController = databaseHandler.getSemesterController();

        usernameField.textProperty().addListener(((observable, oldValue, newValue) -> {
//            if (oldValue.equals(newValue) && comboBox.getItems().isEmpty()){
//                Stack<Semester> semesters = semesterController.findSemesters(userController.find(username, password));
//                while (!semesters.empty()){
//                    comboBox.getItems().add(semesters.pop());
//                }
//            } else {
//                comboBox.getItems().removeAll();
//            }
            comboBox.getItems().clear();
            //System.out.println(oldValue + " " + newValue);
        }));


        if (!usernameField.getText().isEmpty() && comboBox.getItems().isEmpty()){
            Stack<Semester> semesters = semesterController.findSemesters();
            if (semesters.empty()) {
                displayTooltip("Username or password is wrong. Please try again.");
            } else {
                while (!semesters.empty()) {
                    comboBox.getItems().add(semesters.pop());
                }
            }
        }

    }
*/
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
