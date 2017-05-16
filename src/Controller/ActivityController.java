package Controller;

import Model.Activity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Didac on 07/05/2017.
 */
public class ActivityController implements DBQuerry {

    // QUERIES ---------------------------------------------------------------------------------------------------------

    private static final String QUERY_FIND_ACTIVITIES = "SELECT * FROM Activity WHERE task_id = ?";

    // DATABASE INSTANCE -----------------------------------------------------------------------------------------------

    private static DatabaseHandler dbhandler = DatabaseHandler.getInstance();

    // METHODS ---------------------------------------------------------------------------------------------------------

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

    public Activity formActivity(ResultSet resultSet) throws SQLException {
        Integer activityId = resultSet.getInt("activity_ID");
        int quantity = resultSet.getInt("quantity");
        int time = resultSet.getInt("time");
        String title = resultSet.getString("activity_title");
        Date date = resultSet.getDate("date");
        return new Activity(activityId, title, quantity, time,date);
        //return new Activity();
    }
}
