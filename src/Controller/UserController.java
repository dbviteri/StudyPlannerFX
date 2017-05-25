package Controller;

import Model.Assessment;
import Model.Module;
import Model.SemesterProfile;
import Model.User;
import Utils.FileParser;
import Utils.SPException;
import Utils.StageHandler;
import View.AlertDialog;
import javafx.scene.control.Alert;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * Created by Didac on 30/04/2017.
 */
public class UserController implements DBQuery {

    // Constant queries ------------------------------------------------------------------------------------------------

    private static final String QUERY_USERNAME_EXISTS =
            "SELECT username FROM User WHERE username = ?";
    private static final String QUERY_FIND_BY_USERNAME_PASSWORD =
            "SELECT * FROM User WHERE username = ? AND password = MD5(?)";
    private static final String QUERY_INSERT =
            "INSERT INTO User (email, username, password, firstname, lastname, isStaff) VALUES (?, ?, MD5(?), ?, ?, ?)";
    private static  final  String QUERY_FIND_LAST_INSERT_ID = "SELECT * FROM User WHERE username = ?";

    // Variables -------------------------------------------------------------------------------------------------------

    private static DatabaseHandler dbhandler = DatabaseHandler.getInstance();

    // Constructor -----------------------------------------------------------------------------------------------------

    /**
     * Constructs a User controller associated with the database handler
     * @param //dbhandler
     */
    public UserController() {
        //dbhandler = DatabaseHandler.getInstance();
        //System.out.println(dbhandler.getConnection());
    }

    // METHODS FOR QUERIES ---------------------------------------------------------------------------------------------

    @SuppressWarnings("ConstantConditions")
    public void create(User user) {
        /*
        if(user.getId() != null){
            throw new IllegalArgumentException("Already in db");
        }
        */
        Object[] properties = {
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstname(),
                user.getLastname(),
                user.isStaff(),
        };
        try (
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_INSERT, true, properties)
        ) {
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) throw new SPException("Failed to create new user. No rows affected");

            try (ResultSet set = statement.getGeneratedKeys()) {
                if (set.next()) {
                    user.setId(set.getInt(1));
                } else {
                    throw new SPException("Failed to create new user. No key obtained");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbhandler.createSession(user);
    }
    private SemesterProfile parseProfile(File file){
        SemesterProfile profile = FileParser.parseFile(file);
        return profile;
    }

    public void insertProfile(SemesterProfile profile){
        SemesterController semesterController = new SemesterController();
        ModuleController moduleController = new ModuleController();
        AssessmentController assessmentController = new AssessmentController();
        semesterController.insertSemester(profile);
        for (HashMap.Entry entry : profile.getModules().entrySet()) {
            Module module = (Module)entry.getValue();
            moduleController.insertModule(module, profile.getSemesterId());
            for (HashMap.Entry aEntry : module.getAssessments().entrySet()) {
                assessmentController.insertAssessment((Assessment) aEntry.getValue(), module.getId());
            }
        }
    }
    /**
     * Matches a user from a row of a result set
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private User formUser(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("user_id");
        String email = resultSet.getString("email");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        String firstname = resultSet.getString("firstname");
        String lastname = resultSet.getString("lastname");
        boolean isStaff = resultSet.getBoolean("isStaff");

        return new User(id, email, username, password, firstname, lastname, isStaff);
    }

    public User find(String username, String password) {
        return find(QUERY_FIND_BY_USERNAME_PASSWORD, username, password);
    }

    public final boolean userExists(String username) throws SPException {
        try (
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_USERNAME_EXISTS, false, username);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean logIn(StageHandler stageHandler, String username, String password){

        User user = find(username, password);

        // If user is not null, show the main panel
        if(user == null) {
            new AlertDialog(Alert.AlertType.INFORMATION, "User doesn't exist. Try again.");
            return false;
        }
        //databaseHandler.createSession(user);
        DatabaseHandler.getInstance().createSession(user);

        // Reload scene after creating a session
        stageHandler.reloadScene(StageHandler.SCENE.SEMESTER);
        stageHandler.setScene(StageHandler.SCENE.SEMESTER, true, 1024, 768);

        return true;
    }
    // Helper functions ------------------------------------------------------------------------------------------------

    private User find(String sql, Object... properties) throws SPException {
        User user = null;

        try (
                PreparedStatement statement = dbhandler.prepareStatement(sql, false, properties);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) user = formUser(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

}