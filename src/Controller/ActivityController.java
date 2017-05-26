package Controller;

import Model.Activity;
import Utils.SPException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Didac on 07/05/2017.
 */
public class ActivityController implements DBQuery {

    // QUERIES ---------------------------------------------------------------------------------------------------------

    private static final String QUERY_FIND_ACTIVITIES = "SELECT * FROM Activity WHERE task_id = ?";
    private static final String QUERY_INSERT_ACTIVITY =
            "INSERT INTO Activity (quantity, time, activity_title, date, task_id) VALUES (?,?,?,?,?)";
    private static final String QUERY_UPDATE_ACTIVITY =
            "UPDATE Activity SET quantity = ?, time = ?, activity_title = ?, data = ? WHERE activity_ID = ?";

    // DATABASE INSTANCE -----------------------------------------------------------------------------------------------

    private static DatabaseHandler dbhandler = DatabaseHandler.getInstance();

    // METHODS ---------------------------------------------------------------------------------------------------------

    /** Function used to return everything
     *  matching the taskID from DB
     *
     * @param taskId
     * @return
     */
    public ArrayList<Activity> findAll(int taskId) {
        ArrayList<Activity> activities = new ArrayList<>();

        try (
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_FIND_ACTIVITIES, false, taskId);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                activities.add(formActivity(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activities;
    }

    /** Function used to insert an
     *  activity into the Database
     *
     * @param activity
     * @param taskId
     * @return
     */
    public boolean insertActivity(Activity activity, int taskId) {
        Object[] properties = {
                activity.getQuantity(),
                activity.getTime(),
                activity.getTitle(),
                activity.getDate(),
                taskId
        };

        try (
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_INSERT_ACTIVITY, true, properties)
        ) {
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) throw new SPException("Failed to create new activity. No rows affected");
            try (ResultSet set = statement.getGeneratedKeys()) {
                if (set.next()) {
                    activity.setActivityId(set.getInt(1));
                } else {
                    throw new SPException("Failed to create new activity. No key obtained");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /** Method used to create an
     *  activity from a resultSet
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    public Activity formActivity(ResultSet resultSet) throws SQLException {
        Integer activityId = resultSet.getInt("activity_ID");
        int quantity = resultSet.getInt("quantity");
        int time = resultSet.getInt("time");
        String title = resultSet.getString("activity_title");
        Date date = resultSet.getDate("date");
        return new Activity(activityId, title, quantity, time,date);
        //return new Activity();
    }

    /** Function used to update
     *  an activity in the Database
     *
     * @param activity
     * @return
     */
    public boolean updateActivity(Activity activity){
        Object[] properties = {
                activity.getQuantity(),
                activity.getTime(),
                activity.getTitle(),
                activity.getDate()

        };
        try (
                PreparedStatement statement =
                        dbhandler.prepareStatement(QUERY_UPDATE_ACTIVITY, false, properties)
        ) {
            int updates = statement.executeUpdate();
            if (updates == 0) throw new SPException("Failed to update Activity. No rows affected");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
