package Controller;

import Model.Task;
import Utils.SPException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * Created by Didac on 04/05/2017.
 */
public class TaskController implements DBQuery {

    // Constant queries ------------------------------------------------------------------------------------------------

    private static final String QUERY_FIND_TASKS =
            "SELECT * FROM Task LEFT JOIN Assessment ON (Task.assessment_id = Assessment.assessment_id)" +
                    " WHERE Task.assessment_id = ?";
    private static final String QUERY_FIND_DEPENDENCIES =
            "SELECT * FROM Task WHERE dependency = ?"; // taskId
    private static final String QUERY_INSERT_TASK =
            "INSERT INTO Task (task_title, task_type, time, criterion, " +
                    "criterion_value, progress, date, dependency, task_assessment_id, task_milestone_id) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String QUERY_DELETE_TASK =
            "DELETE FROM Task WHERE task_id = ?";
    private static final String QUERY_UPDATE_TASK =
            "UPDATE Task SET task_title = ?, task_type = ?, time = ?, criterion = ?," +
                    " criterion_value = ?, progress = ?, date = ? WHERE task_id = ?";

    private DatabaseHandler dbhandler = DatabaseHandler.getInstance();

    public ArrayList<Task> findAll(int assessmentId) {
        return findAll(QUERY_FIND_TASKS, assessmentId);
    }

    public ArrayList<Task> findAllDependencies(int taskId) {
        return findAll(QUERY_FIND_DEPENDENCIES, taskId);
    }

    private ArrayList<Task> findAll(String sql, Object... properties) {
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

    public void insertTask(Task task, Integer assessmentId, Integer milestoneId) {
        Object[] properties = {
                task.getTitle(),
                task.getType().toString(),
                task.getTime(),
                task.getCriterion(),
                task.getCriterionValue(),
                task.getProgress(),
                task.getDate(),
                null,
                assessmentId,
                milestoneId,
        };

        // INSERT TASK
        if (task.getDependency() != null) {
            properties[7] = task.getDependency().getId();
        }


        try (
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_INSERT_TASK, true, properties)
        ) {
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) throw new SPException("Failed to create new task. No rows affected");
            try (ResultSet set = statement.getGeneratedKeys()) {
                if (set.next()) {
                    task.setId(set.getInt(1));
                } else {
                    throw new SPException("Failed to create new Task. No key obtained");
                }
            }
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
    public boolean updateTask(Task task){
        Object[] properties = {
                task.getTitle(),
                task.getType(),
                task.getTime(),
                task.getCriterion(),
                task.getCriterionValue(),
                task.getProgress(),
                task.getDate(),
                task.getId()
        };

        try (
                PreparedStatement statement =
                        dbhandler.prepareStatement(QUERY_UPDATE_TASK, false, properties)
        ) {
            int updates = statement.executeUpdate();
            if (updates == 0) throw new SPException("Failed to update Task. No rows affected");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Task formTask(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("task_id");
        String title = resultSet.getString("task_title");
        Task.TaskType taskType = Task.TaskType.valueOf(resultSet.getString("task_type"));
        int time = resultSet.getInt("time");
        String criterion = resultSet.getString("criterion");
        int criterionValue = resultSet.getInt("criterion_value");
        int progress = resultSet.getInt("progress");
        Date date = resultSet.getDate("date");
        // Find the dependencies for this task
        //ArrayList<Task> dependencies = findAllDependencies(id);
        //int assessmentId = resultSet.getInt("assessment_id");
        Task task = new Task(id, title, taskType, criterion, criterionValue, progress, date);
        task.setTime(time);
        return task;
    }

    public Task formDependency(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("dep_id");
        String title = resultSet.getString("dep_title");
        Task.TaskType taskType = Task.TaskType.valueOf(resultSet.getString("dep_type"));
        int time = resultSet.getInt("dep_time");
        String criterion = resultSet.getString("dep_criterion");
        int criterionValue = resultSet.getInt("dep_crit_val");
        int progress = resultSet.getInt("dep_progress");
        Date date = resultSet.getDate("dep_date");
        Task task = new Task(id, title, taskType, criterion, criterionValue, progress,date);
        task.setTime(time);
        return task;
    }
}
