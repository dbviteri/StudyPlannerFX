package Controller;

import Model.User;
import Utils.SPException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Controller.DatabaseHandler.prepareStatement;

/**
 *
 * Created by Didac on 30/04/2017.
 */
public class UserController {

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
     * @param dbhandler
     */
    UserController(DatabaseHandler dbhandler){
        this.dbhandler = dbhandler;
    }

    public UserController(){}

    public DatabaseHandler getDbhandler() {return dbhandler;}

    // Methods ---------------------------------------------------------------------------------------------------------

    public User find(String username, String password) throws SPException {
        return find(QUERY_FIND_BY_USERNAME_PASSWORD, username, password);
    }

    public boolean userExists(String username) throws SPException {
        try(
                Connection connection = dbhandler.getConnection();
                PreparedStatement statement = prepareStatement(connection, QUERY_USERNAME_EXISTS, false, username);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public User find(String sql, Object... properties) throws SPException {
        User user = null;

        try (
            Connection connection = dbhandler.getConnection();
            PreparedStatement statement = prepareStatement(connection, sql, false, properties);
            ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) user = formUser(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void create(User user){
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
                Connection connection = dbhandler.getConnection();
                PreparedStatement statement = prepareStatement(connection, QUERY_INSERT, true, properties)
        ) {
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) throw new SPException("Failed to create new user. No rows affected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper functions ------------------------------------------------------------------------------------------------

    /**
     * Matches a user from a row of a result set
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private static User formUser(ResultSet resultSet) throws SQLException{
        User user = new User();
        user.setId(resultSet.getInt("user_id"));
        user.setEmail(resultSet.getString("email"));
        user.setUsername(resultSet.getString("username"));
        user.setFirstname(resultSet.getString("firstname"));
        user.setLastname(resultSet.getString("lastname"));
        user.setStaff(resultSet.getBoolean("isStaff"));
        return user;
    }
}
