package StudyPlanner;

import Controller.ActivityController;
import Controller.DatabaseHandler;
import Controller.MilestoneController;
import Controller.TaskController;
import Model.*;
import Utils.StageHandler;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StudyPlanner extends Application {
    private static int UID_VARIABLE = 10000;
    private static String WINDOW_TITLE = "Study Planner";

    private static String LOGINFXML = "../View/LoginView.fxml";
    //private static String LOGINCSS = "../View/LoginCSS.css";

    private static String REGISTERFXML = "../View/RegisterView.fxml";
    //private static String REGISTERCSS = "../View/RegisterCSS.css";
    private static String SEMESTERFXML = "../View/SemesterView.fxml";
    //public static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        if (DatabaseHandler.getInstance().getConnection() != null) {
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

    }

    @Override
    public void stop(){
        //TODO: SAVE STUFF HERE
        //TODO: This method handles when window closes
        //DatabaseHandler.getInstance().closeConnection();
        TaskController taskController = new TaskController();
        ActivityController activityController = new ActivityController();
        SemesterProfile semester = DatabaseHandler.getInstance().getSemesterSession();
        for(Module module : semester.getModules().values()) {
            for(Assessment assessment : module.getAssessments().values()) {
                for(Task task : assessment.getTasks().values()) {
                    if(task.getId() < UID_VARIABLE){
                        taskController.updateTask(task);
                    }
                    else{
                        taskController.insertTask(task);
                    }
                    for(Activity activity : task.getActivities().values()) {
                        if(activity.getActivityId() < UID_VARIABLE){
                            activityController.updateActivity(activity);
                        }
                        else{
                            activityController.insertActivity(activity);
                        }
                    }
                }
                for(Milestone milestone : assessment.getMilestones().values()){
                    if(milestone.getId() < UID_VARIABLE){
                        MilestoneController.updateMilestone(milestone);
                    }
                    else {
                        MilestoneController.insertMilestone(milestone);
                    }
                }
            }
        }
        System.out.println("Window closing");
    }
}
