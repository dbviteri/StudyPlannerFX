package Controller;

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

    private static final String QUERY_FIND_TASKS =
            "SELECT * FROM Task LEFT JOIN Assessment ON (Task.assessment_id = Assessment.assessment_id) WHERE Assessment.assessment_id = ?";
    private static final String QUERY_FIND_DEPENDENCY =
            "SELECT * FROM Task WHERE task_id = ?";
    private static final String QUERY_INSERT_TASK =
            "INSERT INTO Task (title, type, time, criterion, criterion_value, progress, assessment_id, dependency) VALUES (?,?,?,?,?,?,?,?)";
    private static final String QUERY_DELETE_TASK =
            "DELETE FROM Task WHERE task_id = ?";

    private static DatabaseHandler dbhandler = DatabaseHandler.getInstance();

    public ArrayList<Task> findAll(int assessmentId){
        ArrayList<Task> tasks = new ArrayList<>();

        try (
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_FIND_TASKS, assessmentId);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                tasks.add(formTask(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public Task findDependency(int depTaskId){
        Task task = null;
        try (
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_FIND_DEPENDENCY, depTaskId);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) task = formTask(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

    public void insertTask(Task task){
        // INSERT TASK
        Object[] properties = {
                task.getTitle(),
                task.getType().toString(),
                task.getTime(),
                task.getCriterion(),
                task.getCriterionValue(),
                task.getProgress(),
                task.getAssessmentId(),
                null // Dependency
        };

        // if the dependency of task is null, it has no dependency
        if (task.getDependencyTask() != null){
            properties[properties.length - 1] = task.getDependencyTask().getId();
        }

        try (
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_INSERT_TASK, properties);
        ) {
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) throw new SPException("Failed to create new task. No rows affected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(Task task){
        Object[] properties = {
                task.getId()
        };

        try (
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_DELETE_TASK, properties);
        ) {
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) {
                throw new SPException("Failed to delete task. No rows affected");
            } else {
                task.setId(null);
            }
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
            task = findDependency(dep_id);
        }

        int assessmentId = resultSet.getInt("assessment_id");

        return new Task(id, title, taskType, time, criterion, criterionValue, progress, task, assessmentId);

    }
}
