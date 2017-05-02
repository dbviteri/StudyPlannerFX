package View;

import Controller.DatabaseHandler;
import Controller.UserController;
import Model.User;
import Utils.ControlledScene;
import Utils.StageHandler;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;


public class LoginView implements ControlledScene {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Tooltip usernameTooltip;

    private StageHandler stageHandler;

    @FXML
    public void loginUser() {
        DatabaseHandler studyplannerdb = DatabaseHandler.getInstance("xdn15mcu_studyplanner.jdbc");
        UserController uc = studyplannerdb.getUserController();
        String username = usernameField.getText();//"xdn15mcu";
        String password = passwordField.getText();//"test";
        User user = uc.find(username, password);
        // If user is not null, show the main panel
        if(user == null) {
            usernameField.getStyleClass().add("error");
            //final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
            //usernameField.pseudoClassStateChanged(errorClass, true);

            usernameTooltip.setText("Username or password is wrong. Please try again.");

            Point2D point2D = usernameField.localToScene(0.0, 0.0);
            usernameTooltip.setAutoHide(true);
            usernameTooltip.show(stageHandler.getStage(),
                    point2D.getX() + usernameField.getScene().getX() +
                            usernameField.getScene().getWindow().getX() + usernameField.getWidth(),
                    point2D.getY() + usernameField.getScene().getY() + usernameField.getScene().getWindow().getY());
        }
        else showSemester(user.getFirstname());
    }

    @FXML
    private void showRegister() {
        stageHandler.setScene(StageHandler.SCENE.REGISTER, false);
    }

    private void showSemester(String name) {
        stageHandler.setStageTitle("Welcome back " + name + "!");
        stageHandler.setScene(StageHandler.SCENE.SEMESTER, true);
    }

    @Override
    public void setParentScene(StageHandler parentScene) {
        stageHandler = parentScene;
    }
}
