package Controller;

import Utils.ControlledScene;
import Utils.StageHandler;

/**
 *
 * Created by Didac on 01/05/2017.
 */
public class SemesterController {

    // Constant queries ------------------------------------------------------------------------------------------------

    //TODO-IMPORTANT: Queries for semester. What data will be stored in the database?

    //? Query to insert a module after reading the file
    //? Query to

    private static final String QUERY_USERNAME_EXISTS =
            "SELECT username FROM User WHERE username = ?";

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

    // Load semester file?


}
