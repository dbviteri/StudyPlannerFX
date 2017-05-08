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
public class UserController {

    // Constant queries ------------------------------------------------------------------------------------------------

    private static final String QUERY_USERNAME_EXISTS =
            "SELECT username FROM User WHERE username = ?";
    private static final String QUERY_FIND_BY_USERNAME_PASSWORD =
            "SELECT * FROM User WHERE username = ? AND password = MD5(?)";
    private static final String QUERY_INSERT =
            "INSERT INTO User (email, username, password, firstname, lastname, isStaff) VALUES (?, ?, MD5(?), ?, ?, ?)";

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

    public static void create(User user) {
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

    protected final User find(String username, String password) {
        return find(QUERY_FIND_BY_USERNAME_PASSWORD, username, password);
    }

    protected final boolean userExists(String username) throws SPException {
        try (
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_USERNAME_EXISTS, username);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Helper functions ------------------------------------------------------------------------------------------------

    protected final User find(String sql, Object... properties) throws SPException {
        User user = null;

        try (
                PreparedStatement statement = dbhandler.prepareStatement(sql, properties);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) user = formUser(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

}