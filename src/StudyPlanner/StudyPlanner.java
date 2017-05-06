package StudyPlanner;

import Controller.DatabaseHandler;
import Utils.StageHandler;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StudyPlanner extends Application {

    private static String WINDOW_TITLE = "Study Planner";

    private static String LOGINFXML = "../View/LoginView.fxml";
    //private static String LOGINCSS = "../View/LoginCSS.css";

    private static String REGISTERFXML = "../View/RegisterView.fxml";
    //private static String REGISTERCSS = "../View/RegisterCSS.css";
    private static String SEMESTERFXML = "../View/SemesterView.fxml";
    //public static Stage stage;


    @Override
    public void start(Stage stage) throws Exception{

        StageHandler stageHandler = new StageHandler(stage);

        stageHandler.loadScene(StageHandler.SCENE.LOGIN, LOGINFXML);
        stageHandler.loadScene(StageHandler.SCENE.REGISTER, REGISTERFXML);
        stageHandler.loadScene(StageHandler.SCENE.SEMESTER, SEMESTERFXML);

        stageHandler.setScene(StageHandler.SCENE.LOGIN, false);

        Group root = new Group();
        root.getChildren().add(stageHandler);

        Scene scene = new Scene(root);

        //root.scaleXProperty().bind(scene.widthProperty());
        //root.scaleYProperty().bind(scene.heightProperty());
        System.out.println(stage.getHeight());
        stage.setScene(scene);
        stage.setTitle(WINDOW_TITLE);
        stage.show();

    }

    @Override
    public void stop(){
        //TODO: SAVE STUFF HERE
        //TODO: This method handles when window closes
        DatabaseHandler.getInstance().closeConnection();
        System.out.println("Window closing");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
