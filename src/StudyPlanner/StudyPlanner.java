package StudyPlanner;

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
        root.getChildren().addAll(stageHandler);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(WINDOW_TITLE);
        stage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
