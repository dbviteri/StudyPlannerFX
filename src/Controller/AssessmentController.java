package Controller;

import Model.Assessment;
import Utils.SPException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
/**
 * Created by 100125468 on 04/05/2017.
 */
public class AssessmentController {

    // QUERIES ---------------------------------------------------------------------------------------------------------

    private static final String QUERY_FIND_ASSESSMENTS =
            //"SELECT * FROM Assessment LEFT JOIN Assessment ON (Assessment.module_code = Module.code) WHERE Module.code = ?";
            "SELECT * FROM Assessment WHERE Assessment.module_id = ?";
    private static final String QUERY_INSERT_ASSESSMENT =
            "INSERT INTO Assessment (title, type, weight, deadline, completion,module_code) VALUES (?,?,?,?,?,?)";
    private static final String QUERY_UPDATE_ASSESSMENT =
            "UPDATE Assessment SET title = ?, type = ?, weight = ?, deadline = ?, completion = ? WHERE assessment_id = ?";
    private static final String QUERY_DELETE_ASSESSMENT =
            "";

    // DATABASE INSTANCE -----------------------------------------------------------------------------------------------

    private static DatabaseHandler dbhandler = DatabaseHandler.getInstance();

    // METHODS ---------------------------------------------------------------------------------------------------------

    public static ArrayList<Assessment> findAll(int moduleId) {
        ArrayList<Assessment> assessments = new ArrayList<>();

        try (
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_FIND_ASSESSMENTS, moduleId);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                assessments.add(formAssessment(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assessments;
    }

    public static boolean insertAssessment(Assessment assessment){
        Object[] properties = {
                assessment.getTitle(),
                assessment.getType().toString(),
                assessment.getWeight(),
                assessment.getDeadLine(),
                assessment.getCompletion(),
                //assessment.getModuleCode()
        };

        try (
                PreparedStatement statement =
                        dbhandler.prepareStatement(QUERY_INSERT_ASSESSMENT, properties)
        ) {
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) throw new SPException("Failed to add Assessmentsd. No rows affected");
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateAssessment(Assessment assessment){
        Object[] properties = {
                assessment.getTitle(),
                assessment.getType().toString(),
                assessment.getWeight(),
                assessment.getDeadLine(), // TODO: CHANGE TO SQL DATE
                assessment.getCompletion(),
        };

        try (
                PreparedStatement statement =
                        dbhandler.prepareStatement(QUERY_UPDATE_ASSESSMENT, properties)
        ) {
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) throw new SPException("Failed to update Assessments. No rows affected");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // HELPER FUNCTIONS ------------------------------------------------------------------------------------------------

    public static Assessment formAssessment(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("assessment_id");
        String title = resultSet.getString("assessment_title");
        Assessment.Type eventType = Assessment.Type.valueOf(resultSet.getString("assessment_type"));
        int weight = resultSet.getInt("weight");
        Date deadline = resultSet.getDate("deadline");
        int completion = resultSet.getInt("completion");

        return new Assessment(id, title, eventType, weight, deadline, completion);
    }
}
