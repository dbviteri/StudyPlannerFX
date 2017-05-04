package View;

import Controller.DatabaseHandler;
import Controller.SemesterController;
import Model.Semester;
import Model.User;
import Utils.ControlledScene;
import Utils.StageHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Didac on 01/05/2017.
 */
public class SemesterView implements ControlledScene {

    @FXML Label testLabel;
    @FXML VBox vBox;
    @FXML Menu userMenu;

    // Constants
    private final String WELCOME_MSG = "Welcome back, ";

    private SemesterController semesterController;
    DatabaseHandler databaseHandler;
    private StageHandler stageHandler;
    private User user;

    public SemesterView(StageHandler stageHandler){
        databaseHandler = DatabaseHandler.getInstance("xdn15mcu_studyplanner.jdbc");
        semesterController = databaseHandler.getSemesterController();
        this.stageHandler = stageHandler;
    }

    public void initialize(){
        //semesterLabel.setText("test");
        if (databaseHandler.getUserSession() == null) return;

        user = databaseHandler.getUserSession();

        // Fill stage and other ui components with user variables ------------------------------------------------------

        stageHandler.getStage().setTitle(WELCOME_MSG + user.getFirstname() + "!");
        userMenu.setText(user.getFirstname());

        System.out.println(user.getEmail());
        ArrayList<Semester> semesters = semesterController.findAll(user);

        System.out.println(semesters.get(0).toString());
        testLabel.setText(semesters.get(0).toString());

        vBox.prefHeightProperty().bind(stageHandler.getStage().heightProperty().subtract(20));
        vBox.prefWidthProperty().bind(stageHandler.getStage().widthProperty());
        //System.out.println(vBox.prefHeightProperty());

    }

    @FXML
    public void logOut() {
        // Show log in screen and unload this scene from the stage handler
        // TODO: Upload to database all changes done
        // TODO: Delete session
        stageHandler.setScene(StageHandler.SCENE.LOGIN, false);
        databaseHandler.deleteSession();
    }

    private void populateView(){
        //System.out.println(semesterLabel.toString());
        //menu.setText("test");

        //semesterLabel.setText(semesters.get(0).toString());
    }


    //public void initialize(){

        // Show nothing if there's no session. Because of this, the scene for this view
        // has to be reloaded before being displayed.

//        handler = (EventHandler<MouseEvent>) event -> {
//            loadStuff();
//            System.out.println("Handling event " + event.getEventType());
//            event.consume();
//        };

        //vBox.addEventHandler(MouseEvent.MOUSE_ENTERED, handler);
    //}

    // Get modules for semester view

//    public SemesterView (User user) {
//        DatabaseHandler databaseHandler = DatabaseHandler.getInstance("xdn15mcu_studyplanner.jdbc");
//        SemesterController semesterController = databaseHandler.getSemesterController();
//
//        semesterController.findSemesters(user);
//    }


//    public void initialize(){
//        DatabaseHandler databaseHandler = DatabaseHandler.getInstance("xdn15mcu_studyplanner.jdbc");
//        SemesterController semesterController = databaseHandler.getSemesterController();
//
//
//        //user = databaseHandler.getUserSession(); // If null, session hasn't been started
//
//        //stageHandler.setStageTitle("Welcome back " + user.getFirstname() + "!");
//
//        //pane.prefWidthProperty().bind(stageHandler.getStage().widthProperty());
//        //pane.prefHeightProperty().bind(stageHandler.getStage().heightProperty());
//    }
//
//    @FXML
//    private void testFunction(){
//        stageHandler.getStage().widthProperty().addListener(e -> {
//            //stageHandler.getScene(StageHandler.SCENE.SEMESTER).prefWidth(stageHandler.getStage().getWidth());
//            pane.minWidth(stageHandler.getStage().getWidth());
//            System.out.println("ASDF " + stageHandler.getStage().getWidth() + " AASDF");
//        });
//
//        stageHandler.getStage().heightProperty().addListener(e -> {
//            //stageHandler.getScene(StageHandler.SCENE.SEMESTER).prefHeight(stageHandler.getStage().getHeight());
//            pane.minHeight(stageHandler.getStage().getHeight());
//            System.out.println("ASDF " + stageHandler.getStage().getHeight() + " AASDF");
//        });
//    }

    @Override
    public void setParentScene(StageHandler parentScene) {
        stageHandler = parentScene;
    }


}
