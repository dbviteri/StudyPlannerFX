package Controller;

import Model.*;
import Utils.SPException;
import Utils.StageHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by Didac on 01/05/2017.
 */
public class SemesterController implements DBQuery {

    // Constant queries ------------------------------------------------------------------------------------------------

    //TODO-IMPORTANT: Queries for semester. What data will be stored in the database?

    //? Query to insert a module after reading the file
    //? Query to

    private static final String QUERY_COUNT_SEMESTERS =
            "SELECT *, count *";
    private static final String QUERY_ALL_SEMESTERS =
            "SELECT start_date, end_date FROM SemesterProfile WHERE semester_id = ?";
    private static final String QUERY_USER_SEMESTER =
            //"SELECT * FROM Semester_Profile WHERE Semester_Profile.user_id = ?";
            "SELECT Semester_Profile.*, Module.*, Assessment.*, Milestone.*, Task.*," +
                    "dep_task.task_id AS 'dep_id'," +
                    "dep_task.task_title AS 'dep_title'," +
                    "dep_task.task_type AS 'dep_type'," +
                    "dep_task.time AS 'dep_time'," +
                    "dep_task.criterion AS 'dep_criterion'," +
                    "dep_task.criterion_value AS 'dep_crit_val'," +
                    "dep_task.progress AS 'dep_progress'," +
                    "dep_task.date AS 'dep_date', " +
                    "Activity.*," +
                    "Note.note_title," +
                    "Note.text," +
                    "Note.task_id AS 'note_task_id'," +
                    "Note.activity_id AS 'note_activity_id'," +
                    "Note.date " +
                    "FROM Semester_Profile " +
                    "JOIN Module ON (Semester_Profile.semester_id = Module.Semester_ID) " +
                    "LEFT JOIN Assessment ON (Module.module_id = Assessment.module_id) " +
                    "LEFT JOIN Milestone ON (Assessment.assessment_id = Milestone.assessment_id) " +
                    "LEFT JOIN Task ON (Assessment.assessment_id = Task.task_assessment_id) OR (Task.task_milestone_id = Milestone.Milestone_id) " +
                    "LEFT JOIN Task dep_task ON (Task.task_id = dep_task.dependency) " +
                    "LEFT JOIN Activity ON (Activity.task_id = Task.task_id) " +
                    "LEFT JOIN Note ON (Note.activity_ID = Activity.activity_ID OR Note.task_ID = Task.task_id) " +
                    "WHERE user_id = ? ORDER BY Task.task_id DESC";
    private static final String QUERY_INSERT_SEMESTER =
            "INSERT INTO Semester_Profile (start_date, end_date, user_id) VALUES (?,?,?)";
    private static final String QUERY_GET_SEMESTER_ID =
            "SELECT Semester_ID FROM SemesterProfile WHERE user_id = ? ";


    // Variables -------------------------------------------------------------------------------------------------------

    protected static DatabaseHandler dbhandler = DatabaseHandler.getInstance();

    // Constructor -----------------------------------------------------------------------------------------------------


    /**
     * Find semesters for a given user
     *
     * @param
     * @return
     */
//    public static SemesterProfile find(int userId) {
//        return find(QUERY_USER_SEMESTER, userId);
//    }
    public void loadSemester(int userId) {
        Session.addSemesterToUser(find(QUERY_USER_SEMESTER, userId));
    }


