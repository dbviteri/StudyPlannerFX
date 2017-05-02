package Controller;

import Utils.SPException;
import Utils.SPProperties;

import java.sql.*;

/**
 * Created by Didac on 29/04/2017.
 */
public abstract class DatabaseHandler {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final String DB_PROPERTY_URL = "url";
    private static final String DB_PROPERTY_DRIVER = "driver";
    private static final String DB_PROPERTY_USERNAME = "username";
    private static final String DB_PROPERTY_PASSWORD = "password";

    // Methods ---------------------------------------------------------------------------------------------------------

    /**
     * Returns an instance of database handler for the given name.
     *
     * @param name Database name it will look for in the properties
     * @return An instance of DatabaseHandler
     * @throws SPException If the database name doesn't exist or properties file is missing.
     */
    public static DatabaseHandler getInstance(String name) throws SPException {
        if (name == null) {
            throw new SPException("Database name is null");
        }

        SPProperties properties = new SPProperties(name);
        String url = properties.getProperty(DB_PROPERTY_URL);
        //String driver = properties.getProperty(DB_PROPERTY_DRIVER);
        String username = properties.getProperty(DB_PROPERTY_USERNAME);
        String password = properties.getProperty(DB_PROPERTY_PASSWORD);

        DatabaseHandler instance;
        /*
        // Load driver
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new SPException("Driver missing from properties", e);
        }
        */
        instance = new DriverManagerSP(url, username, password);

        return instance;
    }

    public static PreparedStatement prepareStatement
            (Connection connection, String sql, boolean returnGeneratedSet, Object... values)
            throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql,
                returnGeneratedSet ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);

        for (int i = 0; i < values.length; i++){
            statement.setObject(i + 1, values[i]);
        }

        return statement;
    }

    /**
     * Returns a connection to database.
     * @return Connection to database.
     * @throws SQLException If the connection fails.
     */
    abstract Connection getConnection() throws SQLException;

    // Getters ---------------------------------------------------------------------------------------------------------

    /**
     * Returns the User Controller associated with the database handler
     * @return
     */
    public UserController getUserController(){
        return new UserController(this);
    }

    public SemesterController getSemesterController() { return new SemesterController(this); }

    //----------------------------------------------- ADD MORE CONTROLLERS HERE ----------------------------------------



}

// Default implementation ----------------------------------------------------------------------------------------------

class DriverManagerSP extends DatabaseHandler {
    private String url;
    private String username;
    private String password;

    DriverManagerSP(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, username, password);
    }
}

