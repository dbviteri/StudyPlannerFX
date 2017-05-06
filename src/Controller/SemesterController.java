package Controller;

import Model.Assessment;
import Model.Module;
import Model.SemesterProfile;
import Model.User;
import Utils.ControlledScene;
import Utils.StageHandler;
import com.oracle.javafx.jmx.json.impl.JSONStreamReaderImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import sun.misc.JavaIOAccess;

/**
 *
 * Created by Didac on 01/05/2017.
 */
public class SemesterController implements ControlledScene {

    // Constant queries ------------------------------------------------------------------------------------------------

    //TODO-IMPORTANT: Queries for semester. What data will be stored in the database?

    //? Query to insert a module after reading the file
    //? Query to

    private static final String QUERY_COUNT_SEMESTERS =
            "SELECT *, count *";

    private static final String QUERY_ALL_SEMESTERS =
            "SELECT start_date, end_date FROM SemesterProfile WHERE semester_id = ?";

    private static final String QUERY_USER_SEMESTER =
            "SELECT * from SemesterProfile INNER JOIN User ON SemesterProfile.user_id = User.?";

    private static final String QUERY_USERNAME_EXISTS =
            "SELECT * FROM SemesterProfile WHERE username = ?";
    private static final String QUERY_FIND_BY_USERNAME_PASSWORD =
            "SELECT * FROM User WHERE username = ? AND password = MD5(?)";
    private static final String QUERY_INSERT_USER =
            "INSERT INTO User (email, username, password, firstname, lastname, isStaff) VALUES (?, ?, MD5(?), ?, ?, ?)";
    private static final String QUERY_INSERT_SEMESTER =
            "INSERT INTO Semester_Profile (start_date,end_date,user_id) VALUES (?,?,?)";

    @FXML Menu userMenu;
    @FXML VBox vBox;

    // Variables -------------------------------------------------------------------------------------------------------

    private DatabaseHandler dbhandler;
    private StageHandler stageHandler;
    //private MasterController masterC;

    // Constructor -----------------------------------------------------------------------------------------------------

    /**
     * Constructs a SemesterProfile controller.
     */
    public SemesterController(StageHandler stageHandler){
        dbhandler = DatabaseHandler.getInstance();
        this.stageHandler = stageHandler;
    }

    // Methods ---------------------------------------------------------------------------------------------------------

    /**
     * Allows for post-processing of the FXML components. It is called after the constructor.
     */
    public void initialize() {
        //semesterLabel.setText("test");
        if (dbhandler.getUserSession() == null) return;

        User user = dbhandler.getUserSession();
        userMenu.setText(user.getFirstname());

        stageHandler.getStage().setTitle("Welcome back, " + user.getFirstname() + "!");
        vBox.prefWidthProperty().bind(stageHandler.getStage().widthProperty());
        vBox.prefHeightProperty().bind(stageHandler.getStage().heightProperty().subtract(20));
        System.out.println(user.getEmail());
    }

    @FXML
    public void logOut(){
        //dbhandler.closeConnection();
        dbhandler.deleteSession();
        stageHandler.reloadScene(StageHandler.SCENE.LOGIN);
        stageHandler.setScene(StageHandler.SCENE.LOGIN, false);
    }

    /**
     * Find semesters for a given user
     * @param
     * @return
     */
    /*
    public SemesterProfile find(User user){
        SemesterProfile semester = null;
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
    public SemesterProfile find(String sql, Object... properties){
        SemesterProfile semesterProfile = null;

        try (
            PreparedStatement statement = dbhandler.prepareStatement(sql, false, properties);
            ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) formSemester(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return semesterProfile;
    }
    public static boolean insertSemester(SemesterProfile semester) {

        return false;
    }
    // Load semester file?
    // TODO : add file checking or rely on SQL checks
    // TODO : One function for checking and parsing or TWO separate ones ?
//    public boolean checkFile(File file) throws IOException {
//        boolean valid = false;
//        String line;
//        final String separator = ",";
//        Module aModule;
//        Assessment assessment;
//        BufferedReader reader = openFile(file);
//        SemesterProfile semesterProfile = new SemesterProfile();
//        User user= dbhandler.getUserSession();
//
//        reader.readLine();
//        reader.readLine();
//        JSONObject obj = new JSONObject();
//        if (reader != null && file.getName().contains(".csv")) {
//            while ((line = reader.readLine()) != null) {
//                String[] data = line.split(separator);
//                if(data.length == 2)
//                if(data.length == 2) {
//                    aModule = new Module(data[0], data[1]);
//                    ModuleController.insertModule(aModule);
//                }
//                else {
//                    reader.readLine();
//                    assessment = new Assessment(data[0],data[1],
//                            Integer.parseInt(data[2]), makeDate(data[3]));
//
//                }
//            }
//        }
//            return valid;
//
//    }
    // Helper functions ------------------------------------------------------------------------------------------------
    public boolean parseJson(JSONObject json){
        // Get data for a Semester profile
        Date sem_start = (Date)json.get("start_date");
        Date sem_end = (Date)json.get("end_date");
        int user_id = dbhandler.getUserSession().getId();
        // Create the semester profile
        SemesterProfile semester = new SemesterProfile(sem_start,sem_end);
        // Add it to the db


        return false;
    }
    /** Helper Function takes a string representation of a date
     *  and turns it into Date object (DD,MM,YYYY) format
     *
     * @param date
     * @return date
     */
    private static Date makeDate(String date) {
        DateFormat format = new SimpleDateFormat("DD,MM,YYYY", Locale.UK);
        Date aDate = new Date();
        try {
            aDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return aDate;
    }
    /** Function takes a file and returns a file reader
     *
     * @param file
     * @return BufferedReader
     * @throws IOException
     */
    private static BufferedReader openFile(File file){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return reader;
    }
    private static JSONObject parseFile(File file){
        JSONParser parser = new JSONParser();
        try {
            FileReader rd = new FileReader(file);
            Object obj = parser.parse(rd);
            JSONObject json = (JSONObject)obj;
            return json;
        } catch (org.json.simple.parser.ParseException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    private static SemesterProfile formSemester(ResultSet resultSet) throws SQLException {
        SemesterProfile semesterProfile = new SemesterProfile();

        semesterProfile.setStartDate(resultSet.getDate("start_date"));
        semesterProfile.setEndDate(resultSet.getDate("end_date"));

        return semesterProfile;
    }

    @Override
    public void setParentScene(StageHandler parentScene) {
        stageHandler = parentScene;
    }
}
