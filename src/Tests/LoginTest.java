package Tests;

import Controller.DatabaseHandler;
import Controller.UserController;
import Model.User;
import View.LoginView;
import org.junit.Test;

/**
 * Created by Didac on 01/05/2017.
 */
public class LoginTest {
    @Test
    public void loginUser() throws Exception {
        LoginView testLoginView = new LoginView();
        testLoginView.directLogIn();
    }
    @Test
    public void addUserToDB(){
        DatabaseHandler dbHanndler = DatabaseHandler.getInstance();
        UserController controller = new UserController();
        User user = new User("xdt","aef","4775","aefaf","aefeaf",false);
        //int userID = UserController.create(user);

        //System.out.println(userID);

    }

}