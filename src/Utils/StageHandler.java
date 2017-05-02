package Utils;

import StudyPlanner.StudyPlanner;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;

/**
 * Provides a handler to switch between scenes, and provides a public enum with the scenes available
 * Created by Didac on 30/04/2017.
 */
public class StageHandler extends StackPane{

    // Constants -------------------------------------------------------------------------------------------------------

    private static final double STAGE_WIDTH = 600;
    private static final double STAGE_HEIGHT = 400;

    public enum SCENE {
        LOGIN, REGISTER, SEMESTER
    }
    // Variables -------------------------------------------------------------------------------------------------------

    // Hash map of scene names, and corresponding nodes in the stack pane
    private HashMap<SCENE, Node> scenes = new HashMap<>();
    private Stage stage;

    // Constructor -----------------------------------------------------------------------------------------------------

    /**
     * Constructs a handler for scenes.
     * @param stage The stage where all the scenes are displayed. Needed to be able to resize the window.
     */
    public StageHandler(Stage stage){
        super();
        this.stage = stage;
    }

    // Methods ---------------------------------------------------------------------------------------------------------

    /**
     * It will remove the currently displayed screen if there's already one being displayed
     * and show the scene passed with animation.
     * @param sceneName Scene to be displayed
     */
    private void animateAndChange(SCENE sceneName){
        final DoubleProperty opacity = opacityProperty();

        if (!getChildren().isEmpty()) {    //if there's more than one scene
            Timeline fade = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                    new KeyFrame(new Duration(100), t -> {
                        getChildren().remove(0);                    //remove the displayed screen
                        getChildren().add(0, scenes.get(sceneName));     //add the screen
                        Timeline fadeIn = new Timeline(
                                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                new KeyFrame(new Duration(100), new KeyValue(opacity, 1.0)));
                        fadeIn.play();
                    }, new KeyValue(opacity, 0.0)));
            fade.play();

        } else {
            setOpacity(0.0);
            getChildren().add(scenes.get(sceneName));       //no one else been displayed, then just show
            Timeline fadeIn = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                    new KeyFrame(new Duration(100), new KeyValue(opacity, 1.0)));
            fadeIn.play();
        }
    }


    /**
     * Loads a scene to get it ready for use with setScene()
     * @param sceneName The scene name to load
     * @param FXMLName The scene fxml, which describes the appearance.
     * @return
     */
    public boolean loadScene(SCENE sceneName, String FXMLName){
        try {
            FXMLLoader resourceLoader = new FXMLLoader(getClass().getResource(FXMLName));

            // Get a scene based on the fxml
            Parent scene = resourceLoader.load();

            ControlledScene sceneController = resourceLoader.getController();

            sceneController.setParentScene(this);
            //scene.getStyleClass().add("../View/LoginCSS.css");
            //scene.getStyleClass().addAll("root", "button");
            //scene.getStylesheets().add("View/LoginCSS.css");
            //System.out.println(scene.getStyle());
            // Add the scene to hash map
            scenes.put(sceneName, scene);

            return  true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sets the displayed scene to the one passed.
     * @param sceneName     The name of the scene to be displayed
     * @param resizable     Whether it's resizable or not
     * @param stageWidth    The width of the window to be resized to
     * @param stageHeight   The height of the window to be resized to
     * @return
     */
    public boolean setScene(SCENE sceneName, boolean resizable, double stageWidth, double stageHeight){
        if (scenes.get(sceneName) == null) {
            System.out.println("Scene hasn't been loaded");
            return false;
        }

        // Change the stage's dimensions and make it resizable or not
        stage.setWidth(stageWidth);
        stage.setMinWidth(stageWidth);
        stage.setHeight(stageHeight);
        stage.setMinHeight(stageHeight);
        stage.setResizable(resizable);

        // Do animation and change scene
        animateAndChange(sceneName);
        return true;
    }

    /**
     * Sets the displayed scene to the one passed.
     * @param sceneName     The name of the scene to be displayed
     * @param resizable     Whether it's resizable or not
     * @return
     */
    public boolean setScene(SCENE sceneName, boolean resizable){
        return setScene(sceneName, resizable, STAGE_WIDTH, STAGE_HEIGHT);
    }


    public Node getScene(SCENE sceneName){
        return scenes.get(sceneName);
    }
    public void setStageTitle(String title){
        stage.setTitle(title);
    }
    public Stage getStage(){return stage;}
    /*
    public boolean unloadScene(SCENE sceneName) {
        if (scenes.remove(sceneName) == null) {
            System.out.println("Scene doesn't exist");
            return false;
        } else {
            return true;
        }
    }
    */
}
