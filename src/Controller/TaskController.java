package Controller;

import Model.Assessment;
import Model.Module;
import Model.SemesterProfile;
import Model.Task;
import Utils.SPException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * Created by Didac on 04/05/2017.
 */
public class TaskController {

    // Constant queries ------------------------------------------------------------------------------------------------

    private static final String QUERY_FIND_SPECIFIC_TASK = "SELECT * FROM Task INNER JOIN Assessment WHERE assessment_id = ? AND Task.task_id = 2";
    private static final String QUERY_FIND_TASKS = "SELECT * FROM Task LEFT JOIN Assessment ON (Task.assessment_id = Assessment.assessment_id) WHERE Assessment.assessment_id = ?";
    private static final String QUERY_FIND_DEPENDENCY = "SELECT * FROM Task WHERE task_id = ?";
    private static final String QUERY_INSERT =
            "INSERT INTO Task (title, task_type, time, criterion, criterion_value, progress) VALUES (?,?,?,?,?,?)";
    private static final String QUERY_INSERT_DEPENDENCY =
            "ADD CONSTRAINT '";

    private DatabaseHandler dbhandler;

    public TaskController(){
        dbhandler = DatabaseHandler.getInstance();
    }

    public ArrayList<Task> findAll(Assessment assessment){
        Task task = null;
        ArrayList<Task> tasks = new ArrayList<>();

        try (
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_FIND_TASKS, false, assessment.getId());
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                task = formTask(resultSet);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    private Task find_dependency(int depTaskId){
        Task task = null;
        try (
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_FIND_DEPENDENCY, false, depTaskId);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) task = formTask(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

    public void addTask(Task task){

        // INSERT TASK
        Object[] properties = {
                task.getTitle(),
                task.getType().toString(),
                task.getTime(),
                task.getCriterion(),
                task.getCriterionValue(),
                task.getProgress(),
                task.getDependencyTask().getId()

        };

        // CHECK IF DEPENDENCIES

        // ADD OR DELETE DEPENDENCIES

        // GET ALL DEPENDENCIES COUNT, MATCH AGAINST DEPENDENCIES .SIZE(), FOR EACH ID THAT DOESNAE MATCH, DELETE

        try (
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_INSERT, true, properties)
        ) {
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) throw new SPException("Failed to create new task. No rows affected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Task formTask(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("task_id");
        String title = resultSet.getString("title");
        Task.TaskType taskType = Task.TaskType.valueOf(resultSet.getString("type"));
        int time = resultSet.getInt("time");
        String criterion = resultSet.getString("criterion");
        int criterionValue = resultSet.getInt("criterion_value");
        int progress = resultSet.getInt("progress");

        // For the dependency, we will retrieve the id of the task it depends on
        Task task = null;
        int dep_id = resultSet.getInt("dependency");
        if (dep_id != 0){
            task = find_dependency(dep_id);
        }

        return new Task(id, title, taskType, time, criterion, criterionValue, progress, task);

    }
}
