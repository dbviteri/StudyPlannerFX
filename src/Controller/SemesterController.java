package Controller;

import Model.Assessment;
import Model.Module;
import Model.SemesterProfile;
import Model.User;
import Utils.ControlledScene;
import Utils.SPException;
import Utils.StageHandler;
import com.sun.xml.internal.bind.v2.TODO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
import java.util.Iterator;
import java.util.Locale;

import javax.swing.filechooser.FileNameExtensionFilter;

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
            "SELECT * FROM SemesterProfile INNER JOIN User ON SemesterProfile.user_id = User.?";
    private static final String QUERY_INSERT_SEMESTER =
            "INSERT INTO Semester_Profile (start_date,end_date,user_id) VALUES (?,?,?)";
    private static final String QUERY_GET_SEMESTER_ID =
            "SELECT Semester_ID FROM SemesterProfile WHERE user_id = ? ";


    @FXML Menu userMenu;
    @FXML VBox vBox;

    // Variables -------------------------------------------------------------------------------------------------------

    private static DatabaseHandler dbhandler = DatabaseHandler.getInstance();
    private StageHandler stageHandler;
    //private MasterController masterC;

    // Constructor -----------------------------------------------------------------------------------------------------

    /**
     * Constructs a SemesterProfile controller.
     */
    public SemesterController(StageHandler stageHandler){
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
        Date start = semester.getStartDate();
        Date end = semester.getEndDate();
        int userid = dbhandler.getUserSession().getId();
        try (
                PreparedStatement statement =
                        dbhandler.prepareStatement(QUERY_INSERT_SEMESTER,true,start,end,userid);

        ) {
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) throw new SPException("Failed to add Semester. No rows affected");
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
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