    private SemesterProfile find(String sql, Object... properties) {
        SemesterProfile semesterProfile = null;

        try (
                PreparedStatement statement = dbhandler.prepareStatement(sql, false, properties);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) semesterProfile = formSemester(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return semesterProfile;
    }

    public void insertSemester(SemesterProfile semester) {
        Object[] properties = {
                semester.getStartDate(),
                semester.getEndDate(),
                dbhandler.getUserSession().getId()
        };

        try (
                PreparedStatement statement =
                        dbhandler.prepareStatement(QUERY_INSERT_SEMESTER, true, properties)

        ) {
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) throw new SPException("Failed to create new Semester. No rows affected");

            try (ResultSet set = statement.getGeneratedKeys()) {
                if (set.next()) {
                    semester.setSemesterId(set.getInt(1));
                } else {
                    throw new SPException("Failed to create new Semester. No key obtained");
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private SemesterProfile formSemester(ResultSet resultSet) throws SQLException {
        TaskController taskController = new TaskController();
        ActivityController activityController = new ActivityController();
        SemesterProfile semesterProfile = new SemesterProfile();
        Map<Module, Module> modules = semesterProfile.getModules();
        //ArrayList<Module> modules = semesterProfile.getModules();
        do {
            /** BUILD SEMESTER **/
            semesterProfile.setSemesterId(resultSet.getInt("semester_id"));
            semesterProfile.setStartDate(resultSet.getDate("start_date"));
            semesterProfile.setEndDate(resultSet.getDate("end_date"));

            /** BUILD MODULE **/
            Module module = ModuleController.formModule(resultSet);

            semesterProfile.addModule(module);

            /** BUILD ASSESSMENT **/

            Assessment assessment = AssessmentController.formAssessment(resultSet);

            modules.get(module).addAssessment(assessment);

            Milestone milestone = null;
            /** BUILD MILESTONES IF ASSESSMENT HAS THEM **/
            int milestone_id = resultSet.getInt("milestone_id");
            if (!resultSet.wasNull()) {
                String title = resultSet.getString("milestone_title");
                double progress = resultSet.getDouble("milestone_progress");
                Date mDate = resultSet.getDate("milestone_start");
                Date deadline = resultSet.getDate("milestone_deadline");
                milestone = new Milestone(milestone_id, title, progress, mDate, deadline);

                modules.get(module).getAssessments().get(assessment).addMilestone(milestone);
            }


            /** IF THERE IS A TASK BUILD IT AND ADD IT TO ASSESSMENT OR MILESTONE **/
            resultSet.getInt("task_id");
            if (!resultSet.wasNull()) {
                HashMap<Task, Task> milestoneTasks = new HashMap<>();
                Map<Task, Task> assessmentTasks = modules.get(module).getAssessments().get(assessment).getTasks();
                if (milestone != null) {
                    milestoneTasks = modules.get(module).getAssessments().get(assessment).getMilestones().get(milestone).getTasks();
                }

                Task task = taskController.formTask(resultSet);
                if (assessmentTasks.containsKey(task)) task = assessmentTasks.get(task);
                if (milestoneTasks.containsKey(task)) task = milestoneTasks.get(task);

                /** IF TASK HAS ACTIVITIES BUILD IT AND ADD IT TO TASK **/
                resultSet.getInt("activity_ID");
                if (!resultSet.wasNull()) {
                    Activity activity = activityController.formActivity(resultSet);

                    task.addActivity(activity);

                    int noteActivityId = resultSet.getInt("note_activity_id");
                    if (!resultSet.wasNull()) {
                        Note note = NoteController.formNote(resultSet);
                        ActivityNote activityNote = new ActivityNote(noteActivityId, note.getTitle(), note.getText(), note.getDate());

                        task.getActivities().get(activity).addNote(activityNote);
                    }
                }

                int noteTaskId = resultSet.getInt("note_task_id");
                if (!resultSet.wasNull()) {
                    Note note = NoteController.formNote(resultSet);
                    TaskNote taskNote = new TaskNote(noteTaskId, note.getTitle(), note.getText(), note.getDate());

                    task.setTaskNote(taskNote);
                }

                // If task milestone id is not null, belongs to a milestone
                resultSet.getInt("task_milestone_id");
                if (!resultSet.wasNull()) {
                    /** FIND WHERE THE DEPENDENCY BELONGS **/
                    int depId = resultSet.getInt("dep_id");
                    if (!resultSet.wasNull()) { // Add the dependency
//                        Map<Task, Task> milestoneTasks = modules.get(module).getAssessments().get(assessment)
//                                .getMilestones().get(milestone).getTasks();

                        // if taskid = dep_id add the current row task as a dependency
                        for (Task currentTask : milestoneTasks.values()) {
                            if (currentTask.getId() == depId) {
                                task.addDependency(currentTask);
                                break;
                            }
                        }
                    }

                    modules.get(module).getAssessments()
                            .get(assessment).getMilestones()
                            .get(milestone).addTask(task);
                } else {
                    /** FIND WHERE THE DEPENDENCY BELONGS **/
                    int depId = resultSet.getInt("dep_id");
                    if (!resultSet.wasNull()) { // Add the dependency
//                        Map<Task, Task> assessmentTasks = modules.get(module).getAssessments().get(assessment)
//                                .getTasks();

                        // if taskid = dep_id add the current row task as a dependency
                        for (Task currentTask : assessmentTasks.values()) {
                            if (currentTask.getId() == depId) currentTask.addDependency(task);
                        }
                    }

                    modules.get(module).getAssessments()
                            .get(assessment).addTask(task);
                }


            }
        } while (resultSet.next());
        return semesterProfile;
    }

    public void updateSemester(SemesterProfile semesterProfile){
        for(Module module : semesterProfile.getModules().values()) {
            for(Assessment assessment : module.getAssessments().values()){
                AssessmentController.updateDeadline(assessment);
            }
        }
    }
    public void logOut(StageHandler stageHandler){
        dbhandler.deleteSession();
        stageHandler.reloadScene(StageHandler.SCENE.LOGIN);
        stageHandler.setScene(StageHandler.SCENE.LOGIN,false);
    }
    public boolean savaSemester(SemesterProfile semester){

        return false;
    }



    // Load semester file?
    // TODO : add file checking or rely on SQL checks
    // TODO : One function for checking and parsing or TWO separate ones ?
//    public boolean checkFile(File file) throws IOException {
//        boolean valid = false;
//        String line;
//        final String separator = ",";
//        Module aModule;
//        Assessment assessment;
//        BufferedReader reader = openFile(file);
//        SemesterProfile semesterProfile = new SemesterProfile();
//        User user= dbhandler.getUserSession();
//
//        reader.readLine();
//        reader.readLine();
//        JSONObject obj = new JSONObject();
//        if (reader != null && file.getTitle().contains(".csv")) {
//            while ((line = reader.readLine()) != null) {
//                String[] data = line.split(separator);
//                if(data.length == 2)
//                if(data.length == 2) {
//                    aModule = new Module(data[0], data[1]);
//                    ModuleController.insertModule(aModule);
//                }
//                else {
//                    reader.readLine();
//                    assessment = new Assessment(data[0],data[1],
//                            Integer.parseInt(data[2]), makeDate(data[3]));
//
//                }
//            }
//        }
//            return valid;
//
//    }
    // Helper functions ------------------------------------------------------------------------------------------------


}
