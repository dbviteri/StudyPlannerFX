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
            "SELECT * FROM Task LEFT JOIN Assessment ON (Task.assessment_id = Assessment.assessment_id) WHERE Task.assessment_id = ?";
    private static final String QUERY_FIND_DEPENDENCIES =
            "SELECT * FROM Task WHERE dependency = ?"; // taskId
    private static final String QUERY_INSERT_TASK =
            "INSERT INTO Task (title, type, time, criterion, criterion_value, progress, assessment_id, dependency) VALUES (?,?,?,?,?,?,?,?)";
    private static final String QUERY_DELETE_TASK =
            "DELETE FROM Task WHERE task_id = ?";

    private static DatabaseHandler dbhandler = DatabaseHandler.getInstance();

    public static ArrayList<Task> findAll(int assessmentId) {
        return findAll(QUERY_FIND_TASKS, assessmentId);
    }

    public static ArrayList<Task> findAllDependencies(int taskId) {
        return findAll(QUERY_FIND_DEPENDENCIES, taskId);
    }

    private static ArrayList<Task> findAll(String sql, Object... properties) {
        ArrayList<Task> tasks = new ArrayList<>();

        try (
                PreparedStatement statement = dbhandler.prepareStatement(sql, false, properties);
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

    public void insertTask(Task task){
        // INSERT TASK
        Object[] properties = {
                task.getTitle(),
                task.getType().toString(),
                task.getTime(),
                task.getCriterion(),
                task.getCriterionValue(),
                task.getProgress(),
                //task.getAssessmentId(),
                null // Dependency
        };

        // if the dependency of task is null, it has no dependency
//        if (task.getDependencyTasks() != null) {
//            properties[properties.length - 1] = task.getDependencyTasks().get(0).getId();
//        }

        try (
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_INSERT_TASK, false, properties)
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
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_DELETE_TASK, false, properties)
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

    public static Task formTask(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("task_id");
        String title = resultSet.getString("task_title");
        Task.TaskType taskType = Task.TaskType.valueOf(resultSet.getString("task_type"));
        int time = resultSet.getInt("time");
        String criterion = resultSet.getString("criterion");
        int criterionValue = resultSet.getInt("criterion_value");
        int progress = resultSet.getInt("progress");

        // Find the dependencies for this task
        //ArrayList<Task> dependencies = findAllDependencies(id);
        //int assessmentId = resultSet.getInt("assessment_id");

        //return new Task(id, title, taskType, time, criterion, criterionValue, progress, task, assessmentId);
        return new Task(id, title, taskType, time, criterion, criterionValue, progress);
    }

    public static Task formDependency(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("dep_id");
        String title = resultSet.getString("dep_title");
        Task.TaskType taskType = Task.TaskType.valueOf(resultSet.getString("dep_type"));
        int time = resultSet.getInt("dep_time");
        String criterion = resultSet.getString("dep_criterion");
        int criterionValue = resultSet.getInt("dep_crit_val");
        int progress = resultSet.getInt("dep_progress");

        return new Task(id, title, taskType, time, criterion, criterionValue, progress);
    }
}
