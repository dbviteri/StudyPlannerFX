package Controller;

import Model.Semester;
import Model.User;
import Utils.ControlledScene;
import Utils.StageHandler;
import com.sun.xml.internal.bind.v2.TODO;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    private static final String QUERY_INSERT_USER =
            "INSERT INTO User (email, username, password, firstname, lastname, isStaff) VALUES (?, ?, MD5(?), ?, ?, ?)";
    private static final String QUERY_INSERT_MODULES =
            "INSERT INTO Module (name,code,Semester_ID) VALUES (?,?,?)";
    private static final String QUERY_INSERT_ASSINGMENT = "";


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
     * @param
     * @return
     */

    public ArrayList<Semester> findAll(User user){
        return findAll(QUERY_ALL_SEMESTERS, user.getId());
    }


    public ArrayList<Semester> findAll(String sql, Object... properties){
        ArrayList<Semester> semesters = new ArrayList<>();
        Semester semester = null;
        try (
                Connection connection = dbhandler.getConnection();
                PreparedStatement statement = prepareStatement(connection, sql,
                        false, properties);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()){
                semesters.add(formSemester(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return semesters;
    }

    public Semester find(String sql, Object... properties){
        Semester semester = null;

        try (
            Connection connection = dbhandler.getConnection();
            PreparedStatement statement = prepareStatement(connection, sql, false, properties);
            ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) formSemester(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return semester;
    }

    // Load semester file?
    // TODO : add file checking or rely on SQL checks
    // TODO : One function for checking and parsing or TWO separate ones ?
    public boolean checkFile(File file) throws IOException {
        boolean valid = true;
        String line;
        final String separator = ",";
        BufferedReader reader = openFile(file);

        line = reader.readLine();
        String[] headers = line.split(separator);
        if(!headers[0].equals("Module Name")) { valid = false; }
        if(!headers[1].equals("Module Code")) { valid = false; }
        if(!headers[2].equals("Assessment Name")) { valid = false; }
        if(!headers[3].equals("Assessment Type")) { valid = false; }
        if(!headers[4].equals("Assessment Weight")) { valid = false; }
        if(!headers[5].equals("Assessment DeadLine")) { valid = false; }
        if(!headers[6].equals("Assessment Name")) { valid = false; }
        if(reader != null){
            while( (line = reader.readLine()) != null){
                String[] data = line.split(separator);
                if(data[0].equals("Module")){

                }
            }
        }
        return valid;
    }

    // Helper functions ------------------------------------------------------------------------------------------------

    /** Function takes a file and returns a file reader
     *
     * @param file
     * @return BufferedReader
     * @throws IOException
     */
    private static BufferedReader openFile(File file) throws IOException{
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return reader;
    }
    private static Semester formSemester(ResultSet resultSet) throws SQLException {
        Semester semester = new Semester();

        semester.setStartDate(resultSet.getString("start_date"));
        semester.setEndDate(resultSet.getString("end_date"));

        return semester;
    }
}
