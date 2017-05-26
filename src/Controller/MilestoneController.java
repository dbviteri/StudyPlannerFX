package Controller;

import Model.Milestone;
import Utils.SPException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * Created by 100125468 on 16/05/2017.
 */
public class MilestoneController {
    private static DatabaseHandler dbhandler = DatabaseHandler.getInstance();
    private final static String QUERY_INSERT_MILESTONE =
            "INSERT INTO Milestone " +
                    "(milestone_title, milestone_progress, milestone_start, milestone_deadline, assessment_id) " +
                    "VALUES (?,?,?,?,?)";
    private final static String QUERY_UPDATE_MILESTONE =
            "UPDATE Milestone SET milestone_title = ?, milestone_progress = ?," +
                    " milestone_start = ?, milestone_deadline = ? WHERE Milestone_id = ?";

    /** Function used to insert
     *  a milestone in the database
     *
     * @param milestone
     * @param assessmentId
     */
    public void insertMilestone(Milestone milestone, int assessmentId) {
        Object[] properties = {
                milestone.getTitle(),
                milestone.getProgress(),
                milestone.getStart(),
                milestone.getDeadline(),
                assessmentId
        };
        try (
                PreparedStatement statement =
                        dbhandler.prepareStatement(QUERY_INSERT_MILESTONE, true, properties)
        ) {
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) throw new SPException("Failed to create new Semester. No rows affected");

            try (ResultSet set = statement.getGeneratedKeys()) {
                if (set.next()) {
                    //semester.setSemesterId(set.getInt(1));
                    milestone.setId(set.getInt(1));
                } else {
                    throw new SPException("Failed to create new Milestone. No key obtained");
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Function used to update
     *  a milestone in the database
     *
     * @param milestone
     * @return true if updated/ false otherwise
     */
    public static boolean updateMilestone(Milestone milestone){
        Object[] properties = {
                milestone.getTitle(),
                milestone.getProgress(),
                milestone.getStart(),
                milestone.getDeadline()
        };
        try (
                PreparedStatement statement =
                        dbhandler.prepareStatement(QUERY_UPDATE_MILESTONE, false, properties)
        ) {
            int updates = statement.executeUpdate();
            if (updates == 0) throw new SPException("Failed to update Milestone. No rows affected");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
