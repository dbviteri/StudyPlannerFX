package Tests;

import Controller.DatabaseHandler;
import Controller.UserController;
import Model.SemesterProfile;
import org.junit.Before;
import org.junit.Test;

/**
 * Test
 */
public class DatabaseHandlerTest {
    private DatabaseHandler databaseHandler;

    @Before
    public void setUp() throws Exception {
        databaseHandler = DatabaseHandler.getInstance();
    }

    @Test
    public void getInstance() throws Exception {
        assert databaseHandler != null;
    }

    @Test
    public void getConnection() throws Exception {
        assert databaseHandler.getConnection() != null;
    }

    @Test
    public void closeConnection() throws Exception {
        databaseHandler.closeConnection();
        assert databaseHandler.getConnection() == null;
    }

    @Test
    public void getUserSession() throws Exception {
        UserController userController = new UserController();
        userController.logIn("nxz14epa", "1234");
        assert databaseHandler.getUserSession() != null;
    }

    @Test
    public void getSemesterSession() throws Exception {
        UserController userController = new UserController();
        userController.logIn("nxz14epa", "1234");
        addSemesterToUser();
        assert databaseHandler.getSemesterSession() != null;
    }

    @Test
    public void createSession() throws Exception {
        UserController userController = new UserController();
        userController.logIn("nxz14epa", "1234");
        assert databaseHandler.getUserSession() != null;
    }

    @Test
    public void addSemesterToUser() throws Exception {
        databaseHandler.addSemesterToUser(new SemesterProfile());
        assert databaseHandler.getSemesterSession() != null;
    }

    @Test
    public void deleteSession() throws Exception {
        createSession();
        databaseHandler.deleteSession();
        assert databaseHandler.getUserSession() == null;
    }

}