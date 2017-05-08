package Controller;

import Model.Activity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Didac on 07/05/2017.
 */
public class ActivityController {

    // QUERIES ---------------------------------------------------------------------------------------------------------

    private static String QUERY_FIND_ACTIVITIES = "";

    // DATABASE INSTANCE -----------------------------------------------------------------------------------------------

    private static DatabaseHandler dbhandler = DatabaseHandler.getInstance();

    // METHODS ---------------------------------------------------------------------------------------------------------

    public ArrayList<Activity> activities(int taskId) {
        ArrayList<Activity> assessments = new ArrayList<>();

        try (
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_FIND_ACTIVITIES, taskId);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                assessments.add(formActivity(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assessments;
    }

    private Activity formActivity(ResultSet resultSet) {
        return null;
        //return new Activity();
    }
}
