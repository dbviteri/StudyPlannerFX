package Controller;

import Model.User;
import Utils.SPException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * Created by Didac on 30/04/2017.
 */
public abstract class UserController{

    // Constant queries ------------------------------------------------------------------------------------------------

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
     * Constructs a User controller associated with the database handler
     * @param //dbhandler
     */
    public UserController() {
        dbhandler = DatabaseHandler.getInstance();
        //System.out.println(dbhandler.getConnection());
    }

    // METHODS FOR QUERIES ---------------------------------------------------------------------------------------------

    protected final User find(String username, String password) throws SPException {
        return find(QUERY_FIND_BY_USERNAME_PASSWORD, username, password);
    }

    protected final boolean userExists(String username) throws SPException {
        try(
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_USERNAME_EXISTS, false, username);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    protected final User find(String sql, Object... properties) throws SPException {
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

    protected final void create(User user){
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // FXML HANDLING ---------------------------------------------------------------------------------------------------

//    @FXML private TextField usernameField;
//    @FXML private PasswordField passwordField;
//    @FXML private Tooltip usernameTooltip;
//    @FXML private ComboBox comboBox;
//
//    @FXML
//    public void loginUser() {
//        //User user = dbhandler.getUserSession();
//        String username = usernameField.getText();//"xdn15mcu";
//        String password = passwordField.getText();//"test";
//        User user = find(username, password);
//
//        // If user is not null, show the main panel
//        if(user == null) {
//            displayTooltip("Username or password is wrong. Please try again.");
//        } else {
//            //databaseHandler.createSession(user);
//
//            // Reload scene after creating a session
//            stageHandler.reloadScene(StageHandler.SCENE.SEMESTER);
//            stageHandler.setScene(StageHandler.SCENE.SEMESTER, true);
//        }
//    }
//
//    private void displayTooltip(String message){
//        usernameField.getStyleClass().add("error");
//        //final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
//        //usernameField.pseudoClassStateChanged(errorClass, true);
//
//        usernameTooltip.setText(message);
//
//        Point2D point2D = usernameField.localToScene(0.0, 0.0);
//        usernameTooltip.setAutoHide(true);
//        usernameTooltip.show(stageHandler.getStage(),
//                point2D.getX() + usernameField.getScene().getX() +
//                        usernameField.getScene().getWindow().getX() + usernameField.getWidth(),
//                point2D.getY() + usernameField.getScene().getY() + usernameField.getScene().getWindow().getY());
//    }
//
//    @FXML
//    private void showRegister() {
//        stageHandler.setScene(StageHandler.SCENE.REGISTER, false);
//    }
//
//    @Override
//    public void setParentScene(StageHandler parentScene) {
//        stageHandler = parentScene;
//    }

    // Helper functions ------------------------------------------------------------------------------------------------

    /**
     * Matches a user from a row of a result set
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private static User formUser(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("user_id");
        String email = resultSet.getString("email");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        String firstname = resultSet.getString("firstname");
        String lastname = resultSet.getString("lastname");
        boolean isStaff = resultSet.getBoolean("isStaff");

        return new User(id, email, username, password, firstname, lastname, isStaff);
    }
}