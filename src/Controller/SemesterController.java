package Controller;

import Model.Semester;
import Model.User;
import Utils.ControlledScene;
import Utils.StageHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Controller.DatabaseHandler.prepareStatement;

/**
 *
 * Created by Didac on 01/05/2017.
 */
public class SemesterController {

    // Constant queries ------------------------------------------------------------------------------------------------

    //TODO-IMPORTANT: Queries for semester. What data will be stored in the database?

    //? Query to insert a module after reading the file
    //? Query to

    private static final String QUERY_COUNT_SEMESTERS =
            "SELECT *, count *";

    private static final String QUERY_ALL_SEMESTERS =
            "SELECT start_date, end_date FROM Semester WHERE semester_id = ?";

    private static final String QUERY_USER_SEMESTER =
            "SELECT * from Semester INNER JOIN User ON Semester.user_id = User.?";

    private static final String QUERY_USERNAME_EXISTS =
            "SELECT * FROM Semester WHERE username = ?";

    private static final String QUERY_FIND_BY_USERNAME_PASSWORD =
            "SELECT * FROM User WHERE username = ? AND password = MD5(?)";

    private static final String QUERY_INSERT =
            "INSERT INTO User (email, username, password, firstname, lastname, isStaff) VALUES (?, ?, MD5(?), ?, ?, ?)";


    // Variables -------------------------------------------------------------------------------------------------------

    private DatabaseHandler dbhandler;

    // Constructor -----------------------------------------------------------------------------------------------------

    /**
     * Constructs a Semester controller associated with the database handler
     * @param dbhandler
     */
    SemesterController(DatabaseHandler dbhandler){
        this.dbhandler = dbhandler;
    }

    // Methods ---------------------------------------------------------------------------------------------------------

    /**
     * Find semesters for a given user
     * @param user
     * @return
     */
    /*
    public Semester find(User user){
        Semester semester = null;
        try (
                Connection connection = dbhandler.getConnection();
                PreparedStatement statement = prepareStatement(connection, QUERY_ALL_SEMESTERS,
                        false, properties);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()){
                formSemester(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return semester;
    }
*/
    public Semester find(String sql, Object... properties){
        Semester semester = null;

        try (
            Connection connection = dbhandler.getConnection();
            PreparedStatement statement = prepareStatement(connection, sql, false, properties);
            ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) formSemester(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return semester;
    }

    // Load semester file?

    // Helper functions ------------------------------------------------------------------------------------------------

    private static Semester formSemester(ResultSet resultSet) throws SQLException {
        Semester semester = new Semester();

        semester.setStartDate(resultSet.getString("start_date"));
        semester.setEndDate(resultSet.getString("end_date"));

        return semester;
    }
}
