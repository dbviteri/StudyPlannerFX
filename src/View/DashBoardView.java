package View;

import Controller.DatabaseHandler;
import Controller.SemesterController;
import Model.Assessment;
import Model.Module;
import Model.SemesterProfile;
import Model.User;
import Utils.StageHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.Date;
import java.util.Map;

/**
 * Created by Didac on 08/05/2017.
 */
public class DashBoardView {

    private static final int COMPLETED_COLUMN = 2;
    private static final int UPCOMING_COLUMN = 1;
    private static final int MISSED_COLUMN = 0;

    private static int ROW_INDEX_COMPLETED = 1;
    private static int ROW_INDEX_UPCOMING = 1;
    private static int ROW_INDEX_MISSED = 1;

    @FXML
    GridPane dashboardGrid;
    @FXML
    GridPane completedDeadlineGrid;
    @FXML
    GridPane upcomingDeadlineGrid;
    @FXML
    GridPane missedDeadlineGrid;

    private DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

    public void load(StageHandler stageHandler) {
        User user = databaseHandler.getUserSession();
        //SemesterProfile semesterProfile = MasterController.getSemester(user.getId());
        SemesterProfile semesterProfile = SemesterController.find(user.getId());
        Map<Module, Module> modules = semesterProfile.getModules();

        // Decide what pane should the assignments go based on deadline
        Date date = new Date();
        // For each module
        for (Map.Entry<Module, Module> moduleEntry : modules.entrySet()) {
            // Find the related assessments for that module
            Map<Assessment, Assessment> assessments = moduleEntry.getValue().getAssessments();
            for (Map.Entry<Assessment, Assessment> assessmentEntry : assessments.entrySet()) {
                // For each module create a label with the module name
                Assessment assessment = assessmentEntry.getValue();

                // Create a list of labels of the size of assessments
                // If compareTo is more than 0, date is after the deadline
                // Should go to missed deadlines

                // If compareTo is less than 0, date is before the deadline
                // Should go to upcoming deadlines
                Label assessmentInfo = new Label(assessment.toString());


                if (date.compareTo(assessment.getDeadLine()) < 0) {
                    Button button = new Button();
//                    Bounds upcomingDeadlinesLblBounds = upcomingDeadlinesLbl.getBoundsInLocal();
//                    Point2D labelLocation = upcomingDeadlinesLbl.localToScreen()
                    upcomingDeadlineGrid.add(assessmentInfo, UPCOMING_COLUMN, ROW_INDEX_UPCOMING);
                    //upcomingDeadlineGrid.addColumn(UPCOMING_COLUMN, assessmentInfo);
                    upcomingDeadlineGrid.addRow(ROW_INDEX_UPCOMING, button);
                    //upcomingDeadlineGrid.setVgap(20);
                    //upcomingDeadlinesLbl.setText(assessment.getTitle());
                    ROW_INDEX_UPCOMING++;
                } else if (date.compareTo(assessment.getDeadLine()) > 0) {
                    missedDeadlineGrid.add(assessmentInfo, MISSED_COLUMN, ROW_INDEX_MISSED);
                    //missedDeadlineGrid.setVgap(20);
                    //missedDeadlinesLbl.setText(assessment.getTitle());
                    ROW_INDEX_MISSED++;
                }

                //assessmentInfo.setPadding(new Insets(0, 0, 20, 0));
                //System.out.println(date.compareTo(assessment.getDeadLine()));
            }


        }

//        // Re-fit the gridpane after adding the new rows based on the largest row index
        int rowsAdded = (ROW_INDEX_COMPLETED > ROW_INDEX_MISSED) ? ROW_INDEX_COMPLETED : ROW_INDEX_MISSED;
        rowsAdded = (rowsAdded > ROW_INDEX_UPCOMING) ? rowsAdded : ROW_INDEX_UPCOMING;

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.RIGHT);
        upcomingDeadlineGrid.getColumnConstraints().add(columnConstraints);

        completedDeadlineGrid.setVgap(20);
        upcomingDeadlineGrid.setVgap(20);
        missedDeadlineGrid.setVgap(20);

        completedDeadlineGrid.setHgap(20);
        upcomingDeadlineGrid.setHgap(20);
        missedDeadlineGrid.setHgap(20);
        //completedDeadlineGrid.setAlignment(Pos.CENTER);


        //System.out.println(SemesterController.find(user.getId()).getEndDate());
        //System.out.println("can call");
    }
}
