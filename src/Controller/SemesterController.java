package Controller;

import Model.Assessment;
import Model.Module;
import Model.Semester;
import Model.User;
import Utils.ControlledScene;
import Utils.SPException;
import Utils.StageHandler;
import com.sun.xml.internal.bind.v2.TODO;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

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
    private static final String QUERY_INSERT_ASSINGMENT =
            "INSERT INTO Assessment (title,isExam,deadline,weight,module_ID) VALUES (?,?,?,?,?)";

    @FXML Menu userMenu;
    @FXML VBox vBox;

    // Variables -------------------------------------------------------------------------------------------------------

    private DatabaseHandler dbhandler;
    private StageHandler stageHandler;

    // Constructor -----------------------------------------------------------------------------------------------------

    /**
     * Constructs a Semester controller.
     */
    public SemesterController(StageHandler stageHandler){
        dbhandler = DatabaseHandler.getDatabaseHandler();
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
            PreparedStatement statement = dbhandler.prepareStatement(sql, false, properties);
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
        boolean valid = false;
        String line;
        final String separator = ",";
        BufferedReader reader = openFile(file);
        Module aModule;
        Assessment anAsse;

        reader.readLine();
        reader.readLine();

        if (reader != null && file.getName().contains(".csv")) {
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(separator);
                if(data.length == 3) {
                    aModule = new Module(data[0], data[1]);

                }
                else {
                    reader.readLine();
                    anAsse = new Assessment(data[0],Integer.parseInt(data[1]),
                            Integer.parseInt(data[2]), makeDate(data[3]));

                }
            }
        }
            return valid;

    }
    // Helper functions ------------------------------------------------------------------------------------------------

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

    @Override
    public void setParentScene(StageHandler parentScene) {
        stageHandler = parentScene;
    }
}
